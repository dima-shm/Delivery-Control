package com.shm.dim.delcontrol.model;

import java.util.ArrayList;

public class Order {

    private int mId;

    private int mCompanyId;

    private String mCustomerName;

    private String mDeliveryAddress;

    private String mDeliveryDate;

    private String mDeliveryTime;

    private String mComment;

    private String mStatus;

    private ArrayList<OrderProduct> mOrderProducts;

    public Order(int id, int companyId, String customerName, String deliveryAddress,
                 String deliveryDate, String deliveryTime, String comment,
                 String status, ArrayList<OrderProduct> orderProducts) {
        mId = id;
        mCompanyId = companyId;
        mCustomerName = customerName;
        mDeliveryAddress = deliveryAddress;
        mDeliveryDate = deliveryDate;
        mDeliveryTime = deliveryTime;
        mComment = comment;
        mStatus = status;
        mOrderProducts = orderProducts;
    }

    public int getId() {
        return mId;
    }

    public int getCompanyId() {
        return mCompanyId;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public String getDeliveryAddress() {
        return mDeliveryAddress;
    }

    public String getDeliveryDate() {
        return mDeliveryDate;
    }

    public String getDeliveryTime() {
        return mDeliveryTime;
    }

    public String getComment() {
        return mComment;
    }

    public String getStatus() {
        return mStatus;
    }

    public ArrayList<OrderProduct> getOrderProducts() {
        return mOrderProducts;
    }

}