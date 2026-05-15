package com.bookingsystem.payment.strategy;

public class VisaPayment implements PaymentStrategy {

    private String cardNumber;

    public VisaPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Visa Card: " + cardNumber);
    }
}