package com.e_garden.api.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Le type Security controller.
 */
@RestController
@RequestMapping
@CrossOrigin
public class SecurityController {

    /**
     * Controller permettant de récupérer une csrf-token pour un formulaire.
     *
     * @param request injection de HttpServletRequest
     * @return retourne un object contenant le token
     */
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
