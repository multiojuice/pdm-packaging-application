package com.pdm.packaging.user;

public class User {
    private int userID;
    private String name;
    private boolean isPremium;
    private int phoneNumber;
    private int businessID;

    public User(int userID, String name, boolean isPremium, int phoneNumber, int businessID) {
        this.userID = userID;
        this.name = name;
        this.isPremium = isPremium;
        this.phoneNumber = phoneNumber;
        this.businessID = businessID;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getBusinessID() {
        return businessID;
    }
}
