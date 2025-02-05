package com.e_garden.api.conf;

import com.e_garden.api.exception.CustomAccessDeniedHandler;
import com.e_garden.api.security.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Le type Security conf.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConf {

    private final UserDetailsService userService;
    private final JWTFilter jwtFilter;

    /**
     * Instancie un nouveau Security conf.
     *
     * @param userService le user service
     * @param jwtFilter   le jwt filter
     */
    @Autowired
    public SecurityConf(UserDetailsService userService, JWTFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;

    }

    /**
     * Toutes les requêtes recues par l'API passe par cette fonction, l'appel est automatiquement géré par Spring Boot.
     * La fonction requestMatchers() permet d'exclure le contrôle de sécurité.
     * Il faut ajouter uniquement les routes nécessitant aucune vérification.
     *
     * @param httpSecurity la requête
     * @return HttpSecurity security filter chain
     * @throws Exception une exception est retourné en cas d'accès refusé
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        String url = "/api/";
        return httpSecurity
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(url + "user/login", url + "csrf-token", "/swagger-ui.html", "/api-docs").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandlers()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Custom access denied handlers access denied handler.
     *
     * @return l'access denied handler
     */
    @Bean
    public AccessDeniedHandler customAccessDeniedHandlers() {
        return new CustomAccessDeniedHandler();
    }

    /**
     * Fonction qui permet de vérifier l'authentification avec un email et un password.
     * Elle définit l'encodeur qui permettra de hacher le password et de le comparer avec le password de la DB.
     *
     * @return AuthenticationProvider authentication provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userService);
        return provider;
    }

    /**
     * Fonction qui permet de définir des informations sur l'aspect Rôle et autorité de l'application.
     * La méthode précise que le rôle ne contient pas de préfixe, par défaut les rôles débutent par "ROLE_".
     *
     * @return un Objet GrantedAuthorityDefaults
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Supprime le préfixe "ROLE_"
    }

    /**
     * Authentication manager.
     *
     * @param configuration le configuration
     * @return le authentication manager
     * @throws Exception l'exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
