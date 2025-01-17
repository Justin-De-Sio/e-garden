package com.e_garden.api.Report;

import com.e_garden.api.Log.Levels;
import com.e_garden.api.Log.LogService;
import com.e_garden.api.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final LogService logService;

    @Autowired
    public ReportService(ReportRepository reportRepository, LogService logService) {
        this.reportRepository = reportRepository;
        this.logService = logService;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Report saveReport(Report report) {
        report.setReportDate(LocalDateTime.now());
        logService.createLog(String.valueOf(Levels.REPORT), "Enregistrement d'un rapport", "report id : " + report.toString());
        return reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        logService.createLog(String.valueOf(Levels.REPORT), "Suppression d'un rapport", "report id : " + id);
        reportRepository.deleteById(id);
    }

    public PageDTO<Report> getPaginatedReports(int page, int size) {
        Page<Report> reportPage = reportRepository.findAllByValidatedOrderByReportDateAsc(true, PageRequest.of(page, size));

        return (new PageDTO<>(
                reportPage.getContent(),
                reportPage.getNumber(),
                reportPage.getSize(),
                reportPage.getTotalElements(),
                reportPage.getTotalPages()
        ));
    }

    public int getUnvalidatedReportsCount() {
        return reportRepository.countAllByValidatedAndReportDateGreaterThan(false, LocalDateTime.now().minusDays(30));
    }
}
