package org.expleo.TicketBookingJavaProject.model;

public class Administrator extends User {
    private String department;
    private String accessLevel;
    private String theatreName;

    public Administrator() { super(); }

    public Administrator(int id, String name, String email, String phone, String password, String department, String theatreName) {
        super(id, name, email, phone, password, "THEATRE_ADMIN");
        this.department = department;
        this.accessLevel = "THEATRE_ADMIN";
        this.theatreName = theatreName;
    }

    public String getDepartment() { return department; }
    public String getAccessLevel() { return accessLevel; }
    public String getTheatreName() { return theatreName; }

    public void setDepartment(String department) { this.department = department; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
    public void setTheatreName(String theatreName) { this.theatreName = theatreName; }
}