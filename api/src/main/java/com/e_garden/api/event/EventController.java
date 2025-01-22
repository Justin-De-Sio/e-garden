package com.e_garden.api.event;

import com.e_garden.api.PageDTO;
import com.e_garden.api.report.Report;
import com.e_garden.api.report.ReportService;
import com.e_garden.api.user.User;
import com.e_garden.api.user.UserPrincipal;
import com.e_garden.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@CrossOrigin
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

    //  TODO Lors du badgeage via le site web de notre application,
    //   si la personne est autorisée, un indicateur vert apparaîtra.
    //   En revanche, l’indicateur devient rouge si la personne n’est pas
    //   autorisée à accéder au potager.

    @GetMapping("/door/{id}")
    public ResponseEntity<Report> saveEntry(@PathVariable Long id) {
        Event event = new Event();
        event.setTitle("Enregistrement de passage");
        event.setEventType(0);
        event.setDoorNumber(Math.toIntExact(id));

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        if (userByEmail == null)
            return ResponseEntity.notFound().build();
        event.setUser(userByEmail);
        eventService.saveEvents(event);
        return ResponseEntity.ok(reportService.saveReport(new Report(userByEmail)));
    }

    @GetMapping("/statistique")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Integer> getEventStatistique() {
        return ResponseEntity.ok(eventService.getEventTypeUserBadgeCount());
    }
}
