package com.bookingsystem.review.model;

public class Review {

    private String reviewId;
    private String userId;
    private String eventId;
    private int rating;
    private String comment;

    public Review(String reviewId,
                  String userId,
                  String eventId,
                  int rating,
                  String comment) {

        this.reviewId = reviewId;
        this.userId = userId;
        this.eventId = eventId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}