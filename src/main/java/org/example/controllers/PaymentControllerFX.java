package org.example.controllers;

import com.bookingsystem.payment.service.PaymentContext;
import com.bookingsystem.payment.strategy.VisaPayment;
import com.bookingsystem.payment.strategy.PayPalPayment;
import com.bookingsystem.payment.strategy.WalletPayment;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.SceneManager;

public class PaymentControllerFX {

    // ── FXML fields ─────────────────────────────────────────────────────────
    @FXML private ComboBox<String> methodCombo;
    @FXML private VBox  visaBox;
    @FXML private TextField cardNumberField;
    @FXML private VBox  paypalBox;
    @FXML private TextField paypalEmailField;
    @FXML private VBox  walletBox;
    @FXML private TextField walletBalanceField;
    @FXML private TextField amountField;
    @FXML private Label paymentStatusLabel;
    @FXML private Label bookingIdLabel;

    // ── Backend ──────────────────────────────────────────────────────────────
    private final PaymentContext paymentContext = new PaymentContext();

    // ── Lifecycle ────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        methodCombo.setItems(FXCollections.observableArrayList("Visa", "PayPal", "Wallet"));
        methodCombo.setValue("Visa");
        updateMethodPanels("Visa");

        // Show last booking id from session
        int bookingId = Integer.parseInt(SessionState.getInstance().getLastBookingId());
        bookingIdLabel.setText(bookingId == -1
                ? "No booking selected — paying manually."
                : "Paying for Booking ID: " + bookingId);

        methodCombo.valueProperty().addListener((obs, oldVal, newVal) -> updateMethodPanels(newVal));
    }

    // ── Show correct input panel ─────────────────────────────────────────────
    private void updateMethodPanels(String method) {
        visaBox.setVisible(false);
        visaBox.setManaged(false);
        paypalBox.setVisible(false);
        paypalBox.setManaged(false);
        walletBox.setVisible(false);
        walletBox.setManaged(false);

        if (method == null) return;
        switch (method) {
            case "Visa":
                visaBox.setVisible(true);
                visaBox.setManaged(true);
                break;
            case "PayPal":
                paypalBox.setVisible(true);
                paypalBox.setManaged(true);
                break;
            case "Wallet":
                walletBox.setVisible(true);
                walletBox.setManaged(true);
                break;
        }
    }

    // ── Execute payment ──────────────────────────────────────────────────────
    @FXML
    private void handlePay() {
        String method = methodCombo.getValue();
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException e) {
            paymentStatusLabel.setText("❌ Please enter a valid amount.");
            return;
        }

        try {
            switch (method) {
                case "Visa": {
                    String cardNumber = cardNumberField.getText().trim();
                    if (cardNumber.isEmpty()) {
                        paymentStatusLabel.setText("❌ Please enter your card number.");
                        return;
                    }
                    paymentContext.setStrategy(new VisaPayment(cardNumber));
                    break;
                }
                case "PayPal": {
                    String email = paypalEmailField.getText().trim();
                    if (email.isEmpty()) {
                        paymentStatusLabel.setText("❌ Please enter your PayPal email.");
                        return;
                    }
                    paymentContext.setStrategy(new PayPalPayment(email));
                    break;
                }
                case "Wallet": {
                    String balanceText = walletBalanceField.getText().trim();
                    double balance = 0;
                    try { balance = Double.parseDouble(balanceText); }
                    catch (NumberFormatException ex) {
                        paymentStatusLabel.setText("❌ Enter valid wallet balance.");
                        return;
                    }
                    paymentContext.setStrategy(new WalletPayment(balance));
                    break;
                }
                default:
                    paymentStatusLabel.setText("❌ Unknown payment method.");
                    return;
            }

            boolean success = paymentContext.executePayment(amount);
            if (success) {
                paymentStatusLabel.setText("✅ Payment of $" + String.format("%.2f", amount)
                        + " via " + method + " was successful!");
            } else {
                paymentStatusLabel.setText("❌ Payment failed. Please check your details.");
            }
        } catch (Exception e) {
            paymentStatusLabel.setText("❌ Payment error: " + e.getMessage());
        }
    }

    // ── Navigation ───────────────────────────────────────────────────────────
    @FXML private void goHome()     { SceneManager.getInstance().showHome(); }
    @FXML private void goBooking()  { SceneManager.getInstance().showBooking(); }
}
