package com.bookingsystem.event.model;

import com.bookingsystem.shared.enums.EventStatus;
import com.bookingsystem.shared.enums.EventType;

import java.time.LocalDateTime;

/**
 * Abstract base class for all event types.
 * Part of the Factory Pattern — each subclass is a concrete product.
 */
public abstract class Event {

    protected String eventId;
    protected String title;
    protected String description;
    protected String location;
    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;
    protected double priceNormal;
    protected double priceVIP;
    protected int totalSeats;
    protected int availableSeats;
    protected EventStatus status;
    protected EventType type;
    protected String organizerId;

    // Constructor
    public Event(String eventId, String title, String description, String location,
                 LocalDateTime startDateTime, LocalDateTime endDateTime,
                 double priceNormal, double priceVIP, int totalSeats, String organizerId) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.priceNormal = priceNormal;
        this.priceVIP = priceVIP;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.status = EventStatus.UPCOMING;
        this.organizerId = organizerId;
    }

    // Abstract method — كل event نوع بيعرّف نفسه
    public abstract String getEventDetails();

    // Book a seat
    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    // Cancel a seat
    public void cancelSeat() {
        if (availableSeats < totalSeats) {
            availableSeats++;
        }
    }

    // Getters & Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    public double getPriceNormal() { return priceNormal; }
    public void setPriceNormal(double priceNormal) { this.priceNormal = priceNormal; }

    public double getPriceVIP() { return priceVIP; }
    public void setPriceVIP(double priceVIP) { this.priceVIP = priceVIP; }

    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public EventType getType() { return type; }

    public String getOrganizerId() { return organizerId; }
    public void setOrganizerId(String organizerId) { this.organizerId = organizerId; }

    @Override
    public String toString() {
        return "[" + type + "] " + title + " | " + location + " | " + startDateTime + " | Seats: " + availableSeats + "/" + totalSeats;
    }
}