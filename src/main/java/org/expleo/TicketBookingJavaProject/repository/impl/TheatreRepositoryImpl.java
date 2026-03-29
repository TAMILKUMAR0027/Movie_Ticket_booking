package org.expleo.TicketBookingJavaProject.repository.impl;

import org.expleo.TicketBookingJavaProject.model.Theatre;
import java.util.ArrayList;
import java.util.List;

public class TheatreRepositoryImpl {

    public static List<Theatre> theatres = new ArrayList<>();

    static {
        theatres.add(new Theatre(1, "PVR Cinemas", null));
        theatres.add(new Theatre(2, "INOX", null));
        theatres.add(new Theatre(3, "Cinepolis", null));
        theatres.add(new Theatre(4, "AMC Theatres", null));
        theatres.add(new Theatre(5, "Regal Cinemas", null));
    }

    public List<Theatre> getTheatres() {
        return theatres;
    }

    public List<Theatre> getTheatresByCity(String city) {
        return theatres;
    }

    public Theatre getTheatreById(int id) {
        for (Theatre t : theatres) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public void addTheatre(Theatre theatre) {
        theatres.add(theatre);
    }
}
