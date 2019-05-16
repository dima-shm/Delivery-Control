package com.shm.dim.delcontrol.model;

import java.util.ArrayList;

public class OrdersList {

    private static ArrayList<Order> mOrders = new ArrayList<>();

    public static ArrayList<Order> getOrders() {
        return mOrders;
    }

    public static void setOrders(ArrayList<Order> orders) {
        mOrders = orders;
    }

    public static Order getOrder(int index) {
        return mOrders.get(index);
    }

    public static void addOrder(Order order) {
        mOrders.add(order);
    }

    public static int getOrderCount() {
        return mOrders.size();
    }

}