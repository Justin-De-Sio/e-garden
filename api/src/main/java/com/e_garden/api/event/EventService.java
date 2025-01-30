package com.e_garden.api.event;

import com.e_garden.api.exception.ObjectNotFoundException;
import com.e_garden.api.log.Levels;
import com.e_garden.api.log.LogService;
import com.e_garden.api.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final LogService log;

    @Autowired
    public EventService(EventRepository eventRepository, LogService log) {
        this.eventRepository = eventRepository;
        this.log = log;
    }

    public PageDTO<EventDTO> getPaginatedEvents(int page, int size) {
        Page<Event> eventPage = eventRepository.findAll(PageRequest.of(page, size));
        List<EventDTO> eventDTOs = eventPage
                .getContent()
                .stream()
                .map(this::getEventDTOFromEvent)
                .toList();
        return (new PageDTO<>(
                eventDTOs,
                eventPage.getNumber(),
                eventPage.getSize(),
                eventPage.getTotalElements(),
                eventPage.getTotalPages()
        ));
    }

    public Event getEventsById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty())
            throw new ObjectNotFoundException("Event non trouvé avec l'ID : " + id);
        else
            return event.get();
    }

    public void saveEvents(Event event) {
        log.createLog(String.valueOf(Levels.EVENT), "Événement ajouté", event.toString());
        eventRepository.save(event);
    }

    public void deleteEvents(Long id) {
        eventRepository.deleteById(id);
        log.createLog(String.valueOf(Levels.EVENT), "Événement supprimé", "event id :" + id);
    }

    /**
     * Méthode privée permettant de convertir un objet Event en EventDTO
     * @param event l'Event
     * @return un EventDTO complet
     */
    private EventDTO getEventDTOFromEvent(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setEventType(event.getEventType());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setCreatedAt(event.getCreatedAt());
        eventDTO.setUserId(event.getUser() == null ? -1 : event.getUser().getId());
        String date = event.getCreatedAt().format(DateTimeFormatter.ofPattern("'le' dd MMMM yyyy 'à' HH'H'mm"));
        return switch (event.getEventType()) {
            case 0 -> {
                eventDTO.setDescription(event.getUser().getName() + " " +
                        event.getUser().getSurname() + " a badgé " + date +
                        " à la porte numéro : " + event.getDoorNumber() + ".");
                yield eventDTO;
            }
            case 1 -> {
                eventDTO.setDescription("La caméra a détecté un mouvement " + date + ".");
                yield eventDTO;
            }
            default -> {
                eventDTO.setDescription("Erreur interne, type d'événement non reconnu");
                yield eventDTO;
            }
        };
    }

    /**
     * Méthode afin de récupérer le nombre d'événements lié à utilisateur, soit le nombre de passages
     * sur les 30 derniers jours.
     * @return un entier
     */
    public int getCountOfEventTypeUserBadge() {
        return eventRepository.countAllByEventTypeAndCreatedAtGreaterThan(0, LocalDateTime.now().minusDays(30));
    }

}


