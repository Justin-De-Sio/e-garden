package com.e_garden.api.door;

import com.e_garden.api.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Le type Door controller.
 */
@Controller
@RequestMapping("/door")
@CrossOrigin
@Secured("ADMINISTRATEUR")
public class DoorController {

    private final DoorService doorService;

    /**
     * Instancie un nouveau Door controller.
     *
     * @param doorService le door service
     */
    @Autowired
    public DoorController(DoorService doorService) {
        this.doorService = doorService;
    }

    /**
     * Gets all doors.
     *
     * @return all doors
     */
    @GetMapping
    public List<Door> getAllDoors() {
        return doorService.getAllDoor();
    }

    /**
     * Create door response entity.
     *
     * @param door la porte
     * @return response entity
     */
    @PostMapping
    public ResponseEntity<Door> createDoor(@RequestBody Door door) {
        return ResponseEntity.ok(doorService.saveDoor(door));
    }

    /**
     * Update door response entity.
     *
     * @param door la porte
     * @return response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Door> updateDoor(@RequestBody Door door) {
        return ResponseEntity.ok(doorService.saveDoor(door));
    }

    /**
     * Delete door response entity.
     *
     * @param id l'identifiant
     * @return le response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoor(@PathVariable String id) {
        doorService.deleteDoor(doorService.getDoorById(Long.valueOf(id)));
        return ResponseEntity.noContent().build();
    }
}
