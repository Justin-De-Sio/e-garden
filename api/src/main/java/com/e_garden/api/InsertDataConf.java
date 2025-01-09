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
            User user = new User("thomas", "thomas");
            User gabin = new User("gabin", "gabin");
            userService.saveUsers(List.of(user, gabin));

        };
    }
}
