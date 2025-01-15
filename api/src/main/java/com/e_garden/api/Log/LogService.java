package com.e_garden.api.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Log createLog(String level, String message, String details) {
        Log log = new Log(level, message, details);
        return logRepository.save(log);
    }

    public List<Log> getAllLogsByType(String[] levels) {
        return logRepository.findByLevelIn(List.of(levels));
    }

    public Optional<Log> getLogById(Long id) {
        return logRepository.findById(id);
    }

    private void deleteLogById(Long id) {
        logRepository.deleteById(id);
    }
}

