package com.e_garden.api.User;


import com.e_garden.api.Events.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<>();


    @Setter
    @Getter
    @Column(nullable = true , length = 2048)
    private String password;
}
