package com.orders.entities;

import java.io.Serializable;

public class Product_details implements Serializable {

    private int id;

    private int order_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "Product_details{" +
                "id=" + id +
                ", order_id=" + order_id +
                '}';
    }
}
