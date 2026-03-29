package org.expleo.TicketBookingJavaProject.model;

public class Cardpayment {
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvv;
    private double amount;

    public Cardpayment() {}

    public Cardpayment(String cardNumber, String cardHolder, String expiryDate, String cvv, double amount) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = amount;
    }

    public String getCardNumber() { return cardNumber; }
    public String getCardHolder() { return cardHolder; }
    public String getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
    public double getAmount() { return amount; }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public void setCvv(String cvv) { this.cvv = cvv; }
    public void setAmount(double amount) { this.amount = amount; }

    public boolean validateCard() {
        return cardNumber != null && cardNumber.length() == 16 && cvv != null && cvv.length() == 3;
    }
}
