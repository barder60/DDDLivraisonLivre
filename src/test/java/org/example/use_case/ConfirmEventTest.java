package org.example.use_case;

import org.example.model.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmEventTest {
    ConfirmEvent confirmEvent;

    EventRepository fakeEvents;

    @BeforeEach
    void setup() {
        fakeEvents = new FakeEventRepository();
        confirmEvent = new ConfirmEvent(fakeEvents);
    }

    @Test
    void should_find_event_by_id_and_admins_and_contributors_by_ids_and_check_if_not_in_other_event() {
        confirmEvent.confirm(1L);
    }
}