package com.e_garden.api.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        // Préparer les informations de connexion
        String loginRequest = objectMapper.writeValueAsString(new LoginRequest("username", "password"));

        // Effectuer une requête POST pour se connecter
        MvcResult result = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
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
                .andExpect(jsonPath("$[0].username", notNullValue())); // Vérifie que le premier utilisateur a un username
    }

    // Classe interne pour représenter la requête de login
    private static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Getters et setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
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
