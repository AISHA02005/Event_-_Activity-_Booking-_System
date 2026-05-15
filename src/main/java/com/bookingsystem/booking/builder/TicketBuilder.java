package com.bookingsystem.booking.builder;

import com.bookingsystem.booking.model.Ticket;
import com.bookingsystem.shared.enums.TicketType;

import java.time.LocalDateTime;
import java.util.List;


public interface TicketBuilder {


    TicketBuilder setTicketId(String ticketId);


    TicketBuilder setBookingId(String bookingId);


    TicketBuilder setEventId(String eventId);


    TicketBuilder setUserId(String userId);


    TicketBuilder setSeatNumber(String seatNumber);


    TicketBuilder setTicketType(TicketType ticketType);


    TicketBuilder setBasePrice(double basePrice);


    TicketBuilder setBookingDate(LocalDateTime bookingDate);


    TicketBuilder addExtra(String extra);


    TicketBuilder addExtras(List<String> extras);


    TicketBuilder setNotes(String notes);


    TicketBuilder reset();


    Ticket build();
}
