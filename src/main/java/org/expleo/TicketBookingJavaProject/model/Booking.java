package org.expleo.TicketBookingJavaProject.model;

public class Booking {

    private int bookingId;
    private double totalAmount;
    private String status;

    public int getBookingId() { return bookingId; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(String status) { this.status = status; }
}
