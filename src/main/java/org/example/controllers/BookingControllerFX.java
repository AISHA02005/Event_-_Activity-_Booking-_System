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

    // ── FXML fields ─────────────────────────────────────────────────────────
    @FXML private TextField eventIdField;
    @FXML private TextField userIdField;
    @FXML private TextField seatField;
    @FXML private ComboBox<String> ticketTypeCombo;
    @FXML private ComboBox<String> extrasCombo;
    @FXML private Label statusLabel;
    @FXML private Label bookingResultLabel;

    // ── Booking history table ────────────────────────────────────────────────
    @FXML private TableView<Booking> bookingsTable;
    @FXML private TableColumn<Booking, String> colBookingId;
    @FXML private TableColumn<Booking, String> colBookingEvent;
    @FXML private TableColumn<Booking, String> colBookingUser;
    @FXML private TableColumn<Booking, String> colBookingStatus;
    @FXML private TableColumn<Booking, String> colBookingSeat;

    // ── Cancel / confirm fields ──────────────────────────────────────────────
    @FXML private TextField cancelIdField;
    @FXML private TextField confirmIdField;

    // ── Seat availability ────────────────────────────────────────────────────
    @FXML private TextField seatCheckEventField;
    @FXML private TextField seatCheckSeatField;
    @FXML private Label seatStatusLabel;
    @FXML private TextArea availableSeatsArea;

    // ── Backend ──────────────────────────────────────────────────────────────
    private final BookingService bookingService = new BookingService();
    private final ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    // ── Lifecycle ────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        // Ticket types
        ticketTypeCombo.setItems(FXCollections.observableArrayList("NORMAL", "VIP", "CUSTOM"));
        ticketTypeCombo.setValue("NORMAL");

        // Extras (for custom booking)
        extrasCombo.setItems(FXCollections.observableArrayList(
                "None", "Meal", "Insurance", "Parking", "VIP Lounge"));
        extrasCombo.setValue("None");

        setupBookingTable();
        loadAllBookings();

        // Pre-fill from session if an event was selected in the Events screen
        int sessionEventId = Integer.parseInt(SessionState.getInstance().getSelectedEventId());
        if (sessionEventId != -1) {
            eventIdField.setText(String.valueOf(sessionEventId));
        }

        int sessionUserId = Integer.parseInt(SessionState.getInstance().getCurrentUserId());
        userIdField.setText(String.valueOf(sessionUserId));
    }

    // ── Table setup ──────────────────────────────────────────────────────────
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

    // ── Create booking ───────────────────────────────────────────────────────
    @FXML
    private void handleCreateBooking() {
        try {
            int eventId = Integer.parseInt(eventIdField.getText().trim());
            int userId  = Integer.parseInt(userIdField.getText().trim());
            int seat    = Integer.parseInt(seatField.getText().trim());
            String type = ticketTypeCombo.getValue();
            String extra = extrasCombo.getValue();

            Booking booking;
            switch (type) {
                case "VIP":
                    booking = bookingService.createVipBooking(
                            SessionState.getInstance().getCurrentUserId(),
                            SessionState.getInstance().getSelectedEventId(),
                            "A1",
                            300
                    );
                    break;
                case "CUSTOM":
                    booking = bookingService.createCustomBooking(
                            SessionState.getInstance().getCurrentUserId(),
                            SessionState.getInstance().getSelectedEventId(),
                            "A1",
                            TicketType.VIP,
                            300,
                            List.of("Snack"),
                            "Near stage"
                    );
                    break;
                default:
                    booking = bookingService.createNormalBooking(
                            SessionState.getInstance().getCurrentUserId(),
                            SessionState.getInstance().getSelectedEventId(),
                            "A1",
                            150
                    );
                    break;
            }

            if (booking != null) {
                SessionState.getInstance().setLastBookingId(booking.getBookingId());
                bookingResultLabel.setText("✅ Booking created! ID: " + booking.getBookingId()
                        + " | Status: " + booking.getStatus());
                statusLabel.setText("Success");
                loadAllBookings();
            } else {
                bookingResultLabel.setText("❌ Booking failed. Check event/seat availability.");
                statusLabel.setText("Failed");
            }
        } catch (NumberFormatException e) {
            bookingResultLabel.setText("❌ Please enter valid numeric IDs and seat number.");
            statusLabel.setText("Input error");
        } catch (Exception e) {
            bookingResultLabel.setText("❌ Error: " + e.getMessage());
            statusLabel.setText("Error");
        }
    }

    // ── Cancel booking ───────────────────────────────────────────────────────
    @FXML
    private void handleCancelBooking() {
        try {
            String bookingId = cancelIdField.getText().trim();

            Booking result = bookingService.cancelBooking(bookingId, "User cancelled");

            if (result != null) {
                bookingResultLabel.setText("❌ Booking " + bookingId + " cancelled.");
                loadAllBookings();
            } else {
                bookingResultLabel.setText("❌ Could not cancel booking " + bookingId + ".");
            }

        } catch (Exception e) {
            bookingResultLabel.setText("❌ " + e.getMessage());
        }
    }

    // ── Confirm booking ──────────────────────────────────────────────────────
    @FXML
    private void handleConfirmBooking() {
        try {
            String bookingId = confirmIdField.getText().trim();

            Booking booking = bookingService.confirmBooking(bookingId);

            bookingResultLabel.setText("✅ Booking " + bookingId + " confirmed.");
            loadAllBookings();

        } catch (NumberFormatException e) {
            bookingResultLabel.setText("❌ Enter a valid booking ID.");
        } catch (Exception e) {
            bookingResultLabel.setText("❌ " + e.getMessage());
        }
    }

    // ── Seat availability ────────────────────────────────────────────────────
    @FXML
    private void handleCheckSeat() {
        try {
            String eventId = String.valueOf(Integer.parseInt(seatCheckEventField.getText().trim()));
            int seat    = Integer.parseInt(seatCheckSeatField.getText().trim());
            boolean available = bookingService.isSeatAvailable(eventId, String.valueOf(seat));
            seatStatusLabel.setText(available
                    ? "✅ Seat " + seat + " is AVAILABLE"
                    : "❌ Seat " + seat + " is TAKEN");
        } catch (NumberFormatException e) {
            seatStatusLabel.setText("Enter valid event ID and seat number.");
        } catch (Exception e) {
            seatStatusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleShowAvailableSeats() {
        try {
            String eventId = String.valueOf(Integer.parseInt(seatCheckEventField.getText().trim()));
            List<String> available =
                    bookingService.getAvailableSeats(eventId);
            Set<String> taken     = bookingService.getTakenSeats(String.valueOf(eventId));
            availableSeatsArea.setText(
                    "Available: " + (available != null ? available.toString() : "[]")
                            + "\nTaken:     " + (taken != null ? taken.toString() : "[]"));
        } catch (NumberFormatException e) {
            availableSeatsArea.setText("Enter a valid event ID.");
        } catch (Exception e) {
            availableSeatsArea.setText("Error: " + e.getMessage());
        }
    }

    // ── Load all bookings ────────────────────────────────────────────────────
    @FXML
    private void loadAllBookings() {
        bookingList.clear();
        try {
            List<Booking> all = bookingService.getAllBookings();
            if (all != null) bookingList.addAll(all);
        } catch (Exception e) {
            statusLabel.setText("Could not load bookings: " + e.getMessage());
        }
    }

    // ── Navigation ───────────────────────────────────────────────────────────
    @FXML private void goHome()    { SceneManager.getInstance().showHome(); }
    @FXML private void goPayment() { SceneManager.getInstance().showPayment(); }
}
