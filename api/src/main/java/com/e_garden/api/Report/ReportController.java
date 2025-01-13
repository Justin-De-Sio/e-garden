package com.e_garden.api.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Récupérer un rapport par ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Récupérer tous les rapports
    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    // Récupérer les 10 derniers comptes rendus non remplis
    @GetMapping("/unfilled")
    public List<Report> getLast10UnfilledReports() {
        return reportService.findLast10UnfilledReports();
    }

    // Créer un nouveau rapport
    @PostMapping
    public Report createReport(@RequestBody Report report) {
        return reportService.saveReport(report);
    }

    // Mettre à jour un rapport existant (TODO : logique spécifique)
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report reportDetails) {
        return reportService.getReportById(id)
                .map(existingReport -> {
                    // Mettre à jour les champs nécessaires
                    existingReport.setReportDate(reportDetails.getReportDate());
                    existingReport.setContent(reportDetails.getContent());
                    existingReport.setUser(reportDetails.getUser());

                    // Sauvegarder les modifications
                    Report updatedReport = reportService.saveReport(existingReport);
                    return ResponseEntity.ok(updatedReport);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprimer un rapport par ID
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
