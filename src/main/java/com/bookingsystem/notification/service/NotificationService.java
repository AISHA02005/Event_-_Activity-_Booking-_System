package com.bookingsystem.notification.service;

import com.bookingsystem.notification.model.Notification;
import com.bookingsystem.notification.observer.NotificationManager;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private NotificationManager manager;
    private List<Notification> notifications =
            new ArrayList<>();

    public NotificationService(NotificationManager manager) {
        this.manager = manager;
    }

    public void sendNotification(String message) {

        Notification notification =
                new Notification(message, "INFO");

        notifications.add(notification);

        manager.notifyUsers(notification);
    }

    public List<Notification> getAllNotifications() {
        return notifications;
    }
}