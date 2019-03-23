package com.pdm.packaging.order;

public class Order {
    private final int orderID;
    private final int senderID;
    private final int receiverID;
    private final boolean isPrePaid;
    private final int cost;

    public Order(int orderID, int senderID, int receiverID, boolean isPrePaid, int cost) {
        this.orderID = orderID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.isPrePaid = isPrePaid;
        this.cost = cost;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public boolean isPrePaid() {
        return isPrePaid;
    }

    public int getCost() {
        return cost;
    }
}
