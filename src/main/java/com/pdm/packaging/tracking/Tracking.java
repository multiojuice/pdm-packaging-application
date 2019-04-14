package com.pdm.packaging.tracking;

public class Tracking {
    private int trackingID;
    private int transportID;
    private int currentlocationID;

    public Tracking(int trackingID, int transportID, int currentlocationID) {
        this.trackingID = trackingID;
        this.transportID = transportID;
        this.currentlocationID = currentlocationID;
    }

    public int getTrackingID() { return trackingID; }

    public int getTransportID() { return transportID; }

    public int getcurrentlocationID() { return currentlocationID; }
}
