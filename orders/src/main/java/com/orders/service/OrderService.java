package com.orders.service;

import com.orders.entities.GenericResponse;
import com.orders.entities.OrderEntity;

public interface OrderService {


    GenericResponse addOrder(OrderEntity ord);

    GenericResponse getOrderList();

    GenericResponse getSpecificOrderDetails(int ordId);

}
