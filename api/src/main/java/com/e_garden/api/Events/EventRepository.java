package com.e_garden.api.Events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Override
    Page<Event> findAll(Pageable pageable);
    Integer countAllByEventTypeAndCreatedAtGreaterThan(Integer eventType, LocalDateTime reportDate);

}
