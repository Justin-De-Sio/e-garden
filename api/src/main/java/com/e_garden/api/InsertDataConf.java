package com.e_garden.api;

import com.e_garden.api.User.User;
import com.e_garden.api.User.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class InsertDataConf {
    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            User thomas = new User("thomas.robert@ece.fr", "thomas");
            User gabin = new User("gabin.cornaire@ece.fr", "gabin");
            gabin.setSurname("cornaire");
            gabin.setName("Gabin");
            gabin.setClassName("ING4");
            gabin.setGroupNumber(3);
            User justin = new User("justin.desio@ece.fr", "justin");
            User augustin = new User("augustin.meaudre@ece.fr", "augustin");
            User enzo = new User("enzo.romero@ece.fr", "enzo");
            User julien = new User("julien.parquet@ece.fr", "enzo");
            userService.saveUsers(List.of(thomas, gabin, justin, augustin, enzo, julien));
        };
    }
}
