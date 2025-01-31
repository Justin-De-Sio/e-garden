package com.e_garden.api.log;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Log() {
    }

    public Log(String level, String message, String details) {
        this();
        this.level = level;
        this.message = message;
        this.details = details;
    }

    /**
     * Permet d'ajouter automatique la date de création/enregistrement du log.
     */
    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

