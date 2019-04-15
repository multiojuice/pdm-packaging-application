package com.pdm.packaging.user;

public class User {
    private int userID;
    private String name;
    private boolean isPremium;
    private String phoneNumber;
    private int businessID;
    private String businessName;

    public User(int userID, String name, boolean isPremium, String phoneNumber, int businessID) {
        this.userID = userID;
        this.name = name;
        this.isPremium = isPremium;
        this.phoneNumber = phoneNumber;
        this.businessID = businessID;
    }

    public User(int userID, String name, boolean isPremium, String phoneNumber, String businessName) {
        this.userID = userID;
        this.name = name;
        this.isPremium = isPremium;
        this.phoneNumber = phoneNumber;
        this.businessName = businessName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getBusinessID() {
        return businessID;
    }

    public String getBusinessName() { return businessName; }
}
