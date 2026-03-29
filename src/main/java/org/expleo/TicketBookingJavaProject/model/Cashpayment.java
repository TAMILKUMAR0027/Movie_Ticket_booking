package org.expleo.TicketBookingJavaProject.model;

public class Cashpayment {
    private double amount;
    private double amountPaid;
    private double change;

    public Cashpayment() {}

    public Cashpayment(double amount) {
        this.amount = amount;
        this.amountPaid = 0;
        this.change = 0;
    }

    public double getAmount() { return amount; }
    public double getAmountPaid() { return amountPaid; }
    public double getChange() { return change; }

    public void setAmount(double amount) { this.amount = amount; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    public void setChange(double change) { this.change = change; }

    public boolean processPayment(double paid) {
        if (paid >= amount) {
            this.amountPaid = paid;
            this.change = paid - amount;
            return true;
        }
        return false;
    }
}
