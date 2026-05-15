package com.bookingsystem.booking;

import com.bookingsystem.booking.builder.ConcreteTicketBuilder;
import com.bookingsystem.booking.builder.TicketDirector;
import com.bookingsystem.booking.controller.BookingController;
import com.bookingsystem.booking.model.Booking;
import com.bookingsystem.booking.model.Ticket;
import com.bookingsystem.shared.enums.BookingStatus;
import com.bookingsystem.shared.enums.TicketType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BookingModuleDemo {

    private static final String DIVIDER = "\n" + "═".repeat(55) + "\n";
    private static final String STEP    = "─".repeat(55);

    public static void main(String[] args) {

        System.out.println(DIVIDER);
        System.out.println("   🎟️  EVENT & ACTIVITY BOOKING SYSTEM — DEMO");
        System.out.println("        Builder Pattern Demonstration");
        System.out.println(DIVIDER);

        BookingController controller = new BookingController();

        // ──────────────────────────────────────────────────────
        // DEMO 1: Build a ticket DIRECTLY using the Builder
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 1: Building a Ticket using Builder directly\n" + STEP);
        System.out.println("(Shows step-by-step construction without Director)\n");

        Ticket rawTicket = new Ticket.Builder(
                "T-MANUAL-01",
                "B-MANUAL-01",
                "E-CONCERT-01",
                "U001",
                "A5",
                TicketType.VIP,
                175.0   // already has VIP price applied
        )
                .withExtra("Premium Seat")
                .withExtra("Backstage Access")
                .withExtra("Parking Access")
                .withNotes("Front row VIP — manually built via Ticket.Builder")
                .build();

        System.out.println("Directly built ticket:");
        System.out.println(rawTicket);

        // ──────────────────────────────────────────────────────
        // DEMO 2: Build tickets using the Director (recipes)
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 2: Building Tickets via TicketDirector\n" + STEP);

        TicketDirector director = new TicketDirector(new ConcreteTicketBuilder());

        Ticket directedVip = director.buildVipTicket(
                "T-DIR-VIP-01", "B-DIR-01", "E-CONCERT-01",
                "U002", "VIP-1", 100.0
        );
        System.out.println("Director-built VIP ticket (price auto-multiplied ×1.75):");
        System.out.println(directedVip);

        Ticket directedNormal = director.buildNormalTicket(
                "T-DIR-NRM-01", "B-DIR-02", "E-CONCERT-01",
                "U003", "B12", 60.0
        );
        System.out.println("Director-built Normal ticket:");
        System.out.println(directedNormal);

        Ticket normalWithOptions = director.buildNormalTicketWithOptions(
                "T-DIR-NRM-02", "B-DIR-03", "E-CONCERT-01",
                "U004", "C8", 60.0, true, true // snack + parking
        );
        System.out.println("Normal ticket with Snack + Parking:");
        System.out.println(normalWithOptions);

        // ──────────────────────────────────────────────────────
        // DEMO 3: Create bookings through the Controller
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 3: Creating Bookings via BookingController\n" + STEP);

        // VIP booking — Concert event
        Booking vipBooking = controller.bookVipTicket(
                "U-ALICE", "E-CONCERT-01", "VIP-5", 120.0
        );

        // Normal booking — same event
        Booking normalBooking = controller.bookNormalTicket(
                "U-BOB", "E-CONCERT-01", "B7", 60.0
        );

        // Custom VIP booking — Cinema event
        Booking customBooking = controller.bookCustomTicket(
                "U-CAROL",
                "E-CINEMA-02",
                "D4",
                TicketType.VIP,
                80.0,
                List.of("Snack Combo", "Premium Seat"),
                "VIP cinema experience"
        );

        // Normal booking — Workshop
        Booking workshopBooking = controller.bookNormalTicket(
                "U-DAN", "E-WORKSHOP-03", "W2", 45.0
        );

        // Normal booking with extras via custom
        Booking gymBooking = controller.bookCustomTicket(
                "U-EVE",
                "E-GYM-04",
                "G3",
                TicketType.NORMAL,
                30.0,
                List.of("Snack Combo", "Parking Access"),
                "Morning spin class"
        );

        System.out.println("\n✅ Created 5 bookings.");

        // ──────────────────────────────────────────────────────
        // DEMO 4: Cancel a booking
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 4: Cancelling a Booking\n" + STEP);

        if (normalBooking != null) {
            String cancelId = normalBooking.getBookingId();
            System.out.println("Cancelling booking: " + cancelId);
            Booking cancelled = controller.cancelBooking(cancelId, "User changed plans");
            System.out.println("New status: " + (cancelled != null ? cancelled.getStatus() : "FAILED"));

            // Try to cancel again — should fail with clear message
            System.out.println("\nAttempting to cancel the same booking again (should fail):");
            Booking secondCancel = controller.cancelBooking(cancelId, "Trying again");
            System.out.println("Result: " + (secondCancel == null ? "Correctly prevented ✅" : "ERROR: Allowed duplicate cancel ❌"));
        }

        // ──────────────────────────────────────────────────────
        // DEMO 5: Confirm → Complete lifecycle
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 5: Confirming and Completing a Booking\n" + STEP);

        if (vipBooking != null) {
            String bookingId = vipBooking.getBookingId();
            System.out.println("Confirming VIP booking: " + bookingId);
            controller.confirmBooking(bookingId);

            System.out.println("Completing VIP booking: " + bookingId);
            controller.completeBooking(bookingId);

            System.out.println("Final status: " + vipBooking.getStatus());
        }

        // ──────────────────────────────────────────────────────
        // DEMO 6: Check seat availability
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 6: Seat Availability Check\n" + STEP);

        // B7 was booked then cancelled — should now be AVAILABLE again
        controller.checkSeatAvailability("E-CONCERT-01", "B7");

        // VIP-5 is COMPLETED but was a real booking — should be TAKEN
        controller.checkSeatAvailability("E-CONCERT-01", "VIP-5");

        // Unclaimed seat
        controller.checkSeatAvailability("E-CONCERT-01", "Z99");

        // Full layout availability for concert
        List<String> concertSeats = List.of("VIP-1","VIP-2","VIP-3","VIP-4","VIP-5",
                "A1","A2","A3","B7","C10","D4","Z99");
        List<String> available = controller.getAvailableSeats("E-CONCERT-01", concertSeats);
        Set<String> taken      = controller.getTakenSeats("E-CONCERT-01");
        System.out.println("\n🗺️  Concert seat layout:");
        System.out.println("   Available (" + available.size() + "): " + available);
        System.out.println("   Taken     (" + taken.size() + "): "    + taken);

        // ──────────────────────────────────────────────────────
        // DEMO 7: Prevent duplicate seat booking
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 7: Prevent Duplicate Seat Booking\n" + STEP);

        if (workshopBooking != null) {
            String takenSeat = workshopBooking.getTicket().getSeatNumber(); // W2
            System.out.println("Trying to book already-taken seat: " + takenSeat);
            Booking duplicate = controller.bookNormalTicket(
                    "U-FRANK", "E-WORKSHOP-03", takenSeat, 45.0
            );
            System.out.println("Result: " + (duplicate == null
                    ? "Correctly blocked ✅" : "ERROR: Duplicate allowed ❌"));
        }

        // ──────────────────────────────────────────────────────
        // DEMO 8: Search booking by ID
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 8: Search Booking by ID\n" + STEP);

        if (customBooking != null) {
            String searchId = customBooking.getBookingId();
            System.out.println("Searching for booking ID: " + searchId);
            Optional<Booking> found = controller.getBookingById(searchId);
            found.ifPresent(b -> System.out.println("Found: " + b.getBookingId()
                    + " | Seat: " + b.getTicket().getSeatNumber()
                    + " | Status: " + b.getStatus()));
        }

        // Search for nonexistent booking
        System.out.println("\nSearching for a non-existent booking:");
        controller.getBookingById("B-DOES-NOT-EXIST");

        // ──────────────────────────────────────────────────────
        // DEMO 9: Display all bookings
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 9: Display All Bookings\n" + STEP);
        controller.displayAllBookings();

        // ──────────────────────────────────────────────────────
        // DEMO 10: VIP-only extra validation
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 10: VIP-only Extra Validation\n" + STEP);
        System.out.println("Attempting to add 'Backstage Access' to a NORMAL ticket...");

        try {
            new ConcreteTicketBuilder()
                    .setTicketId("T-INVALID")
                    .setBookingId("B-INVALID")
                    .setEventId("E-TEST")
                    .setUserId("U-TEST")
                    .setSeatNumber("X1")
                    .setTicketType(TicketType.NORMAL)
                    .setBasePrice(50.0)
                    .addExtra("Backstage Access")   // VIP-only!
                    .build();
            System.out.println("ERROR: Should have thrown exception ❌");
        } catch (IllegalStateException e) {
            System.out.println("Correctly rejected ✅: " + e.getMessage());
        }

        // ──────────────────────────────────────────────────────
        // DEMO 11: Admin status change
        // ──────────────────────────────────────────────────────
        System.out.println("\n📌 DEMO 11: Admin — Force Status Change\n" + STEP);

        if (gymBooking != null) {
            System.out.println("Admin forcing GYM booking to CONFIRMED...");
            controller.changeStatus(gymBooking.getBookingId(), BookingStatus.CONFIRMED);
            System.out.println("New status: " + gymBooking.getStatus());
        }

        // ──────────────────────────────────────────────────────
        // Summary
        // ──────────────────────────────────────────────────────
        System.out.println(DIVIDER);
        System.out.println("   ✅  ALL DEMO SCENARIOS COMPLETED SUCCESSFULLY");
        System.out.println("   Total bookings in system: " + controller.getAllBookings().size());
        System.out.println(DIVIDER);
    }
}
