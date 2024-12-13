package com.orders.service.impl;

import com.orders.entities.*;
import com.orders.repositories.OrderRepository;
import com.orders.service.OrderService;

import com.orders.service.ProductFeignClient;
import com.orders.utils.DataManagementUtil;
import com.orders.utils.OrderConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.orders.utils.OrderConstants.*;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DataManagementUtil dataManagementUtil;

    @Autowired
    private ProductFeignClient productFeginClient;

    @Override
    public GenericResponse addOrder(OrderEntity ord) {

        GenericResponse responseBody = new GenericResponse();
        int insertCount = orderRepository.saveOrderDetails(ord.getOrderId(), ord.getProductId(),ord.getQuantity(),ord.getOrderDate(),ord.getDeliveryDate());
        if(insertCount > 0){
            responseBody = dataManagementUtil.response(SUCCESS_CODE,
                    OrderConstants.ADD_ORDER_SUCCESS ,insertCount);
        }
        else
            responseBody = dataManagementUtil.response(INTERNAL_SERVER_ERROR_CODE,
                    OrderConstants.ADD_ORDER_FAILED, insertCount);
        return responseBody;
    }


    @Override
    @Cacheable (value = "orderList")  // @Cacheable - to get the data store in db
    public GenericResponse getOrderList() {
        GenericResponse responseBody = new GenericResponse();
        System.out.println("service method invoked... ");
        List<Product_details> mappingList = orderRepository.getOrderDetails();
        if(mappingList == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new ArrayList<>());
        } else if (mappingList.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(OrderConstants.ORDER_LIST_DATA_NOT_FOUND);
            responseBody.setData(new ArrayList<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(OrderConstants.ORDER_LIST_DATA_FOUND);
            responseBody.setData(mappingList);
        }
        return responseBody;
    }

   // @CircuitBreaker(name = "myService", fallbackMethod = "fallbackMethod")
    @Override
    @Cacheable(value = "specificOrders", key = "#ordId")
    public GenericResponse getSpecificOrderDetails(int ordId) {
        GenericResponse responseBody = new GenericResponse();
        System.out.println("getSpecificOrders invoked..... "+ordId);
        List<OrderEntity> mappingDetails = orderRepository.getMappingDetailsById(ordId); // prodids

        if(mappingDetails == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new HashMap<>());
        } else if (mappingDetails.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(ORDER_DATA_NOT_FOUND);
            responseBody.setData(new HashMap<>());
        } else {
            OrderListResponse res = new OrderListResponse();
            List<Object> productData= new ArrayList<>();
            try{
                mappingDetails.stream().forEach( data ->{
                    System.out.println(data.getProductId());
                    productData.add( productFeginClient.getProductListById(data.getProductId()).getData());
                });
            }catch (Exception e){
                e.printStackTrace();
            }

            res.setOrdId(mappingDetails.get(0).getOrderId());
            res.setProdData(productData);
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(ORDER_DATA_FOUND);
            responseBody.setData(res);

        }
        return responseBody;
    }

    public GenericResponse fallbackMethod(Throwable e) {

        GenericResponse reponse= new GenericResponse();
        reponse.setData(null);
        reponse.setMessage("product service is down");
        reponse.setStatus(404);
        return reponse;
    }



}
