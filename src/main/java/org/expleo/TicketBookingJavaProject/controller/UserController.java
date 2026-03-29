package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.service.UserService;
import org.expleo.TicketBookingJavaProject.model.User;
import java.util.Scanner;

public class UserController {

    private UserService service = new UserService();
    private Scanner sc = new Scanner(System.in);

    public void showLoginMenu() {
        System.out.println("\n========== LOGIN MENU ==========");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                performLogin();
                break;
            case 2:
                performRegistration();
                break;
            case 3:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    public void performLogin() {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = service.login(email, password);
        if (user != null) {
            System.out.println("Welcome, " + user.getName() + "!");
        }
    }

    public void performRegistration() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter role (CUSTOMER/ADMIN/OFFICER): ");
        String role = sc.nextLine();

        service.registerUser(name, email, phone, role);
    }

    public UserService getService() {
        return service;
    }
}
