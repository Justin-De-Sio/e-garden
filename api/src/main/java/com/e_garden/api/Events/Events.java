package com.e_garden.api.Events;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "events")
@Setter
@Getter
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String createdAt;

    // Constructeurs, getters, setters
    public Events() {}

    public Events(String title, String description, String createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

}
