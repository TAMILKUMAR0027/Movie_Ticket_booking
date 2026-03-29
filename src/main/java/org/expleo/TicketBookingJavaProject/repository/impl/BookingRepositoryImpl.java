package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.City;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.model.Showtime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryImpl {

    public static List<City> cities = new ArrayList<>();
    public static List<Theatre> theatres = new ArrayList<>();
    public static List<Movie> movies = new ArrayList<>();
    public static List<Showtime> showtimes = new ArrayList<>();
    public static List<Booking> bookings = new ArrayList<>();

    static {
        cities.add(new City(1, "New York"));
        cities.add(new City(2, "Los Angeles"));
        cities.add(new City(3, "Chicago"));

        theatres.add(new Theatre(1, "PVR Cinemas", null));
        theatres.add(new Theatre(2, "INOX", null));
        theatres.add(new Theatre(3, "Cinepolis", null));

        movies.add(new Movie(1, "Avengers: Endgame", "English"));
        movies.add(new Movie(2, "Spider-Man: No Way Home", "English"));
        movies.add(new Movie(3, "Baahubali 2", "Telugu"));
        movies.add(new Movie(4, "RRR", "Telugu"));
        movies.add(new Movie(5, "Dhoom 3", "Hindi"));

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
}
