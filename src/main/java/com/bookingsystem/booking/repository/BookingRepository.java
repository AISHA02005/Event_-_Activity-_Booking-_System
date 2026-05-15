package com.bookingsystem.booking.repository;

import com.bookingsystem.booking.model.Booking;

import java.util.*;
import java.util.stream.Collectors;


public class BookingRepository {


    private final Map<String, Booking> bookings = new HashMap<>();



    private final Map<String, Set<String>> bookedSeats = new HashMap<>();

    // ---- CRUD operations ----



    public void save(Booking booking) {
        if (booking == null) throw new IllegalArgumentException("Booking cannot be null");
        bookings.put(booking.getBookingId(), booking);

        // Track the seat as booked for this event
        bookedSeats
                .computeIfAbsent(booking.getEventId(), k -> new HashSet<>())
                .add(booking.getTicket().getSeatNumber());
    }


    public Optional<Booking> findById(String bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }


    public List<Booking> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(bookings.values()));
    }


    public List<Booking> findByUserId(String userId) {
        return bookings.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }


    public List<Booking> findByEventId(String eventId) {
        return bookings.values().stream()
                .filter(b -> b.getEventId().equals(eventId))
                .collect(Collectors.toList());
    }


    public boolean isSeatTaken(String eventId, String seatNumber) {
        return bookings.values().stream()
                .filter(b -> b.getEventId().equals(eventId))
                .filter(b -> b.getTicket().getSeatNumber().equals(seatNumber))
                .anyMatch(b -> {
                    switch (b.getStatus()) {
                        case PENDING:
                        case CONFIRMED:
                        case COMPLETED:
                            return true;
                        default:
                            return false; // CANCELLED seats are freed
                    }
                });
    }



    public List<String> getAvailableSeats(String eventId, List<String> allSeats) {
        return allSeats.stream()
                .filter(seat -> !isSeatTaken(eventId, seat))
                .collect(Collectors.toList());
    }


    public Set<String> getTakenSeats(String eventId) {
        return bookings.values().stream()
                .filter(b -> b.getEventId().equals(eventId))
                .filter(b -> {
                    switch (b.getStatus()) {
                        case PENDING:
                        case CONFIRMED:
                        case COMPLETED:
                            return true;
                        default:
                            return false;
                    }
                })
                .map(b -> b.getTicket().getSeatNumber())
                .collect(Collectors.toSet());
    }


    public boolean delete(String bookingId) {
        return bookings.remove(bookingId) != null;
    }


    public int count() {
        return bookings.size();
    }


    public boolean exists(String bookingId) {
        return bookings.containsKey(bookingId);
    }
}
