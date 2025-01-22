package com.e_garden.api.security;

import com.e_garden.api.user.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {
    private final String secretKey;

    /**
     * Le constructeur initialise le secretKet permettant de chiffrer le mot de passe.
     */
    public JWTService() {
        this.secretKey = System.getenv("JWT_SECRET");
        /*
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        */
    }

    /**
     * Méthode qui permet de générer le JWT Token
     *
     * @param email de l'utilisateur
     * @param role  de l'utilisateur
     * @return le token en String
     */
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 18)) // 158 heures
                .and()
                .signWith(getKey())
                .compact();
    }

    /**
     * Méthode retournant la Key pour signer le token, elle utilise le secret.
     *
     * @return un objet SecretKey
     */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Retourne l'email d'un Token utilisateur
     *
     * @param token en texte
     * @return l'email de l'utilisateur
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Retourne le role d'un Token utilisateur
     *
     * @param token de l'utilisateur en texte
     * @return le role de l'utilisateur
     */
    public String extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Méthode permettant de retourner les informations d'un Token
     *
     * @param token         de l'utilisateur en texte
     * @param claimResolver le Résolver
     * @param <T>
     * @return Un objet de Typ Claims
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Méthode permettant d'extraire les informations d'un Token
     *
     * @param token de l'utilisateur en texte
     * @return un object Claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Méthode qui vérifié que l'utilisateur correspond bien à l'utilisateur dans le Token et qu'il n'est pas expiré.
     *
     * @param token       de l'utilisateur en texte
     * @param userDetails utilisateur courant
     * @return un boolean true si le token est valide, false sinon
     */
    public boolean validateToken(String token, UserPrincipal userDetails) {
        final String userName = extractEmail(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Méthode qui contrôle la date d'expiration du Token par rapport à la date du jour.
     *
     * @param token de l'utilisateur en texte
     * @return true si valide, false sinon
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Méthode qui retourne la date d'expiration dans un Token.
     *
     * @param token de l'utilisateur en texte
     * @return la date d'expiration
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
