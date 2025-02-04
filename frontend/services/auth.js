
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
      throw new Error(errorMessage || 'Erreur de connexion.');
    }
    // Extraire le token
    const token = await response.text();
    console.log("Token reçu :", token);

    if (!token) {
      throw new Error('Aucun token reçu. Vérifiez votre backend.');
    }

    // Stocker le token dans un cookie
    const jwtCookie = useCookie('session');
    jwtCookie.value = token;

    return token;
  } catch (error) {
    console.error('Erreur lors de la connexion :', error);
    throw error;
  }
}
