package com.e_garden.api.door;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * L'interface Door repository.
 */
@Repository
public interface DoorRepository extends JpaRepository<Door, Long> {
}
