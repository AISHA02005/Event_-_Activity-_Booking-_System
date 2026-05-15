package com.bookingsystem.event;

import com.bookingsystem.event.composite.EventGroup;
import com.bookingsystem.event.composite.EventLeaf;
import com.bookingsystem.event.controller.EventController;
import com.bookingsystem.event.model.Event;
import com.bookingsystem.shared.enums.EventStatus;
import com.bookingsystem.shared.enums.EventType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ✅ Demo — Event Management Module
 * بيوضح الـ Factory Pattern + Composite Pattern
 */
public class EventManagementDemo {

    public static void main(String[] args) {
        EventController controller = new EventController();

        System.out.println("============================================");
        System.out.println("   🎉 Event Management System — Demo");
        System.out.println("============================================\n");

        // ===== 1. إنشاء Concert =====
        Map<String, Object> concertParams = new HashMap<>();
        concertParams.put("eventId", "EVT-001");
        concertParams.put("title", "Cairo Jazz Night");
        concertParams.put("description", "An amazing jazz concert in the heart of Cairo");
        concertParams.put("location", "Cairo Opera House");
        concertParams.put("startDateTime", LocalDateTime.of(2025, 8, 15, 20, 0));
        concertParams.put("endDateTime",   LocalDateTime.of(2025, 8, 15, 23, 0));
        concertParams.put("priceNormal", 150.0);
        concertParams.put("priceVIP",    350.0);
        concertParams.put("totalSeats",  200);
        concertParams.put("organizerId", "ORG-01");
        concertParams.put("artistName",  "Fathy Salama");
        concertParams.put("genre",       "Jazz");

        Event concert = controller.createEvent(EventType.CONCERT, concertParams);

        // ===== 2. إنشاء Cinema =====
        Map<String, Object> cinemaParams = new HashMap<>();
        cinemaParams.put("eventId", "EVT-002");
        cinemaParams.put("title", "Dune: Part Three Premiere");
        cinemaParams.put("description", "Special premiere screening");
        cinemaParams.put("location", "City Stars Cinema");
        cinemaParams.put("startDateTime", LocalDateTime.of(2025, 9, 1, 18, 30));
        cinemaParams.put("endDateTime",   LocalDateTime.of(2025, 9, 1, 21, 0));
        cinemaParams.put("priceNormal", 80.0);
        cinemaParams.put("priceVIP",    200.0);
        cinemaParams.put("totalSeats",  150);
        cinemaParams.put("organizerId", "ORG-02");
        cinemaParams.put("movieTitle",  "Dune: Part Three");
        cinemaParams.put("director",    "Denis Villeneuve");
        cinemaParams.put("durationMinutes", 155);
        cinemaParams.put("language",    "English");
        cinemaParams.put("ageRating",   "PG-13");

        Event cinema = controller.createEvent(EventType.CINEMA, cinemaParams);

        // ===== 3. إنشاء Workshop =====
        Map<String, Object> workshopParams = new HashMap<>();
        workshopParams.put("eventId", "EVT-003");
        workshopParams.put("title", "UI/UX Design Masterclass");
        workshopParams.put("description", "Learn modern UI/UX design principles");
        workshopParams.put("location", "GrEEK Campus, Cairo");
        workshopParams.put("startDateTime", LocalDateTime.of(2025, 8, 20, 10, 0));
        workshopParams.put("endDateTime",   LocalDateTime.of(2025, 8, 20, 16, 0));
        workshopParams.put("priceNormal", 500.0);
        workshopParams.put("priceVIP",    800.0);
        workshopParams.put("totalSeats",  30);
        workshopParams.put("organizerId", "ORG-03");
        workshopParams.put("topic",       "UI/UX Design");
        workshopParams.put("instructor",  "Sara Ahmed");
        workshopParams.put("skillLevel",  "Intermediate");
        workshopParams.put("materialsProvided", true);

        Event workshop = controller.createEvent(EventType.WORKSHOP, workshopParams);

        // ===== 4. إنشاء Trip =====
        Map<String, Object> tripParams = new HashMap<>();
        tripParams.put("eventId", "EVT-004");
        tripParams.put("title", "North Coast Summer Trip");
        tripParams.put("description", "3-day trip to the North Coast");
        tripParams.put("location", "Cairo Departure Point");
        tripParams.put("startDateTime", LocalDateTime.of(2025, 7, 10, 6, 0));
        tripParams.put("endDateTime",   LocalDateTime.of(2025, 7, 13, 20, 0));
        tripParams.put("priceNormal", 2500.0);
        tripParams.put("priceVIP",    4000.0);
        tripParams.put("totalSeats",  45);
        tripParams.put("organizerId", "ORG-04");
        tripParams.put("destination",    "North Coast, Egypt");
        tripParams.put("transportType",  "Private Bus");
        tripParams.put("mealsIncluded",  true);
        tripParams.put("hotelIncluded",  true);

        Event trip = controller.createEvent(EventType.TRIP, tripParams);

        // ===== 5. إنشاء GymClass =====
        Map<String, Object> gymParams = new HashMap<>();
        gymParams.put("eventId", "EVT-005");
        gymParams.put("title", "Morning Yoga Flow");
        gymParams.put("description", "Start your day with energy");
        gymParams.put("location", "Gold's Gym, Maadi");
        gymParams.put("startDateTime", LocalDateTime.of(2025, 8, 5, 7, 0));
        gymParams.put("endDateTime",   LocalDateTime.of(2025, 8, 5, 8, 0));
        gymParams.put("priceNormal", 50.0);
        gymParams.put("priceVIP",    100.0);
        gymParams.put("totalSeats",  20);
        gymParams.put("organizerId", "ORG-05");
        gymParams.put("classType",      "Yoga");
        gymParams.put("trainerName",    "Mohamed Nour");
        gymParams.put("fitnessLevel",   "Beginner");
        gymParams.put("durationMinutes", 60);

        Event gymClass = controller.createEvent(EventType.GYM_CLASS, gymParams);

        System.out.println();

        // ===== Display All Events =====
        System.out.println("============================================");
        controller.displayAllEvents();

        System.out.println();

        // ===== Display Event Details =====
        System.out.println("============================================");
        System.out.println("📄 Event Details:\n");
        controller.displayEvent("EVT-001");

        System.out.println();

        // ===== Update Event =====
        System.out.println("============================================");
        System.out.println("✏️ Updating Event...");
        controller.updateTitle("EVT-002", "Dune 3 — IMAX Special Premiere");
        controller.updateLocation("EVT-003", "AUC, New Cairo");

        System.out.println();

        // ===== Cancel Event =====
        System.out.println("============================================");
        System.out.println("❌ Cancelling Event...");
        controller.cancelEvent("EVT-004");

        System.out.println();

        // ===== Filter by Type =====
        System.out.println("============================================");
        controller.displayEventsByType(EventType.CONCERT);

        System.out.println();

        // ===== Available Events =====
        System.out.println("============================================");
        controller.displayAvailableEvents();

        System.out.println();

        // ===== Composite Pattern Demo =====
        System.out.println("============================================");
        System.out.println("🗂️ Composite Pattern — Event Groups:\n");

        EventGroup summerFestival = new EventGroup("Summer Festival 2025");
        summerFestival.add(new EventLeaf(concert));
        summerFestival.add(new EventLeaf(cinema));
        summerFestival.add(new EventLeaf(workshop));

        EventGroup allEvents = new EventGroup("All Events");
        allEvents.add(summerFestival);
        allEvents.add(new EventLeaf(gymClass));

        allEvents.display();
        System.out.printf("%n💰 Total Revenue Potential: $%.2f%n", allEvents.getTotalRevenuePotential());

        System.out.println("\n============================================");
        System.out.println("✅ Demo Completed Successfully!");
    }
}