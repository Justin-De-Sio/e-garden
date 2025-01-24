
import {JWTPayload} from "~/services/jwtpayload"
export default defineNuxtRouteMiddleware( async (to, from) => {
  const token = useCookie('session').value;
  const router = useRouter();

  if (!token) {
    return navigateTo('/login'); 
  }


  const token_payload = JWTPayload(token);

  const currentTime = Math.floor(Date.now() / 1000);
  if (token_payload.exp && token_payload.exp < currentTime) {
      document.cookie = "session=;";
      return navigateTo("/login"); 
  }

  if (Array.isArray(to.meta.role) && !to.meta.role.includes(token_payload.role)) {
      return navigateTo('/unauthorized'); 
  }

});
