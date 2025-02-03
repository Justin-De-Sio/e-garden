export function JWTPayload(token){
    const split_token = token.split(".");
    return JSON.parse(atob(split_token[1]))
}