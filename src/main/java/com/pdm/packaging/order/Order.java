package com.pdm.packaging.order;

public class Order {
    private final int orderID;
    private final int senderID;
    private final int receiverID;
    private final boolean isPrePaid;
    private final double cost;
    private final boolean isComplete;

    public Order(int orderID, int senderID, int receiverID, boolean isPrePaid, double cost, boolean isComplete) {
        this.orderID = orderID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.isPrePaid = isPrePaid;
        this.cost = cost;
        this.isComplete = isComplete;
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

    public double getCost() {
        return cost;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
