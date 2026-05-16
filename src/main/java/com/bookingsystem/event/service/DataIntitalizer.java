package com.bookingsystem.event.service;

import com.bookingsystem.shared.enums.EventType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DataIntitalizer {

    private final EventService eventService;

    public DataIntitalizer(EventService eventService) {
        this.eventService = eventService;
    }

    public void loadSampleData() {

        createWorkshop("AI Workshop", "Learn AI basics", "Cairo", 200, 300);
        createWorkshop("Java Advanced Workshop", "Deep Java concepts", "Giza", 150, 250);

        createCinema("Avengers Night", "Marvel movie screening", "City Center", 120, 200);
        createCinema("Interstellar Night", "Sci-fi experience", "Downtown", 100, 180);

        createTrip("Alex Trip", "Sea trip", "Alexandria", 300, 500);
        createTrip("Fayoum Safari", "Desert adventure", "Fayoum", 400, 650);

        createGym("Fitness Bootcamp", "Intense training", "Nasr City", 250, 400);
        createGym("Yoga Class", "Relaxing yoga", "Heliopolis", 180, 300);

        createConcert("Amr Diab Live", "Music night", "Cairo Stadium", 500, 1000);
        createConcert("Rock Festival", "Live bands", "Alex Park", 350, 700);
    }

    // ---------------- WORKSHOP ----------------
    private void createWorkshop(String title, String desc, String location, double normal, double vip) {
        Map<String, Object> params = baseParams(title, desc, location, normal, vip);

        params.put("topic", "Technology");
        params.put("instructor", "Dr. Ahmed");
        params.put("skillLevel", "Beginner");
        params.put("materialsProvided", true);

        eventService.createEvent(EventType.WORKSHOP, params);
    }

    // ---------------- CINEMA ----------------
    private void createCinema(String title, String desc, String location, double normal, double vip) {
        Map<String, Object> params = baseParams(title, desc, location, normal, vip);

        params.put("movieTitle", title);
        params.put("director", "Nolan");
        params.put("durationMinutes", 120);
        params.put("language", "English");
        params.put("ageRating", "PG-13");

        eventService.createEvent(EventType.CINEMA, params);
    }

    // ---------------- TRIP ----------------
    private void createTrip(String title, String desc, String location, double normal, double vip) {
        Map<String, Object> params = baseParams(title, desc, location, normal, vip);

        params.put("destination", location);
        params.put("transportType", "Bus");
        params.put("mealsIncluded", true);
        params.put("hotelIncluded", true);

        eventService.createEvent(EventType.TRIP, params);
    }

    // ---------------- GYM ----------------
    private void createGym(String title, String desc, String location, double normal, double vip) {
        Map<String, Object> params = baseParams(title, desc, location, normal, vip);

        params.put("classType", "Fitness");
        params.put("trainerName", "Coach Mohamed");
        params.put("fitnessLevel", "Beginner");
        params.put("durationMinutes", 90);

        eventService.createEvent(EventType.GYM_CLASS, params);
    }

    // ---------------- CONCERT ----------------
    private void createConcert(String title, String desc, String location, double normal, double vip) {
        Map<String, Object> params = baseParams(title, desc, location, normal, vip);

        params.put("artistName", "Amr Diab");
        params.put("genre", "Pop");

        eventService.createEvent(EventType.CONCERT, params);
    }

    // ---------------- COMMON ----------------
    private Map<String, Object> baseParams(String title, String desc, String location,
                                           double normal, double vip) {

        Map<String, Object> params = new HashMap<>();

        params.put("title", title);
        params.put("description", desc);
        params.put("location", location);

        params.put("startDateTime", LocalDateTime.now().plusDays(2));
        params.put("endDateTime", LocalDateTime.now().plusDays(2).plusHours(3));

        params.put("priceNormal", normal);
        params.put("priceVIP", vip);
        params.put("totalSeats", 50);
        params.put("organizerId", "SYSTEM");

        return params;
    }
}