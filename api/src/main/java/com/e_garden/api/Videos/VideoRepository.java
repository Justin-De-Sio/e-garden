package com.e_garden.api.Videos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findVideoByFileName(String fileName);

    List findAllByExtension(Integer extension);
}
