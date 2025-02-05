package com.e_garden.api.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Le type Custom access denied handler.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Fonction permettant de gérer manuellement les erreurs d'appel.
     * Elle retourne un texte personnalisé dans la Response de l'appel.
     *
     * @param request               la requête
     * @param response              la réponse
     * @param accessDeniedException Injection de l'objet AccessDeniedException
     * @throws IOException      l'exception levée avec le message personnalisée
     */
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Accès refusé : Vous n'avez pas les autorisations requises.");
    }

}