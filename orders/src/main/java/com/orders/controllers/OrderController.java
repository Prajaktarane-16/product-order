package com.orders.controllers;


import com.orders.entities.GenericResponse;
import com.orders.entities.OrderEntity;
import com.orders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrderDetails")
    public ResponseEntity addOrderDetails(@RequestBody OrderEntity ordEntity){
        log.info("Entered the /college/addStudentDetails API..",ordEntity);
        GenericResponse res = orderService.addOrder(ordEntity);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getListOfOrders")
    public ResponseEntity getListOfOrders(/*@RequestHeader("userId") String userId*/){
        log.info("Entered the /college/getListOfColleges API");
        //System.out.println("userId : "+userId);
        GenericResponse res = orderService.getOrderList();
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getSpecificOrderData/{ordId}")
    public ResponseEntity getSpecificOrderData(@PathVariable int ordId ){
        log.info("Entered the /college/getSpecificClgData API");
        //System.out.println("userId : "+userId);
        GenericResponse res = orderService.getSpecificOrderDetails(ordId);

        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


}
