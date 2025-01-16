package com.e_garden.api.Events;

import com.e_garden.api.PageDTO;
import com.e_garden.api.Report.Report;
import com.e_garden.api.Report.ReportService;
import com.e_garden.api.User.User;
import com.e_garden.api.User.UserPrincipal;
import com.e_garden.api.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "*")
public class EventController {
    private final EventService eventService;
    private final UserService userService;
    private final ReportService reportService;

    @Autowired
    public EventController(EventService eventService, UserService userService, ReportService reportService) {
        this.eventService = eventService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE", "UTILISATEUR"})
    public ResponseEntity<Event> getEventsById(@PathVariable Long id) {
        return eventService.getEventsById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/paginated")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<PageDTO<EventDTO>> getPaginatedEvents(@RequestParam(defaultValue = "10") Integer size,
                                                                @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(eventService.getPaginatedEvents(page, size));
    }

    @DeleteMapping("/{id}")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Void> deleteEvents(@PathVariable Long id) {
        if (eventService.getEventsById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            eventService.deleteEvents(id);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/door/{id}")
    @Secured({"ADMINISTRATEUR", "RESPONSABLE", "UTILISATEUR"})
    public ResponseEntity<Event> saveEntry(@PathVariable Long id) {
        Event event = new Event();
        event.setTitle("Enregistrement de passage");
        event.setEventType(0);
        event.setDoorNumber(Math.toIntExact(id));

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        if (userByEmail == null)
            return ResponseEntity.notFound().build();
        event.setUser(userByEmail);
        reportService.saveReport(new Report(userByEmail));
        return ResponseEntity.ok(eventService.saveEvents(event));
    }
}
