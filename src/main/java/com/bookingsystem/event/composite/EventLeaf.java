package com.bookingsystem.event.composite;

import com.bookingsystem.event.model.Event;

/**
 * Composite Pattern — Leaf Node
 * يمثل event واحد في الـ tree
 */
public class EventLeaf implements EventComponent {

    private final Event event;

    public EventLeaf(Event event) {
        this.event = event;
    }

    @Override
    public String getName() {
        return event.getTitle();
    }

    @Override
    public void display() {
        System.out.println("  └── " + event.getEventDetails());
    }

    @Override
    public double getTotalRevenuePotential() {
        return event.getPriceNormal() * event.getTotalSeats();
    }

    public Event getEvent() {
        return event;
    }
}