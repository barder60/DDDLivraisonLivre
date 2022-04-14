package org.example.use_case;

import org.example.model.Event;
import org.example.model.EventRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FakeEventRepository implements EventRepository {
    private final List<Event> events = List.of(
            new Event(
                    1L,
                    LocalDate.of(2012, 12, 12),
                    "location1",
                    "description1"
            )
    );

    @Override
    public List<Event> findAll() {
        return events;
    }

    @Override
    public Boolean existsByDateAndLocation(LocalDate date, String location) {
        return events.stream()
                .anyMatch(
                        event -> event.getDate().equals(date) && event.getLocation().equals(location)
                );
    }

    @Override
    public Event create(Event event) {
        return event;
    }

    @Override
    public Optional<Event> findById(Long eventId) {
        return events.stream().filter(
                event -> event.getId().equals(eventId)
        ).findFirst();
    }
}