package org.expleo.TicketBookingJavaProject.controller;

import org.expleo.TicketBookingJavaProject.service.TheatreService;
import org.expleo.TicketBookingJavaProject.model.Theatre;
import java.util.List;

public class MovieController {

    private TheatreService service = new TheatreService();

    public void displayAllTheatres() {
        List<Theatre> theatres = service.getAllTheatres();
        System.out.println("\n========== AVAILABLE THEATRES ==========");
        for (int i = 0; i < theatres.size(); i++) {
            Theatre t = theatres.get(i);
            System.out.println((i + 1) + ". " + t.getName());
        }
    }

    public void displayTheatresByCity(String city) {
        List<Theatre> theatres = service.getTheatresByCity(city);
        System.out.println("\nTheatres in " + city + ":");
        for (Theatre t : theatres) {
            System.out.println("- " + t.getName());
        }
    }

    public TheatreService getService() {
        return service;
    }
}
