package com.bookingsystem.event.model;

import com.bookingsystem.shared.enums.EventType;

import java.time.LocalDateTime;

/**
 * Concrete Event — Trip 🏝️
 * Created by EventFactory
 */
public class TripEvent extends Event {

    private String destination;
    private String transportType; // Bus, Plane, Train
    private boolean mealsIncluded;
    private boolean hotelIncluded;

    public TripEvent(String eventId, String title, String description, String location,
                     LocalDateTime startDateTime, LocalDateTime endDateTime,
                     double priceNormal, double priceVIP, int totalSeats,
                     String organizerId, String destination, String transportType,
                     boolean mealsIncluded, boolean hotelIncluded) {
        super(eventId, title, description, location, startDateTime, endDateTime,
                priceNormal, priceVIP, totalSeats, organizerId);
        this.type = EventType.TRIP;
        this.destination = destination;
        this.transportType = transportType;
        this.mealsIncluded = mealsIncluded;
        this.hotelIncluded = hotelIncluded;
    }

    @Override
    public String getEventDetails() {
        return "🏝️ Trip: " + title +
                "\n   Destination: " + destination +
                "\n   Transport: " + transportType +
                "\n   Meals Included: " + (mealsIncluded ? "Yes" : "No") +
                "\n   Hotel Included: " + (hotelIncluded ? "Yes" : "No") +
                "\n   Departure: " + location +
                "\n   Date: " + startDateTime +
                "\n   Price (Normal): $" + priceNormal +
                "\n   Price (VIP): $" + priceVIP +
                "\n   Available Seats: " + availableSeats + "/" + totalSeats;
    }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getTransportType() { return transportType; }
    public void setTransportType(String transportType) { this.transportType = transportType; }

    public boolean isMealsIncluded() { return mealsIncluded; }
    public void setMealsIncluded(boolean mealsIncluded) { this.mealsIncluded = mealsIncluded; }

    public boolean isHotelIncluded() { return hotelIncluded; }
    public void setHotelIncluded(boolean hotelIncluded) { this.hotelIncluded = hotelIncluded; }
}