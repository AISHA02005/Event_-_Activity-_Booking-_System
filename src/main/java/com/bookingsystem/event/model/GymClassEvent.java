package com.bookingsystem.event.model;

import com.bookingsystem.shared.enums.EventType;

import java.time.LocalDateTime;

/**
 * Concrete Event — Gym Class 🏋️
 * Created by EventFactory
 */
public class GymClassEvent extends Event {

    private String classType; // Yoga, Zumba, CrossFit, Pilates
    private String trainerName;
    private String fitnessLevel; // Beginner, Intermediate, Advanced
    private int durationMinutes;

    public GymClassEvent(String eventId, String title, String description, String location,
                         LocalDateTime startDateTime, LocalDateTime endDateTime,
                         double priceNormal, double priceVIP, int totalSeats,
                         String organizerId, String classType, String trainerName,
                         String fitnessLevel, int durationMinutes) {
        super(eventId, title, description, location, startDateTime, endDateTime,
                priceNormal, priceVIP, totalSeats, organizerId);
        this.type = EventType.GYM_CLASS;
        this.classType = classType;
        this.trainerName = trainerName;
        this.fitnessLevel = fitnessLevel;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String getEventDetails() {
        return "🏋️ Gym Class: " + title +
                "\n   Class Type: " + classType +
                "\n   Trainer: " + trainerName +
                "\n   Fitness Level: " + fitnessLevel +
                "\n   Duration: " + durationMinutes + " mins" +
                "\n   Location: " + location +
                "\n   Date: " + startDateTime +
                "\n   Price (Normal): $" + priceNormal +
                "\n   Price (VIP): $" + priceVIP +
                "\n   Available Seats: " + availableSeats + "/" + totalSeats;
    }

    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    public String getFitnessLevel() { return fitnessLevel; }
    public void setFitnessLevel(String fitnessLevel) { this.fitnessLevel = fitnessLevel; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
}