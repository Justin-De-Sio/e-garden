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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true) // La contrainte nullable=true permet qu'un événement ne soit pas nécessairement lié à un utilisateur
    private User user;

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
