package com.e_garden.api.videos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findVideoByFileName(String fileName);

    void deleteByFileName(String fileName); // Suppression par le nom du fichier
}
