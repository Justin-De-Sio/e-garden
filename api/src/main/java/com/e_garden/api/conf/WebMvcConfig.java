package com.e_garden.api.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Le type Web mvc config.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Méthode permettant de configurer la racine de la root de l'API.
     * Elle ajoute le "/api" pour tous les appels.
     *
     * @param configurer injection de l'objet PathMatchConfigurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", c -> c.isAnnotationPresent(RestController.class));
    }

}
