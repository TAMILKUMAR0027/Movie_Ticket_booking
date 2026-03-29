package org.expleo.TicketBookingJavaProject.model;

import java.util.List;

public class Hall {
    private int hallId;
    private String hallName;
    private int totalSeats;
    private String hallType;
    private List<Showtime> showtimes;

    public Hall() {}

    public Hall(int hallId, String hallName, int totalSeats, String hallType) {
        this.hallId = hallId;
        this.hallName = hallName;
        this.totalSeats = totalSeats;
        this.hallType = hallType;
    }

    public int getHallId() { return hallId; }
    public String getHallName() { return hallName; }
    public int getTotalSeats() { return totalSeats; }
    public String getHallType() { return hallType; }
    public List<Showtime> getShowtimes() { return showtimes; }

    public void setHallId(int hallId) { this.hallId = hallId; }
    public void setHallName(String hallName) { this.hallName = hallName; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public void setHallType(String hallType) { this.hallType = hallType; }
    public void setShowtimes(List<Showtime> showtimes) { this.showtimes = showtimes; }
}
