#!/bin/bash
# Ce script installe Nginx, Certbot et son plugin pour Nginx sur Ubuntu,
# configure Nginx et génère automatiquement les certificats SSL pour le domaine e-garden.xyz.
#
# Avant de lancer ce script, assurez-vous que :
#   - Le domaine e-garden.xyz pointe vers ce serveur.
#   - Vous avez bien configuré vos règles de pare-feu pour autoriser les ports 80 et 443.
#
# Modifiez la variable EMAIL ci-dessous avec votre adresse email.
#
# Pour utiliser ce script :
#   1. Enregistrez-le sous un fichier (par exemple install_nginx_certbot.sh).
#   2. Rendez-le exécutable : chmod +x install_nginx_certbot.sh
#   3. Exécutez-le : ./install_nginx_certbot.sh

set -e

# Paramètres à modifier selon vos besoins
EMAIL="admin@e-garden.xyz"    # Remplacez par votre adresse email
DOMAIN="e-garden.xyz"         # Domaine pour lequel générer le certificat

echo "Mise à jour de la liste des paquets..."
sudo apt update

echo "Installation de Nginx..."
sudo apt install -y nginx

echo "Installation de Certbot et du plugin Certbot pour Nginx..."
sudo apt install -y certbot python3-certbot-nginx

echo "Sauvegarde de la configuration Nginx actuelle (si présente)..."
if [ -f /etc/nginx/sites-enabled/default ]; then
    sudo cp /etc/nginx/sites-enabled/default /etc/nginx/sites-enabled/default.bak
    echo "Configuration sauvegardée dans /etc/nginx/sites-enabled/default.bak"
fi

echo "Création de la nouvelle configuration Nginx dans /etc/nginx/sites-enabled/default..."
# On écrase la configuration existante avec la configuration souhaitée.
sudo tee /etc/nginx/sites-enabled/default > /dev/null <<'EOF'
##
# Exemple de configuration Nginx pour e-garden.xyz.
##

# Bloc par défaut pour servir les requêtes non spécifiées
server {
    listen 80 default_server;
    listen [::]:80 default_server;

    root /var/www/html;
    index index.html index.htm index.nginx-debian.html;

    server_name _;

    location / {
        proxy_pass http://localhost:3000; # Nuxt écoute sur le port 3000
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }
}

# Bloc pour rediriger le domaine e-garden.xyz vers HTTPS
server {
    listen 80;
    listen [::]:80;
    server_name e-garden.xyz;
    return 301 https://$host$request_uri;
}

# Bloc SSL pour e-garden.xyz (les directives SSL seront gérées par Certbot)
server {
    listen [::]:443 ssl ipv6only=on; # managed by Certbot
    listen 443 ssl;                 # managed by Certbot
    server_name e-garden.xyz;       # managed by Certbot

    root /var/www/html;
    index index.html index.htm index.nginx-debian.html;

    location / {
        proxy_pass http://localhost:3000; # Nuxt écoute sur le port 3000
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }

    ssl_certificate /etc/letsencrypt/live/e-garden.xyz/fullchain.pem;  # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/e-garden.xyz/privkey.pem;  # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf;                    # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;                      # managed by Certbot
}

# Bloc complémentaire pour gérer d'éventuelles requêtes non redirigées (selon Certbot)
server {
    if ($host = e-garden.xyz) {
        return 301 https://$host$request_uri;
    } # managed by Certbot

    listen 80;
    listen [::]:80;
    server_name e-garden.xyz;
    return 404; # managed by Certbot
}
EOF

echo "Test de la configuration Nginx..."
sudo nginx -t

echo "Redémarrage de Nginx..."
sudo systemctl reload nginx

echo "Génération du certificat SSL pour le domaine ${DOMAIN} avec Certbot..."
sudo certbot --nginx --non-interactive --agree-tos --email "${EMAIL}" -d "${DOMAIN}"

echo "Certificat généré et configuration SSL mise à jour par Certbot."
echo "Script terminé avec succès."
