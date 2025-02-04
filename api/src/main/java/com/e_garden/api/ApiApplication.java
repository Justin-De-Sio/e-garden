package com.e_garden.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Le type Api application.
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ApiApplication {
	/**
	 * Le point d'entrée de l'application.
	 *
	 * @param args l'input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
