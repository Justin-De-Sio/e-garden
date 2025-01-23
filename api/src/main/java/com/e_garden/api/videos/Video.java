package com.e_garden.api.videos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.bytedeco.javacpp.chrono.Hours;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

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
