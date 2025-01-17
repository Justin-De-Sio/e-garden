package com.e_garden.api.Report;

import com.e_garden.api.PageDTO;
import com.e_garden.api.User.User;
import com.e_garden.api.User.UserPrincipal;
import com.e_garden.api.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    @Autowired
    public ReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/paginated")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<PageDTO<Report>> getPaginatedReports(@RequestParam(defaultValue = "10") Integer size,
                                                                   @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(reportService.getPaginatedReports(page, size));
    }

    // TODO : a revoir si necessaire ?? Tu rentres tu badges on te prépare un rapport
    @PostMapping
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Report> createReport(@RequestBody ReportDTO report) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        if (userByEmail == null)
            return ResponseEntity.notFound().build();
        Report reportToSave = new Report(userByEmail, report.getContent());
        reportToSave.setValidated(report.isValidated());
        return ResponseEntity.ok(reportService.saveReport(reportToSave));
    }

    @PutMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE", "UTILISATEUR"})
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report reportDetails) {
        Optional<Report> currentReport = reportService.getReportById(id);
        if (currentReport.isEmpty())
            return ResponseEntity.notFound().build();
        Report updatedReport = currentReport.get();

        if (updatedReport.isValidated())
            return ResponseEntity.badRequest().build();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        if (userByEmail == null)
            return ResponseEntity.notFound().build();
        if (!Objects.equals(updatedReport.getUser().getEmail(), userByEmail.getEmail()))
            return ResponseEntity.badRequest().build();

        updatedReport.setContent(reportDetails.getContent());
        updatedReport.setValidated(reportDetails.isValidated());
        return ResponseEntity.ok(reportService.saveReport(updatedReport));
    }

    // TODO à revoir si on supprime
    @DeleteMapping("/{id}")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportService.getReportById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        }
    }
}
