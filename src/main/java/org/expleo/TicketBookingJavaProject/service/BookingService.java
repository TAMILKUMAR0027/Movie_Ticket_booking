package org.expleo.TicketBookingJavaProject.service;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.BookingRepositoryImpl;
import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.model.Booking;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingService {

    private SeatRepositoryImpl seatRepo = new SeatRepositoryImpl();
    private BookingRepositoryImpl bookingRepo = new BookingRepositoryImpl();
    private static final AtomicInteger idGenerator = new AtomicInteger(1000);

    public boolean validateTicketCount(int ticketCount) {
        if (ticketCount <= 0) return false;
        List<Seat> seats = seatRepo.getSeats();
        int availableSeats = 0;
        for (Seat seat : seats) {
            if (seat.getStatus().equalsIgnoreCase("AVAILABLE")) {
                availableSeats++;
            }
        }
        return ticketCount <= availableSeats;
    }

    public void confirmBooking(Booking booking, Payment payment) {
        if (payment.getStatus().equalsIgnoreCase("SUCCESS")) {
            booking.setStatus("CONFIRMED");
            System.out.println("Booking Confirmed!");
        } else {
            booking.setStatus("FAILED");
            System.out.println("Booking Failed!");
        }
    }

    public void bookSeats(int count) {
        List<Seat> seats = seatRepo.getSeats();
        int booked = 0;
        for (Seat seat : seats) {
            if (seat.getStatus().equalsIgnoreCase("AVAILABLE")) {
                seat.setStatus("BOOKED");
                booked++;
            }
            if (booked == count) break;
        }
        System.out.println(count + " Seats Booked Successfully!");
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.getBookings();
    }

    public Booking getBookingById(int id) {
        return bookingRepo.getBookingById(id);
    }

    public List<Booking> getBookingsByUser(String email) {
        return bookingRepo.getBookingsByUser(email);
    }

    public Booking createBooking(Booking booking) {
        booking.setBookingId(idGenerator.getAndIncrement());
        booking.setStatus("CONFIRMED");
        bookingRepo.addBooking(booking);
        return booking;
    }

    public void updateSeatStatus(String seatLabel, String status) {
        for (Seat seat : seatRepo.getSeats()) {
            if (seat.getSeatLabel().equalsIgnoreCase(seatLabel)) {
                seat.setStatus(status);
                break;
            }
        }
    }
}
