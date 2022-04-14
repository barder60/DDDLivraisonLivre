package org.example.use_case;

import org.example.model.Admin;
import org.example.model.AdminRepository;
import org.example.model.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ConfirmEventTest {
    ConfirmEvent confirmEvent;

    EventRepository fakeEvents;
    AdminRepository fakeAdmins;

    @BeforeEach
    void setup() {
        fakeEvents = new FakeEventRepository();
        fakeAdmins = new FakeAdminRepository();
        confirmEvent = new ConfirmEvent(fakeEvents, fakeAdmins);
    }

    @Test
    void when_event_id_not_found_should_throw_event_not_found_exception() {
        var notExistEventId = 2513L;

        assertThatThrownBy(() -> confirmEvent.confirm(notExistEventId))
                .isExactlyInstanceOf(EventNotFoundException.class);
    }

    @Test
    void when_admin_already_present_in_event_same_date_but_another_location_should_be_remove_to_current_event() {

    }

    @Test
    void should_find_event_by_id_and_admins_and_contributors_by_ids_and_check_if_not_in_other_event() {
        confirmEvent.confirm(1L);

    }
}