package org.example.use_case;


import org.example.model.Event;
import org.example.model.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CreateEventTest {
    CreateEvent createEvent;

    EventRepository fakeEvents;

    @BeforeEach
    void setup() {
        fakeEvents = new FakeEventRepository();
        createEvent = new CreateEvent(fakeEvents);
    }

    @Test
    void should_throw_when_events_already_exists_by_date_and_location() {
        assertThatThrownBy(() -> createEvent.execute(
                LocalDate.of(2012, 12, 12),
                "location1",
                ""))
                .isExactlyInstanceOf(EventAlreadyExistException.class);
    }

    @Test
    void should_create_events_and_return_created_event() {
        var result = createEvent.execute(LocalDate.of(2011, 12, 12),
                "location1",
                "");

        var expected = new Event(0L, LocalDate.of(2011, 12, 12),
                "location1",
                "",
                false,
                null,
                null
        );

        assertThat(result).isEqualTo(expected);
    }
}