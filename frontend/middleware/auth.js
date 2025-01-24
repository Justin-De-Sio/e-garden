import { JWTPayload } from "~/services/jwtpayload";

export default defineNuxtRouteMiddleware(async (to, from) => {
  const jwtCookie = useCookie('session'); 
  const token = jwtCookie.value;


  if (to.path === '/login') {
    return;
  }

  // Si le token n'existe pas, rediriger vers login
  if (!token) {
    return navigateTo('/login');
  }


  let token_payload;
  try {
    token_payload = JWTPayload(token);
  } catch (error) {
    console.error("Erreur de décryptage du token:", error);
    jwtCookie.value = null;
    return navigateTo('/login');
  }

  // Vérifier si le token est expiré
  const currentTime = Math.floor(Date.now() / 1000);
  if (token_payload.exp && token_payload.exp < currentTime) {
    console.warn("Token expiré");
    jwtCookie.value = null; 
    return navigateTo('/login');
  }

  // Vérifier les rôles si définis
  if (Array.isArray(to.meta.role) && !to.meta.role.includes(token_payload.role)) {
    return navigateTo('/unauthorized'); 
  }
});
