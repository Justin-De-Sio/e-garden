
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

    const token = await response.text();

    if (!token) {
      throw new Error('Aucun token reçu. Vérifiez votre backend.');
    }

    const jwtCookie = useCookie('session');
    jwtCookie.value = token
    alert('Connexion réussie !');
  } catch (error) {
    console.error('Erreur lors de la connexion :', error);
    throw error;
  }
}
