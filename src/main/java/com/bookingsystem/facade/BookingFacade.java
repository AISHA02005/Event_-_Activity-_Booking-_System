package com.bookingsystem.facade;

import com.bookingsystem.booking.model.Booking;
import com.bookingsystem.booking.service.BookingService;
import com.bookingsystem.event.model.Event;
import com.bookingsystem.event.service.EventService;
import com.bookingsystem.notification.observer.NotificationManager;
import com.bookingsystem.notification.service.NotificationService;
import com.bookingsystem.payment.service.PaymentContext;
import com.bookingsystem.payment.strategy.VisaPayment;
import com.bookingsystem.shared.enums.EventType;

import java.util.HashMap;
import java.util.Map;

public class BookingFacade {

    private EventService eventService;
    private BookingService bookingService;
    private PaymentContext paymentContext;
    private NotificationService notificationService;

    public BookingFacade() {
        eventService = new EventService();
        bookingService = new BookingService();
        paymentContext = new PaymentContext();
        notificationService = new NotificationService(new NotificationManager());
    }

    private void validatePaymentMethod(String method) {
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Payment method is required");
        }
    }

    public void fullBookingProcess(String paymentMethod) {

        System.out.println("=== BOOKING PROCESS STARTED ===");

        validatePaymentMethod(paymentMethod);

        Map<String, Object> params = new HashMap<>();

        params.put("title", "AI Workshop");
        params.put("description", "Learn AI basics");
        params.put("location", "Cairo");

        params.put("startDateTime", java.time.LocalDateTime.now().plusDays(1));
        params.put("endDateTime", java.time.LocalDateTime.now().plusDays(1).plusHours(3));

        params.put("priceNormal", 200.0);
        params.put("priceVIP", 300.0);
        params.put("totalSeats", 50);
        params.put("organizerId", "ORG-1");

        params.put("topic", "Artificial Intelligence");
        params.put("instructor", "Dr. Ahmed");
        params.put("skillLevel", "Beginner");
        params.put("materialsProvided", true);

        Event event = eventService.createEvent(EventType.WORKSHOP, params);

        System.out.println("Event created successfully");

        Booking booking = bookingService.createVipBooking(
                "USER-1",
                event.getEventId(),
                "A1",
                300
        );

        System.out.println("Booking created successfully");

        switch (paymentMethod.toLowerCase()) {

            case "visa":
                paymentContext.setStrategy(new VisaPayment("1234-5678-9999"));
                break;

            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }

        paymentContext.executePayment(300);

        System.out.println("Payment completed");

        bookingService.confirmBooking(booking.getBookingId());

        notificationService.sendNotification(
                "Booking confirmed for event: " + event.getTitle()
        );

        System.out.println("=== PROCESS COMPLETED SUCCESSFULLY ===");
    }
}