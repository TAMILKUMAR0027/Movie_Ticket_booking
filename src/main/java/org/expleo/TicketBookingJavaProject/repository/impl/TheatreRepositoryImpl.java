package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.model.Theatre;
import org.expleo.TicketBookingJavaProject.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class TheatreRepositoryImpl {

    public static List<Theatre> theatres = new ArrayList<>();
    public static List<Movie> allMovies = new ArrayList<>();

    static {
        Theatre t1 = new Theatre(1, "PVR Cinemas", "Chennai", "Anna Nagar", 5);
        Theatre t2 = new Theatre(2, "INOX", "Chennai", "Express Avenue", 4);
        Theatre t3 = new Theatre(3, "Cinepolis", "Coimbatore", "Fun Republic", 3);
        Theatre t4 = new Theatre(4, "AMC Theatres", "Madurai", "Vikram Shopping Complex", 4);
        Theatre t5 = new Theatre(5, "Regal Cinemas", "Trichy", "Rockfort", 3);
        
        theatres.add(t1);
        theatres.add(t2);
        theatres.add(t3);
        theatres.add(t4);
        theatres.add(t5);

        allMovies.add(new Movie(1, "Avengers: Endgame", "English", "3h 1m", "8.4"));
        allMovies.add(new Movie(2, "Spider-Man: No Way Home", "English", "2h 28m", "8.2"));
        allMovies.add(new Movie(3, "Baahubali 2", "Telugu", "2h 51m", "8.5"));
        allMovies.add(new Movie(4, "RRR", "Telugu", "3h 2m", "8.0"));
        allMovies.add(new Movie(5, "Dhoom 3", "Hindi", "2h 53m", "7.3"));
        allMovies.add(new Movie(6, "Pushpa", "Telugu", "2h 59m", "7.6"));
        allMovies.add(new Movie(7, "Jawan", "Hindi", "2h 49m", "7.5"));
        allMovies.add(new Movie(8, "Leo", "Tamil", "2h 44m", "7.8"));
    }

    public List<Theatre> getTheatres() {
        return theatres;
    }

    public List<Theatre> getTheatresByCity(String city) {
        List<Theatre> result = new ArrayList<>();
        for (Theatre t : theatres) {
            if (t.getCity().equalsIgnoreCase(city)) {
                result.add(t);
            }
        }
        return result;
    }

    public Theatre getTheatreById(int id) {
        for (Theatre t : theatres) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public Theatre getTheatreByName(String name) {
        for (Theatre t : theatres) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    public void addTheatre(Theatre theatre) {
        theatres.add(theatre);
    }

    public void removeTheatre(int id) {
        theatres.removeIf(t -> t.getId() == id);
    }

    public List<Movie> getMoviesByTheatre(int theatreId) {
        return allMovies;
    }

    public void addMovieToTheatre(int theatreId, Movie movie) {
        allMovies.add(movie);
    }

    public void removeMovieFromTheatre(int theatreId, int movieId) {
        allMovies.removeIf(m -> m.getId() == movieId);
    }

    public List<String> getAllCities() {
        List<String> cities = new ArrayList<>();
        for (Theatre t : theatres) {
            if (!cities.contains(t.getCity())) {
                cities.add(t.getCity());
            }
        }
        return cities;
    }
}