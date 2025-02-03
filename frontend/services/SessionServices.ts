import jwtDecode from "jwt-decode";
import type {JWToken} from "~/model/JWToken";

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

export function resetToken():void {
    console.log("Reset token");
    const sessionCookie = useCookie('session');
    sessionCookie.value = null;
}

export function saveToken(token:string):void {
    const sessionCookie = useCookie('session');
    sessionCookie.value = token;
}