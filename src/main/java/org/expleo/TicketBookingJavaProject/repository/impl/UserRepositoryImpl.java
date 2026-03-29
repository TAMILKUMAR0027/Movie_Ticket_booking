package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.model.User;
import org.expleo.TicketBookingJavaProject.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl {

    public static List<User> users = new ArrayList<>();

    static {
        users.add(new Customer(1, "John Doe", "john@example.com", "9876543210", "PREMIUM"));
        users.add(new Customer(2, "Jane Smith", "jane@example.com", "9876543211", "BASIC"));
        users.add(new Customer(3, "Bob Wilson", "bob@example.com", "9876543212", "PREMIUM"));
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

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(int id) {
        users.removeIf(u -> u.getId() == id);
    }
}
