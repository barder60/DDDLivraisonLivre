package org.example.model;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Event {
    private final EventId eventId;
    private final LocalDate date;
    private final String location;
    private final String description;
    private final boolean confirmed;
    private final List<Long> adminsIds;
    private final List<Long> contributorsIds;

    public Event(EventId id, LocalDate date, String location, String description, boolean confirmed, List<Long> adminsIds, List<Long> contributorsIds) {
        this.eventId = id;
        this.date = date;
        this.location = location;
        this.description = description;
        this.confirmed = confirmed;
        this.adminsIds = adminsIds;
        this.contributorsIds = contributorsIds;
    }

    public EventId getEventId() {
        return eventId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public List<Long> getAdminsIds() {
        return adminsIds;
    }

    public List<Long> getContributorsIds() {
        return contributorsIds;
    }

    public boolean hasAtLeastOneAdminAlreadyInEvent(List<Long> adminsIdsOtherEvent) {
        return getAdminsIds().stream().noneMatch(adminsIdsOtherEvent::contains);
    }

    public boolean hasAtLeastOneContributorAlreadyIn(List<Long> contributorIdsOtherEvent) {
        return getContributorsIds().stream().noneMatch(contributorIdsOtherEvent::contains);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }
}
