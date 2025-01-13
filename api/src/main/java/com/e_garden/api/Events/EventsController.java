package com.e_garden.api.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")

public class EventsController {
    private final EventsService eventsService;

    @Autowired
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Events> getEventsById(@PathVariable Long id) {
        return eventsService.getEventsById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Events> getAllEvents() {
        return eventsService.getAllEvents();
    }

    @PostMapping
    public Events createEvents(@RequestBody Events events) {
        return eventsService.saveEvents(events);
    }

    // Permet de MAJ un event existant -> Utile ? (TODO : Vérifier si cette partie est utile)
    @PutMapping("/{id}")
    public ResponseEntity<Events> updateEvents(@PathVariable Long id, @RequestBody Events eventsDetails) {
        // TODO: Implémenter la logique de mise à jour
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvents(@PathVariable Long id) {
        if (eventsService.getEventsById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            eventsService.deleteEvents(id);
            return ResponseEntity.noContent().build();
        }
    }
}
