#!/bin/bash

# Arrêter l'exécution en cas d'erreur
set -e

# Déterminer le répertoire du script
SCRIPT_DIR="$(dirname "$(realpath "$0")")"

# Arrêter les processus existants utilisant les ports 8080 et 3000
echo "Arrêt des processus sur les ports 8080 et 3000..."
fuser -k 8080/tcp || true
fuser -k 3000/tcp || true

# Naviguer vers le répertoire du projet et récupérer les dernières modifications
echo "Mise à jour du projet depuis le dépôt Git..."
cd "$SCRIPT_DIR"
git pull origin main

# Construire et démarrer le frontend
echo "Construction et démarrage du frontend..."
cd "$SCRIPT_DIR/frontend"
npm ci
npm run build
nohup node .output/server/index.mjs > "$SCRIPT_DIR/logs/frontend.log" 2>&1 &

# Construire et démarrer l'API
echo "Construction et démarrage de l'API..."
cd "$SCRIPT_DIR/api"
export $(grep -v '^#' "$SCRIPT_DIR/api/.env" | xargs)
mvn clean install
nohup java -jar target/api-0.0.1-SNAPSHOT.jar > "$SCRIPT_DIR/logs/api.log" 2>&1 &
