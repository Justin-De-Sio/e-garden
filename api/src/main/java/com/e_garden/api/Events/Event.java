package com.e_garden.api.Events;

import com.e_garden.api.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Setter
@Getter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    // Relation avec l'utilisateur (un événement peut être lié à un seul utilisateur ou aucun)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true) // La contrainte nullable=true permet qu'un événement ne soit pas nécessairement lié à un utilisateur
    private User user;

    public Event() {
        this.title = "";
        this.createdAt = LocalDateTime.now();
        this.user = null;
    }

    public Event(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }
}
