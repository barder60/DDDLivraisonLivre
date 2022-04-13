package org.example.model;

import java.time.LocalDate;
import java.util.Objects;

public class Event {
    private LocalDate date;
    private String location;
    private String description;

    public Event(LocalDate date, String location, String description) {
        this.date = date;
        this.location = location;
        this.description = description;
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
