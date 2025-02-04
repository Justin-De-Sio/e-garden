import {getTokenObject, resetToken} from "~/services/SessionServices.ts";

export default defineNuxtRouteMiddleware(async (to, from) => {
  const token = getTokenObject();

  if (to.path === '/login') {
    return;
  }

  // Si le token n'existe pas, rediriger vers login
  if (!token) {
    return navigateTo(`/login?redirect=${encodeURIComponent(to.fullPath)}`);
  }

  // Vérifier si le token est expiré
  const currentTime = Math.floor(Date.now() / 1000);
  if (token.exp && token.exp < currentTime) {
    console.warn("Token expiré");
    resetToken();
    return navigateTo(`/login?redirect=${encodeURIComponent(to.fullPath)}`);
  }

  // Vérifier les rôles si définis
  if (Array.isArray(to.meta.role) && !to.meta.role.includes(token.role)) {
    return navigateTo('/unauthorized'); 
  }
});
