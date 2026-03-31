package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.model.Customer;
import org.expleo.TicketBookingJavaProject.model.Administrator;
import org.expleo.TicketBookingJavaProject.model.Officer;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl {

    public static List<User> users = new ArrayList<>();

    static {
        users.add(new Customer(1, "John Doe", "john@example.com", "9876543210", "password123", "PREMIUM"));
        users.add(new Customer(2, "Jane Smith", "jane@example.com", "9876543211", "password123", "BASIC"));
        users.add(new Customer(3, "Bob Wilson", "bob@example.com", "9876543212", "password123", "PREMIUM"));
        
        users.add(new Administrator(101, "Super Admin", "admin@system.com", "9999999999", "admin123", "IT", "All"));
        users.add(new Administrator(102, "Theatre Admin", "theatre@pvr.com", "8888888888", "theatre123", "Operations", "PVR Cinemas"));
        
        users.add(new Officer(201, "Officer One", "officer1@pvr.com", "7777777777", "officer123", "MORNING"));
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserById(int id) {
        for (User u : users) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public User getUserByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    public User login(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(int id) {
        users.removeIf(u -> u.getId() == id);
    }

    public void removeUserByEmail(String email) {
        users.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
    }
}