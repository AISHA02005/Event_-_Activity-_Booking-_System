package com.bookingsystem.notification.observer;

import com.bookingsystem.notification.model.Notification;

public interface Observer {

    void update(Notification notification);
}