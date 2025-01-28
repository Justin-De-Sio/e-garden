# Application Nuxt JS
L'application est développé avec la technologie Nuxt.js.
Le projet intègre des composants de NuxtUI.
Look at the [Nuxt documentation](https://nuxt.com/docs/getting-started/introduction) to learn more.

## Prérequis
Vous devez avoir un NodeJS d'installer, avec la version la plus récente.

## API
Pour fonctionner l'application Web a besoin de données.
Le front réalise des appels à une API à une adresse précise.

## Fichier de configuration
Un fichier de configuration `.env` doit être présent à la racine du projet front-end.
Dans ce fichier de configuration, on doit retrouver les informations suivantes :
- API_URL=http://localhost:8080

## Sécurité
Le projet intègre de la sécurité.
Trois pages ne nécessitent pas de sécurité, login, 404, bienvenue.
Autrement les pages ne fonctionnent qu'en présence d'un Token utilisateur valide.
Le Token est utilisé pour faire des demandes de données auprès du back-end.
Si le Token est invalide, une erreur sera retourné.

## Lancement
Pour lancer le projet, il faut télécharger le projet depuis le dépot Github.
Répondre aux prérequis.
En ligne de commande se déplacer dans le dossier `front-end`.
Lancer la commande `npm install`.
S'il n'y a pas d'erreur, lancer `npm run dev`.
Si tout a bien fonctionné, alors le projet sera disponible à l'adresse suivante : http://localhost:3000
Check out the [deployment documentation](https://nuxt.com/docs/getting-started/deployment) for more information.

## Utilisation
Des fonctions ont été définis et des composants sont disponibles.
Nous recommandons d'utiliser les éléments déjà définis pour continuer le projet.