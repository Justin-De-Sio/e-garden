package com.e_garden.api.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * L'interface Log repository.
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    /**
     * Find by level in list.
     *
     * @param levels les levels
     * @return la list
     */
    List<Log> findByLevelIn(List<String> levels);
}
