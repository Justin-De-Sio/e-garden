package com.e_garden.api.Conf;

import com.e_garden.api.Exception.CustomAccessDeniedHandler;
import com.e_garden.api.Security.JWTFilter;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConf {

    private final UserDetailsService userService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConf(UserDetailsService userService, JWTFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;

    }

    /**
     * Toutes les requêtes recues par l'API passe par cette fonction, l'appel est automatiquement géré par Spring Boot.
     * La fonction requestMatchers() permet d'exclure le contrôle de sécurité.
     * Il faut ajouter uniquement les contrôles nécessite aucune vérification.
     *
     * @param httpSecurity la requête
     * @return HttpSecurity
     * @throws Exception une exception est retourné en cas d'accès refusé
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        String url = "/api/";
        return httpSecurity
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(url + "user/login", url + "csrf-token", "/swagger-ui.html", "/api-docs").permitAll()
                        // .anyRequest().permitAll())
                        .anyRequest().authenticated())
                //.httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandlers()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandlers() {
        return new CustomAccessDeniedHandler();
    }

    /**
     * Fonction qui permet de vérifier l'authentification avec un email et un password.
     * Elle définit l'encodeur qui permettra de hacher le password et de le comparer avec le password de la DB.
     *
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
