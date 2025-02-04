package com.e_garden.api.report;

import com.e_garden.api.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Le type Report.
 */
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Clé étrangère vers User
    private User user;

    /**
     * Date d'enregistrement de la dernière version du rapport
     */
    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    /**
     * Contenu du rapport
     */
    @Column(name = "content", nullable = true, columnDefinition = "TEXT")
    private String content;

    /**
     * Statut du rapport, si true alors le rapport est validé et n'est plus modifiable
     */
    @Column(name = "validated", nullable = false)
    private boolean validated;

    /**
     * Date de la création du rapport
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Instancie un nouveau Report.
     */
    public Report() {
        this.validated = false;
    }

    /**
     * Instancie un nouveau Report.
     *
     * @param user le user
     */
    public Report(User user) {
        this();
        this.user = user;
    }

    /**
     * Instancie un nouveau Report.
     *
     * @param user    le user
     * @param content le content
     */
    public Report(User user, String content) {
        this(user);
        this.content = content;
    }

    /**
     * Gets identifiant.
     *
     * @return l'identifiant
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
     * Gets user.
     *
     * @return l'user
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
     * Gets report date.
     *
     * @return le report date
     */
    public LocalDateTime getReportDate() {
        return reportDate;
    }

    /**
     * Sets report date.
     *
     * @param reportDate le report date
     */
    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * Gets content.
     *
     * @return le content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content le content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Is validated boolean.
     *
     * @return le boolean
     */
    public boolean isValidated() {
        return validated;
    }

    /**
     * Sets validated.
     *
     * @param validated le validated
     */
    public void setValidated(boolean validated) {
        this.validated = validated;
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
     * To string string.
     *
     * @return le string
     */
    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", user=" + user +
                ", reportDate=" + reportDate +
                ", content='" + content + '\'' +
                ", validated=" + validated +
                ", createdAt=" + createdAt +
                '}';
    }

    /**
     * Cette méthode est appelée automatiquement avant l'insertion dans la base de données.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}