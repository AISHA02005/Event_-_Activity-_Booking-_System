package org.example;

import com.bookingsystem.payment.service.PaymentContext;
import com.bookingsystem.payment.strategy.PayPalPayment;
import com.bookingsystem.payment.strategy.VisaPayment;
import com.bookingsystem.payment.strategy.WalletPayment;

public class Main {

    public static void main(String[] args) {

        PaymentContext payment = new PaymentContext();

        payment.setStrategy(new VisaPayment("1111-2222-3333"));
        payment.executePayment(250);

        System.out.println();

        payment.setStrategy(new PayPalPayment("student@mail.com"));
        payment.executePayment(120);

        System.out.println();

        payment.setStrategy(new WalletPayment(300));
        payment.executePayment(100);
    }
}