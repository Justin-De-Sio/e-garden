export interface JWToken {
    exp: number,
    iat: number,
    role: string,
    sub: string,
}