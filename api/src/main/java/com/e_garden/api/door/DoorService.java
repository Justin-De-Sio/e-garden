package com.e_garden.api.door;

import com.e_garden.api.exception.ObjectNotFoundException;
import com.e_garden.api.log.Levels;
import com.e_garden.api.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Le type Door service.
 */
@Service
public class DoorService {
    private final DoorRepository doorRepository;
    private final LogService logService;

    /**
     * Instancie un nouveau Door service.
     *
     * @param doorRepository le door repository
     * @param logService     le log service
     */
    @Autowired
    public DoorService(DoorRepository doorRepository, LogService logService) {
        this.doorRepository = doorRepository;
        this.logService = logService;
    }

    /**
     * Gets all doors.
     *
     * @return le all door
     */
    public List<Door> getAllDoor() {
        return doorRepository.findAll();
    }

    /**
     * Save a door, une porte.
     *
     * @param door la porte
     * @return la porte
     */
    public Door saveDoor(Door door) {
        if (door.getName().length() < 2)
            throw new IllegalArgumentException("Une porte doit avoir contenir minimum deux caractères");
        Door doorSaved = doorRepository.save(door);
        logService.createLog(String.valueOf(Levels.DOOR), "Enregistrement d'une porte", "Porte : " + door);
        return doorSaved;
    }

    /**
     * Delete door.
     *
     * @param door la porte
     */
    public void deleteDoor(Door door) {
        logService.createLog(String.valueOf(Levels.DOOR), "Enregistrement d'une porte", "Porte : " + door.toString());
        doorRepository.deleteById(door.getId());
    }

    /**
     * Gets door by identifiant.
     *
     * @param id the identifiant
     * @return le door by identifiant
     */
    public Door getDoorById(Long id) {
        Optional<Door> doorOptional = doorRepository.findById(id);
        if (doorOptional.isEmpty())
            throw new ObjectNotFoundException("Porte non trouvé avec l'ID : " + id);
        return doorOptional.get();
    }

    public Door getDoorByOrder(Long id) {
        Optional<Door> doorOptional = doorRepository.findByDoorNumber(Math.toIntExact(id));
        if (doorOptional.isEmpty())
            throw new ObjectNotFoundException("Porte non trouvé avec l'ID : " + id);
        return doorOptional.get();
    }
}
