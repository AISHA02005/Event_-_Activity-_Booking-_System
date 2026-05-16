package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.SceneManager;

public class SplashController {

    @FXML private Label titleLabel;
    @FXML private Label subtitleLabel;
    @FXML private Button enterButton;

    @FXML
    public void initialize() {
        titleLabel.setText("🎟  Event & Activity Booking System");
        subtitleLabel.setText("Concerts · Cinema · Workshops · Trips · Gym Classes");
    }

    @FXML
    private void handleEnter() {
        SceneManager.getInstance().showHome();
    }
}
