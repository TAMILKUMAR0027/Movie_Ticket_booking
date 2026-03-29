package org.expleo.TicketBookingJavaProject.model;

public class Booking {

    private int bookingId;
    private int movieId;
    private String movieTitle;
    private String showtime;
    private String seats;
    private double totalAmount;
    private String status;
    private String paymentMethod;
    private String userEmail;

    public Booking() {}

    public int getBookingId() { return bookingId; }
    public int getMovieId() { return movieId; }
    public String getMovieTitle() { return movieTitle; }
    public String getShowtime() { return showtime; }
    public String getSeats() { return seats; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getUserEmail() { return userEmail; }

    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public void setShowtime(String showtime) { this.showtime = showtime; }
    public void setSeats(String seats) { this.seats = seats; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(String status) { this.status = status; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
