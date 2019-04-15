package com.pdm.packaging.packages;

public class Package {
    private int packageID;
    private int orderID;
    private int shippingStatus;
    private String statusDesc;
    private int weight;
    private int deliveryTime;
    private int traitID;
    private String trait;
    private int trackingID;

    public Package(int packageID, int orderID, int shippingStatus, String statusDesc, int weight, int deliveryTime, int traitID, String trait, int trackingID) {
        this.packageID = packageID;
        this.orderID = orderID;
        this.shippingStatus = shippingStatus;
        this.statusDesc = statusDesc;
        this.weight = weight;
        this.deliveryTime = deliveryTime;
        this.traitID = traitID;
        this.trait = trait;
        this.trackingID = trackingID;
    }

    public Package(int packageID) {
        this.packageID = packageID;
    }

    public int getPackageID() {
        return packageID;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getShippingStatus() {
        return shippingStatus;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public int getWeight() {
        return weight;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int getTraitID() {
        return traitID;
    }

    public String getTrait() {
        return trait;
    }

    public int getTrackingID() {
        return trackingID;
    }
}
