package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Payment;
import java.util.Random;

public class PaymentService {

    public Payment processPayment(double amount, String method) {

        Payment payment = new Payment();
        payment.setPaymentId(new Random().nextInt(1000));
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus("SUCCESS");

        System.out.println("Payment Successful using " + method);

        return payment;
    }

    public boolean validatePayment(Payment payment) {
        return payment.getStatus().equalsIgnoreCase("SUCCESS");
    }
}
