/*
package com.e_garden.api.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void loginAndRetrieveToken() throws Exception {
        // Préparer les informations de connexion (utiilelisateur et mot de passe)
        Dotenv dotenv = Dotenv.configure().directory("./").filename(".env").load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        // Effectuer une requête POST pour se connecter
        MvcResult result = mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"thomas.robert@ece.fr\",\"password\":\"thomas\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue())) // Vérifie que le token est présent
                .andReturn();

        // Extraire le token de la réponse
        String responseBody = result.getResponse().getContentAsString();
        LoginResponse loginResponse = objectMapper.readValue(responseBody, LoginResponse.class);
        token = loginResponse.getToken();
    }

    @Test
    void testGetAllUsersWithToken() throws Exception {
        // Effectuer une requête GET à /user/all avec le token dans l'en-tête Authorization
        mockMvc.perform(get("/api/user/all")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty()))) // Vérifie que la réponse contient une liste non vide
                .andExpect(jsonPath("$[0].email", notNullValue())) // Vérifie que le premier utilisateur a un email
                .andExpect(jsonPath("$[0].username", notNullValue())); // Vérifie que le premier utilisateur a un nom d'utilisateur
    }

    // Classe interne pour représenter la requête de login
    private static class LoginRequest {
        private String email;
        private String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Getters et setters
        public String getUsername() { return email; }
        public void setUsername(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // Classe interne pour représenter la réponse de login
    private static class LoginResponse {
        private String token;

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}
*/
