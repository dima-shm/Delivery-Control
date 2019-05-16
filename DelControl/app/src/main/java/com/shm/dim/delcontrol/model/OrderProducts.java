package com.shm.dim.delcontrol.model;

public class OrderProducts {

    private int mId;

    private int mOrderId;

    private String mProductName;

    private String mDescription;

    private double mPrice;

    public OrderProducts(int id, int orderId, String productName, String description, double price) {
        mId = id;
        mOrderId = orderId;
        mProductName = productName;
        mDescription = description;
        mPrice = price;
    }

    public int getId() {
        return mId;
    }

    public int getOrderId() {
        return mOrderId;
    }

    public String getProductName() {
        return mProductName;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getPrice() {
        return mPrice;
    }

}
