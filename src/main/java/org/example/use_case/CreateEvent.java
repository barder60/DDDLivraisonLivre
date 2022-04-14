package org.example.use_case;

import org.example.model.Event;
import org.example.model.EventId;
import org.example.model.EventRepository;

import java.time.LocalDate;

public class CreateEvent {
    private final EventRepository events;

    public CreateEvent(EventRepository events) {
        this.events = events;
    }

    public Event execute(LocalDate date, String location, String description) {
        if (events.existsByDateAndLocation(date, location)) {
            throw new EventAlreadyExistException();
        }
        var eventToCreate = new Event(new EventId(0L), date, location, description, false, null, null);

        return events.create(eventToCreate);
    }
}
