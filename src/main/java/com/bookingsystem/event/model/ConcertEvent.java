package com.bookingsystem.event.model;

import com.bookingsystem.shared.enums.EventType;

import java.time.LocalDateTime;

/**
 * Concrete Event — Concert 🎶
 * Created by EventFactory
 */
public class ConcertEvent extends Event {

    private String artistName;
    private String genre; // Rock, Pop, Jazz...

    public ConcertEvent(String eventId, String title, String description, String location,
                        LocalDateTime startDateTime, LocalDateTime endDateTime,
                        double priceNormal, double priceVIP, int totalSeats,
                        String organizerId, String artistName, String genre) {
        super(eventId, title, description, location, startDateTime, endDateTime,
                priceNormal, priceVIP, totalSeats, organizerId);
        this.type = EventType.CONCERT;
        this.artistName = artistName;
        this.genre = genre;
    }

    @Override
    public String getEventDetails() {
        return "🎶 Concert: " + title +
                "\n   Artist: " + artistName +
                "\n   Genre: " + genre +
                "\n   Location: " + location +
                "\n   Date: " + startDateTime +
                "\n   Price (Normal): $" + priceNormal +
                "\n   Price (VIP): $" + priceVIP +
                "\n   Available Seats: " + availableSeats + "/" + totalSeats;
    }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}