package org.expleo.TicketBookingJavaProject.model;

public class Movie {
    private int id;
    private String title;
    private String language;
    private String duration;
    private String rating;
    private String showtime;
    private boolean active;

    public Movie() {}

    public Movie(int id, String title, String language, String duration, String rating) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.duration = duration;
        this.rating = rating;
        this.active = true;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getLanguage() { return language; }
    public String getDuration() { return duration; }
    public String getRating() { return rating; }
    public String getShowtime() { return showtime; }
    public boolean isActive() { return active; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setLanguage(String language) { this.language = language; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setRating(String rating) { this.rating = rating; }
    public void setShowtime(String showtime) { this.showtime = showtime; }
    public void setActive(boolean active) { this.active = active; }
}