package org.example.controllers;

import com.bookingsystem.review.model.Review;
import com.bookingsystem.review.service.ReviewService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.SceneManager;

import java.util.List;

public class ReviewControllerFX {

    // ── FXML fields ─────────────────────────────────────────────────────────
    @FXML private TextField reviewUserIdField;
    @FXML private TextField reviewEventIdField;
    @FXML private ComboBox<Integer> ratingCombo;
    @FXML private TextArea commentArea;
    @FXML private Label reviewStatusLabel;

    @FXML private TableView<Review> reviewsTable;
    @FXML private TableColumn<Review, String> colRevId;
    @FXML private TableColumn<Review, String> colRevUser;
    @FXML private TableColumn<Review, String> colRevEvent;
    @FXML private TableColumn<Review, String> colRevRating;
    @FXML private TableColumn<Review, String> colRevComment;

    // ── Backend ──────────────────────────────────────────────────────────────
    private final ReviewService reviewService = new ReviewService();
    private final ObservableList<Review> reviewList = FXCollections.observableArrayList();

    // ── Lifecycle ────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        ratingCombo.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        ratingCombo.setValue(5);

        setupReviewTable();
        loadAllReviews();

        // Pre-fill from session
        reviewUserIdField.setText(String.valueOf(SessionState.getInstance().getCurrentUserId()));
        String  eventId = SessionState.getInstance().getSelectedEventId();
        if (eventId != "-1") reviewEventIdField.setText(String.valueOf(eventId));
    }

    // ── Table setup ──────────────────────────────────────────────────────────
    private void setupReviewTable() {
        colRevId.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getReviewId())));
        colRevUser.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getUserId())));
        colRevEvent.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getEventId())));
        colRevRating.setCellValueFactory(d ->
                new SimpleStringProperty(
                        "★".repeat(d.getValue().getRating())
                                + "☆".repeat(5 - d.getValue().getRating())));
        colRevComment.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getComment()));

        reviewsTable.setItems(reviewList);
        reviewsTable.setPlaceholder(new Label("No reviews yet."));
    }

    // ── Add review ───────────────────────────────────────────────────────────
    @FXML
    private void handleAddReview() {
        String userText  = reviewUserIdField.getText().trim();
        String eventText = reviewEventIdField.getText().trim();
        String comment   = commentArea.getText().trim();
        Integer rating   = ratingCombo.getValue();

        if (userText.isEmpty() || eventText.isEmpty() || comment.isEmpty() || rating == null) {
            reviewStatusLabel.setText("❌ Fill in all fields.");
            return;
        }
        try {
            String userId  = String.valueOf(Integer.parseInt(userText));
            int eventId = Integer.parseInt(eventText);
            Review review = reviewService.addReview(userId, String.valueOf(eventId), rating, comment);
            if (review != null) {
                reviewStatusLabel.setText("✅ Review submitted! ID: " + review.getReviewId());
                commentArea.clear();
                ratingCombo.setValue(5);
                loadAllReviews();
            } else {
                reviewStatusLabel.setText("❌ Could not submit review.");
            }
        } catch (NumberFormatException e) {
            reviewStatusLabel.setText("❌ User ID and Event ID must be numbers.");
        } catch (Exception e) {
            reviewStatusLabel.setText("❌ " + e.getMessage());
        }
    }

    // ── Load all reviews ─────────────────────────────────────────────────────
    @FXML
    private void loadAllReviews() {
        reviewList.clear();
        try {
            List<Review> all = reviewService.getAllReviews();
            if (all != null) reviewList.addAll(all);
        } catch (Exception e) {
            reviewStatusLabel.setText("Error loading reviews: " + e.getMessage());
        }
    }

    // ── Navigation ───────────────────────────────────────────────────────────
    @FXML private void goHome() { SceneManager.getInstance().showHome(); }
}
