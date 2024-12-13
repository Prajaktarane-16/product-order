package com.orders.service;

import com.orders.entities.GenericResponse;
import com.orders.service.impl.OrderServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//with docker container
@FeignClient(name = "Product-service" ,  url = "${product-service-base-url}" , fallback = OrderServiceImpl.class)
// with local
//@FeignClient(name = "Product" ,  url = "http://localhost:5055")
public interface ProductFeignClient {

    @GetMapping("/product/order/getSpecificOrderData/{prdId}")
    public GenericResponse getProductListById(@PathVariable int prdId);
}
