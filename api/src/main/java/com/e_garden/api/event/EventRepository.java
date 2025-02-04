package com.e_garden.api.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * L'interface Event repository.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * Count all by event type and created at greater than integer.
     *
     * @param eventType  le event type
     * @param reportDate le report date
     * @return le integer
     */
    Integer countAllByEventTypeAndCreatedAtGreaterThan(Integer eventType, LocalDateTime reportDate);

    /**
     * Find all by order by created at desc page.
     *
     * @param pageable le pageable
     * @return le page
     */
    Page<Event> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
