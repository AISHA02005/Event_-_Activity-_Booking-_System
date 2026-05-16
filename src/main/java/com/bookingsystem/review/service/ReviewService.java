package com.bookingsystem.review.service;

import com.bookingsystem.review.model.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewService {

    private List<Review> reviews = new ArrayList<>();

    public Review addReview(String userId,
                            String eventId,
                            Integer rating,
                            String comment) {

        Review review = new Review(
                UUID.randomUUID().toString(),
                userId,
                eventId,
                rating,
                comment
        );

        reviews.add(review);

        return review;
    }

    public List<Review> getAllReviews() {
        return reviews;
    }
}