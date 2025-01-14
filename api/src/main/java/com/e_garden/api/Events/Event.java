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

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    // type 0 : user padge ; type 1 : caméra détecte un mouvement ; ...
    @Column(nullable = false, name = "event_type")
    private Integer eventType;

    @Column(nullable = true, name = "door_number")
    private Integer doorNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true) // La contrainte nullable=true permet qu'un événement ne soit pas nécessairement lié à un utilisateur
    private User user;

    public Event() {
        this.eventType = 0;
        this.title = "";
        this.createdAt = LocalDateTime.now();
        this.user = null;
    }

    public Event(String title) {
        this();
        this.title = title;
    }
}
