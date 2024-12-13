package com.product.services;

import com.product.entities.GenericResponse;
import com.product.entities.ProductEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {

    GenericResponse addProduct(ProductEntity pro);
    GenericResponse getProductList();
    GenericResponse getSpecificProductDetails(int sId);
    GenericResponse getDataByProIds(int productIds);
}
