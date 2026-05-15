package com.bookingsystem.event.service;

import com.bookingsystem.event.factory.EventFactory;
import com.bookingsystem.event.factory.EventFactoryImpl;
import com.bookingsystem.event.model.Event;
import com.bookingsystem.event.repository.EventRepository;
import com.bookingsystem.shared.enums.EventStatus;
import com.bookingsystem.shared.enums.EventType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Event Service — Business Logic
 *
 * هنا بيحصل كل اللي يخص الـ events:
 * - إنشاء event عن طريق الـ Factory
 * - تعديل event
 * - حذف event
 * - جلب events
 */
public class EventService {

    private final EventRepository repository;
    private final EventFactory factory;

    public EventService() {
        this.repository = new EventRepository();
        this.factory = new EventFactoryImpl();
    }

    // Constructor injection (للـ testing)
    public EventService(EventRepository repository, EventFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    // ===== Create =====

    /**
     * إنشاء event جديد عن طريق الـ Factory Pattern
     */
    public Event createEvent(EventType type, Map<String, Object> params) {
        Event event = factory.createEvent(type, params);
        repository.save(event);
        System.out.println("✅ Event created: " + event.getTitle() + " [" + event.getEventId() + "]");
        return event;
    }

    // ===== Read =====

    public Optional<Event> getEventById(String eventId) {
        return repository.findById(eventId);
    }

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public List<Event> getEventsByType(EventType type) {
        return repository.findByType(type);
    }

    public List<Event> getAvailableEvents() {
        return repository.findAvailable();
    }

    public List<Event> getEventsByOrganizer(String organizerId) {
        return repository.findByOrganizer(organizerId);
    }

    // ===== Update =====

    public boolean updateEventTitle(String eventId, String newTitle) {
        Optional<Event> opt = repository.findById(eventId);
        if (opt.isPresent()) {
            opt.get().setTitle(newTitle);
            repository.save(opt.get());
            System.out.println("✏️ Event title updated: " + newTitle);
            return true;
        }
        System.out.println("❌ Event not found: " + eventId);
        return false;
    }

    public boolean updateEventStatus(String eventId, EventStatus newStatus) {
        Optional<Event> opt = repository.findById(eventId);
        if (opt.isPresent()) {
            opt.get().setStatus(newStatus);
            repository.save(opt.get());
            System.out.println("🔄 Event status updated to: " + newStatus);
            return true;
        }
        System.out.println("❌ Event not found: " + eventId);
        return false;
    }

    public boolean updateEventLocation(String eventId, String newLocation) {
        Optional<Event> opt = repository.findById(eventId);
        if (opt.isPresent()) {
            opt.get().setLocation(newLocation);
            repository.save(opt.get());
            return true;
        }
        return false;
    }

    // ===== Delete =====

    public boolean deleteEvent(String eventId) {
        if (repository.exists(eventId)) {
            repository.delete(eventId);
            System.out.println("🗑️ Event deleted: " + eventId);
            return true;
        }
        System.out.println("❌ Event not found: " + eventId);
        return false;
    }

    // ===== Cancel =====

    public boolean cancelEvent(String eventId) {
        return updateEventStatus(eventId, EventStatus.CANCELLED);
    }
}