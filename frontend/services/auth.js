import axios from 'axios';

export async function login(email, password, $cookies) {
  try {
    // Envoi de la requête à l'API
    const response = await axios.post('http://localhost:8080/api/user/login', {
      email,
      password,
    });

    console.log('Réponse API :', response);

    const token = response.data;

    // Vérifie si un token est reçu
    if (!token) {
        throw new Error('Aucun token reçu. Vérifiez votre backend.');
        }

    // Stockage du token dans les cookies
    $cookies.set('token', token, {
      httpOnly: false, 
    });


  } catch (error) {
    console.error('Erreur lors de la connexion :', error);
  }
}
