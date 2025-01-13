package com.e_garden.api.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "*")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventsById(@PathVariable Long id) {
        return eventService.getEventsById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/paginated/{nb}")
    public ResponseEntity<Page<Event>> getPaginatedEvents(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @PathVariable String nb) {
        Page<Event> eventsPage = eventService.getPaginatedEvents(page, size);
        return ResponseEntity.ok(eventsPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvents(@PathVariable Long id) {
        if (eventService.getEventsById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            eventService.deleteEvents(id);
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/door/{id}")
    public ResponseEntity<Event> saveEntry(@PathVariable Long id) {
        Event event = new Event();
        event.setTitle("Door Passed");
        event.setDescription("User with the badge ID : " + id + " passed the door.");

        // TODO sauvegarder l'utilisateurs courant
        //UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //if (!Objects.equals(userService.getUserByEmail(user.getUsername()).getId(), id))
        //  return ResponseEntity.notFound().build();
        //event.setUser(user);
        return ResponseEntity.ok(eventService.saveEvents(event));
    }
}
