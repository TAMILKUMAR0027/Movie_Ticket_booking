package org.expleo.TicketBookingJavaProject.model;

public class Administrator extends User {
    private String department;
    private String accessLevel;

    public Administrator() { super(); }

    public Administrator(int id, String name, String email, String phone, String department) {
        super(id, name, email, phone, "ADMIN");
        this.department = department;
        this.accessLevel = "FULL";
    }

    public String getDepartment() { return department; }
    public String getAccessLevel() { return accessLevel; }

    public void setDepartment(String department) { this.department = department; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
}
