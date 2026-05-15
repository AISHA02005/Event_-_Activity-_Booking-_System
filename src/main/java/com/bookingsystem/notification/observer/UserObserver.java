package com.bookingsystem.notification.observer;

import com.bookingsystem.notification.model.Notification;

public class UserObserver implements Observer {

    private String userName;

    public UserObserver(String userName) {
        this.userName = userName;
    }

    @Override
    public void update(Notification notification) {

        System.out.println(
                userName + " received: "
                        + notification.getMessage()
        );
    }
}