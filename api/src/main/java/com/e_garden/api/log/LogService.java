package com.e_garden.api.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Le type Log service.
 */
@Service
public class LogService {

    private final LogRepository logRepository;

    /**
     * Instancie un nouveau Log service.
     *
     * @param logRepository le log repository
     */
    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * Create a log.
     *
     * @param level   le level
     * @param message le message
     * @param details le details
     */
    public void createLog(String level, String message, String details) {
        Log log = new Log(level, message, details);
        logRepository.save(log);
    }

    /**
     * Gets all logs by type.
     *
     * @param levels les levels
     * @return all logs by type
     */
    public List<Log> getAllLogsByType(String[] levels) {
        return logRepository.findByLevelIn(List.of(levels));
    }

    /**
     * Gets log by identifiant.
     *
     * @param id l'identifiant
     * @return le log by identifiant
     */
    public Optional<Log> getLogById(Long id) {
        return logRepository.findById(id);
    }

    private void deleteLogById(Long id) {
        logRepository.deleteById(id);
    }
}

