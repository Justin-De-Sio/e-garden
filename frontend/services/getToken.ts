import jwtDecode from "jwt-decode";

/**
 * Fonction pour récupérer le token de l'utilisateur.
 * Le token contient plusieurs informations :
 * - le "role" de l'utilisateur ;
 * - le "sub", email, de l'utilisateur ;
 * - le "iat", date de création, du token :
 * - le "exp", date d'expiration, du token.
 * @returns {object Token}
 */
export function getToken() {
    let sessionCookie;
    try {
        sessionCookie = (useCookie('session')); // Récupérer le cookie
        const token = sessionCookie.value;
        return token ? jwtDecode(token) : "";
    } catch (error) {
        return "";
    }
}