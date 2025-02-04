import type {JWToken} from "~/model/JWToken";
import jwtDecode from "jwt-decode";

/**
 * Fonction pour récupérer le token de l'utilisateur.
 * Le token contient plusieurs informations :
 * - le "role" de l'utilisateur ;
 * - le "sub", email, de l'utilisateur ;
 * - le "iat", date de création, du token :
 * - le "exp", date d'expiration, du token.
 * @returns {unknown}
 */
export function getTokenObject():JWToken | null {
    try {
        const token = getTokenString();
        return token ? jwtDecode(token) : null;
    } catch (error) {
        console.error("Impossible de lire la session");
        resetToken();
        return null;
    }
}

/**
 * Methode qui retourne le JWToken en format string de l'utilisateur.
 */
export function getTokenString(): string {
    let sessionCookie;
    try {
        sessionCookie = (useCookie('session')); // Récupérer le cookie
        return sessionCookie.value as string;
    } catch (error) {
        console.error("Session introuvable");
        resetToken();
        return "";
    }
}

/**
 * Méthode qui efface le Token courant de l'utilisateur.
 */
export function resetToken():void {
    console.log("Reset token");
    const sessionCookie = useCookie('session');
    sessionCookie.value = null;
}

/**
 * Méthode qui permet d'enregistrer le JWToken de l'utilisateur.
 * @param token
 */
export function saveToken(token:string):void {
    const sessionCookie = useCookie('session');
    sessionCookie.value = token;
}