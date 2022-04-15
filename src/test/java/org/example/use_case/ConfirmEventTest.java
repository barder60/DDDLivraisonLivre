package org.example.use_case;

import org.example.model.Event;
import org.example.model.EventId;
import org.example.model.NotificationRepository;
import org.example.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ConfirmEventTest {
    ConfirmEvent confirmEvent;

    FakeEventRepository fakeEvents;
    FakeAdminDao fakeAdmins;
    FakeContributorDao fakeContributors;

    NotificationRepository mockNotifyRepository;

    @BeforeEach
    void setup() {
        fakeEvents = new FakeEventRepository();
        fakeAdmins = new FakeAdminDao();
        fakeContributors = new FakeContributorDao();
        mockNotifyRepository = mock(NotificationRepository.class);
        confirmEvent = new ConfirmEvent(fakeEvents, fakeAdmins, fakeContributors, mockNotifyRepository);
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

    @Test
    void when_event_confirmed_should_notify_admins_link_to_event() {
        confirmEvent.confirm(1L);

        var expectedEvent = new Event(
                new EventId(1L),
                LocalDate.of(2012, 12, 12),
                "location1",
                "description1",
                false,
                new ArrayList<>(List.of(1L)),
                new ArrayList<>(List.of(1L))
        );
        verify(mockNotifyRepository, times(1))
                .notifyAdmin("admin@email.com", expectedEvent);
    }

    @Test
    void when_event_confirmed_should_notify_contributor_with_role_link_to_event() {
        confirmEvent.confirm(1L);

        var expectedEvent = new Event(
                new EventId(1L),
                LocalDate.of(2012, 12, 12),
                "location1",
                "description1",
                false,
                new ArrayList<>(List.of(1L)),
                new ArrayList<>(List.of(1L))
        );
        verify(mockNotifyRepository, times(1))
                .notifyContributor("contri@butor.com", Role.DRIVER, expectedEvent);
    }

    @Test
    void when_event_has_not_one_conductor_should_throw_exception() {
        // remove driver
        fakeContributors.removeContributorById(1L);

        assertThatThrownBy(() -> confirmEvent.confirm(1L)).isExactlyInstanceOf(NoDriverInEventException.class);
    }
}