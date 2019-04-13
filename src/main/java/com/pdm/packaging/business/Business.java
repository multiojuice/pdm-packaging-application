package com.pdm.packaging.business;

public class Business {
    private int businessID;
    private String name;
    private String address;

    public Business(int businessID, String name, String address) {
        this.businessID = businessID;
        this.name = name;
        this.address = address;
    }

    public int getBusinessID() {
        return businessID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
