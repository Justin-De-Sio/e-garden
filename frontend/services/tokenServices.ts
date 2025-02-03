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
    let sessionCookie;
    try {
        sessionCookie = useCookie('session'); // Récupérer le cookie
        const token = sessionCookie.value;
        return token ? jwtDecode(token) : null;
    } catch (error) {
        sessionCookie = useCookie('session'); // Récupérer le cookie
        // warining TODO
        return null;
    }
}

export function resetToken():void {
    const sessionCookie = useCookie('session');
    sessionCookie.value = null;
}

export function JWTPayload(token: string) {
    const split_token = token.split(".");
    return JSON.parse(atob(split_token[1]))
}