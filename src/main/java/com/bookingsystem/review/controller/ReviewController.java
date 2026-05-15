package com.bookingsystem.review.controller;

import com.bookingsystem.review.model.Review;
import com.bookingsystem.review.service.ReviewService;

public class ReviewController {

    private ReviewService service;

    public ReviewController(ReviewService service) {

        this.service = service;
    }

    public void submitReview(

            String userName,
            int rating,
            String comment
    ) {

        Review review =
                new Review(userName, rating, comment);

        service.addReview(review);

        System.out.println("Review Added Successfully");
    }
}