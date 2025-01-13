package com.e_garden.api.Events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Events, Long> {
    Page<Events> findAll(Pageable pageable);
}
