package com.e_garden.api;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv load() {
        Dotenv dotenv = Dotenv.configure().directory("api/").load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        return dotenv;
    }
}
