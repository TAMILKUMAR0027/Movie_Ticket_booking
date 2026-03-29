package org.expleo.TicketBookingJavaProject.model;

public class Officer extends User {
    private String shift;
    private String assignedHall;

    public Officer() { super(); }

    public Officer(int id, String name, String email, String phone, String shift) {
        super(id, name, email, phone, "OFFICER");
        this.shift = shift;
        this.assignedHall = "";
    }

    public String getShift() { return shift; }
    public String getAssignedHall() { return assignedHall; }

    public void setShift(String shift) { this.shift = shift; }
    public void setAssignedHall(String assignedHall) { this.assignedHall = assignedHall; }
}
