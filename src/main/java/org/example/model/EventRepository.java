package org.example.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository {
    List<Event>  findAll();

    Boolean existsByDateAndLocation(LocalDate date, String location);

    Event create(Event event);

    Optional<Event> findById(Long eventId);
}
