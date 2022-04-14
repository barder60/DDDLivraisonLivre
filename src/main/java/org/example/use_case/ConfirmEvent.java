package org.example.use_case;

import org.example.model.AdminRepository;
import org.example.model.ContributorRepository;
import org.example.model.Event;
import org.example.model.EventRepository;

import java.util.List;

public class ConfirmEvent {
    private final EventRepository eventRepository;
    private final AdminRepository adminRepository;
    private final ContributorRepository contributorRepository;

    public ConfirmEvent(
            EventRepository eventRepository,
            AdminRepository adminRepository,
            ContributorRepository contributorRepository
    ) {
        this.eventRepository = eventRepository;
        this.adminRepository = adminRepository;
        this.contributorRepository = contributorRepository;
    }

    public void confirm(Long eventId) {
        var event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        var sameDayEvents = eventRepository.findAllByDate(event.getDate());

        Event confirmedEvent = confirmEvent(event, sameDayEvents);

        eventRepository.save(confirmedEvent);
    }

    private Event confirmEvent(Event event, List<Event> sameDayEvents) {
        var adminsIds = event.getAdminsIds();
        sameDayEvents.stream()
                .filter(sameDayEvent -> sameDayEvent.hasAtLeastOneAdminAlreadyInEvent(adminsIds))
                .findAny()
                .orElseThrow(AdminNotAvailableInEventException::new);

        var contributorIds = event.getContributorsIds();
        sameDayEvents.stream()
                .filter(sameDayEvent -> sameDayEvent.hasAtLeastOneContributorAlreadyIn(contributorIds))
                .findAny()
                .orElseThrow(ContributorNotAvailableInEventException::new);

        return createConfirmedEvent(event);
    }

    private Event createConfirmedEvent(Event event) {
        return new Event(
                event.getId(),
                event.getDate(),
                event.getLocation(),
                event.getDescription(),
                true,
                event.getAdminsIds(),
                event.getContributorsIds()
        );
    }
}
