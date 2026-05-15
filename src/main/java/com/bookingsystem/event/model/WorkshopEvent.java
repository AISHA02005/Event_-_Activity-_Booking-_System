package com.bookingsystem.event.model;

import com.bookingsystem.shared.enums.EventType;

import java.time.LocalDateTime;

/**
 * Concrete Event — Workshop 🎨
 * Created by EventFactory
 */
public class WorkshopEvent extends Event {

    private String topic;
    private String instructor;
    private String skillLevel; // Beginner, Intermediate, Advanced
    private boolean materialsProvided;

    public WorkshopEvent(String eventId, String title, String description, String location,
                         LocalDateTime startDateTime, LocalDateTime endDateTime,
                         double priceNormal, double priceVIP, int totalSeats,
                         String organizerId, String topic, String instructor,
                         String skillLevel, boolean materialsProvided) {
        super(eventId, title, description, location, startDateTime, endDateTime,
                priceNormal, priceVIP, totalSeats, organizerId);
        this.type = EventType.WORKSHOP;
        this.topic = topic;
        this.instructor = instructor;
        this.skillLevel = skillLevel;
        this.materialsProvided = materialsProvided;
    }

    @Override
    public String getEventDetails() {
        return "🎨 Workshop: " + title +
                "\n   Topic: " + topic +
                "\n   Instructor: " + instructor +
                "\n   Skill Level: " + skillLevel +
                "\n   Materials Provided: " + (materialsProvided ? "Yes" : "No") +
                "\n   Location: " + location +
                "\n   Date: " + startDateTime +
                "\n   Price (Normal): $" + priceNormal +
                "\n   Price (VIP): $" + priceVIP +
                "\n   Available Seats: " + availableSeats + "/" + totalSeats;
    }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public String getSkillLevel() { return skillLevel; }
    public void setSkillLevel(String skillLevel) { this.skillLevel = skillLevel; }

    public boolean isMaterialsProvided() { return materialsProvided; }
    public void setMaterialsProvided(boolean materialsProvided) { this.materialsProvided = materialsProvided; }
}