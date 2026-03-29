package org.expleo.TicketBookingJavaProject;

import org.expleo.TicketBookingJavaProject.controller.AppController;

public class App {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   TICKET BOOKING SYSTEM - JAVA PROJECT");
        System.out.println("========================================");

        try {
            AppController controller = new AppController();
            controller.start();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
