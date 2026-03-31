package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Showtime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryImpl {

    public static List<Showtime> showtimes = new ArrayList<>();
    public static List<Booking> bookings = new ArrayList<>();

    static {
        showtimes.add(new Showtime("10:00 AM"));
        showtimes.add(new Showtime("01:00 PM"));
        showtimes.add(new Showtime("04:00 PM"));
        showtimes.add(new Showtime("07:00 PM"));
        showtimes.add(new Showtime("10:00 PM"));
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public Booking getBookingById(int id) {
        for (Booking b : bookings) {
            if (b.getBookingId() == id) return b;
        }
        return null;
    }

    public List<Booking> getBookingsByUser(String email) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (email != null && email.equals(b.getUserEmail())) {
                result.add(b);
            }
        }
        return result;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void cancelBooking(int bookingId) {
        bookings.removeIf(b -> b.getBookingId() == bookingId);
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }
}