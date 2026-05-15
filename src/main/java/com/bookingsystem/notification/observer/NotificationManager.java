package com.bookingsystem.notification.observer;

import com.bookingsystem.notification.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

    private List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyUsers(Notification notification) {

        for (Observer observer : observers) {
            observer.update(notification);
        }
    }
}