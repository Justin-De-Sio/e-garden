package com.e_garden.api.Report;
import com.e_garden.api.User.User;
import com.e_garden.api.User.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
}
