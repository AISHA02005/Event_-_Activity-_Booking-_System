package com.bookingsystem.event.composite;

/**
 * Composite Pattern — Component Interface
 *
 * بيخلينا نتعامل مع event واحد أو group من events بنفس الطريقة
 * مثال: Festival فيه ConcertEvent + WorkshopEvent + GymClassEvent
 */
public interface EventComponent {
    String getName();
    void display();
    double getTotalRevenuePotential(); // Normal price × seats
}