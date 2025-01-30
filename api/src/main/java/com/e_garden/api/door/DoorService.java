package com.e_garden.api.door;

import com.e_garden.api.exception.ObjectNotFoundException;
import com.e_garden.api.log.Levels;
import com.e_garden.api.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoorService {
    private final DoorRepository doorRepository;
    private final LogService logService;

    @Autowired
    public DoorService(DoorRepository doorRepository, LogService logService) {
        this.doorRepository = doorRepository;
        this.logService = logService;
    }

    public List<Door> getAllDoor() {
        return doorRepository.findAll();
    }

    public Door saveDoor(Door door) {
        if (door.getName().length() < 2)
            throw new IllegalArgumentException("Une porte doit avoir contenir minimum deux caractères"); // todo a tester
        Door doorSaved = doorRepository.save(door);
        logService.createLog(String.valueOf(Levels.DOOR), "Enregistrement d'une porte", "Porte : " + door);
        return doorSaved;
    }

    public void deleteDoor(Door door) {
        logService.createLog(String.valueOf(Levels.DOOR), "Enregistrement d'une porte", "Porte : " + door.toString());
        doorRepository.deleteById(door.getId());
    }

    public Door getDoorById(Long id) {
        Optional<Door> doorOptional = doorRepository.findById(id);
        if (doorOptional.isEmpty())
            throw new ObjectNotFoundException("Porte non trouvé avec l'ID : " + id);
        return doorOptional.get();
    }
}
