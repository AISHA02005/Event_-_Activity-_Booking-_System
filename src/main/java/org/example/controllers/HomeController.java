package org.example.controllers;

import javafx.fxml.FXML;
import org.example.SceneManager;

public class HomeController {

    @FXML
    private void goEvents() {
        SceneManager.getInstance().showEvents();
    }

    @FXML
    private void goBooking() {
        SceneManager.getInstance().showBooking();
    }

    @FXML
    private void goPayment() {
        SceneManager.getInstance().showPayment();
    }

    @FXML
    private void goNotifications() {
        SceneManager.getInstance().showNotifications();
    }

    @FXML
    private void goReviews() {
        SceneManager.getInstance().showReviews();
    }

    @FXML
    private void goSearch() {
        SceneManager.getInstance().showSearch();
    }
}
