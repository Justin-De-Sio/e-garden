import {getTokenObject, JWTPayload} from "~/services/tokenServices.ts";

export default defineNuxtRouteMiddleware(async (to, from) => {
  const token = getTokenObject();

  if (to.path === '/login') {
    return;
  }

  // Si le token n'existe pas, rediriger vers login
  if (!token) {
    return navigateTo(`/login?redirect=${encodeURIComponent(to.fullPath)}`);
  }

  let token_payload;
  try {
    token_payload = JWTPayload(token);
  } catch (error) {
    console.error("Erreur de décryptage du token:", error);
    jwtCookie.value = null;
    return navigateTo(`/login?redirect=${encodeURIComponent(to.fullPath)}`);
  }

  // Vérifier si le token est expiré
  const currentTime = Math.floor(Date.now() / 1000);
  if (token_payload.exp && token_payload.exp < currentTime) {
    console.warn("Token expiré");
    jwtCookie.value = null; 
    return navigateTo(`/login?redirect=${encodeURIComponent(to.fullPath)}`);
  }

  // Vérifier les rôles si définis
  if (Array.isArray(to.meta.role) && !to.meta.role.includes(token_payload.role)) {
    return navigateTo('/unauthorized'); 
  }
});
