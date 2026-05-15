package com.bookingsystem.booking.model;

public class Ticket {

    private String seatNumber;
    private String type;
    private double price;
    private String eventName;

    public Ticket(String seatNumber, String type, double price, String eventName) {
        this.seatNumber = seatNumber;
        this.type = type;
        this.price = price;
        this.eventName = eventName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public String toString() {
        return eventName + " | " + seatNumber + " | " + type + " | " + price;
    }
}