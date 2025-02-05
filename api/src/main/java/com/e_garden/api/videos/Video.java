package com.e_garden.api.videos;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Le type Video.
 */
@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_duration", nullable = false)
    private Duration fileDuration;
    @Column(name = "file_date", nullable = false)
    private LocalDateTime fileDate;

    /**
     * Instancie un nouveau Video.
     */
    public Video() {
    }

    /**
     * Instancie un nouveau Video.
     *
     * @param fileName     le filename
     * @param fileDuration le file duration
     */
    public Video(String fileName, Duration fileDuration) {
        this.fileName = fileName;
        this.fileDuration = fileDuration;
        this.fileDate = LocalDateTime.now();
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
     * Gets filename.
     *
     * @return le filename
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets filename.
     *
     * @param fileName le filename
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets file duration.
     *
     * @return le file duration
     */
    public Duration getFileDuration() {
        return fileDuration;
    }

    /**
     * Sets file duration.
     *
     * @param fileDuration le file duration
     */
    public void setFileDuration(Duration fileDuration) {
        this.fileDuration = fileDuration;
    }

    /**
     * Gets file date.
     *
     * @return le file date
     */
    public LocalDateTime getFileDate() {
        return fileDate;
    }

    /**
     * Sets file date.
     *
     * @param fileDate le file date
     */
    public void setFileDate(LocalDateTime fileDate) {
        this.fileDate = fileDate;
    }
}
