package com.e_garden.api.log;


import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Le type Log.
 */
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

    /**
     * Instancies un nouveau Log.
     */
    public Log() {
    }

    /**
     * Instancies un nouveau Log.
     *
     * @param level   le level
     * @param message le message
     * @param details le details
     */
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

    /**
     * Gets level.
     *
     * @return le level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level le level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Gets message.
     *
     * @return le message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message le message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets details.
     *
     * @return les details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets details.
     *
     * @param details les details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Gets timestamp.
     *
     * @return le timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp le timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

