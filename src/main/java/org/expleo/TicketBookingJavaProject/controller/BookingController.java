package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import java.util.List;
import java.util.Scanner;
import org.expleo.TicketBookingJavaProject.model.Seat;
import org.expleo.TicketBookingJavaProject.controller.SeatController;

public class BookingController {

    private BookingService service = new BookingService();
    private SeatController seatController = new SeatController();

    public boolean validateTickets(int count) {
        return service.validateTicketCount(count);
    }

    public void confirmBooking(Booking booking, Payment payment) {
        service.confirmBooking(booking, payment);
    }

    public void bookSeats(int count) {
        service.bookSeats(count);
    }

    public void checkTicketAvailability(int ticketCount) {
        if (!service.validateTicketCount(ticketCount)) {
            System.out.println("Invalid ticket count or insufficient seats.");
            return;
        }

        seatController.showSeatSelectionPage();

        List<Seat> selectedSeats = null;

        if (ticketCount == 1) {
            while (selectedSeats == null || selectedSeats.isEmpty()) {
                Seat s = seatController.selectSingleSeat();
                if (s != null) {
                    selectedSeats = List.of(s);
                }
            }
        } else {
            while (selectedSeats == null || selectedSeats.isEmpty()) {
                selectedSeats = seatController.selectMultipleSeats(ticketCount);
            }
        }

        System.out.println("\nSelected Seats:");
        for (Seat s : selectedSeats) System.out.print(s.getSeatLabel() + " ");

        double amount = ticketCount * 150;
        System.out.println("\nAmount: Rs." + amount);

        Scanner sc = new Scanner(System.in);
        System.out.print("Confirm Booking? (yes/no): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Cancelled.");
            return;
        }

        seatController.confirmSeats(selectedSeats);
        String bookingId = "BOOK-" + System.currentTimeMillis();

        System.out.println("\nBooking Confirmed!");
        System.out.println("Booking ID: " + bookingId);
    }
}
