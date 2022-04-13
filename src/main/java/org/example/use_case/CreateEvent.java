package org.example.use_case;

import org.example.model.Event;
import org.example.model.Events;

import java.time.LocalDate;

public class CreateEvent {
    private final Events events;

    public CreateEvent(Events events) {
        this.events = events;
    }

    public Event execute(LocalDate date, String location, String description) {
        if (events.existsByDateAndLocation(date, location)) {
            throw new EventAlreadyExistException();
        }
        var eventToCreate = new Event(date, location, description);

        return events.create(eventToCreate);
    }
}
