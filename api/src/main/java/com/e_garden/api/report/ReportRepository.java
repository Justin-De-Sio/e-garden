package com.e_garden.api.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * L'interface Report repository.
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    /**
     * Count all by validated and report date greater than integer.
     *
     * @param validated  le validated
     * @param reportDate le report date
     * @return un integer
     */
    Integer countAllByValidatedAndReportDateGreaterThan(boolean validated, LocalDateTime reportDate);

    /**
     * Find all by order by report date desc page.
     *
     * @param of le of
     * @return la page
     */
    Page<Report> findAllByOrderByReportDateDesc(Pageable of);

    /**
     * Find all by user identifiant and validated order by report date desc page.
     *
     * @param userId    l'user id
     * @param validated le validated
     * @param pageable  la pageable
     * @return la page
     */
    Page<Report> findAllByUser_IdAndValidatedOrderByReportDateDesc(Long userId, boolean validated, Pageable pageable);

    /**
     * Find all by validated order by report date desc page.
     *
     * @param validated le validated
     * @param pageable  la pageable
     * @return la page
     */
    Page<Report> findAllByValidatedOrderByReportDateDesc(boolean validated, Pageable pageable);
}


