import jwtDecode from "jwt-decode";

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