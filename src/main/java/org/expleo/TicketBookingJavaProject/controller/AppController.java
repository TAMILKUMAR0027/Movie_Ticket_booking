package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.controller.UserController;
import org.expleo.TicketBookingJavaProject.controller.SelectionController;
import org.expleo.TicketBookingJavaProject.controller.BookingController;
import org.expleo.TicketBookingJavaProject.controller.SearchController;
import org.expleo.TicketBookingJavaProject.controller.MovieController;
import org.expleo.TicketBookingJavaProject.model.User;
import java.util.Scanner;

public class AppController {

    private Scanner sc = new Scanner(System.in);
    private UserController userController = new UserController();
    private SelectionController selectionController = new SelectionController();
    private BookingController bookingController = new BookingController();
    private SearchController searchController = new SearchController();
    private MovieController movieController = new MovieController();
    private User currentUser;

    public void start() {
        System.out.println("========================================");
        System.out.println("   WELCOME TO TICKET BOOKING SYSTEM");
        System.out.println("========================================");

        userController.showLoginMenu();

        showMainMenu();
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Search Movies");
            System.out.println("2. Book Tickets");
            System.out.println("3. View Theatres");
            System.out.println("4. View Profile");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    searchController.searchMovie();
                    break;
                case 2:
                    selectionController.startBookingFlow();
                    System.out.print("Enter number of tickets: ");
                    int count = sc.nextInt();
                    bookingController.checkTicketAvailability(count);
                    break;
                case 3:
                    movieController.displayAllTheatres();
                    break;
                case 4:
                    System.out.println("User: " + (currentUser != null ? currentUser.getName() : "Guest"));
                    break;
                case 5:
                    System.out.println("Thank you for using Ticket Booking System!");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
