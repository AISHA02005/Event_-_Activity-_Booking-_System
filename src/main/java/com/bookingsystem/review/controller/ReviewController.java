package com.bookingsystem.review.controller;

import com.bookingsystem.review.model.Review;
import com.bookingsystem.review.service.ReviewService;
import org.example.controllers.SessionState;

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

        service.addReview(
                SessionState.getInstance().getCurrentUserId(),
                SessionState.getInstance().getSelectedEventId(),
                rating,
                comment
        );

        System.out.println("Review Added Successfully");
    }
}