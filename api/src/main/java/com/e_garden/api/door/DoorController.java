package com.e_garden.api.door;

import com.e_garden.api.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/report")
@CrossOrigin
@Secured("ADMINISTRATEUR")
public class DoorController {

    private final DoorService doorService;

    @Autowired
    public DoorController(DoorService doorService) {
        this.doorService = doorService;
    }

    @GetMapping
    public List<Door> getAllDoors() {
        return doorService.getAllDoor();
    }

    @PostMapping
    public ResponseEntity<Door> createDoor(@RequestBody Door door) {
        return ResponseEntity.ok(doorService.saveDoor(door));
    }

    @PutMapping
    public ResponseEntity<Door> updateDoor(@RequestBody Door door) {
        return ResponseEntity.ok(doorService.saveDoor(door));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteDoor(@RequestBody Long id) {
        doorService.deleteDoor(doorService.getDoorById(id));
        return ResponseEntity.noContent().build();
    }
}
