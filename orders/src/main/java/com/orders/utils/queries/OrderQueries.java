package com.orders.utils.queries;

public class OrderQueries {

    public static final String addOrderQuery = "insert into orders (order_id , product_id, quantity , order_date, delivery_date) values (?,?,?,?,?)";

    public static final String getOrderList = "select id, order_id from orders group by order_id";

    public static final String getProductList = "select order_id,product_id,quantity,order_date,delivery_date " +
            "from orders where order_id = ?";
}
