package com.bookingsystem.review.service;

import com.bookingsystem.review.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {

    private List<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {

        reviews.add(review);
    }

    public List<Review> getAllReviews() {

        return reviews;
    }
}