package com.pdm.packaging.tracking;

public class Tracking {
    private int trackingID;
    private int transportID;
    private String transportName;
    private int currentlocationID;
    private String currentlocationName;

    public Tracking(int trackingID, int transportID, int currentlocationID) {
        this.trackingID = trackingID;
        this.transportID = transportID;
        this.currentlocationID = currentlocationID;
    }

    public Tracking(int trackingID, int transportID, String transportName, int currentlocationID, String currentlocationName) {
        this.trackingID = trackingID;
        this.transportID = transportID;
        this.transportName = transportName;
        this.currentlocationID = currentlocationID;
        this.currentlocationName = currentlocationName;
    }

    public int getTrackingID() { return trackingID; }

    public int getTransportID() { return transportID; }

    public String getTransportName() {
        return transportName;
    }

    public int getcurrentlocationID() { return currentlocationID; }

    public String getCurrentlocationName() {
        return currentlocationName;
    }
}
