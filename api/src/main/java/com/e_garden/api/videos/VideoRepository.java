package com.e_garden.api.videos;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findVideoByFileName(String fileName);
    List<Video> findAllByFilePath(String filePath);
}
