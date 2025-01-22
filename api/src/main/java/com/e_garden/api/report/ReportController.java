package com.e_garden.api.report;

import com.e_garden.api.PageDTO;
import com.e_garden.api.user.User;
import com.e_garden.api.user.UserPrincipal;
import com.e_garden.api.user.UserService;
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

    @PutMapping("/{id}")
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

    @GetMapping("/statistique")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Integer> getReportsStatistique() {
        return ResponseEntity.ok(reportService.getUnvalidatedReportsCount());
    }
}
