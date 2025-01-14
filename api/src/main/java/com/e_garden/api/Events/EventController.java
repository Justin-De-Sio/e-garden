package com.e_garden.api.Events;

import com.e_garden.api.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/paginated")
    public ResponseEntity<PageDTO<EventDTO>> getPaginatedEvents(@RequestParam(defaultValue = "10") Integer size,
                                                                @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(eventService.getPaginatedEvents(page, size));
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
        event.setTitle("Enregistrement de passage");
        event.setEventType(0);
        event.setDoorNumber(Math.toIntExact(id));

        // TODO sauvegarder l'utilisateurs courant
        //UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //if (!Objects.equals(userService.getUserByEmail(user.getUsername()).getId(), id))
        //  return ResponseEntity.notFound().build();
        //event.setUser(user);
        return ResponseEntity.ok(eventService.saveEvents(event));
    }
}
