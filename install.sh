#!/bin/bash
# Ce script installe toutes les dépendances nécessaires sur une installation Ubuntu de base,
# en installant Java 23, Node.js 22, Maven, et quelques paquets essentiels.

# Arrêter l'exécution en cas d'erreur
set -e

echo "Mise à jour de la liste des paquets..."
sudo apt-get update

echo "Installation des paquets essentiels : git, curl, wget, build-essential, psmisc..."
sudo apt-get install -y git curl wget build-essential psmisc

echo "Installation de Maven..."
sudo apt-get install -y maven

echo "Installation de ffmpeg..."
sudo apt-get install -y ffmpeg

echo "Installation d'OpenJDK 23..."
sudo apt-get install -y openjdk-23-jdk

echo "Installation de Node.js 22..."
# Utilisation du script officiel de NodeSource pour installer Node.js version 22
curl -fsSL https://deb.nodesource.com/setup_22.x | sudo -E bash -
sudo apt-get install -y nodejs

echo "Toutes les dépendances ont été installées avec succès."
