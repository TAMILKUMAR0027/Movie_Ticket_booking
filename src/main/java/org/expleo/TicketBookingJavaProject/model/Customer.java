package org.expleo.TicketBookingJavaProject.model;

public class Customer extends User {
    private String membershipType;
    private int loyaltyPoints;

    public Customer() { super(); }

    public Customer(int id, String name, String email, String phone, String membershipType) {
        super(id, name, email, phone, "CUSTOMER");
        this.membershipType = membershipType;
        this.loyaltyPoints = 0;
    }

    public String getMembershipType() { return membershipType; }
    public int getLoyaltyPoints() { return loyaltyPoints; }

    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }
}
