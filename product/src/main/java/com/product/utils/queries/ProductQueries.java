package com.product.utils.queries;

public class ProductQueries {

    public static final String addProductQuery = "insert into product (product_id , product_name, description,unit_price) values (?,?,?,?)";
    public static final String getProductList = "select product_id,product_name,description,unit_price from product";
    public static final String getSpecificProData = "select product_id,product_name,description,unit_price from product where product_id = ?";
}
