package org.example.use_case;


import org.example.model.Event;
import org.example.model.Events;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateEventTest {
    CreateEvent createEvent;

    Events mockEvents;

    @BeforeEach
    void setup() {
        mockEvents = mock(Events.class);
        createEvent = new CreateEvent(mockEvents);
    }

    @Test
    void should_check_if_event_exits_with_same_location_and_date() {
        var day = LocalDate.now();
        var location = "France";

        createEvent.execute(day, location, "description");

        verify(mockEvents, times(1)).existsByDateAndLocation(day, location);
    }

    @Test
    void when_event_exists_with_same_location_and_date_should_throw_error() {
        var day = LocalDate.now();
        var location = "France";

        when(mockEvents.existsByDateAndLocation(day, location)).thenReturn(true);

        assertThatThrownBy(()-> createEvent.execute(day, location, "description"))
                .isExactlyInstanceOf(EventAlreadyExistException.class);
    }

    @Test
    void when_event_not_exists_should_create_event() {
        var day = LocalDate.now();
        var location = "France";
        var description = "description";

        when(mockEvents.existsByDateAndLocation(day, location)).thenReturn(false);

        createEvent.execute(day, location, description);

        var expectedEvent = new Event(day, location, description);
        verify(mockEvents, times(1)).create(expectedEvent);
    }

    @Test
    void when_event_create_should_return_saved_event() {
        var day = LocalDate.now();
        var location = "France";
        var description = "description";

        when(mockEvents.existsByDateAndLocation(day, location)).thenReturn(false);
        var eventToSave = new Event(day, location, description);
        var savedEvent = new Event(day, location, description);
        when(mockEvents.create(eventToSave)).thenReturn(savedEvent);

        var result = createEvent.execute(day, location, description);

        assertThat(result).isEqualTo(savedEvent);
    }
}