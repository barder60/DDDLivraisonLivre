package org.example.model;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Event {
    private Long id = 0L;
    private LocalDate date;
    private String location;
    private String description;
    private boolean confirmed;
    private List<Long> adminsIds;
    private List<Long> contributorsIds;

    public Event(Long id, LocalDate date, String location, String description) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.description = description;
        this.confirmed = false;
    }

    public Event(LocalDate date, String location, String description) {
        this.date = date;
        this.location = location;
        this.description = description;
        this.confirmed = false;
    }

    public Event(LocalDate date, String location, String description, boolean confirmed) {
        this.date = date;
        this.location = location;
        this.description = description;
        this.confirmed = confirmed;
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(date, event.date) && Objects.equals(location, event.location) && Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, location, description);
    }
}
