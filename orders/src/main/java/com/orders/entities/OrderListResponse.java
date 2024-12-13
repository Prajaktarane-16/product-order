package com.orders.entities;

import lombok.Data;
import netscape.javascript.JSObject;

import java.io.Serializable;
import java.util.List;


public class OrderListResponse<T> implements Serializable {

    private int ordId;

    private Object prodData;

    public int getOrdId() {
        return ordId;
    }

    public void setOrdId(int ordId) {
        this.ordId = ordId;
    }

    public Object getProdData() {
        return prodData;
    }

    public void setProdData(Object prodData) {
        this.prodData = prodData;
    }

    @Override
    public String toString() {
        return "OrderListResponse{" +
                "ordId=" + ordId +
                ", prodData=" + prodData +
                '}';
    }
}
