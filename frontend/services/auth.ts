import {saveToken} from "~/services/SessionServices.js";

/**
 * Fonction qui permet de réaliser la demande de connexion pour le login utilisateur.
 * Elle prend en paramètre un email et un mot de passe.
 * La méthode enregistre le Token utilisateur dans une session si la demande est valide, sinon elle n'enregistre rien.
 * @param email_var au format string
 * @param password_var au format string
 * @returns {Promise<string>} retourne un string avec un token si valide ou un message d'erreur.
 */
export async function login(email_var, password_var) {
  try {
    const response = await fetch('/api/user/login', 
      {
      method: 'POST',
      headers:{
        "Content-type": "application/json"
      },
      body: JSON.stringify({email: email_var, password: password_var})
    });

    if (!response.ok) {
      const errorMessage = await response.text();
      console.error(errorMessage || 'Erreur de connexion.');
    }
    // Extraire le token
    const token = await response.text();
    if (!token) {
      console.error('Aucun token reçu.');
    }
    saveToken(token);
    return token;
  } catch (error) {
    console.error('Erreur lors de la connexion :', error);
    throw error;
  }
}
