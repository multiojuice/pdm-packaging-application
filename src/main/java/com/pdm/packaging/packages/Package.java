package com.pdm.packaging.packages;

public class Package {
    private int packageID;
    private int orderID;
    private int shippingStatus;
    private int weight;
    private int deliveryTime;
    private int trait;
    private int trackingID;

    public Package(int packageID, int orderID, int shippingStatus, int weight, int deliveryTime, int trait, int trackingID) {
        this.packageID = packageID;
        this.orderID = orderID;
        this.shippingStatus = shippingStatus;
        this.weight = weight;
        this.deliveryTime = deliveryTime;
        this.trait = trait;
        this.trackingID = trackingID;
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

    public int getWeight() {
        return weight;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int getTrait() {
        return trait;
    }

    public int getTrackingID() {
        return trackingID;
    }
}
