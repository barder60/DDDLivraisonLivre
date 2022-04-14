package org.example.use_case;

import org.example.model.AdminRepository;
import org.example.model.ContributorRepository;
import org.example.model.Event;
import org.example.model.EventRepository;

import java.util.Optional;

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

        var adminsIds = event.getAdminsIds();
        var contributorIds = event.getContributorsIds();
        var sameDayEvents = eventRepository.findAllByDate(event.getDate());

        sameDayEvents.stream()
                .filter(sameDayEvent -> sameDayEvent.getAdminsIds().stream().noneMatch(adminsIds::contains))
                .findAny()
                .orElseThrow(AdminNotAvailableInEventException::new);

        // pareil pour contribut
        sameDayEvents.stream()
                .filter(sameDayEvent -> sameDayEvent.getContributorsIds().stream().noneMatch(contributorIds::contains))
                .findAny()
                .orElseThrow(ContributorNotAvailableInEventException::new);



        // mettre à jour l'event
        var confirmedEvent = new Event(
                event.getId(),
                event.getDate(),
                event.getLocation(),
                event.getDescription(),
                true,
                event.getAdminsIds(),
                event.getContributorsIds()
        );
        eventRepository.save(confirmedEvent);
    }
}
