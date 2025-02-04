package com.e_garden.api.videos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * L'interface Video repository.
 */
public interface VideoRepository extends JpaRepository<Video, Long> {
    /**
     * Find first by file date between order by file duration desc video.
     *
     * @param start le start
     * @param end   le end
     * @return le video
     */
    Video findFirstByFileDateBetweenOrderByFileDurationDesc(LocalDateTime start, LocalDateTime end);

    /**
     * Delete by file name.
     *
     * @param fileName le file name
     */
    void deleteByFileName(String fileName); // Suppression par le nom du fichier

    /**
     * Find by file date after list.
     *
     * @param date le date
     * @return le list
     */
    List<Video> findByFileDateAfter(LocalDateTime date);

    /**
     * Find by file date between list.
     *
     * @param start le start
     * @param end   le end
     * @return le list
     */
    List<Video> findByFileDateBetween(LocalDateTime start, LocalDateTime end);
}
