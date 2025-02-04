package com.e_garden.api.door;

import com.e_garden.api.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Door {
    @JsonIgnore
    @OneToMany(mappedBy = "door", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Event> events = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 125)
    private String name;

    public Door() {
    }

    public Door(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Door{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
