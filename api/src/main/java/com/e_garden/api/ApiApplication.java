package com.e_garden.api;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

/**
 * Le type Api application.
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ApiApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
	}
	/**
	 * Le point d'entrée de l'application.
	 *
	 * @param args l'input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
