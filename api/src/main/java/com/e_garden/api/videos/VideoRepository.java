package com.e_garden.api.videos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findFirstByFileDateBetweenOrderByFileDurationDesc(LocalDateTime start, LocalDateTime end);
    void deleteByFileName(String fileName); // Suppression par le nom du fichier
}
