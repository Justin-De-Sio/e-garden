package com.e_garden.api.videos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@Getter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Setter
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_date", nullable = false)
    private LocalDateTime fileDate;

    public Video() {
    }

    public Video(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileDate = LocalDateTime.now();
    }
}
