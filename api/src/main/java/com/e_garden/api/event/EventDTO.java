package com.e_garden.api.event;

import java.time.LocalDateTime;

/**
 * Le type Event dto.
 */
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private Integer eventType;
    private Long userId;
    private LocalDateTime createdAt;

    /**
     * Instancies un nouveau Event dto.
     */
    public EventDTO() {
    }

    /**
     * Instancies un nouveau Event dto.
     *
     * @param id          l'identifiant
     * @param title       le titre
     * @param description la description
     * @param eventType   le type de l'événement
     * @param userId      l'identifiant de l'utilisateur
     */
    public EventDTO(Long id, String title, String description, Integer eventType, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventType = eventType;
        this.userId = userId;
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
     * Gets an event type.
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
     * Gets description.
     *
     * @return la description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description la description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets user identifiant.
     *
     * @return le user identifiant
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user identifiant.
     *
     * @param userId le user identifiant
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
