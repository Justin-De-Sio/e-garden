import {getTokenObject, resetToken} from "~/services/SessionServices.ts";

export default defineNuxtRouteMiddleware((to, from) => {
  const token = getTokenObject();

  if (token) {
    try {
      // Décoder le token pour vérifier son expiration
      const exp = token.exp;
      const currentTime = Math.floor(Date.now() / 1000);

      if (exp && exp > currentTime) {
        return navigateTo('/wire-report'); // Redirige si le token est valide
      } else {
        console.error('Token expiré');
        resetToken();
      }
    } catch (error) {
      console.error('Erreur lors du décryptage du token:', error);
      resetToken();
    }
  }
  // Laisser l'utilisateur sur la page de login s'il n'a pas de token ou si le token est invalide
});
