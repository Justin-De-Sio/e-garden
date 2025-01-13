package com.e_garden.api.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    java.util.List<com.e_garden.api.Report.Report> findTop10ByContentIsNullOrContentOrderByReportDateDesc(String emptyContent);
}
