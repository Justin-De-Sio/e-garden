package com.e_garden.api.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Integer countAllByValidatedAndReportDateGreaterThan(boolean validated, LocalDateTime reportDate);

    Page<Report> findAllByOrderByReportDateDesc(Pageable of);

    Page<Report> findAllByUser_IdAndValidatedOrderByReportDateDesc(Long userId, boolean validated, Pageable pageable);

    Page<Report> findAllByValidatedOrderByReportDateDesc(boolean validated, Pageable pageable);
}


