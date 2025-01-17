package com.e_garden.api.Conf;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    /**
     * Cette fonction permet de charger le fichier avec les variables d'environnements.
     * Elle est lancé automatiquement par Spring Boot avec l'annotation @Bean.
     * En fonction du projet directory dans votre machine local ou du serveur, le .directory('PATH') peut être changé.
     * @return elle retourne un object Dotenv spécifique.
     */
    @Bean
    public static Dotenv load() {
        Dotenv dotenv = Dotenv.configure().directory("api/").filename(".env").load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        return dotenv;
    }
}
