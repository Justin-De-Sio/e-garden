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

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private Integer fileType;

    @Column(name = "extension", nullable = false)
    private Integer extension;

    public Video() {
    }

    public Video(Integer extension, Integer fileType, String fileName) {
        this.extension = extension;
        this.fileType = fileType;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", fileName='" + fileName + '\'' +
                ", fileType=" + fileType +
                ", extension=" + extension +
                '}';
    }

    /**
     * * Cette méthode est appelée automatiquement avant l'insertion dans la base de données.
     */
    @PrePersist
    public void prePersist() {
        this.year = LocalDateTime.now().getYear();
        this.month = LocalDateTime.now().getMonthValue();
    }
}
