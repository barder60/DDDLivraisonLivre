package org.example.model;

public interface NotificationRepository {
    void notifyAdmin(String adminEmail, Event event);
    void notifyContributor(String contributorEmail, Role role, Event event);
}
