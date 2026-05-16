package org.example.controllers;

import com.bookingsystem.booking.model.Booking;
import com.bookingsystem.booking.service.BookingService;
import com.bookingsystem.shared.enums.TicketType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.SceneManager;

import java.util.List;
import java.util.Set;

public class BookingControllerFX {

    @FXML private TextField eventIdField;
    @FXML private TextField userIdField;
    @FXML private TextField seatField;
    @FXML private ComboBox<String> ticketTypeCombo;
    @FXML private ComboBox<String> extrasCombo;
    @FXML private Label statusLabel;
    @FXML private Label bookingResultLabel;

    @FXML private TableView<Booking> bookingsTable;
    @FXML private TableColumn<Booking, String> colBookingId;
    @FXML private TableColumn<Booking, String> colBookingEvent;
    @FXML private TableColumn<Booking, String> colBookingUser;
    @FXML private TableColumn<Booking, String> colBookingStatus;
    @FXML private TableColumn<Booking, String> colBookingSeat;

    @FXML private TextField cancelIdField;
    @FXML private TextField confirmIdField;

    @FXML private TextField seatCheckEventField;
    @FXML private TextField seatCheckSeatField;
    @FXML private Label seatStatusLabel;
    @FXML private TextArea availableSeatsArea;

    private final BookingService bookingService = new BookingService();
    private final ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ticketTypeCombo.setItems(FXCollections.observableArrayList("NORMAL", "VIP", "CUSTOM"));
        ticketTypeCombo.setValue("NORMAL");

        extrasCombo.setItems(FXCollections.observableArrayList(
                "None", "Meal", "Insurance", "Parking", "VIP Lounge"));
        extrasCombo.setValue("None");

        setupBookingTable();
        loadAllBookings();

        // ✅ Safe session handling
        try {
            String eventIdStr = SessionState.getInstance().getSelectedEventId();
            if (eventIdStr != null && !eventIdStr.isEmpty()) {
                eventIdField.setText(eventIdStr);
            }
        } catch (Exception ignored) {}

        try {
            String userIdStr = SessionState.getInstance().getCurrentUserId();
            if (userIdStr != null && !userIdStr.isEmpty()) {
                userIdField.setText(userIdStr);
            }
        } catch (Exception ignored) {}
    }

    private void setupBookingTable() {
        colBookingId.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getBookingId())));
        colBookingEvent.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getEventId())));
        colBookingUser.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getUserId())));
        colBookingStatus.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getStatus() != null
                        ? d.getValue().getStatus().name() : ""));
        colBookingSeat.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getTicket() != null
                        ? String.valueOf(d.getValue().getTicket().getSeatNumber()) : "-"));

        bookingsTable.setItems(bookingList);
        bookingsTable.setPlaceholder(new Label("No bookings yet."));
    }

    @FXML
    private void handleCreateBooking() {
        try {
            if (eventIdField.getText().isEmpty() ||
                    userIdField.getText().isEmpty() ||
                    seatField.getText().isEmpty()) {

                bookingResultLabel.setText("❌ Fill all fields first.");
                return;
            }

            int eventId = Integer.parseInt(eventIdField.getText().trim());
            int userId  = Integer.parseInt(userIdField.getText().trim());
            int seat    = Integer.parseInt(seatField.getText().trim());

            String type = ticketTypeCombo.getValue();

            Booking booking;

            switch (type) {
                case "VIP":
                    booking = bookingService.createVipBooking(
                            String.valueOf(userId),
                            String.valueOf(eventId),
                            String.valueOf(seat),
                            300
                    );
                    break;

                case "CUSTOM":
                    booking = bookingService.createCustomBooking(
                            String.valueOf(userId),
                            String.valueOf(eventId),
                            String.valueOf(seat),
                            TicketType.VIP,
                            300,
                            List.of("Snack"),
                            "Near stage"
                    );
                    break;

                default:
                    booking = bookingService.createNormalBooking(
                            String.valueOf(userId),
                            String.valueOf(eventId),
                            String.valueOf(seat),
                            150
                    );
                    break;
            }

            if (booking != null) {
                SessionState.getInstance().setLastBookingId(booking.getBookingId());
                bookingResultLabel.setText("✅ Booking created! ID: " + booking.getBookingId());
                statusLabel.setText("Success");
                loadAllBookings();
            } else {
                bookingResultLabel.setText("❌ Booking failed.");
                statusLabel.setText("Failed");
            }

        } catch (NumberFormatException e) {
            bookingResultLabel.setText("❌ Enter valid numbers.");
            statusLabel.setText("Input error");
        } catch (Exception e) {
            bookingResultLabel.setText("❌ " + e.getMessage());
            statusLabel.setText("Error");
        }
    }

    @FXML
    private void handleCancelBooking() {
        try {
            String bookingId = cancelIdField.getText().trim();

            if (bookingId.isEmpty()) {
                bookingResultLabel.setText("❌ Enter booking ID.");
                return;
            }

            Booking result = bookingService.cancelBooking(bookingId, "User cancelled");

            if (result != null) {
                bookingResultLabel.setText("❌ Booking cancelled.");
                loadAllBookings();
            } else {
                bookingResultLabel.setText("❌ Cancel failed.");
            }

        } catch (Exception e) {
            bookingResultLabel.setText("❌ " + e.getMessage());
        }
    }

    @FXML
    private void handleConfirmBooking() {
        try {
            String bookingId = confirmIdField.getText().trim();

            if (bookingId.isEmpty()) {
                bookingResultLabel.setText("❌ Enter booking ID.");
                return;
            }

            bookingService.confirmBooking(bookingId);

            bookingResultLabel.setText("✅ Booking confirmed.");
            loadAllBookings();

        } catch (Exception e) {
            bookingResultLabel.setText("❌ " + e.getMessage());
        }
    }

    @FXML
    private void handleCheckSeat() {
        try {
            String eventId = seatCheckEventField.getText().trim();
            String seat    = seatCheckSeatField.getText().trim();

            if (eventId.isEmpty() || seat.isEmpty()) {
                seatStatusLabel.setText("Enter data first.");
                return;
            }

            boolean available = bookingService.isSeatAvailable(eventId, seat);

            seatStatusLabel.setText(available
                    ? "✅ Available"
                    : "❌ Taken");

        } catch (Exception e) {
            seatStatusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleShowAvailableSeats() {
        try {
            String eventId = seatCheckEventField.getText().trim();

            if (eventId.isEmpty()) {
                availableSeatsArea.setText("Enter event ID.");
                return;
            }

            List<String> available = bookingService.getAvailableSeats(eventId);
            Set<String> taken     = bookingService.getTakenSeats(eventId);

            availableSeatsArea.setText(
                    "Available: " + available + "\nTaken: " + taken);

        } catch (Exception e) {
            availableSeatsArea.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void loadAllBookings() {
        bookingList.clear();
        try {
            List<Booking> all = bookingService.getAllBookings();
            if (all != null) bookingList.addAll(all);
        } catch (Exception e) {
            statusLabel.setText("Load error: " + e.getMessage());
        }
    }

    @FXML private void goHome()    { SceneManager.getInstance().showHome(); }
    @FXML private void goPayment() { SceneManager.getInstance().showPayment(); }
}