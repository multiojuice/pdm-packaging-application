package com.pdm.packaging.tracking;

public class FutureTracking {
    private int trackingID;
    private int stopNumber;
    private int currentlocationID;
    private String currentlocationName;


    public FutureTracking(int trackingID, int stopNumber, int currentlocationID, String currentlocationName) {
        this.trackingID = trackingID;
        this.stopNumber = stopNumber;
        this.currentlocationID = currentlocationID;
        this.currentlocationName = currentlocationName;
    }

    public int getTrackingID() { return trackingID; }

    public int getStopNumber() {
        return stopNumber;
    }

    public int getcurrentlocationID() { return currentlocationID; }

    public String getCurrentlocationName() {
        return currentlocationName;
    }

}
