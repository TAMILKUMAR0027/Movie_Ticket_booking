package org.expleo.TicketBookingJavaProject.model;

public class Officer extends User {
    private String shift;
    private String assignedHall;
    private String theatreName;

    public Officer() { super(); }

    public Officer(int id, String name, String email, String phone, String password, String shift) {
        super(id, name, email, phone, password, "OFFICER");
        this.shift = shift;
        this.assignedHall = "";
    }

    public String getShift() { return shift; }
    public String getAssignedHall() { return assignedHall; }
    public String getTheatreName() { return theatreName; }

    public void setShift(String shift) { this.shift = shift; }
    public void setAssignedHall(String assignedHall) { this.assignedHall = assignedHall; }
    public void setTheatreName(String theatreName) { this.theatreName = theatreName; }
}