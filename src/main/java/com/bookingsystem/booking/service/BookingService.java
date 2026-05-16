package com.bookingsystem.booking.service;

import com.bookingsystem.booking.builder.ConcreteTicketBuilder;
import com.bookingsystem.booking.builder.TicketBuilder;
import com.bookingsystem.booking.builder.TicketDirector;
import com.bookingsystem.booking.model.Booking;
import com.bookingsystem.booking.model.Ticket;
import com.bookingsystem.booking.repository.BookingRepository;
import com.bookingsystem.shared.enums.BookingStatus;
import com.bookingsystem.shared.enums.TicketType;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public class BookingService {

    private final BookingRepository repository;
    private final TicketDirector director;
    private final TicketBuilder builder;

    public BookingService() {
        this.repository = new BookingRepository();
        this.builder    = new ConcreteTicketBuilder();
        this.director   = new TicketDirector(builder);
    }

    /** Package-private constructor for testing with injected repo. */
    BookingService(BookingRepository repository) {
        this.repository = repository;
        this.builder    = new ConcreteTicketBuilder();
        this.director   = new TicketDirector(builder);
    }

    // =========================================================
    // CREATE BOOKING
    // =========================================================


    public Booking createVipBooking(String userId,
                                    String eventId,
                                    String seatNumber,
                                    double basePrice) {
        validateSeatAvailability(eventId, seatNumber);

        String bookingId = generateId("B");
        String ticketId  = generateId("T");

        Ticket ticket = director.buildVipTicket(
                ticketId, bookingId, eventId, userId, seatNumber, basePrice
        );

        Booking booking = new Booking(bookingId, userId, eventId, ticket);
        repository.save(booking);

        System.out.println("✅ VIP Booking created: " + bookingId + " | Seat: " + seatNumber);
        return booking;
    }

    /**
     * Creates a new booking using the Director's Normal ticket recipe.
     *
     * @param userId     the user creating the booking
     * @param eventId    the event being booked
     * @param seatNumber the seat to reserve
     * @param basePrice  the base ticket price
     * @return the created Booking (PENDING status)
     */
    public Booking createNormalBooking(String userId,
                                       String eventId,
                                       String seatNumber,
                                       double basePrice) {
        validateSeatAvailability(eventId, seatNumber);

        String bookingId = generateId("B");
        String ticketId  = generateId("T");

        Ticket ticket = director.buildNormalTicket(
                ticketId, bookingId, eventId, userId, seatNumber, basePrice
        );

        Booking booking = new Booking(bookingId, userId, eventId, ticket);
        repository.save(booking);

        System.out.println("✅ Normal Booking created: " + bookingId + " | Seat: " + seatNumber);
        return booking;
    }




    public Booking createCustomBooking(String userId,
                                       String eventId,
                                       String seatNumber,
                                       TicketType ticketType,
                                       double basePrice,
                                       List<String> extras,
                                       String notes) {
        validateSeatAvailability(eventId, seatNumber);

        String bookingId = generateId("B");
        String ticketId  = generateId("T");

        // Use builder directly for full customization
        Ticket ticket = new ConcreteTicketBuilder()
                .setTicketId(ticketId)
                .setBookingId(bookingId)
                .setEventId(eventId)
                .setUserId(userId)
                .setSeatNumber(seatNumber)
                .setTicketType(ticketType)
                .setBasePrice(basePrice)
                .addExtras(extras)
                .setNotes(notes)
                .build();

        Booking booking = new Booking(bookingId, userId, eventId, ticket);
        repository.save(booking);

        System.out.println("✅ Custom Booking created: " + bookingId + " | Seat: " + seatNumber);
        return booking;
    }

    // =========================================================
    // CANCEL BOOKING
    // =========================================================



    public Booking cancelBooking(String bookingId, String reason) {
        Booking booking = findBookingOrThrow(bookingId);
        booking.cancel(reason); // Status logic is in the model
        repository.save(booking);
        System.out.println("❌ Booking cancelled: " + bookingId + " | Reason: " + reason);
        return booking;
    }

    // =========================================================
    // CONFIRM BOOKING (simulate payment)
    // =========================================================




    public Booking confirmBooking(String bookingId) {
        Booking booking = findBookingOrThrow(bookingId);
        booking.confirm();
        repository.save(booking);
        System.out.println("💳 Booking confirmed (payment processed): " + bookingId);
        return booking;
    }

    // =========================================================
    // COMPLETE BOOKING
    // =========================================================

    /**
     * Marks a booking as COMPLETED (event has occurred).
     *
     * @param bookingId the booking to complete
     * @return the updated Booking
     */
    public Booking completeBooking(String bookingId) {
        Booking booking = findBookingOrThrow(bookingId);
        booking.complete();
        repository.save(booking);
        System.out.println("🏁 Booking completed: " + bookingId);
        return booking;
    }

    // =========================================================
    // CHANGE STATUS (admin-level override)
    // =========================================================


    public Booking changeBookingStatus(String bookingId, BookingStatus newStatus) {
        Booking booking = findBookingOrThrow(bookingId);
        booking.changeStatus(newStatus);
        repository.save(booking);
        System.out.println("🔄 Booking status changed: " + bookingId + " → " + newStatus);
        return booking;
    }

    // =========================================================
    // QUERIES
    // =========================================================


    public Optional<Booking> findBookingById(String bookingId) {
        return repository.findById(bookingId);
    }


    public List<Booking> getAllBookings() {
        return repository.findAll();
    }


    public List<Booking> getBookingsByUser(String userId) {
        return repository.findByUserId(userId);
    }


    public List<Booking> getBookingsByEvent(String eventId) {
        return repository.findByEventId(eventId);
    }

    // =========================================================
    // SEAT AVAILABILITY
    // =========================================================


    public boolean isSeatAvailable(String eventId, String seatNumber) {
        return !repository.isSeatTaken(eventId, seatNumber);
    }


    public List<String> getAvailableSeats(String eventId) {
        return repository.getAvailableSeats(eventId);
    }


    public Set<String> getTakenSeats(String eventId) {
        return repository.getTakenSeats(eventId);
    }

    // =========================================================
    // DISPLAY
    // =========================================================


    public void displayBooking(String bookingId) {
        Booking booking = findBookingOrThrow(bookingId);
        booking.displayDetails();
    }


    public void displayAllBookings() {
        List<Booking> all = repository.findAll();
        if (all.isEmpty()) {
            System.out.println("📭 No bookings found.");
            return;
        }
        System.out.println("\n📋 ALL BOOKINGS (" + all.size() + " total):");
        System.out.println("═".repeat(46));
        all.forEach(Booking::displayDetails);
    }

    // =========================================================
    // INTERNAL HELPERS
    // =========================================================



    private void validateSeatAvailability(String eventId, String seatNumber) {
        if (repository.isSeatTaken(eventId, seatNumber)) {
            throw new IllegalStateException(
                    "Seat '" + seatNumber + "' is already taken for event '" + eventId + "'."
            );
        }
    }


    private Booking findBookingOrThrow(String bookingId) {
        return repository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Booking not found: " + bookingId
                ));
    }


    private String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
