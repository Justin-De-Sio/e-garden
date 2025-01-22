

export default defineNuxtRouteMiddleware((to, from) => {
  const token = useCookie('session').value;

  if (!token) {
    return navigateTo('/login'); 
  }
});
