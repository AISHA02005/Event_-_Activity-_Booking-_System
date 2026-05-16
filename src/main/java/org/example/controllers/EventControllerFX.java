package org.example.controllers;

import com.bookingsystem.event.controller.EventController;
import com.bookingsystem.event.model.Event;
import com.bookingsystem.shared.enums.EventStatus;
import com.bookingsystem.shared.enums.EventType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.example.SceneManager;

import java.util.List;

public class EventControllerFX {

    // ── FXML fields ─────────────────────────────────────────────────────────
    @FXML private TableView<Event> eventsTable;
    @FXML private TableColumn<Event, String> colId;
    @FXML private TableColumn<Event, String> colTitle;
    @FXML private TableColumn<Event, String> colType;
    @FXML private TableColumn<Event, String> colLocation;
    @FXML private TableColumn<Event, String> colDate;
    @FXML private TableColumn<Event, String> colSeats;
    @FXML private TableColumn<Event, String> colStatus;
    @FXML private TableColumn<Event, String> colVipPrice;
    @FXML private TableColumn<Event, String> colNormalPrice;
    @FXML private ComboBox<String> filterTypeCombo;
    @FXML private Label statusLabel;
    @FXML private HBox detailsBox;
    @FXML private Label detailTitle;
    @FXML private Label detailLocation;
    @FXML private Label detailDescription;
    @FXML private Label detailSeats;

    // ── Backend ──────────────────────────────────────────────────────────────
    private final EventController eventController = new EventController();
    private final ObservableList<Event> eventList = FXCollections.observableArrayList();

    // ── Lifecycle ────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        setupColumns();
        setupFilter();
        loadAllEvents();
        setupSelectionListener();
    }

    // ── Column setup ─────────────────────────────────────────────────────────
    private void setupColumns() {
        colId.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getEventId())));
        colTitle.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getTitle()));
        colType.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getType() != null
                        ? d.getValue().getType().name() : ""));
        colLocation.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getLocation()));
        colDate.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getStartDateTime() != null
                        ? d.getValue().getStartDateTime().toString() : ""));
        colSeats.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getAvailableSeats())));
        colStatus.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getStatus() != null
                        ? d.getValue().getStatus().name() : ""));
        colVipPrice.setCellValueFactory(d ->
                new SimpleStringProperty(String.format("$%.2f", d.getValue().getPriceVIP())));
        colNormalPrice.setCellValueFactory(d ->
                new SimpleStringProperty(String.format("$%.2f", d.getValue().getPriceNormal())));

        eventsTable.setItems(eventList);
        eventsTable.setPlaceholder(new Label("No events found."));
    }

    // ── Filter combo setup ───────────────────────────────────────────────────
    private void setupFilter() {
        filterTypeCombo.getItems().add("ALL");
        for (EventType type : EventType.values()) {
            filterTypeCombo.getItems().add(type.name());
        }
        filterTypeCombo.setValue("ALL");
    }

    // ── Selection detail panel ───────────────────────────────────────────────
    private void setupSelectionListener() {
        eventsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        detailsBox.setVisible(true);
                        detailTitle.setText("📌 " + newVal.getTitle());
                        detailLocation.setText("📍 " + newVal.getLocation());
                        detailDescription.setText(newVal.getDescription() != null
                                ? newVal.getDescription() : "No description.");
                        detailSeats.setText("Available seats: " + newVal.getAvailableSeats()
                                + " / " + newVal.getTotalSeats());
                    } else {
                        detailsBox.setVisible(false);
                    }
                });
        detailsBox.setVisible(false);
    }

    // ── Load events from backend ─────────────────────────────────────────────
    @FXML
    private void loadAllEvents() {
        eventList.clear();
        try {
            List<Event> events = eventController.getAllEvents();
            if (events != null) {
                eventList.addAll(events);
            }
            statusLabel.setText("Loaded " + eventList.size() + " event(s).");
        } catch (Exception e) {
            statusLabel.setText("Error loading events: " + e.getMessage());
        }
    }

    @FXML
    private void applyFilter() {
        String selected = filterTypeCombo.getValue();
        eventList.clear();
        try {
            if ("ALL".equals(selected)) {
                List<Event> events = eventController.getAllEvents();
                if (events != null) eventList.addAll(events);
            } else {
                EventType type = EventType.valueOf(selected);
                List<Event> events = eventController.getEventsByType(type);
                if (events != null) eventList.addAll(events);
            }
            statusLabel.setText("Showing " + eventList.size() + " event(s) — filter: " + selected);
        } catch (Exception e) {
            statusLabel.setText("Filter error: " + e.getMessage());
        }
    }

    @FXML
    private void showAvailableOnly() {
        eventList.clear();
        try {
            List<Event> events = eventController.getAvailableEvents();
            if (events != null) eventList.addAll(events);
            statusLabel.setText("Available events: " + eventList.size());
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void openBookingForSelected() {
        Event selected = eventsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Event Selected",
                    "Please select an event first.");
            return;
        }
        // Store selected event id in a shared state and navigate
        SessionState.getInstance().setSelectedEventId(selected.getEventId());
        SceneManager.getInstance().showBooking();
    }

    @FXML
    private void goHome() {
        SceneManager.getInstance().showHome();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
