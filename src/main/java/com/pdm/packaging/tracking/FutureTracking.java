package com.pdm.packaging.tracking;

public class FutureTracking {
    private int trackingID;
    private int stopNumber;
    private int currentlocationID;
    private String currentlocationName;
    private int upcominglocationID;
    private String upcominglocationName;


    public FutureTracking(int trackingID, int stopNumber, int currentlocationID, String currentlocationName, int upcominglocationID, String upcominglocationName) {
        this.trackingID = trackingID;
        this.stopNumber = stopNumber;
        this.currentlocationID = currentlocationID;
        this.currentlocationName = currentlocationName;
        this.upcominglocationID = upcominglocationID;
        this.upcominglocationName = upcominglocationName;
    }

    public int getTrackingID() { return trackingID; }

    public int getStopNumber() {
        return stopNumber;
    }

    public int getcurrentlocationID() { return currentlocationID; }

    public String getCurrentlocationName() {
        return currentlocationName;
    }

    public int getUpcominglocationID() {
        return upcominglocationID;
    }

    public String getUpcominglocationName() {
        return upcominglocationName;
    }
}
