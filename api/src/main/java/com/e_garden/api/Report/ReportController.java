package com.e_garden.api.Report;

import com.e_garden.api.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/paginated")
    public ResponseEntity<PageDTO<Report>> getPaginatedReports(@RequestParam(defaultValue = "10") Integer size,
                                                                   @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(reportService.getPaginatedReports(page, size));
    }

    // TODO : a revoir si necessaire ?? Tu rentres tu badges on te prépare un rapport
    @PostMapping
    public Report createReport(@RequestBody Report report) {
        return reportService.saveReport(report);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report reportDetails) {
        Optional<Report> currentReport = reportService.getReportById(id);
        if (currentReport.isEmpty())
            return ResponseEntity.notFound().build();
        Report updatedReport = currentReport.get();
        updatedReport.setContent(reportDetails.getContent());
        return ResponseEntity.ok(reportService.saveReport(updatedReport));
    }

    // TODO à revoir si on supprime
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportService.getReportById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        }
    }
}
