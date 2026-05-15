package com.bookingsystem.payment.strategy;

public class WalletPayment implements PaymentStrategy {

    private double balance;

    public WalletPayment(double balance) {
        this.balance = balance;
    }

    @Override
    public void pay(double amount) {

        if (balance >= amount) {
            balance -= amount;

            System.out.println("Paid " + amount +
                    " using Wallet. Remaining balance: " + balance);

        } else {
            System.out.println("Insufficient balance!");
        }
    }
}