export default defineNuxtRouteMiddleware((to, from) => {
  const jwtCookie = useCookie('session'); // Récupérer le cookie
  const token = jwtCookie.value;

  if (token) {
    try {
      // Décoder le token pour vérifier son expiration
      const { exp } = JWTPayload(token);
      const currentTime = Math.floor(Date.now() / 1000);

      if (exp && exp > currentTime) {
        return navigateTo('/wire-report.vue'); // Redirige si le token est valide
      } else {
        console.warn('Token expiré');
        jwtCookie.value = null; // Supprimer le cookie si expiré
      }
    } catch (error) {
      console.error('Erreur lors du décryptage du token:', error);
      jwtCookie.value = null; // Supprimer le cookie si invalide
    }
  }

  // Laisser l'utilisateur sur la page de login s'il n'a pas de token ou si le token est invalide
});
