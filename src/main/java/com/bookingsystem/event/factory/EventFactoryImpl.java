package com.bookingsystem.event.factory;

import com.bookingsystem.event.model.*;
import com.bookingsystem.shared.enums.EventType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class EventFactoryImpl implements EventFactory {

    @Override
    public Event createEvent(EventType type, Map<String, Object> params) {

        String eventId  = (String) params.getOrDefault("eventId", UUID.randomUUID().toString());
        String title    = (String) params.get("title");
        String desc     = (String) params.get("description");
        String location = (String) params.get("location");

        LocalDateTime start = (LocalDateTime) params.get("startDateTime");
        LocalDateTime end   = (LocalDateTime) params.get("endDateTime");

        double priceNormal = (double) params.get("priceNormal");
        double priceVIP    = (double) params.get("priceVIP");
        int totalSeats     = (int) params.get("totalSeats");
        String organizerId = (String) params.get("organizerId");

        return switch (type) {

            case CINEMA -> new CinemaEvent(
                    eventId, title, desc, location, start, end,
                    priceNormal, priceVIP, totalSeats, organizerId,
                    (String) params.get("movieTitle"),
                    (String) params.get("director"),
                    (int) params.get("durationMinutes"),
                    (String) params.get("language"),
                    (String) params.get("ageRating")
            );

            case WORKSHOP -> new WorkshopEvent(
                    eventId, title, desc, location, start, end,
                    priceNormal, priceVIP, totalSeats, organizerId,
                    (String) params.get("topic"),
                    (String) params.get("instructor"),
                    (String) params.get("skillLevel"),
                    (boolean) params.get("materialsProvided")
            );

            case TRIP -> new TripEvent(
                    eventId, title, desc, location, start, end,
                    priceNormal, priceVIP, totalSeats, organizerId,
                    (String) params.get("destination"),
                    (String) params.get("transportType"),
                    (boolean) params.get("mealsIncluded"),
                    (boolean) params.get("hotelIncluded")
            );

            case GYM_CLASS -> new GymClassEvent(
                    eventId, title, desc, location, start, end,
                    priceNormal, priceVIP, totalSeats, organizerId,
                    (String) params.get("classType"),
                    (String) params.get("trainerName"),
                    (String) params.get("fitnessLevel"),
                    (int) params.get("durationMinutes")
            );

            case CONCERT -> new ConcertEvent(
                    eventId, title, desc, location, start, end,
                    priceNormal, priceVIP, totalSeats, organizerId,
                    (String) params.get("artistName"),
                    (String) params.get("genre")
            );
        };
    }
}