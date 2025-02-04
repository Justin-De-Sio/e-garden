package com.e_garden.api.event;

import com.e_garden.api.door.Door;
import com.e_garden.api.user.User;
import com.e_garden.api.videos.Video;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Le type Event.
 */
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "door_id")
    private Door door;

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

    /**
     * Instancie un nouveau Event.
     */
    public Event() {
        this.eventType = -1;
        this.title = "";
        this.createdAt = LocalDateTime.now();
        this.user = null;
    }

    /**
     * Instancie un nouveau Event.
     *
     * @param title le title
     */
    public Event(String title) {
        this();
        this.title = title;
    }

    /**
     * Gets video.
     *
     * @return le video
     */
    public Video getVideo() {
        return video;
    }

    /**
     * Sets video.
     *
     * @param video le video
     */
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
     * @param id le identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets title.
     *
     * @return le title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title le title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets created at.
     *
     * @return le created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt le created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets event type.
     *
     * @return le event type
     */
    public Integer getEventType() {
        return eventType;
    }

    /**
     * Sets event type.
     *
     * @param eventType le event type
     */
    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets door.
     *
     * @return le door
     */
    public Door getDoor() {
        return door;
    }

    /**
     * Sets door.
     *
     * @param door le door
     */
    public void setDoor(Door door) {
        this.door = door;
    }

    /**
     * Gets user.
     *
     * @return le user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user l'user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * To string string.
     *
     * @return le string
     */
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", eventType=" + eventType +
                ", doorNumber=" + door +
                ", user=" + user +
                '}';
    }
}
