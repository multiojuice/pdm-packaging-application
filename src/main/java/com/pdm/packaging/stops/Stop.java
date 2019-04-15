package com.pdm.packaging.stops;

public class Stop {
    private int trackingID;
    private int locationID;
    private int stopNum;
    private String stopName;

    public Stop(int trackingID, int locationID, int stopNum){
        this.trackingID = trackingID;
        this.locationID = locationID;
        this.stopNum = stopNum;
    }

    public Stop(int trackingID, int locationID, String stopName){
        this.trackingID = trackingID;
        this.locationID = locationID;
        this.stopName = stopName;
    }

    public int getTrackingID(){
        return trackingID;
    }

    public int getLocationID() {
        return locationID;
    }

    public int getStopNum() {
        return stopNum;
    }

    public String getStopName(){return stopName;}
}
