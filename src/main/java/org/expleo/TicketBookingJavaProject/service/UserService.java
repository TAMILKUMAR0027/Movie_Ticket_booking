package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.model.Customer;
import org.expleo.TicketBookingJavaProject.model.Administrator;
import org.expleo.TicketBookingJavaProject.model.Officer;
import org.expleo.TicketBookingJavaProject.repository.impl.UserRepositoryImpl;

import java.util.List;

public class UserService {

    private UserRepositoryImpl repo = new UserRepositoryImpl();

    public User login(String email, String password) {
        List<User> users = repo.getUsers();
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Login successful for: " + u.getName());
                return u;
            }
        }
        System.out.println("User not found!");
        return null;
    }

    public void registerUser(String name, String email, String phone, String role) {
        int newId = repo.getUsers().size() + 1;
        User user = null;

        if ("CUSTOMER".equalsIgnoreCase(role)) {
            user = new Customer(newId, name, email, phone, "BASIC");
        } else if ("ADMIN".equalsIgnoreCase(role)) {
            user = new Administrator(newId, name, email, phone, "IT");
        } else if ("OFFICER".equalsIgnoreCase(role)) {
            user = new Officer(newId, name, email, phone, "MORNING");
        } else {
            user = new User(newId, name, email, phone, role);
        }

        repo.addUser(user);
        System.out.println("User registered successfully!");
    }

    public List<User> getAllUsers() {
        return repo.getUsers();
    }

    public User getUserById(int id) {
        return repo.getUserById(id);
    }
}
