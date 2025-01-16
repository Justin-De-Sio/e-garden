package com.e_garden.api.Security;

import com.e_garden.api.Exception.CustomAccessDeniedHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "*")
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConf {

    private final UserDetailsService userService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConf(UserDetailsService userService, JWTFilter jwtFilter){
        this.userService = userService;
        this.jwtFilter = jwtFilter;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        String url = "/api/";
        return httpSecurity
                .csrf(customizer -> customizer.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(url+"user/login", url+"csrf-token", "/swagger-ui.html", "/api-docs").permitAll()
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

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider ();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
