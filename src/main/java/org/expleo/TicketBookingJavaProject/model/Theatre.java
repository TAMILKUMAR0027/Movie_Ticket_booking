package org.expleo.TicketBookingJavaProject.model;
import java.util.List;

public class Theatre {
    private int id;
    private String name;
    private String city;
    private String location;
    private int totalScreens;
    private String adminEmail;
    private List<Movie> movies;
    private boolean active;

    public Theatre() {}

    public Theatre(int id, String name, String city, String location, int totalScreens) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.location = location;
        this.totalScreens = totalScreens;
        this.active = true;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getLocation() { return location; }
    public int getTotalScreens() { return totalScreens; }
    public String getAdminEmail() { return adminEmail; }
    public List<Movie> getMovies() { return movies; }
    public boolean isActive() { return active; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCity(String city) { this.city = city; }
    public void setLocation(String location) { this.location = location; }
    public void setTotalScreens(int totalScreens) { this.totalScreens = totalScreens; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
    public void setMovies(List<Movie> movies) { this.movies = movies; }
    public void setActive(boolean active) { this.active = active; }
}