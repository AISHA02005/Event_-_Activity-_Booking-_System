package com.bookingsystem.payment.service;

import com.bookingsystem.payment.strategy.PaymentStrategy;

public class PaymentContext {

    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean executePayment(double amount) {

        if (strategy == null) {
            System.out.println("Please select payment method!");

        } else {
            strategy.pay(amount);
        }

        return false;
    }
}