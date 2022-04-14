package org.example.use_case;

import org.example.model.Event;
import org.example.model.EventRepository;

import java.time.LocalDate;
import java.util.Optional;

public class ConfirmEvent {
    private final EventRepository eventRepository;

    public ConfirmEvent(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void confirm(Long eventId) {
        var maybeEvent = eventRepository.findById(eventId);

    }
}
