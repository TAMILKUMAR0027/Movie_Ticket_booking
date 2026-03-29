package org.expleo.TicketBookingJavaProject.service;

import java.util.List;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.repository.impl.SeatRepositoryImpl;
import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.model.Booking;

public class BookingService {

    private SeatRepositoryImpl repo = new SeatRepositoryImpl();

    public boolean validateTicketCount(int ticketCount) {

        if (ticketCount <= 0) return false;

        List<Seat> seats = repo.getSeats();
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

        List<Seat> seats = repo.getSeats();
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
}
