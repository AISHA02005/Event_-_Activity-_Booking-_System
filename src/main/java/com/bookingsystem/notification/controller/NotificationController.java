package com.bookingsystem.notification.controller;

import com.bookingsystem.notification.service.NotificationService;

public class NotificationController {

    private NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    public void bookingSuccess() {

        service.sendNotification(
                "Booking completed successfully!"
        );
    }
}