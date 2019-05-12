package com.shm.dim.delcontrol.model;

public class OrderProduct {

    private int mId;

    private int mOrderId;

    private String mProductName;

    private String mDescriotion;

    private String mPrice;

    public OrderProduct(int id, int orderId, String productName, String descriotion, String price) {
        mId = id;
        mOrderId = orderId;
        mProductName = productName;
        mDescriotion = descriotion;
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

    public String getDescriotion() {
        return mDescriotion;
    }

    public String getPrice() {
        return mPrice;
    }

}
