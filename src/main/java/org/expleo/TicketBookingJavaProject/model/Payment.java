package org.expleo.TicketBookingJavaProject.model;

public class Payment {

    private int paymentId;
    private double amount;
    private String method;
    private String status;

    public int getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public String getStatus() { return status; }

    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setMethod(String method) { this.method = method; }
    public void setStatus(String status) { this.status = status; }
}
