package org.example;

import com.bookingsystem.notification.controller.NotificationController;
import com.bookingsystem.notification.observer.NotificationManager;
import com.bookingsystem.notification.observer.UserObserver;
import com.bookingsystem.notification.service.NotificationService;

import com.bookingsystem.review.controller.ReviewController;
import com.bookingsystem.review.service.ReviewService;

import com.bookingsystem.payment.service.PaymentContext;
import com.bookingsystem.payment.strategy.PayPalPayment;
import com.bookingsystem.payment.strategy.VisaPayment;
import com.bookingsystem.payment.strategy.WalletPayment;
import com.bookingsystem.facade.BookingFacade;





public class Main {

        public static void main(String[] args) {

                // =========================
                // NOTIFICATION MODULE
                // =========================
                System.out.println("\n########################################");
                System.out.println("        NOTIFICATION SYSTEM");
                System.out.println("########################################\n");

                NotificationManager manager =  NotificationManager .getInstance();

                UserObserver user1 = new UserObserver("Malak");
                UserObserver user2 = new UserObserver("Guest");

                manager.subscribe(user1);
                manager.subscribe(user2);

                NotificationService notificationService =
                        new NotificationService(manager);

                NotificationController notificationController =
                        new NotificationController(notificationService);

                System.out.println(">>> Sending Booking Notification...\n");
                notificationController.bookingSuccess();


                // =========================
                // REVIEW MODULE
                // =========================
                System.out.println("\n########################################");
                System.out.println("              REVIEW SYSTEM");
                System.out.println("########################################\n");

                ReviewService reviewService = new ReviewService();

                ReviewController reviewController =
                        new ReviewController(reviewService);

                System.out.println(">>> Adding Reviews...\n");

                reviewController.submitReview(
                        "Malak",
                        5,
                        "Excellent Event!"
                );

                reviewController.submitReview(
                        "Guest",
                        4,
                        "Very Good Experience"
                );


                // =========================
                // PAYMENT MODULE
                // =========================
                System.out.println("\n########################################");
                System.out.println("              PAYMENT SYSTEM");
                System.out.println("########################################\n");

                PaymentContext payment = new PaymentContext();

                payment.setStrategy(new VisaPayment("1111-2222-3333"));
                payment.executePayment(250);

                System.out.println();

                payment.setStrategy(new PayPalPayment("student@mail.com"));
                payment.executePayment(120);

                System.out.println();

                payment.setStrategy(new WalletPayment(300));
                payment.executePayment(100);

                BookingFacade facade = new BookingFacade();
                facade.fullBookingProcess("Visa");
                System.out.println("Application Finished!");




        }
}





