package org.expleo.TicketBookingJavaProject;

import org.expleo.TicketBookingJavaProject.model.*;
import org.expleo.TicketBookingJavaProject.repository.impl.*;
import org.expleo.TicketBookingJavaProject.service.*;
import java.util.*;

public class App {

    static Scanner sc = new Scanner(System.in);
    static UserService userService = new UserService();
    static TheatreRepositoryImpl theatreRepo = new TheatreRepositoryImpl();
    static BookingRepositoryImpl bookingRepo = new BookingRepositoryImpl();
    static User currentUser = null;
    static Movie selectedMovie = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║     ONLINE TICKET BOOKING SYSTEM     ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("1. Register (Customer only)");
            System.out.println("2. Login");
            System.out.println("3. Guest");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    guestMenu();
                    break;
                case 4:
                    System.out.println("Thank you for using our system!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void registerCustomer() {
        System.out.println("\n--- CUSTOMER REGISTRATION ---");
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        userService.registerUser(name, email, phone, password, "CUSTOMER");
        System.out.println("Registration successful! Please login.");
    }

    static void login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        currentUser = userService.login(email, password);

        if (currentUser != null) {
            switch (currentUser.getRole()) {
                case "SUPER_ADMIN":
                case "ADMIN":
                    superAdminMenu();
                    break;
                case "THEATRE_ADMIN":
                    theatreAdminMenu();
                    break;
                case "OFFICER":
                    officerMenu();
                    break;
                case "CUSTOMER":
                    customerMenu();
                    break;
                default:
                    System.out.println("Unknown role!");
            }
        }
    }

    static void guestMenu() {
        while (true) {
            System.out.println("\n--- GUEST MENU ---");
            System.out.println("1. Search Movie");
            System.out.println("2. View Movie List");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 3) break;

            switch (choice) {
                case 1:
                    searchMovie();
                    break;
                case 2:
                    viewMovieList();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== SUPER ADMIN MENU ====================
    static void superAdminMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║        SUPER ADMIN CONSOLE            ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("1. Create Theatre");
            System.out.println("2. Create Theatre Admin");
            System.out.println("3. Remove Theatre");
            System.out.println("4. Remove Theatre Admin");
            System.out.println("5. View Profile");
            System.out.println("6. Update Profile");
            System.out.println("7. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 7) {
                currentUser = null;
                break;
            }

            switch (choice) {
                case 1:
                    createTheatre();
                    break;
                case 2:
                    createTheatreAdmin();
                    break;
                case 3:
                    removeTheatre();
                    break;
                case 4:
                    removeTheatreAdmin();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    updateProfile();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void createTheatre() {
        System.out.println("\n--- CREATE THEATRE ---");
        System.out.print("Enter Theatre Name: ");
        String name = sc.nextLine();
        System.out.print("Enter City: ");
        String city = sc.nextLine();
        System.out.print("Enter Location: ");
        String location = sc.nextLine();
        System.out.print("Enter Number of Screens: ");
        int screens = sc.nextInt();
        sc.nextLine();

        int id = theatreRepo.getTheatres().size() + 1;
        Theatre theatre = new Theatre(id, name, city, location, screens);
        theatreRepo.addTheatre(theatre);
        System.out.println("Theatre created successfully!");
    }

    static void createTheatreAdmin() {
        System.out.println("\n--- CREATE THEATRE ADMIN ---");
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Theatre Name: ");
        String theatreName = sc.nextLine();

        int id = userService.getAllUsers().size() + 100;
        Administrator admin = new Administrator(id, name, email, phone, password, "Operations", theatreName);
        userService.getUsers().add(admin);
        System.out.println("Theatre Admin created successfully!");
    }

    static void removeTheatre() {
        System.out.println("\n--- REMOVE THEATRE ---");
        List<Theatre> theatres = theatreRepo.getTheatres();
        for (Theatre t : theatres) {
            System.out.println(t.getId() + ". " + t.getName() + " - " + t.getCity());
        }
        System.out.print("Enter Theatre ID to remove: ");
        int id = sc.nextInt();
        sc.nextLine();
        theatreRepo.removeTheatre(id);
        System.out.println("Theatre removed!");
    }

    static void removeTheatreAdmin() {
        System.out.println("\n--- REMOVE THEATRE ADMIN ---");
        System.out.print("Enter Admin Email to remove: ");
        String email = sc.nextLine();
        userService.removeUser(email);
        System.out.println("Theatre Admin removed!");
    }

    // ==================== THEATRE ADMIN MENU ====================
    static void theatreAdminMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║       THEATRE ADMIN CONSOLE           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("1. Add Movie");
            System.out.println("2. Update Movie");
            System.out.println("3. Delete Movie");
            System.out.println("4. Create Officer");
            System.out.println("5. View Movie List");
            System.out.println("6. View Profile");
            System.out.println("7. Update Profile");
            System.out.println("8. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 8) {
                currentUser = null;
                break;
            }

            switch (choice) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    updateMovie();
                    break;
                case 3:
                    deleteMovie();
                    break;
                case 4:
                    createOfficer();
                    break;
                case 5:
                    viewMovieList();
                    break;
                case 6:
                    viewProfile();
                    break;
                case 7:
                    updateProfile();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addMovie() {
        System.out.println("\n--- ADD MOVIE ---");
        System.out.print("Enter Movie Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Language: ");
        String language = sc.nextLine();
        System.out.print("Enter Duration: ");
        String duration = sc.nextLine();
        System.out.print("Enter Rating: ");
        String rating = sc.nextLine();

        int id = theatreRepo.getMoviesByTheatre(1).size() + 100;
        Movie movie = new Movie(id, title, language, duration, rating);
        theatreRepo.addMovieToTheatre(1, movie);
        System.out.println("Movie added successfully!");
    }

    static void updateMovie() {
        System.out.println("\n--- UPDATE MOVIE ---");
        List<Movie> movies = theatreRepo.getMoviesByTheatre(1);
        for (Movie m : movies) {
            System.out.println(m.getId() + ". " + m.getTitle() + " (" + m.getLanguage() + ")");
        }
        System.out.print("Enter Movie ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter New Title: ");
        String title = sc.nextLine();
        System.out.print("Enter New Language: ");
        String language = sc.nextLine();
        System.out.println("Movie updated!");
    }

    static void deleteMovie() {
        System.out.println("\n--- DELETE MOVIE ---");
        List<Movie> movies = theatreRepo.getMoviesByTheatre(1);
        for (Movie m : movies) {
            System.out.println(m.getId() + ". " + m.getTitle());
        }
        System.out.print("Enter Movie ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();
        theatreRepo.removeMovieFromTheatre(1, id);
        System.out.println("Movie deleted!");
    }

    static void createOfficer() {
        System.out.println("\n--- CREATE OFFICER ---");
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Shift (MORNING/EVENING): ");
        String shift = sc.nextLine();

        int id = userService.getAllUsers().size() + 200;
        Officer officer = new Officer(id, name, email, phone, password, shift);
        userService.getUsers().add(officer);
        System.out.println("Officer created successfully!");
    }

    // ==================== OFFICER MENU ====================
    static void officerMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║          OFFICER CONSOLE               ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("1. View Movie List");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 4) {
                currentUser = null;
                break;
            }

            switch (choice) {
                case 1:
                    viewMovieList();
                    break;
                case 2:
                    bookTicketFlow();
                    break;
                case 3:
                    cancelTicket();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== CUSTOMER MENU ====================
    static void customerMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         CUSTOMER CONSOLE              ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("1. Search Movie");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Movie List");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. View Profile");
            System.out.println("6. Update Profile");
            System.out.println("7. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 7) {
                currentUser = null;
                selectedMovie = null;
                break;
            }

            switch (choice) {
                case 1:
                    searchMovie();
                    break;
                case 2:
                    bookTicketFlow();
                    break;
                case 3:
                    viewMovieList();
                    break;
                case 4:
                    cancelTicket();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    updateProfile();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ==================== SHARED METHODS ====================
    static void searchMovie() {
        System.out.println("\n--- SEARCH MOVIE ---");
        System.out.print("Enter Movie Name: ");
        String searchTerm = sc.nextLine().toLowerCase();

        List<Movie> movies = theatreRepo.getMoviesByTheatre(1);
        List<Movie> results = new ArrayList<>();

        for (Movie m : movies) {
            if (m.getTitle().toLowerCase().contains(searchTerm)) {
                results.add(m);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No movies found!");
            return;
        }

        System.out.println("\nMatching Movies:");
        for (Movie m : results) {
            System.out.println(m.getId() + ". " + m.getTitle() + " (" + m.getLanguage() + ") - " + m.getDuration());
        }

        if (currentUser != null && (currentUser.getRole().equals("CUSTOMER") || currentUser.getRole().equals("OFFICER"))) {
            System.out.print("\nDo you want to book ticket? (yes/no): ");
            String ans = sc.nextLine();
            if (ans.equalsIgnoreCase("yes")) {
                System.out.print("Select Movie ID: ");
                int id = sc.nextInt();
                sc.nextLine();
                for (Movie m : movies) {
                    if (m.getId() == id) {
                        selectedMovie = m;
                        break;
                    }
                }
                if (selectedMovie != null) {
                    System.out.println("Movie selected: " + selectedMovie.getTitle());
                    bookTicketFlow();
                }
            }
        }
    }

    static void viewMovieList() {
        System.out.println("\n--- MOVIE LIST ---");
        List<Movie> movies = theatreRepo.getMoviesByTheatre(1);
        for (Movie m : movies) {
            System.out.println(m.getId() + ". " + m.getTitle() + " | " + m.getLanguage() + " | " + m.getDuration() + " | Rating: " + m.getRating());
        }
    }

    static void bookTicketFlow() {
        System.out.println("\n--- BOOK TICKET ---");

        Movie movieToBook = selectedMovie;
        if (movieToBook == null) {
            viewMovieList();
            System.out.print("Select Movie ID: ");
            int movieId = sc.nextInt();
            sc.nextLine();
            List<Movie> movies = theatreRepo.getMoviesByTheatre(1);
            for (Movie m : movies) {
                if (m.getId() == movieId) {
                    movieToBook = m;
                    break;
                }
            }
        }

        if (movieToBook == null) {
            System.out.println("Movie not found!");
            return;
        }

        System.out.println("Selected Movie: " + movieToBook.getTitle());

        System.out.println("\n--- SELECT CITY ---");
        List<String> cities = theatreRepo.getAllCities();
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }
        System.out.print("Select City: ");
        int cityChoice = sc.nextInt();
        sc.nextLine();
        String selectedCity = cities.get(cityChoice - 1);

        System.out.println("\n--- SELECT THEATRE ---");
        List<Theatre> theatres = theatreRepo.getTheatresByCity(selectedCity);
        for (int i = 0; i < theatres.size(); i++) {
            System.out.println((i + 1) + ". " + theatres.get(i).getName() + " - " + theatres.get(i).getLocation());
        }
        System.out.print("Select Theatre: ");
        int theatreChoice = sc.nextInt();
        sc.nextLine();
        Theatre selectedTheatre = theatres.get(theatreChoice - 1);

        System.out.println("\n--- SELECT SHOWTIME ---");
        List<Showtime> showtimes = bookingRepo.getShowtimes();
        for (int i = 0; i < showtimes.size(); i++) {
            System.out.println((i + 1) + ". " + showtimes.get(i).getTime());
        }
        System.out.print("Select Showtime: ");
        int showtimeChoice = sc.nextInt();
        sc.nextLine();
        String selectedShowtime = showtimes.get(showtimeChoice - 1).getTime();

        System.out.println("\n--- SELECT SEATS ---");
        System.out.print("Enter number of seats: ");
        int seatCount = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter seat numbers (e.g., A1 A2 A3): ");
        String seatsInput = sc.nextLine();
        String[] seats = seatsInput.split(" ");

        double pricePerSeat = 150.0;
        double totalAmount = seatCount * pricePerSeat;

        System.out.println("\n--- PAYMENT ---");
        System.out.println("Total Amount: Rs." + totalAmount);
        System.out.println("Payment Methods: 1. Card 2. UPI 3. Cash");
        System.out.print("Select Payment Method: ");
        int paymentChoice = sc.nextInt();
        sc.nextLine();

        String paymentMethod = "";
        switch (paymentChoice) {
            case 1: paymentMethod = "Card"; break;
            case 2: paymentMethod = "UPI"; break;
            case 3: paymentMethod = "Cash"; break;
        }

        System.out.println("Processing payment...");
        System.out.println("Payment Successful via " + paymentMethod);

        int bookingId = bookingRepo.getBookings().size() + 1000;
        String userEmail = currentUser != null ? currentUser.getEmail() : "guest@email.com";

        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setMovieId(movieToBook.getId());
        booking.setMovieTitle(movieToBook.getTitle());
        booking.setShowtime(selectedShowtime);
        booking.setSeats(String.join(",", seats));
        booking.setTotalAmount(totalAmount);
        booking.setStatus("CONFIRMED");
        booking.setPaymentMethod(paymentMethod);
        booking.setUserEmail(userEmail);

        bookingRepo.addBooking(booking);

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║      BOOKING CONFIRMED!              ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Movie: " + movieToBook.getTitle());
        System.out.println("Theatre: " + selectedTheatre.getName());
        System.out.println("Showtime: " + selectedShowtime);
        System.out.println("Seats: " + String.join(", ", seats));
        System.out.println("Amount Paid: Rs." + totalAmount);
        System.out.println("Payment Method: " + paymentMethod);

        selectedMovie = null;
    }

    static void cancelTicket() {
        System.out.println("\n--- CANCEL TICKET ---");
        String userEmail = currentUser != null ? currentUser.getEmail() : "";
        List<Booking> userBookings = bookingRepo.getBookingsByUser(userEmail);

        if (userBookings.isEmpty()) {
            System.out.println("No bookings found!");
            return;
        }

        System.out.println("Your Bookings:");
        for (Booking b : userBookings) {
            System.out.println(b.getBookingId() + ". " + b.getMovieTitle() + " - " + b.getShowtime() + " - Rs." + b.getTotalAmount());
        }

        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = sc.nextInt();
        sc.nextLine();

        bookingRepo.cancelBooking(bookingId);
        System.out.println("Booking cancelled successfully!");
    }

    static void viewProfile() {
        System.out.println("\n--- PROFILE ---");
        System.out.println("Name: " + currentUser.getName());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Phone: " + currentUser.getPhone());
        System.out.println("Role: " + currentUser.getRole());
    }

    static void updateProfile() {
        System.out.println("\n--- UPDATE PROFILE ---");
        System.out.print("Enter New Name: ");
        String name = sc.nextLine();
        System.out.print("Enter New Phone: ");
        String phone = sc.nextLine();

        currentUser.setName(name);
        currentUser.setPhone(phone);
        System.out.println("Profile updated successfully!");
    }
}