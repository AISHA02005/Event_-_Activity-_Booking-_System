package com.bookingsystem.booking.model;

import com.bookingsystem.shared.enums.BookingStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Booking {

    private final String bookingId;
    private final String userId;
    private final String eventId;
    private final Ticket ticket;
    private final LocalDateTime createdAt;

    private BookingStatus status;
    private LocalDateTime updatedAt;
    private String cancellationReason;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public Booking(String bookingId, String userId, String eventId, Ticket ticket) {
        if (bookingId == null || bookingId.isBlank()) throw new IllegalArgumentException("bookingId cannot be null/blank");
        if (userId    == null || userId.isBlank())    throw new IllegalArgumentException("userId cannot be null/blank");
        if (eventId   == null || eventId.isBlank())   throw new IllegalArgumentException("eventId cannot be null/blank");
        if (ticket    == null)                        throw new IllegalArgumentException("ticket cannot be null");

        this.bookingId = bookingId;
        this.userId    = userId;
        this.eventId   = eventId;
        this.ticket    = ticket;
        this.status    = BookingStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    // ---- Getters ----

    public String getBookingId()     { return bookingId; }
    public String getUserId()        { return userId; }
    public String getEventId()       { return eventId; }
    public Ticket getTicket()        { return ticket; }
    public BookingStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCancellationReason() { return cancellationReason; }

    // ---- Status transitions ----

    public void confirm() {
        if (this.status != BookingStatus.PENDING) {
            throw new IllegalStateException("Only PENDING bookings can be confirmed. Current status: " + status);
        }
        this.status    = BookingStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel(String reason) {
        if (this.status == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already CANCELLED.");
        }
        if (this.status == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a COMPLETED booking.");
        }
        this.status              = BookingStatus.CANCELLED;
        this.cancellationReason  = reason;
        this.updatedAt           = LocalDateTime.now();
    }

    public void complete() {
        if (this.status != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only CONFIRMED bookings can be completed. Current status: " + status);
        }
        this.status    = BookingStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeStatus(BookingStatus newStatus) {
        this.status    = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    // ---- Display ----

    public void displayDetails() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════════╗\n");
        sb.append("║           BOOKING DETAILS                ║\n");
        sb.append("╠══════════════════════════════════════════╣\n");
        sb.append(String.format("║  Booking ID  : %-26s║%n", bookingId));
        sb.append(String.format("║  User ID     : %-26s║%n", userId));
        sb.append(String.format("║  Event ID    : %-26s║%n", eventId));
        sb.append(String.format("║  Status      : %-26s║%n", status));
        sb.append(String.format("║  Created At  : %-26s║%n", createdAt.format(FORMATTER)));
        sb.append(String.format("║  Updated At  : %-26s║%n", updatedAt.format(FORMATTER)));
        if (cancellationReason != null) {
            sb.append(String.format("║  Cancel Reason: %-25s║%n", cancellationReason));
        }
        sb.append("╠══════════════════════════════════════════╣\n");
        sb.append("║              TICKET INFO                 ║\n");
        sb.append("╠══════════════════════════════════════════╣\n");
        sb.append(String.format("║  Ticket Type : %-26s║%n", ticket.getTicketType().getDisplayName()));
        sb.append(String.format("║  Seat        : %-26s║%n", ticket.getSeatNumber()));
        sb.append(String.format("║  Price       : $%-25.2f║%n", ticket.getTicketPrice()));
        sb.append(String.format("║  Extras      : %-26s║%n",
                ticket.getExtras().isEmpty() ? "None" : String.join(", ", ticket.getExtras())));
        sb.append("╚══════════════════════════════════════════╝\n");
        return sb.toString();
    }
}
