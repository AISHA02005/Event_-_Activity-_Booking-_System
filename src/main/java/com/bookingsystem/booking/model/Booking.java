package com.bookingsystem.booking.model;

import java.util.ArrayList;
import java.util.List;

public class Booking {

    private List<Ticket> tickets = new ArrayList<>();

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void showBooking() {
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}