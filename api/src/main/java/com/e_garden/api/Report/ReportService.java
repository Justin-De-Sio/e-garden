package com.e_garden.api.Report;
import com.e_garden.api.PageDTO;
import com.e_garden.api.User.User;
import com.e_garden.api.User.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Report saveReport(Report report) {
        // Récupérer l'utilisateur depuis la base de données
        User user = userRepository.findById(report.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + report.getUser().getId() + " not found."));

        // Associer l'utilisateur récupéré au rapport
        report.setUser(user);

        // Sauvegarder le rapport
        return reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    public PageDTO<Report> getPaginatedReports(int page, int size) {
        Page<Report> reportPage = reportRepository.findAllByOrderByReportDateAsc(PageRequest.of(page, size));

         return  (new PageDTO<>(
                reportPage.getContent(),
                reportPage.getNumber(),
                reportPage.getSize(),
                reportPage.getTotalElements()
        ));
    }
}
