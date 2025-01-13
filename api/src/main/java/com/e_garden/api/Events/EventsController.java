package com.e_garden.api.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

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

    // Récupération de tous les événements (si nécessaire)
    @GetMapping
    public Iterable<Events> getAllEvents() {
        return eventsService.getAllEvents();
    }

    // Récupération paginée des événements (10 par 10 par défaut)
    @GetMapping("/paginated")
    public ResponseEntity<Page<Events>> getPaginatedEvents(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Page<Events> eventsPage = eventsService.getPaginatedEvents(page, size);
        return ResponseEntity.ok(eventsPage);
    }


    @PostMapping
    public Events createEvents(@RequestBody Events events) {
        return eventsService.saveEvents(events);
    }

    // Permet de MAJ un event existant -> Utile ? (TODO : Vérifier si cette partie est utile)
    @PutMapping("/{id}")
    public ResponseEntity<Events> updateEvents(@PathVariable Long id, @RequestBody Events eventsDetails) {
        return eventsService.getEventsById(id).map(existingEvent -> {
            existingEvent.setTitle(eventsDetails.getTitle());
            existingEvent.setDescription(eventsDetails.getDescription());
            existingEvent.setCreatedAt(eventsDetails.getCreatedAt());
            Events updatedEvent = eventsService.saveEvents(existingEvent);
            return ResponseEntity.ok(updatedEvent);
        }).orElseGet(() -> ResponseEntity.notFound().build());
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

    @PostMapping("/door/{id}")
    public ResponseEntity<Events> saveEntry(@PathVariable Long id) {
        return null;
        // TODO :

    }

}
