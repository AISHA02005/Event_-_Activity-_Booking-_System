package com.bookingsystem.event.repository;

import com.bookingsystem.event.model.Event;
import com.bookingsystem.shared.enums.EventStatus;
import com.bookingsystem.shared.enums.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Event Repository — In-Memory Storage
 *
 * بتخزن وتجيب الـ events (بدل Database في الوقت الحالي)
 * لو حابب تضيف Database بعدين، بتغير الـ implementation هنا بس
 */
public class EventRepository {

    // eventId → Event
    private static final Map<String, Event> storage = new HashMap<>();

    // ===== CRUD =====

    public void save(Event event) {
        storage.put(event.getEventId(), event);
    }

    public Optional<Event> findById(String eventId) {
        return Optional.ofNullable(storage.get(eventId));
    }

    public List<Event> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void delete(String eventId) {
        storage.remove(eventId);
    }

    public boolean exists(String eventId) {
        return storage.containsKey(eventId);
    }

    // ===== Filters =====

    public List<Event> findByType(EventType type) {
        return storage.values().stream()
                .filter(e -> e.getType() == type)
                .collect(Collectors.toList());
    }

    public List<Event> findByStatus(EventStatus status) {
        return storage.values().stream()
                .filter(e -> e.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Event> findByOrganizer(String organizerId) {
        return storage.values().stream()
                .filter(e -> e.getOrganizerId().equals(organizerId))
                .collect(Collectors.toList());
    }

    public List<Event> findAvailable() {
        return storage.values().stream()
                .filter(e -> e.getAvailableSeats() > 0 && e.getStatus() == EventStatus.UPCOMING)
                .collect(Collectors.toList());
    }
}