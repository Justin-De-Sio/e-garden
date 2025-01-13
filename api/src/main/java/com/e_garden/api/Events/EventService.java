package com.e_garden.api.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Page<Event> getPaginatedEvents(int page, int size) {
        return eventRepository.findAll(PageRequest.of(page, size));
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

}


