export function JWTPayload(token){
    const split_token = token.split(".");
    const TokenPayload = JSON.parse(atob(split_token[1]));

    console.log("Payload : ", TokenPayload.role);

}