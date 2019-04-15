package com.pdm.packaging.order;

public class Order {
    private final int orderID;
    private int senderID = 0;
    private String sender;
    private int receiverID;
    private String receiver;
    private boolean isPrePaid;
    private double cost;
    private boolean isComplete;

    public Order(int orderID, int senderID, int receiverID, boolean isPrePaid, double cost, boolean isComplete) {
        this.orderID = orderID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.isPrePaid = isPrePaid;
        this.cost = cost;
        this.isComplete = isComplete;
    }

    public Order(int orderID) {
        this.orderID = orderID;

    }

    public Order(int orderID, String sender, String receiver, boolean isPrePaid, double cost, boolean isComplete) {
        this.orderID = orderID;
        this.sender = sender;
        this.receiver = receiver;
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

    public String getSender() {
        return sender;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public String getReceiver() {
        return receiver;
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
