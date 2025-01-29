package com.e_garden.api.report;

import com.e_garden.api.exception.ObjectNotFoundException;
import com.e_garden.api.log.Levels;
import com.e_garden.api.log.LogService;
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

    public Report getReportById(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isEmpty())
            throw new ObjectNotFoundException("Report non trouvé avec l'ID : " + id);
        else
            return report.get();
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
        Page<Report> reportPage = reportRepository.findAllByOrderByReportDateAsc(PageRequest.of(page, size));

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

    public PageDTO<Report> getMyValidatedReports(int page, int size, Long id) {
        return getUserReportsByValidated(page, size, id, true);
    }

    public PageDTO<Report> getMyNotValidatedReports(int page, int size, Long id) {
        return getUserReportsByValidated(page, size, id, false);
    }

    private PageDTO<Report> getUserReportsByValidated(int page, int size, Long id, boolean validated) {
        Page<Report> reportPage = reportRepository.findAllByUser_IdAndValidatedOrderByReportDateAsc(id, validated,PageRequest.of(page, size));

        return (new PageDTO<>(
                reportPage.getContent(),
                reportPage.getNumber(),
                reportPage.getSize(),
                reportPage.getTotalElements(),
                reportPage.getTotalPages()
        ));
    }

    public PageDTO<Report> getPaginatedValidatedReports(int page, int size) {
        Page<Report> reportPage = reportRepository.findAllByValidatedOrderByReportDateAsc(true, PageRequest.of(page, size));

        return (new PageDTO<>(
                reportPage.getContent(),
                reportPage.getNumber(),
                reportPage.getSize(),
                reportPage.getTotalElements(),
                reportPage.getTotalPages()
        ));
    }

    public Report createReport(Report report) {
        return saveReport(report);
    }
}
