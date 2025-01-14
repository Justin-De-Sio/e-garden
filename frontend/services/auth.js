import axios from 'axios';

export async function login(email, password, $cookies) {
  try {
    // Envoi de la requête à l'API
    const response = await axios.post(
        'http://localhost:8080/api/user/login',
        {
        email,
        password,
        },
        {
        headers: {
            'Content-Type': 'application/json', // Indique que le contenu est en JSON
        },
        }
    );
  

    console.log('Réponse API :', response);

    const token = response.data;

    //const token = await response.text();

    // Utiliser useCookie pour gérer les cookies
    const jwtCookie = useCookie('jwt');
    jwtCookie.value = token; 

    // Vérifie si un token est reçu
    if (!token) {
        throw new Error('Aucun token reçu. Vérifiez votre backend.');
        }

    /*
    // Stockage du token dans les cookies
    $cookies.set('token', token, {
      httpOnly: false, 
    });
        */

  } catch (error) {
    console.error('Erreur lors de la connexion :', error);
  }
}
