package com.e_garden.api.Report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    //Page<Report> findAllByValidatedOrderByReportDateAsc(boolean validated, Pageable pageable);
    Integer countAllByValidatedAndReportDateGreaterThan(boolean validated, LocalDateTime reportDate);
    Page<Report> findAllByOrderByReportDateAsc(Pageable of);
}
