package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static SceneManager instance;
    private Stage primaryStage;

    private static final String CSS_PATH = "/css/style.css";

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void init(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    // ── Navigation helpers ──────────────────────────────────────────────────

    public void showSplash() {
        loadScene("/fxml/splash.fxml", "Booking System");
    }

    public void showHome() {
        loadScene("/fxml/home.fxml", "Home Dashboard");
    }

    public void showEvents() {
        loadScene("/fxml/event/event-view.fxml", "Browse Events");
    }

    public void showBooking() {
        loadScene("/fxml/booking/booking-view.fxml", "Booking");
    }

    public void showPayment() {
        loadScene("/fxml/payment/payment-view.fxml", "Payment");
    }

    public void showNotifications() {
        loadScene("/fxml/notification/notification-view.fxml", "Notifications");
    }

    public void showReviews() {
        loadScene("/fxml/review/review-view.fxml", "Reviews");
    }

    public void showSearch() {
        loadScene("/fxml/search/search-view.fxml", "Search Events");
    }

    // ── Internal loader ─────────────────────────────────────────────────────

    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            String css = getClass().getResource(CSS_PATH) != null
                    ? getClass().getResource(CSS_PATH).toExternalForm()
                    : null;
            if (css != null) {
                scene.getStylesheets().add(css);
            }
            primaryStage.setScene(scene);
            primaryStage.setTitle("Event & Activity Booking System — " + title);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load screen: " + fxmlPath + "\n" + e.getMessage());
        }
    }

    private void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Navigation Error");
        alert.setHeaderText("Screen Load Failed");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
