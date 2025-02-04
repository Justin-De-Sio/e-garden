package com.e_garden.api.security;

import com.e_garden.api.user.MyUserDetailsService;
import com.e_garden.api.user.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Le type Jwt filter.
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final ApplicationContext applicationContext;

    /**
     * Instancie un nouveau Jwt filter.
     *
     * @param jwtService         le jwt service
     * @param applicationContext l'application context
     */
    @Autowired
    public JWTFilter(JWTService jwtService, ApplicationContext applicationContext) {
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
    }

    /**
     * Méthode filtrant les requêtes avec un Token utilisateur.
     *
     * @param request     la requête.
     * @param response    injection de l'objet HttpServletResponse
     * @param filterChain injection de l'objet FilterChain
     * @throws ServletException Exception
     * @throws IOException      Exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // vérifie que le Bearer token est présent.
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtService.extractEmail(token);
        }

        // vérifie que l'email présent dans le Token n'est pas null
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserPrincipal userDetails = (UserPrincipal) applicationContext.getBean(MyUserDetailsService.class).loadUserByUsername(email);
            // vérifie la validité du Token avec les informations de l'utilisateur
            if (jwtService.validateToken(token, userDetails) && userDetails.isEnabled() && userDetails.isAccountNonLocked()) {
                String role = jwtService.extractRoles(token);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null, Collections.singleton(new SimpleGrantedAuthority(role)));
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                // Enregistre les informations d'authentification dans SecurityContextHolder.
            }
        }
        filterChain.doFilter(request, response);
    }
}
