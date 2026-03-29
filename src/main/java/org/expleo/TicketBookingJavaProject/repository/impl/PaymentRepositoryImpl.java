package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.model.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepositoryImpl {

    private static List<Payment> payments = new ArrayList<>();

    public List<Payment> getPayments() {
        return payments;
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public Payment getPaymentById(int id) {
        for (Payment p : payments) {
            if (p.getPaymentId() == id) return p;
        }
        return null;
    }
}
