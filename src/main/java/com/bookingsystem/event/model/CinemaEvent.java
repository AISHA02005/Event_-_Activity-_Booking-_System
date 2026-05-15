package com.bookingsystem.event.model;

import com.bookingsystem.shared.enums.EventType;

import java.time.LocalDateTime;

/**
 * Concrete Event — Cinema 🎬
 * Created by EventFactory
 */
public class CinemaEvent extends Event {

    private String movieTitle;
    private String director;
    private int durationMinutes;
    private String language;
    private String ageRating; // G, PG, PG-13, R

    public CinemaEvent(String eventId, String title, String description, String location,
                       LocalDateTime startDateTime, LocalDateTime endDateTime,
                       double priceNormal, double priceVIP, int totalSeats,
                       String organizerId, String movieTitle, String director,
                       int durationMinutes, String language, String ageRating) {
        super(eventId, title, description, location, startDateTime, endDateTime,
                priceNormal, priceVIP, totalSeats, organizerId);
        this.type = EventType.CINEMA;
        this.movieTitle = movieTitle;
        this.director = director;
        this.durationMinutes = durationMinutes;
        this.language = language;
        this.ageRating = ageRating;
    }

    @Override
    public String getEventDetails() {
        return "🎬 Cinema: " + movieTitle +
                "\n   Director: " + director +
                "\n   Duration: " + durationMinutes + " mins" +
                "\n   Language: " + language +
                "\n   Age Rating: " + ageRating +
                "\n   Location: " + location +
                "\n   Date: " + startDateTime +
                "\n   Price (Normal): $" + priceNormal +
                "\n   Price (VIP): $" + priceVIP +
                "\n   Available Seats: " + availableSeats + "/" + totalSeats;
    }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getAgeRating() { return ageRating; }
    public void setAgeRating(String ageRating) { this.ageRating = ageRating; }
}