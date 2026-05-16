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

    @FXML private ComboBox<String> methodCombo;
    @FXML private VBox visaBox;
    @FXML private TextField cardNumberField;
    @FXML private VBox paypalBox;
    @FXML private TextField paypalEmailField;
    @FXML private VBox walletBox;
    @FXML private TextField walletBalanceField;
    @FXML private TextField amountField;
    @FXML private Label paymentStatusLabel;
    @FXML private Label bookingIdLabel;

    private final PaymentContext paymentContext = new PaymentContext();

    @FXML
    public void initialize() {
        methodCombo.setItems(FXCollections.observableArrayList("Visa", "PayPal", "Wallet"));
        methodCombo.setValue("Visa");
        updateMethodPanels("Visa");

        // ✅ FIX: safe handling for booking ID
        try {
            String bookingIdStr = SessionState.getInstance().getLastBookingId();
            if (bookingIdStr != null && !bookingIdStr.isEmpty()) {
                bookingIdLabel.setText("Paying for Booking ID: " + bookingIdStr);
            } else {
                bookingIdLabel.setText("No booking selected — paying manually.");
            }
        } catch (Exception e) {
            bookingIdLabel.setText("No booking selected — paying manually.");
        }

        methodCombo.valueProperty().addListener((obs, oldVal, newVal) -> updateMethodPanels(newVal));
    }

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

    @FXML
    private void handlePay() {
        String method = methodCombo.getValue();

        double amount;
        try {
            if (amountField.getText().isEmpty()) {
                paymentStatusLabel.setText("❌ Enter amount first.");
                return;
            }
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
                        paymentStatusLabel.setText("❌ Enter card number.");
                        return;
                    }
                    paymentContext.setStrategy(new VisaPayment(cardNumber));
                    break;
                }

                case "PayPal": {
                    String email = paypalEmailField.getText().trim();
                    if (email.isEmpty()) {
                        paymentStatusLabel.setText("❌ Enter PayPal email.");
                        return;
                    }
                    paymentContext.setStrategy(new PayPalPayment(email));
                    break;
                }

                case "Wallet": {
                    String balanceText = walletBalanceField.getText().trim();
                    if (balanceText.isEmpty()) {
                        paymentStatusLabel.setText("❌ Enter wallet balance.");
                        return;
                    }

                    double balance;
                    try {
                        balance = Double.parseDouble(balanceText);
                    } catch (NumberFormatException ex) {
                        paymentStatusLabel.setText("❌ Invalid balance.");
                        return;
                    }

                    paymentContext.setStrategy(new WalletPayment(balance));
                    break;
                }

                default:
                    paymentStatusLabel.setText("❌ Unknown method.");
                    return;
            }

            boolean success = paymentContext.executePayment(amount);

            if (success) {
                paymentStatusLabel.setText("✅ Payment successful: $" +
                        String.format("%.2f", amount) + " via " + method);
            } else {
                paymentStatusLabel.setText("❌ Payment failed.");
            }

        } catch (Exception e) {
            paymentStatusLabel.setText("❌ Error: " + e.getMessage());
        }
    }

    @FXML private void goHome()     { SceneManager.getInstance().showHome(); }
    @FXML private void goBooking()  { SceneManager.getInstance().showBooking(); }
}