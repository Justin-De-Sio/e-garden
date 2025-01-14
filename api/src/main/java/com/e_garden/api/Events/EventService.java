package com.e_garden.api.Events;

import com.e_garden.api.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
                eventPage.getTotalElements()
        ));
    }

    public Optional<Event> getEventsById(Long id) {
        return eventRepository.findById(id);
    }

    public Event saveEvents(Event event) {
        // TODO ADD LOG
        return eventRepository.save(event);
    }

    public void deleteEvents(Long id) {
        // TODO ADD LOG
        eventRepository.deleteById(id);
    }

    private EventDTO getEventDTOFromEvent(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setEventType(event.getEventType());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setUserId(event.getUser() == null ? -1 : event.getUser().getId());
        String date = event.getCreatedAt().format(DateTimeFormatter.ofPattern("'le' dd MMMM yyyy 'à' HH'H'mm"));
        return switch (event.getEventType()) {
            case 0 -> {
                eventDTO.setDescription(event.getUser().getName() + " " +
                        event.getUser().getSurname() + "a badgé " + date +
                        " à la porte numéro : " + event.getDoorNumber() + ".");
                yield eventDTO;
            }
            case 1 -> {
                eventDTO.setDescription("La caméra a détecté un mouvement " + date + ".");
                yield eventDTO;
            }
            default -> {
                eventDTO.setDescription("erreur interne, type d'événement non reconnu");
                yield eventDTO;
            }
        };
    }

}


