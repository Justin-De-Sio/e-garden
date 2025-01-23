
import jwt from 'jsonwebtoken';


export default function ({ route }) {

  const token = useCookie('session').value; 

  if (!token) {
    return navigateTo('/login');  
  }

  try {
    const decoded = jwt.decode(token);
    console.log(decoded)

    // Vérifier si le token est expiré
    const currentTime = Math.floor(Date.now() / 1000); 
    if (decoded.exp && decoded.exp < currentTime) {
      return navigateTo('/login');  
    }

    // Vérifier les rôles si nécessaires
    const requiredRoles = route.meta.roles || [];
    const userRole = decoded.role;

    if (requiredRoles.length && !requiredRoles.includes(userRole)) {
      return navigateTo('/unauthorized');  
    }
  } catch (err) {
    console.error("Invalid token:", err);
    return navigateTo('/login');  
  }
}
