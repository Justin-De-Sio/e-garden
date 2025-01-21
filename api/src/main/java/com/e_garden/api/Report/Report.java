package com.e_garden.api.Report;

import com.e_garden.api.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
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

    public Report() {
        this.validated = false;
    }

    public Report(User user) {
        this();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Report(User user, String content) {
        this(user);
        this.content = content;
    }

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