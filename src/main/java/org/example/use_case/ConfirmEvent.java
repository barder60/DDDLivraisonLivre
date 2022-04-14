package org.example.use_case;

import org.example.model.AdminRepository;
import org.example.model.Event;
import org.example.model.EventRepository;

import java.time.LocalDate;
import java.util.Optional;

public class ConfirmEvent {
    private final EventRepository eventRepository;
    private final AdminRepository adminRepository;

    public ConfirmEvent(
            EventRepository eventRepository,
            AdminRepository adminRepository
    ) {
        this.eventRepository = eventRepository;
        this.adminRepository = adminRepository;
    }

    public void confirm(Long eventId) {
        var maybeEvent = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);

    }
}
