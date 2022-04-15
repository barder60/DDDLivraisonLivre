package org.example.use_case;

import org.example.model.*;

import java.util.List;

public class ConfirmEvent {
    private final EventRepository eventRepository;
    private final AdminDao adminDao;
    private final ContributorDao contributorDao;
    private final NotificationRepository notificationRepository;

    public ConfirmEvent(
            EventRepository eventRepository,
            AdminDao adminDao,
            ContributorDao contributorDao,
            NotificationRepository notificationRepository) {
        this.eventRepository = eventRepository;
        this.adminDao = adminDao;
        this.contributorDao = contributorDao;
        this.notificationRepository = notificationRepository;
    }

    public void confirm(Long eventId) {
        var event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
        var sameDayEvents = eventRepository.findAllByDate(event.getDate());
        var admins = adminDao.findAllByIds(event.getAdminsIds());
        var contributors = contributorDao.findAllByIds(event.getContributorsIds());

        Event confirmedEvent = confirmEvent(event, sameDayEvents, contributors);

        eventRepository.update(confirmedEvent);
        notifyAdminsAboutConfirmedEvent(admins, confirmedEvent);
        notifyContributorsAboutConfirmedEvent(contributors, confirmedEvent);
    }

    private Event confirmEvent(Event event, List<Event> sameDayEvents, List<Contributor> contributors) {
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

        contributors.stream()
            .filter(Contributor::isDriver)
            .findAny()
            .orElseThrow(NoDriverInEventException::new);

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
                        admin.getUser().getUserEmailId().getId(),
                        confirmedEvent
                )
        );
    }

    private void notifyContributorsAboutConfirmedEvent(List<Contributor> contributors, Event confirmedEvent) {
        contributors.forEach(contributor ->
                notificationRepository.notifyContributor(
                        contributor.getUser().getUserEmailId().getId(),
                        contributor.getRole(),
                        confirmedEvent
                )
        );
    }
}
