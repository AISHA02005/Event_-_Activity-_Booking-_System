package com.bookingsystem.notification.service;

import com.bookingsystem.notification.model.Notification;
import com.bookingsystem.notification.observer.NotificationManager;

public class NotificationService {

    private NotificationManager manager;

    public NotificationService(NotificationManager manager) {
        this.manager = manager;
    }

    public void sendNotification(String message) {

        Notification notification =
                new Notification(message, "INFO");

        manager.notifyUsers(notification);
    }
}