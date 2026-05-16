package org.example.controllers;

import com.bookingsystem.event.controller.EventController;
import com.bookingsystem.event.model.Event;
import com.bookingsystem.shared.enums.EventType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.SceneManager;

import java.util.List;
import java.util.stream.Collectors;

public class SearchControllerFX {

    // ── FXML fields ─────────────────────────────────────────────────────────
    @FXML private TextField searchField;
    @FXML private ComboBox<String> typeFilterCombo;
    @FXML private Label searchStatusLabel;

    @FXML private TableView<Event> searchResultsTable;
    @FXML private TableColumn<Event, String> colSrId;
    @FXML private TableColumn<Event, String> colSrTitle;
    @FXML private TableColumn<Event, String> colSrType;
    @FXML private TableColumn<Event, String> colSrLocation;
    @FXML private TableColumn<Event, String> colSrDate;
    @FXML private TableColumn<Event, String> colSrSeats;
    @FXML private TableColumn<Event, String> colSrStatus;

    // ── Backend ──────────────────────────────────────────────────────────────
    private final EventController   eventController = new EventController();
    private final ObservableList<Event> resultList  = FXCollections.observableArrayList();

    // Master list kept in memory for client-side keyword filtering
    private List<Event> masterList = null;

    // ── Lifecycle ────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        setupColumns();
        setupTypeFilter();
        loadMasterList();
    }

    // ── Column setup ─────────────────────────────────────────────────────────
    private void setupColumns() {
        colSrId.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getEventId())));
        colSrTitle.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getTitle()));
        colSrType.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getType() != null
                        ? d.getValue().getType().name() : ""));
        colSrLocation.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getLocation()));
        colSrDate.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getStartDateTime() != null
                        ? d.getValue().getStartDateTime().toString() : ""));
        colSrSeats.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getAvailableSeats())));
        colSrStatus.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getStatus() != null
                        ? d.getValue().getStatus().name() : ""));

        searchResultsTable.setItems(resultList);
        searchResultsTable.setPlaceholder(new Label("Search results will appear here."));
    }

    // ── Filter combo ─────────────────────────────────────────────────────────
    private void setupTypeFilter() {
        typeFilterCombo.getItems().add("ALL");
        for (EventType t : EventType.values()) typeFilterCombo.getItems().add(t.name());
        typeFilterCombo.setValue("ALL");
    }

    // ── Load master list once ────────────────────────────────────────────────
    private void loadMasterList() {
        try {
            masterList = eventController.getAllEvents();
        } catch (Exception e) {
            searchStatusLabel.setText("Could not load event data: " + e.getMessage());
        }
        if (masterList == null) masterList = java.util.Collections.emptyList();
        resultList.addAll(masterList);
        searchStatusLabel.setText("Total events available: " + masterList.size());
    }

    // ── Search handler ───────────────────────────────────────────────────────
    @FXML
    private void handleSearch() {
        String keyword    = searchField.getText().trim().toLowerCase();
        String typeFilter = typeFilterCombo.getValue();

        List<Event> filtered = masterList.stream()
                .filter(e -> {
                    if (keyword.isEmpty()) return true;
                    return (e.getTitle()    != null && e.getTitle().toLowerCase().contains(keyword))
                            || (e.getLocation() != null && e.getLocation().toLowerCase().contains(keyword))
                            || (e.getDescription() != null && e.getDescription().toLowerCase().contains(keyword));
                })
                .filter(e -> {
                    if ("ALL".equals(typeFilter)) return true;
                    return e.getType() != null && e.getType().name().equals(typeFilter);
                })
                .collect(Collectors.toList());

        resultList.setAll(filtered);
        searchStatusLabel.setText("Found " + filtered.size() + " result(s).");
    }

    @FXML
    private void handleClearSearch() {
        searchField.clear();
        typeFilterCombo.setValue("ALL");
        resultList.setAll(masterList);
        searchStatusLabel.setText("Showing all " + masterList.size() + " events.");
    }

    @FXML
    private void handleBookSelected() {
        Event selected = searchResultsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            searchStatusLabel.setText("Please select an event to book.");
            return;
        }
        SessionState.getInstance().setSelectedEventId(selected.getEventId());
        SceneManager.getInstance().showBooking();
    }

    // ── Navigation ───────────────────────────────────────────────────────────
    @FXML private void goHome() { SceneManager.getInstance().showHome(); }
}
