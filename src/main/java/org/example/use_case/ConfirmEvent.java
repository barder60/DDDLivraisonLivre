package org.example.use_case;

import org.example.model.*;

import java.util.List;

public class ConfirmEvent {
    private final EventRepository eventRepository;
    private final AdminRepository adminRepository;
    private final ContributorRepository contributorRepository;
    private final NotificationRepository notificationRepository;

    public ConfirmEvent(
            EventRepository eventRepository,
            AdminRepository adminRepository,
            ContributorRepository contributorRepository,
            NotificationRepository notificationRepository) {
        this.eventRepository = eventRepository;
        this.adminRepository = adminRepository;
        this.contributorRepository = contributorRepository;
        this.notificationRepository = notificationRepository;
    }

    public void confirm(Long eventId) {
        var event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        var sameDayEvents = eventRepository.findAllByDate(event.getDate());
        var admins = adminRepository.findAllByIds(event.getAdminsIds());
        var contributors = contributorRepository.findAllByIds(event.getContributorsIds());

        Event confirmedEvent = confirmEvent(event, sameDayEvents);

        eventRepository.save(confirmedEvent);
        notifyAdminsAboutConfirmedEvent(admins, confirmedEvent);
        notifyContributorsAboutConfirmedEvent(contributors, confirmedEvent);
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
                new EventId(event.getEventId().getId()),
                event.getDate(),
                event.getLocation(),
                event.getDescription(),
                true,
                event.getAdminsIds(),
                event.getContributorsIds()
        );
    }

    private void notifyAdminsAboutConfirmedEvent(List<Admin> admins, Event confirmedEvent) {
        admins.forEach(admin ->
                notificationRepository.notifyAdmin(
                        admin.getUser().getUserId().getId(),
                        confirmedEvent
                )
        );
    }

    private void notifyContributorsAboutConfirmedEvent(List<Contributor> contributors, Event confirmedEvent) {
        contributors.forEach(contributor ->
                notificationRepository.notifyContributor(
                        contributor.getUser().getUserId().getId(),
                        contributor.getRole(),
                        confirmedEvent
                )
        );
    }
}
