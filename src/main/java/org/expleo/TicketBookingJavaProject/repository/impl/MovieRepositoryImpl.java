package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieRepositoryImpl {

    public static List<Movie> movies = new ArrayList<>();

    static {
        movies.add(new Movie(1, "Avengers: Endgame", "English"));
        movies.add(new Movie(2, "Spider-Man: No Way Home", "English"));
        movies.add(new Movie(3, "Baahubali 2", "Telugu"));
        movies.add(new Movie(4, "RRR", "Telugu"));
        movies.add(new Movie(5, "Dhoom 3", "Hindi"));
        movies.add(new Movie(6, "Pushpa", "Telugu"));
        movies.add(new Movie(7, "Jawan", "Hindi"));
        movies.add(new Movie(8, "Leo", "Tamil"));
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Movie> getMoviesByTheatre(int theatreId) {
        return movies;
    }

    public Movie getMovieById(int id) {
        for (Movie m : movies) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }
}
