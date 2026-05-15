package com.bookingsystem.booking.builder;

import com.bookingsystem.booking.model.Ticket;
import com.bookingsystem.shared.enums.TicketType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConcreteTicketBuilder implements TicketBuilder {

    // Internal state — accumulated during building
    private String ticketId;
    private String bookingId;
    private String eventId;
    private String userId;
    private String seatNumber;
    private TicketType ticketType;
    private double basePrice;
    private LocalDateTime bookingDate = LocalDateTime.now();
    private List<String> extras = new ArrayList<>();
    private String notes;

    // ---- Builder method implementations ----

    @Override
    public TicketBuilder setTicketId(String ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    @Override
    public TicketBuilder setBookingId(String bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    @Override
    public TicketBuilder setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    @Override
    public TicketBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public TicketBuilder setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    @Override
    public TicketBuilder setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
        return this;
    }

    @Override
    public TicketBuilder setBasePrice(double basePrice) {
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be positive. Got: " + basePrice);
        }
        this.basePrice = basePrice;
        return this;
    }

    @Override
    public TicketBuilder setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    @Override
    public TicketBuilder addExtra(String extra) {
        if (extra != null && !extra.isBlank()) {
            this.extras.add(extra.trim());
        }
        return this;
    }

    @Override
    public TicketBuilder addExtras(List<String> extrasList) {
        if (extrasList != null) {
            extrasList.forEach(this::addExtra);
        }
        return this;
    }

    @Override
    public TicketBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public TicketBuilder reset() {
        this.ticketId    = null;
        this.bookingId   = null;
        this.eventId     = null;
        this.userId      = null;
        this.seatNumber  = null;
        this.ticketType  = null;
        this.basePrice   = 0;
        this.bookingDate = LocalDateTime.now();
        this.extras      = new ArrayList<>();
        this.notes       = null;
        return this;
    }


    @Override
    public Ticket build() {
        validateRequiredFields();
        validateExtrasForTicketType();

        // Apply ticket type pricing multiplier
        double finalPrice = Math.round(basePrice * ticketType.getPriceMultiplier() * 100.0) / 100.0;

        return new Ticket.Builder(ticketId, bookingId, eventId, userId, seatNumber, ticketType, finalPrice)
                .withBookingDate(bookingDate)
                .withExtras(extras)
                .withNotes(notes)
                .build();
    }


    private void validateRequiredFields() {
        List<String> missing = new ArrayList<>();
        if (ticketId   == null || ticketId.isBlank())   missing.add("ticketId");
        if (bookingId  == null || bookingId.isBlank())  missing.add("bookingId");
        if (eventId    == null || eventId.isBlank())    missing.add("eventId");
        if (userId     == null || userId.isBlank())     missing.add("userId");
        if (seatNumber == null || seatNumber.isBlank()) missing.add("seatNumber");
        if (ticketType == null)                         missing.add("ticketType");
        if (basePrice  <= 0)                            missing.add("basePrice");

        if (!missing.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot build Ticket — missing required fields: " + missing
            );
        }
    }


    private void validateExtrasForTicketType() {
        if (ticketType == TicketType.NORMAL) {
            List<String> vipOnlyExtras = List.of("Backstage Access", "Premium Seat");
            for (String extra : extras) {
                for (String vipExtra : vipOnlyExtras) {
                    if (extra.equalsIgnoreCase(vipExtra)) {
                        throw new IllegalStateException(
                                "Extra '" + extra + "' is only available for VIP tickets."
                        );
                    }
                }
            }
        }
    }
}
