package com.e_garden.api;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
class ApiApplicationTests {

	@Configuration
	static class env {

		@Bean
		public static Dotenv load() {
			Dotenv dotenv = Dotenv.configure().directory("./").filename(".env").load();

			System.out.println(dotenv.toString());
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

            return dotenv;
        }
	}

	@Test
	void contextLoads() {
	}

}
