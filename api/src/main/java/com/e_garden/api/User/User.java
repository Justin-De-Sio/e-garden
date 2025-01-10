package com.e_garden.api.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {

    @Id
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
    @Column(nullable = true, length = 2048)
    private String password;

    @Setter
    @Getter
    @Column(nullable = true, length = 255)
    private String className;

    @Setter
    @Getter
    @Column(nullable = true)
    private Integer groupNumber;

    public User() {
        this.name = "";
        this.surname = "";
        this.groupNumber = -1;
        this.className = "";
    }

    public User(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }
}
