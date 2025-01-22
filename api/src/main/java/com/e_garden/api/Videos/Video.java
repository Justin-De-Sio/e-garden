package com.e_garden.api.Videos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;


    @Column(name = "file_date", nullable = false)
    private LocalDateTime fileDate;

    public Video() {
    }

    public Video(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }


    /**
     * * Cette méthode est appelée automatiquement avant l'insertion dans la base de données.
     */
    @PrePersist
    public void prePersist() {
        this.fileDate = LocalDateTime.now();
    }
}
