package com.e_garden.api.door;

import com.e_garden.api.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Le type Door.
 */
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

    /**
     * Instancie un nouveau Door.
     */
    public Door() {
    }

    /**
     * Instancie un nouveau Door.
     *
     * @param name le name
     */
    public Door(String name) {
        this.name = name;
    }

    /**
     * Gets events.
     *
     * @return l'events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Gets identifiant.
     *
     * @return le identifiant
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets identifiant.
     *
     * @param id l'identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return le name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name le name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * To string string.
     *
     * @return le string
     */
    @Override
    public String toString() {
        return "Door{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
