package org.expleo.TicketBookingJavaProject.model;

public class Upipayment {
    private String upiId;
    private double amount;
    private String transactionRef;

    public Upipayment() {}

    public Upipayment(String upiId, double amount) {
        this.upiId = upiId;
        this.amount = amount;
        this.transactionRef = "";
    }

    public String getUpiId() { return upiId; }
    public double getAmount() { return amount; }
    public String getTransactionRef() { return transactionRef; }

    public void setUpiId(String upiId) { this.upiId = upiId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setTransactionRef(String transactionRef) { this.transactionRef = transactionRef; }

    public boolean validateUpiId() {
        return upiId != null && (upiId.contains("@") || upiId.matches("[a-zA-Z0-9]+"));
    }
}
