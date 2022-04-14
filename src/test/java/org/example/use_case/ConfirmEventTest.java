package org.example.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ConfirmEventTest {
    ConfirmEvent confirmEvent;

    FakeEventRepository fakeEvents;
    FakeAdminRepository fakeAdmins;
    FakeContributorRepository fakeContributors;
    FakeNotificationRepository fakeNotificationRepository;

    @BeforeEach
    void setup() {
        fakeEvents = new FakeEventRepository();
        fakeAdmins = new FakeAdminRepository();
        fakeContributors = new FakeContributorRepository();
        fakeNotificationRepository = new FakeNotificationRepository();
        confirmEvent = new ConfirmEvent(fakeEvents, fakeAdmins, fakeContributors, fakeNotificationRepository);
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

        var adminEmailMap = fakeNotificationRepository.getAdminEmailEventMap();

        assertThat(adminEmailMap.size()).isEqualTo(1);
        var maybeEvent = fakeEvents.findById(1L);
        assertThat(maybeEvent).isNotEmpty();
        var concernedEvent = maybeEvent.get();
        var adminsId = concernedEvent.getAdminsIds();
        var admins = fakeAdmins.findAllByIds(adminsId);
        admins.forEach(admin -> {
            var expectedEvent = adminEmailMap.get(admin.getEmail());
            assertThat(expectedEvent).isEqualTo(concernedEvent);
        });
    }

}