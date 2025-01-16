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

    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    @Column(name = "content", nullable = true, columnDefinition = "TEXT")
    private String content;

    @Column(name = "validated", nullable = false)
    private boolean validated;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Cette méthode est appelée automatiquement avant l'insertion dans la base de données
    // TODO implémenter cette méthode dans log
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Report() {
        this.validated = false;
    }

    public Report(User user) {
        this();
        this.user = user;
    }

    public Report(User user, String content) {
        this(user);
        this.content = content;
    }
}