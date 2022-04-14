package org.example.use_case;

import org.example.model.Event;
import org.example.model.NotificationRepository;
import org.example.model.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeNotificationRepository implements NotificationRepository {

    private Map<String, Event> adminEmailEventMap = new HashMap<>();
    private Map<Map<String, Role>, Event> contributorEmailEventMap = new HashMap<>();

    @Override
    public void notifyAdmin(String adminEmail, Event event) {
        adminEmailEventMap.put(adminEmail, event);
    }

    @Override
    public void notifyContributor(String contributorEmail, Role role, Event event) {
        contributorEmailEventMap.put(Map.of(contributorEmail, role), event);
    }

    public Map<String, Event> getAdminEmailEventMap() {
        return adminEmailEventMap;
    }

    public Map<Map<String, Role>, Event> getContributorEmailEventMap() {
        return contributorEmailEventMap;
    }
}
