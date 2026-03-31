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
        User user = repo.login(email, password);
        if (user != null) {
            System.out.println("Login successful for: " + user.getName() + " (" + user.getRole() + ")");
        } else {
            System.out.println("Invalid credentials!");
        }
        return user;
    }

    public void registerUser(String name, String email, String phone, String password, String role) {
        int newId = repo.getUsers().size() + 100;
        User user = null;

        if ("CUSTOMER".equalsIgnoreCase(role)) {
            user = new Customer(newId, name, email, phone, password, "BASIC");
        } else if ("THEATRE_ADMIN".equalsIgnoreCase(role)) {
            user = new Administrator(newId, name, email, phone, password, "Operations", "");
        } else if ("OFFICER".equalsIgnoreCase(role)) {
            user = new Officer(newId, name, email, phone, password, "MORNING");
        } else {
            user = new User(newId, name, email, phone, password, role);
        }

        repo.addUser(user);
        System.out.println("User registered successfully!");
    }

    public List<User> getAllUsers() {
        return repo.getUsers();
    }

    public List<User> getUsers() {
        return repo.getUsers();
    }

    public User getUserById(int id) {
        return repo.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return repo.getUserByEmail(email);
    }

    public void removeUser(String email) {
        repo.removeUserByEmail(email);
    }

    public void updateUserProfile(User user) {
        System.out.println("Profile updated for: " + user.getName());
    }
}