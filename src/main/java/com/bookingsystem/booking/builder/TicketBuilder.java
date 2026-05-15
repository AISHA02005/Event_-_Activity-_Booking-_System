package com.bookingsystem.booking.builder;
import com.bookingsystem.booking.model.Ticket;

public class TicketBuilder {

    private String seatNumber;
    private String type;
    private double price;
    private String eventName;

    public TicketBuilder setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public TicketBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public TicketBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public TicketBuilder setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public Ticket build() {
        return new Ticket(seatNumber, type, price, eventName);
    }
}