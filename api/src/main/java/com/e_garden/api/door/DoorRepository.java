package com.e_garden.api.door;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * L'interface Door repository.
 */
@Repository
public interface DoorRepository extends JpaRepository<Door, Long> {
    Optional<Door> findByDoorNumber(int doorNumber);
}
