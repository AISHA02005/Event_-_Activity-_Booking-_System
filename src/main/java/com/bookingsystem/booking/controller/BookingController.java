package com.bookingsystem.booking.controller;

import com.bookingsystem.booking.model.Booking;
import com.bookingsystem.booking.service.BookingService;
import com.bookingsystem.shared.enums.BookingStatus;
import com.bookingsystem.shared.enums.TicketType;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public class BookingController {

    private final BookingService bookingService;

    public BookingController() {
        this.bookingService = new BookingService();
    }

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // =========================================================
    // BOOKING CREATION
    // =========================================================


    public Booking bookVipTicket(String userId, String eventId,
                                 String seatNumber, double basePrice) {
        try {
            return bookingService.createVipBooking(userId, eventId, seatNumber, basePrice);
        } catch (Exception e) {
            System.err.println("⚠️  [BookingController] VIP booking failed: " + e.getMessage());
            return null;
        }
    }


    public Booking bookNormalTicket(String userId, String eventId,
                                    String seatNumber, double basePrice) {
        try {
            return bookingService.createNormalBooking(userId, eventId, seatNumber, basePrice);
        } catch (Exception e) {
            System.err.println("⚠️  [BookingController] Normal booking failed: " + e.getMessage());
            return null;
        }
    }


    public Booking bookCustomTicket(String userId, String eventId, String seatNumber,
                                    TicketType ticketType, double basePrice,
                                    List<String> extras, String notes) {
        try {
            return bookingService.createCustomBooking(
                    userId, eventId, seatNumber, ticketType, basePrice, extras, notes
            );
        } catch (Exception e) {
            System.err.println("⚠️  [BookingController] Custom booking failed: " + e.getMessage());
            return null;
        }
    }

    // =========================================================
    // BOOKING MANAGEMENT
    // =========================================================


    public Booking cancelBooking(String bookingId, String reason) {
        try {
            return bookingService.cancelBooking(bookingId, reason);
        } catch (Exception e) {
            System.err.println("⚠️  [BookingController] Cancel failed: " + e.getMessage());
            return null;
        }
    }


    public Booking confirmBooking(String bookingId) {
        try {
            return bookingService.confirmBooking(bookingId);
        } catch (Exception e) {
            System.err.println("⚠️  [BookingController] Confirm failed: " + e.getMessage());
            return null;
        }
    }


    public Booking completeBooking(String bookingId) {
        try {
            return bookingService.completeBooking(bookingId);
        } catch (Exception e) {
            System.err.println("⚠️  [BookingController] Complete failed: " + e.getMessage());
            return null;
        }
    }


    public Booking changeStatus(String bookingId, BookingStatus newStatus) {
        try {
            return bookingService.changeBookingStatus(bookingId, newStatus);
        } catch (Exception e) {
            System.err.println("⚠️  [BookingController] Status change failed: " + e.getMessage());
            return null;
        }
    }

    // =========================================================
    // QUERIES
    // =========================================================


    public Optional<Booking> getBookingById(String bookingId) {
        Optional<Booking> result = bookingService.findBookingById(bookingId);
        if (result.isEmpty()) {
            System.out.println("🔍 No booking found with ID: " + bookingId);
        }
        return result;
    }


    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }


    public List<Booking> getBookingsByUser(String userId) {
        return bookingService.getBookingsByUser(userId);
    }

    // =========================================================
    // SEAT AVAILABILITY
    // =========================================================


    public boolean checkSeatAvailability(String eventId, String seatNumber) {
        boolean available = bookingService.isSeatAvailable(eventId, seatNumber);
        System.out.println("💺 Seat " + seatNumber + " for event " + eventId
                + " → " + (available ? "AVAILABLE ✅" : "TAKEN ❌"));
        return available;
    }


    public List<String> getAvailableSeats(String eventId, List<String> allSeats) {
        return bookingService.getAvailableSeats(eventId, allSeats);
    }


    public Set<String> getTakenSeats(String eventId) {
        return bookingService.getTakenSeats(eventId);
    }

    // =========================================================
    // DISPLAY
    // =========================================================


    public void displayBooking(String bookingId) {
        bookingService.displayBooking(bookingId);
    }


    public void displayAllBookings() {
        bookingService.displayAllBookings();
    }
}
