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

/**
 * Le type Report controller.
 */
@RestController
@RequestMapping("/report")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    /**
     * Instancie un nouveau Report controller.
     *
     * @param reportService le report service
     * @param userService   le user service
     */
    @Autowired
    public ReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    /**
     * Gets a report by identifiant.
     *
     * @param id the identifiant
     * @return le report by identifiant
     */
    @GetMapping("/{id}")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    /**
     * Gets paginated reports.
     *
     * @param size la size
     * @param page la page
     * @return la paginated reports
     */
    @GetMapping("/paginated")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<PageDTO<Report>> getPaginatedReports(@RequestParam(defaultValue = "10") Integer size,
                                                               @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(reportService.getPaginatedReports(page, size));
    }

    /**
     * Gets history paginated reports.
     *
     * @param size la size
     * @param page la page
     * @return l'historique paginated reports
     */
    @GetMapping("/history/paginated")
    public ResponseEntity<PageDTO<Report>> getHistoryPaginatedReports(@RequestParam(defaultValue = "10") Integer size,
                                                               @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(reportService.getPaginatedValidatedReports(page, size));
    }

    /**
     * Gets my validated reports.
     *
     * @param size la size
     * @param page la page
     * @return mes validated reports
     */
    @GetMapping("/myValidatedReports/paginated")
    public ResponseEntity<PageDTO<Report>> getMyValidatedReports(@RequestParam(defaultValue = "10") Integer size,
                                                                 @RequestParam(defaultValue = "0") Integer page) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        if (userByEmail == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(reportService.getMyValidatedReports(page, size, userByEmail.getId()));
    }

    /**
     * Gets my not validated reports.
     *
     * @param size la size
     * @param page la page
     * @return mes non validated reports
     */
    @GetMapping("/myNotValidatedReports/paginated")
    public ResponseEntity<PageDTO<Report>> getMyNotValidatedReports(@RequestParam(defaultValue = "10") Integer size,
                                                                 @RequestParam(defaultValue = "0") Integer page) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        if (userByEmail == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(reportService.getMyNotValidatedReports(page, size, userByEmail.getId()));
    }

    /**
     * Create report response entity.
     *
     * @param newReport le new report
     * @return la response entity
     */
    @PostMapping()
    public ResponseEntity<Report> createReport(@RequestBody Report newReport) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        if (userByEmail == null)
            return ResponseEntity.notFound().build();
        Report report = new Report();
        report.setUser(userByEmail);
        report.setContent(newReport.getContent());
        report.setValidated(newReport.isValidated());
        return ResponseEntity.ok(reportService.createReport(report));
    }

    /**
     * Update report response entity.
     *
     * @param id            l'identifiant
     * @param reportDetails le report details
     * @return la response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report reportDetails) {
        Report updatedReport = reportService.getReportById(id);

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

    /**
     * Delete report response entity.
     *
     * @param id l'identifiant
     * @return le response entity
     */
    @DeleteMapping("/{id}")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Gets reports statistiques.
     *
     * @return la statistique des rapports
     */
    @GetMapping("/statistique")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Integer> getReportsStatistique() {
        return ResponseEntity.ok(reportService.getUnvalidatedReportsCount());
    }
}
