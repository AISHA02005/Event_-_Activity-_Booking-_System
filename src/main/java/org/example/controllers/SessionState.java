package org.example.controllers;

public class SessionState {

    private static SessionState instance;

    private String selectedEventId;
    private String lastBookingId;
    private String currentUserId = "USER-001";

    private SessionState() {}

    public static SessionState getInstance() {
        if (instance == null) {
            instance = new SessionState();
        }
        return instance;
    }

    public String getSelectedEventId() {
        return selectedEventId;
    }

    public void setSelectedEventId(String id) {
        this.selectedEventId = id;
    }

    public String getLastBookingId() {
        return lastBookingId;
    }

    public void setLastBookingId(String id) {
        this.lastBookingId = id;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String id) {
        this.currentUserId = id;
    }
}