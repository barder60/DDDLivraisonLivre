package org.example.use_case;

import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ConfirmEventTest {
    ConfirmEvent confirmEvent;

    FakeEventRepository fakeEvents;
    FakeAdminRepository fakeAdmins;
    FakeContributorRepository fakeContributors;

    @BeforeEach
    void setup() {
        fakeEvents = new FakeEventRepository();
        fakeAdmins = new FakeAdminRepository();
        fakeContributors = new FakeContributorRepository();
        confirmEvent = new ConfirmEvent(fakeEvents, fakeAdmins, fakeContributors);
    }

    @Test
    void when_event_id_not_found_should_throw_event_not_found_exception() {
        var notExistEventId = 2513L;

        assertThatThrownBy(() -> confirmEvent.confirm(notExistEventId))
                .isExactlyInstanceOf(EventNotFoundException.class);
    }

    @Test
    void when_admin_already_present_in_event_same_date_but_another_location_should_throw_exception() {
        var events = fakeEvents.getEvents();
        events.get(1).getAdminsIds().add(1L);

        assertThatThrownBy(() -> confirmEvent.confirm(1L))
                .isExactlyInstanceOf(AdminNotAvailableInEventException.class);
    }


    @Test
    void when_contributor_already_present_in_event_same_date_but_another_location_should_throw_exception() {
        var events = fakeEvents.getEvents();
        events.get(1).getContributorsIds().add(1L);

        assertThatThrownBy(() -> confirmEvent.confirm(1L))
                .isExactlyInstanceOf(ContributorNotAvailableInEventException.class);
    }

    @Test
    void should_find_event_by_id_and_admins_and_contributors_should_confirm_event() {
        var event = fakeEvents.findById(1L);
        assertThat(event.isPresent()).isTrue();
        assertThat(event.get().isConfirmed()).isFalse();

        confirmEvent.confirm(1L);

        var afterConfirmedEvent = fakeEvents.findById(1L);
        assertThat(afterConfirmedEvent.isPresent()).isTrue();
        assertThat(afterConfirmedEvent.get().isConfirmed()).isTrue();
    }

}