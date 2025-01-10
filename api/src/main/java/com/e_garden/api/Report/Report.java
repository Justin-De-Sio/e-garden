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

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Cette méthode est appelée automatiquement avant l'insertion dans la base de données
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(); // Définit automatiquement la date de création
    }
}