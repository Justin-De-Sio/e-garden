package com.e_garden.api.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Integer countAllByEventTypeAndCreatedAtGreaterThan(Integer eventType, LocalDateTime reportDate);

    Page<Event> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
