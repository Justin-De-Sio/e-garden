package com.e_garden.api.user;

/**
 * Enum pour les trois rôles du projet PFE,
 * l'utilisateur correspond à un étudiant qui va au potager,
 * le responsable correspond à un responsable d'Association, qui peut ajouter des participants, et accéder à certain contenu
 * l'administrateur a accès à toutes les fonctionnalités.
 */
public enum Roles {
    /**
     * Utilisateur roles.
     */
    UTILISATEUR,
    /**
     * Responsable roles.
     */
    RESPONSABLE,
    /**
     * Administrateur roles.
     */
    ADMINISTRATEUR
}
