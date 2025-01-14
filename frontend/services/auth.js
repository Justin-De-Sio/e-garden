import axios from 'axios';

export async function login(email, password, $cookies) {
  try {
    const response = await axios.post('http://localhost:8080/api/user/login', {
      email,
      password,
    });

    const token = response.data;

    if (!token) {
      throw new Error('Aucun token reçu. Vérifiez votre backend.');
    }

    // Stocker le token dans les cookies
    $cookies.set('jwt', token, {
      httpOnly: false, // Le cookie peut être lu côté client
      secure: process.env.NODE_ENV === 'production', // HTTPS uniquement en production
      maxAge: 60 * 60 * 24, // Durée de vie en secondes (24 heures)
    });

    return token; // Retourne le token pour un usage éventuel
  } catch (error) {
    console.error('Erreur lors de la connexion :', error);
    throw error;
  }
}
