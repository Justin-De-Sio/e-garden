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

    /**
     * Titre de l'Event
     */
    @Column(nullable = false)
    private String title;

    /**
     * Date de création de l'Event
     */
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Type 0 : action de l'user badge ;
     * Type 1 : caméra détecte un mouvement ;
     * Type 2 : à définir…
     */
    @Column(nullable = false, name = "event_type")
    private Integer eventType;

    /**
     * Numéro de porte, correspond à un badge.
     * Peut aussi être utilisé pour le numéro d'une caméra, ou d'un autre objet à l'avenir.
     */
    @Column(nullable = true, name = "door_number")
    private Integer doorNumber;

    /**
     * L'utilisateur, facultatif, enregistré si on a l'information.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    public Event() {
        this.eventType = -1;
        this.title = "";
        this.user = null;
    }

    public Event(String title) {
        this();
        this.title = title;
    }

    /**
     * Cette méthode est appelée automatiquement avant l'insertion dans la base de données.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", eventType=" + eventType +
                ", doorNumber=" + doorNumber +
                ", user=" + user +
                '}';
    }
}
