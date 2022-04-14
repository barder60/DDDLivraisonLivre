package org.example.use_case;

import org.example.model.Event;
import org.example.model.EventId;
import org.example.model.EventRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeEventRepository implements EventRepository {
    private List<Event> events = new ArrayList<>();

    public FakeEventRepository() {
        this.events.add(new Event(
                new EventId(1L),
                LocalDate.of(2012, 12, 12),
                "location1",
                "description1",
                false,
                new ArrayList<>(List.of(1L)),
                new ArrayList<>(List.of(1L))
        ));
        this.events.add(new Event(
                new EventId(2L),
                LocalDate.of(2012, 12, 12),
                "location2",
                "description1",
                false,
                new ArrayList<>(List.of(2L)),
                new ArrayList<>(List.of(2L))
        ));
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
                event -> event.getEventId().equals(new EventId(eventId))
        ).findFirst();
    }

    @Override
    public List<Event> findAllByDate(LocalDate date) {
        return events.stream()
                .filter(event -> event.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Event event) {
        events.removeIf(currentEvent -> currentEvent.getEventId().equals(event.getEventId()));

        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }
}
