package com.e_garden.api.event;

import com.e_garden.api.PageDTO;
import com.e_garden.api.door.DoorService;
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

/**
 * Le type Event controller.
 */
@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventController {
    private final EventService eventService;
    private final UserService userService;
    private final ReportService reportService;
    private final DoorService doorService;

    /**
     * Instancie un nouveau Event controller.
     *
     * @param eventService  le event service
     * @param userService   le user service
     * @param reportService le report service
     * @param doorService   le door service
     */
    @Autowired
    public EventController(EventService eventService, UserService userService, ReportService reportService, DoorService doorService) {
        this.eventService = eventService;
        this.userService = userService;
        this.reportService = reportService;
        this.doorService = doorService;
    }

    /**
     * Gets events by identifiant.
     *
     * @param id l'identifiant
     * @return l'events l'identifiant
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventsById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventsById(id));
    }

    /**
     * Gets paginated events.
     *
     * @param size the size
     * @param page the page
     * @return le paginated events
     */
    @GetMapping("/paginated")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<PageDTO<EventDTO>> getPaginatedEvents(@RequestParam(defaultValue = "10") Integer size,
                                                                @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(eventService.getPaginatedEvents(page, size));
    }

    /**
     * Delete events response entity.
     *
     * @param id l'identifiant
     * @return le response entity
     */
    @DeleteMapping("/{id}")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Void> deleteEvents(@PathVariable Long id) {
        eventService.deleteEvents(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Méthode qui permet d'enregistrer un passage.
     * La méthode enregistre un événement lié à l'utilisateur et crée un rapport non rempli lié à l'utilisateur.
     *
     * @param id identifiant de la porte
     * @return un objet rapport non rempli
     */
    @GetMapping("/door/{id}")
    public ResponseEntity<Report> saveEntry(@PathVariable Long id) {
        Event event = new Event();
        event.setTitle("Enregistrement de passage");
        event.setEventType(0);
        event.setDoor(doorService.getDoorByOrder(id));

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userByEmail = userService.getUserByEmail(user.getUsername());
        event.setUser(userByEmail);
        eventService.saveEvents(event);
        return ResponseEntity.ok(reportService.saveReport(new Report(userByEmail)));
    }

    /**
     * Gets event statistique.
     *
     * @return l'event statistique
     */
    @GetMapping("/statistique")
    @Secured({"ADMINISTRATEUR"})
    public ResponseEntity<Integer> getEventStatistique() {
        return ResponseEntity.ok(eventService.getCountOfEventTypeUserBadge());
    }
}
