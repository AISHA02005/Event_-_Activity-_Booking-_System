package com.bookingsystem.booking.builder;

import com.bookingsystem.booking.model.Ticket;
import com.bookingsystem.shared.enums.TicketType;

import java.util.List;


public class TicketDirector {

    private final TicketBuilder builder;


    public TicketDirector(TicketBuilder builder) {
        if (builder == null) throw new IllegalArgumentException("Builder cannot be null");
        this.builder = builder;
    }


    public Ticket buildVipTicket(String ticketId,
                                 String bookingId,
                                 String eventId,
                                 String userId,
                                 String seatNumber,
                                 double basePrice) {
        builder.reset();
        return builder
                .setTicketId(ticketId)
                .setBookingId(bookingId)
                .setEventId(eventId)
                .setUserId(userId)
                .setSeatNumber(seatNumber)
                .setTicketType(TicketType.VIP)
                .setBasePrice(basePrice)
                .addExtra("Premium Seat")
                .addExtra("Backstage Access")
                .setNotes("VIP package — enjoy exclusive access")
                .build();
    }




    public Ticket buildVipTicketWithExtras(String ticketId,
                                           String bookingId,
                                           String eventId,
                                           String userId,
                                           String seatNumber,
                                           double basePrice,
                                           List<String> extras) {
        builder.reset();
        return builder
                .setTicketId(ticketId)
                .setBookingId(bookingId)
                .setEventId(eventId)
                .setUserId(userId)
                .setSeatNumber(seatNumber)
                .setTicketType(TicketType.VIP)
                .setBasePrice(basePrice)
                .addExtras(extras)
                .setNotes("Custom VIP package")
                .build();
    }


    public Ticket buildNormalTicket(String ticketId,
                                    String bookingId,
                                    String eventId,
                                    String userId,
                                    String seatNumber,
                                    double basePrice) {
        builder.reset();
        return builder
                .setTicketId(ticketId)
                .setBookingId(bookingId)
                .setEventId(eventId)
                .setUserId(userId)
                .setSeatNumber(seatNumber)
                .setTicketType(TicketType.NORMAL)
                .setBasePrice(basePrice)
                .setNotes("Standard ticket")
                .build();
    }


    public Ticket buildNormalTicketWithOptions(String ticketId,
                                               String bookingId,
                                               String eventId,
                                               String userId,
                                               String seatNumber,
                                               double basePrice,
                                               boolean includeSnack,
                                               boolean includeParking) {
        builder.reset();
        builder
                .setTicketId(ticketId)
                .setBookingId(bookingId)
                .setEventId(eventId)
                .setUserId(userId)
                .setSeatNumber(seatNumber)
                .setTicketType(TicketType.NORMAL)
                .setBasePrice(basePrice);

        if (includeSnack)   builder.addExtra("Snack Combo");
        if (includeParking) builder.addExtra("Parking Access");

        return builder.build();
    }
}
