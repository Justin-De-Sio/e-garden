package com.e_garden.api.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventsService {
    private final EventsRepository eventsRepository;

    /*
    @Autowired
    public Page<Events> getEvents(int page, int size) {
        return eventsRepository.findAll(PageRequest.of(page, size));
    }

     */

    @Autowired
    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    public List<Events> getAllEvents() {
        return eventsRepository.findAll();
    }

    public Optional<Events> getEventsById(Long id) {
        return eventsRepository.findById(id);
    }

    public Events saveEvents(Events events) {
        return eventsRepository.save(events);
    }

    public void deleteEvents(Long id) {
        eventsRepository.deleteById(id);
    }
}


