package org.expleo.TicketBookingJavaProject.model;

import java.util.Date;

public class Ticket {
    private int ticketId;
    private int bookingId;
    private int showtimeId;
    private String seatLabel;
    private double price;
    private String status;
    private Date bookedAt;

    public Ticket() {}

    public Ticket(int ticketId, int bookingId, int showtimeId, String seatLabel, double price) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.showtimeId = showtimeId;
        this.seatLabel = seatLabel;
        this.price = price;
        this.status = "ACTIVE";
        this.bookedAt = new Date();
    }

    public int getTicketId() { return ticketId; }
    public int getBookingId() { return bookingId; }
    public int getShowtimeId() { return showtimeId; }
    public String getSeatLabel() { return seatLabel; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
    public Date getBookedAt() { return bookedAt; }

    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setShowtimeId(int showtimeId) { this.showtimeId = showtimeId; }
    public void setSeatLabel(String seatLabel) { this.seatLabel = seatLabel; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
    public void setBookedAt(Date bookedAt) { this.bookedAt = bookedAt; }
}
