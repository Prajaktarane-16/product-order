package com.product.controllers;

import com.product.entities.GenericResponse;
import com.product.entities.ProductEntity;
import com.product.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProductDetails")
    public ResponseEntity addProductDetails(@RequestBody ProductEntity proEntity){
        log.info("Entered the /product/addProductDetails API..",proEntity);
        GenericResponse res = productService.addProduct(proEntity);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @GetMapping("/getListOfProducts")
    public ResponseEntity getListOfProducts(){
        log.info("Entered the /product/getListOfProducts API");
        GenericResponse res = productService.getProductList();
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getSpecificProData/{pId}")
    public ResponseEntity getSpecificProData(@PathVariable int pId){
        log.info("Entered the /product/getSpecificProData API");
        GenericResponse res = productService.getSpecificProductDetails(pId);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/order/getSpecificOrderData/{prdId}")
    public ResponseEntity getProductsByIds(@PathVariable int prdId){
        log.info("Entered the /product/getProductsByIds API");
        GenericResponse res = productService.getDataByProIds(prdId);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
