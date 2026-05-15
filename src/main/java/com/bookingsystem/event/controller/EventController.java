package com.bookingsystem.event.controller;

import com.bookingsystem.event.model.Event;
import com.bookingsystem.event.service.EventService;
import com.bookingsystem.shared.enums.EventStatus;
import com.bookingsystem.shared.enums.EventType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Event Controller — Entry Point
 *
 * الـ Controller بيستقبل الطلبات ويبعتها للـ Service
 * (في JavaFX هيتربط بالـ FXML, في API هيبقى REST Controller)
 */
public class EventController {

    private final EventService eventService;

    public EventController() {
        this.eventService = new EventService();
    }

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // ===== Create =====

    public Event createEvent(EventType type, Map<String, Object> params) {
        return eventService.createEvent(type, params);
    }

    // ===== Read =====

    public void displayEvent(String eventId) {
        Optional<Event> event = eventService.getEventById(eventId);
        if (event.isPresent()) {
            System.out.println(event.get().getEventDetails());
        } else {
            System.out.println("❌ Event not found: " + eventId);
        }
    }

    public void displayAllEvents() {
        List<Event> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            System.out.println("📭 No events found.");
        } else {
            System.out.println("📋 All Events (" + events.size() + "):");
            events.forEach(e -> System.out.println("  • " + e));
        }
    }

    public void displayEventsByType(EventType type) {
        List<Event> events = eventService.getEventsByType(type);
        System.out.println("📂 " + type + " Events (" + events.size() + "):");
        events.forEach(e -> System.out.println("  • " + e));
    }

    public void displayAvailableEvents() {
        List<Event> events = eventService.getAvailableEvents();
        System.out.println("✅ Available Events (" + events.size() + "):");
        events.forEach(e -> System.out.println("  • " + e));
    }

    // ===== Update =====

    public boolean updateTitle(String eventId, String newTitle) {
        return eventService.updateEventTitle(eventId, newTitle);
    }

    public boolean updateStatus(String eventId, EventStatus status) {
        return eventService.updateEventStatus(eventId, status);
    }

    public boolean updateLocation(String eventId, String newLocation) {
        return eventService.updateEventLocation(eventId, newLocation);
    }

    // ===== Delete / Cancel =====

    public boolean deleteEvent(String eventId) {
        return eventService.deleteEvent(eventId);
    }

    public boolean cancelEvent(String eventId) {
        return eventService.cancelEvent(eventId);
    }
}