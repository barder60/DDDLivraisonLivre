package org.example.model;

import java.time.LocalDate;
import java.util.List;

public interface Events {
    List<Event>  findAll();

    Boolean existsByDateAndLocation(LocalDate date, String location);

    Event create(Event event);
}
