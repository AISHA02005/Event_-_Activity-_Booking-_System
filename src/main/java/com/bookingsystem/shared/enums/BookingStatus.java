package com.bookingsystem.shared.enums;

/**
 * Enum representing all possible states of a booking.
 * Used in the State pattern context and throughout the booking lifecycle.
 */
public enum BookingStatus {
    PENDING,    // Booking created but not yet confirmed/paid
    CONFIRMED,  // Booking confirmed and paid
    CANCELLED,  // Booking was cancelled by user or system
    COMPLETED   // Event has occurred and booking is done
}
