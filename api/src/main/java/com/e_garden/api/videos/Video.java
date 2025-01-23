package com.e_garden.api.videos;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Duration getFileDuration() {
        return fileDuration;
    }

    public void setFileDuration(Duration fileDuration) {
        this.fileDuration = fileDuration;
    }

    public LocalDateTime getFileDate() {
        return fileDate;
    }

    public void setFileDate(LocalDateTime fileDate) {
        this.fileDate = fileDate;
    }

    @Column(name = "file_duration", nullable = false)
    private Duration fileDuration;

    @Column(name = "file_date", nullable = false)
    private LocalDateTime fileDate;

    public Video() {
    }

    public Video(String fileName, Duration fileDuration) {
        this.fileName = fileName;
        this.fileDuration = fileDuration;
        this.fileDate = LocalDateTime.now();
    }
}
