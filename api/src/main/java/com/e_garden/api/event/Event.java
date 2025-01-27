package com.e_garden.api.event;

import com.e_garden.api.user.User;
import com.e_garden.api.videos.Video;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
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

    /**
     * Un event peut être lié à un fichier vidéo
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "video_id", nullable = true)
    private Video video;

    public Event() {
        this.eventType = -1;
        this.title = "";
        this.createdAt = LocalDateTime.now();
        this.user = null;
    }

    public Event(String title) {
        this();
        this.title = title;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    /**
     * Cette méthode est appelée automatiquement avant l'insertion dans la base de données.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(Integer doorNumber) {
        this.doorNumber = doorNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
