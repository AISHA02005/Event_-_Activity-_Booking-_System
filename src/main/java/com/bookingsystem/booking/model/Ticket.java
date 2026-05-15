package com.bookingsystem.booking.model;

import com.bookingsystem.shared.enums.TicketType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Ticket {

    // ---- Required fields ----
    private final String ticketId;
    private final String bookingId;
    private final String eventId;
    private final String userId;
    private final String seatNumber;
    private final TicketType ticketType;
    private final double ticketPrice;
    private final LocalDateTime bookingDate;

    // ---- Optional fields ----
    private final List<String> extras;
    private final String notes;


    private Ticket(Builder builder) {
        this.ticketId    = builder.ticketId;
        this.bookingId   = builder.bookingId;
        this.eventId     = builder.eventId;
        this.userId      = builder.userId;
        this.seatNumber  = builder.seatNumber;
        this.ticketType  = builder.ticketType;
        this.ticketPrice = builder.ticketPrice;
        this.bookingDate = builder.bookingDate;
        this.extras      = Collections.unmodifiableList(new ArrayList<>(builder.extras));
        this.notes       = builder.notes;
    }

    // ---- Getters (immutable) ----

    public String getTicketId()    { return ticketId; }
    public String getBookingId()   { return bookingId; }
    public String getEventId()     { return eventId; }
    public String getUserId()      { return userId; }
    public String getSeatNumber()  { return seatNumber; }
    public TicketType getTicketType()  { return ticketType; }
    public double getTicketPrice() { return ticketPrice; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public List<String> getExtras() { return extras; }
    public String getNotes()       { return notes; }

    @Override
    public String toString() {
        return String.format(
                "┌─────────────────────────────────────────┐%n" +
                        "│              TICKET DETAILS             │%n" +
                        "├─────────────────────────────────────────┤%n" +
                        "│ Ticket ID   : %-26s│%n" +
                        "│ Booking ID  : %-26s│%n" +
                        "│ Event ID    : %-26s│%n" +
                        "│ User ID     : %-26s│%n" +
                        "│ Seat        : %-26s│%n" +
                        "│ Type        : %-26s│%n" +
                        "│ Price       : $%-25.2f│%n" +
                        "│ Date        : %-26s│%n" +
                        "│ Extras      : %-26s│%n" +
                        "│ Notes       : %-26s│%n" +
                        "└─────────────────────────────────────────┘",
                ticketId, bookingId, eventId, userId,
                seatNumber, ticketType.getDisplayName(),
                ticketPrice,
                bookingDate.toLocalDate(),
                extras.isEmpty() ? "None" : String.join(", ", extras),
                notes == null ? "—" : notes
        );
    }

    // =========================================================
    // BUILDER PATTERN — BUILDER CLASS (inner static)
    // =========================================================

    public static class Builder {

        // Required
        private final String ticketId;
        private final String bookingId;
        private final String eventId;
        private final String userId;
        private final String seatNumber;
        private final TicketType ticketType;
        private final double ticketPrice;

        // Optional — defaults
        private LocalDateTime bookingDate = LocalDateTime.now();
        private List<String> extras = new ArrayList<>();
        private String notes;


        public Builder(String ticketId,
                       String bookingId,
                       String eventId,
                       String userId,
                       String seatNumber,
                       TicketType ticketType,
                       double ticketPrice) {

            if (ticketId   == null || ticketId.isBlank())   throw new IllegalArgumentException("ticketId cannot be null/blank");
            if (bookingId  == null || bookingId.isBlank())  throw new IllegalArgumentException("bookingId cannot be null/blank");
            if (eventId    == null || eventId.isBlank())    throw new IllegalArgumentException("eventId cannot be null/blank");
            if (userId     == null || userId.isBlank())     throw new IllegalArgumentException("userId cannot be null/blank");
            if (seatNumber == null || seatNumber.isBlank()) throw new IllegalArgumentException("seatNumber cannot be null/blank");
            if (ticketType == null)                         throw new IllegalArgumentException("ticketType cannot be null");
            if (ticketPrice <= 0)                           throw new IllegalArgumentException("ticketPrice must be positive");

            this.ticketId    = ticketId;
            this.bookingId   = bookingId;
            this.eventId     = eventId;
            this.userId      = userId;
            this.seatNumber  = seatNumber;
            this.ticketType  = ticketType;
            this.ticketPrice = ticketPrice;
        }


        public Builder withBookingDate(LocalDateTime date) {
            if (date == null) throw new IllegalArgumentException("bookingDate cannot be null");
            this.bookingDate = date;
            return this;
        }


        public Builder withExtra(String extra) {
            if (extra != null && !extra.isBlank()) {
                this.extras.add(extra.trim());
            }
            return this;
        }


        public Builder withExtras(List<String> extrasList) {
            if (extrasList != null) {
                extrasList.stream()
                        .filter(e -> e != null && !e.isBlank())
                        .forEach(this.extras::add);
            }
            return this;
        }


        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}
