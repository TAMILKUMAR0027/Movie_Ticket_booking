package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.repository.impl.MovieRepositoryImpl;
import java.util.List;

public class MovieService {
    
    private MovieRepositoryImpl movieRepository = new MovieRepositoryImpl();

    public List<Movie> getAllMovies() {
        return movieRepository.getMovies();
    }

    public Movie getMovieById(int id) {
        return movieRepository.getMovieById(id);
    }

    public List<Movie> getMoviesByLanguage(String language) {
        List<Movie> result = new java.util.ArrayList<>();
        for (Movie m : movieRepository.getMovies()) {
            if (m.getLanguage().equalsIgnoreCase(language)) {
                result.add(m);
            }
        }
        return result;
    }

    public List<Movie> searchByTitle(String title) {
        List<Movie> result = new java.util.ArrayList<>();
        for (Movie m : movieRepository.getMovies()) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }
}
