package com.e_garden.api.User;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "users")
public class User {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = true, length = 255)
    private String name;

    @Setter
    @Getter
    @Column(nullable = true, length = 255)
    private String surname;

    @Setter
    @Getter
    @Column(nullable = false, length = 255)
    private String email;

    @Setter
    @Getter
    @Column(nullable = true , length = 2048)
    private String password;


}
