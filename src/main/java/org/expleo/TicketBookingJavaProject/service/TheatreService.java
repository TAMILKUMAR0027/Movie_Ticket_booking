package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.repository.impl.TheatreRepositoryImpl;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;

import java.util.List;

public class TheatreService {

    private TheatreRepositoryImpl theatreRepo = new TheatreRepositoryImpl();
    private MovieRepositoryImpl movieRepo = new MovieRepositoryImpl();

    public List<Theatre> getAllTheatres() {
        return theatreRepo.getTheatres();
    }

    public List<Theatre> getTheatresByCity(String city) {
        return theatreRepo.getTheatresByCity(city);
    }

    public Theatre getTheatreById(int id) {
        return theatreRepo.getTheatreById(id);
    }

    public void addTheatre(Theatre theatre) {
        theatreRepo.addTheatre(theatre);
    }

    public List<Movie> getMoviesByTheatre(int theatreId) {
        return movieRepo.getMoviesByTheatre(theatreId);
    }
}
