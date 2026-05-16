package org.example.controllers;

import com.bookingsystem.notification.model.Notification;
import com.bookingsystem.notification.observer.NotificationManager;
import com.bookingsystem.notification.observer.UserObserver;
import com.bookingsystem.notification.service.NotificationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.SceneManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotificationControllerFX {

    // ── FXML fields ─────────────────────────────────────────────────────────
    @FXML private TextField subscribeUserIdField;
    @FXML private TextField subscribeUserNameField;
    @FXML private Label subscribeStatusLabel;

    @FXML private TextField sendMessageField;
    @FXML private Label sendStatusLabel;

    @FXML private ListView<String> notificationsListView;
    @FXML private Label listStatusLabel;

    // ── Backend ──────────────────────────────────────────────────────────────
    private final NotificationManager notificationManager = NotificationManager.getInstance();
    private final NotificationService notificationService =
            new NotificationService(notificationManager);

    private final ObservableList<String> notificationItems = FXCollections.observableArrayList();

    // لمنع التكرار
    private final Set<String> displayedNotifications = new HashSet<>();

    // ── Lifecycle ────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        notificationsListView.setItems(notificationItems);
        notificationsListView.setPlaceholder(new Label("No notifications yet."));
    }

    // ── Subscribe ────────────────────────────────────────────────────────────
    @FXML
    private void handleSubscribe() {
        String idText = subscribeUserIdField.getText().trim();
        String name = subscribeUserNameField.getText().trim();

        if (idText.isEmpty() || name.isEmpty()) {
            subscribeStatusLabel.setText("❌ Fill in User ID and Name.");
            return;
        }

        try {
            int userId = Integer.parseInt(idText);

            UserObserver observer = new UserObserver(name);
            notificationManager.subscribe(observer);

            subscribeStatusLabel.setText(
                    "✅ User '" + name + "' (ID: " + userId + ") subscribed."
            );

            // تنظيف الحقول
            subscribeUserIdField.clear();
            subscribeUserNameField.clear();

        } catch (NumberFormatException e) {
            subscribeStatusLabel.setText("❌ User ID must be a number.");
        } catch (Exception e) {
            subscribeStatusLabel.setText("❌ " + e.getMessage());
        }
    }

    // ── Send notification ────────────────────────────────────────────────────
    @FXML
    private void handleSend() {
        String message = sendMessageField.getText().trim();

        if (message.isEmpty()) {
            sendStatusLabel.setText("❌ Message cannot be empty.");
            return;
        }

        try {
            notificationService.sendNotification(message);

            sendStatusLabel.setText("✅ Notification sent!");

            // عرضها في الليست
            addNotificationToList(message);

            // تنظيف الحقل
            sendMessageField.clear();

        } catch (Exception e) {
            sendStatusLabel.setText("❌ " + e.getMessage());
        }
    }

    // ── Show notifications ───────────────────────────────────────────────────
    @FXML
    private void handleShowNotifications() {
        try {
            List<Notification> notifications =
                    notificationService.getAllNotifications();

            if (notifications != null && !notifications.isEmpty()) {
                for (Notification n : notifications) {
                    addNotificationToList(n.toString());
                }
            }

            listStatusLabel.setText("Notifications: " + notificationItems.size());

        } catch (Exception e) {
            listStatusLabel.setText("❌ Error: " + e.getMessage());
        }
    }

    // ── Helper (منع التكرار + تنسيق العرض) ──────────────────────────────────
    private void addNotificationToList(String message) {
        if (message == null || message.isEmpty()) return;

        if (!displayedNotifications.contains(message)) {
            displayedNotifications.add(message);
            notificationItems.add(0, "🔔 " + message);
        }
    }

    // ── Clear list ───────────────────────────────────────────────────────────
    @FXML
    private void handleClearList() {
        notificationItems.clear();
        displayedNotifications.clear();
        listStatusLabel.setText("Cleared.");
    }

    // ── Navigation ───────────────────────────────────────────────────────────
    @FXML
    private void goHome() {
        SceneManager.getInstance().showHome();
    }
}