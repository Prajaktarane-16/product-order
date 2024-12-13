package com.product.services.impl;

import com.product.entities.GenericResponse;
import com.product.entities.ProductEntity;
import com.product.repositories.ProductRepository;
import com.product.services.ProductService;
import com.product.utils.DataManagementUtil;
import com.product.utils.ProductConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DataManagementUtil dataManagementUtil;

    @Override
    public GenericResponse addProduct(ProductEntity pro) {
        GenericResponse responseBody = new GenericResponse();

        int insertCount = productRepository.saveProductDetails(pro.getProductId(), pro.getProductName(),pro.getDescription(),pro.getUnitPrice());
        if(insertCount > 0){
            responseBody = dataManagementUtil.response(ProductConstants.SUCCESS_CODE,
                    ProductConstants.ADD_PRODUCT_SUCCESS,insertCount);
        }
        else
            responseBody = dataManagementUtil.response(ProductConstants.INTERNAL_SERVER_ERROR_CODE,
                    ProductConstants.ADD_PRODUCT_FAILED, insertCount);
        return responseBody;
    }

    @Override
    public GenericResponse getProductList() {
        GenericResponse responseBody = new GenericResponse();
        List<ProductEntity> mappingList = productRepository.getProductDetails();
        if(mappingList == null){
            responseBody.setStatus(ProductConstants.INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(ProductConstants.DATABASE_ERROR_MSG);
            responseBody.setData(new ArrayList<>());
        } else if (mappingList.isEmpty()) {
            responseBody.setStatus(ProductConstants.INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(ProductConstants.PRODUCT_LIST_DATA_NOT_FOUND);
            responseBody.setData(new ArrayList<>());
        } else {
            responseBody.setStatus(ProductConstants.SUCCESS_CODE);
            responseBody.setMessage(ProductConstants.PRODUCT_LIST_DATA_FOUND);
            responseBody.setData(mappingList);
        }
        return responseBody;
    }

    @Override
    public GenericResponse getSpecificProductDetails(int pId) {
        GenericResponse responseBody = new GenericResponse();
        Optional<ProductEntity> mappingDetails = productRepository.getMappingDetailsById(pId);
        if(mappingDetails == null){
            responseBody.setStatus(ProductConstants.INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(ProductConstants.DATABASE_ERROR_MSG);
            responseBody.setData(new HashMap<>());
        } else if (mappingDetails.isEmpty()) {
            responseBody.setStatus(ProductConstants.INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(ProductConstants.PRODUCT_DATA_NOT_FOUND);
            responseBody.setData(new HashMap<>());
        } else {
            responseBody.setStatus(ProductConstants.SUCCESS_CODE);
            responseBody.setMessage(ProductConstants.PRODUCT_DATA_FOUND);
            responseBody.setData(mappingDetails);
        }
        return responseBody;
    }

    @Override
    public GenericResponse getDataByProIds(int productIds) {
        GenericResponse responseBody = new GenericResponse();
        ProductEntity mappingList = productRepository.getProductsData(productIds);
        if(mappingList == null){
            responseBody.setStatus(ProductConstants.INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(ProductConstants.DATABASE_ERROR_MSG);
            responseBody.setData(new ArrayList<>());
        } else {
            responseBody.setStatus(ProductConstants.SUCCESS_CODE);
            responseBody.setMessage(ProductConstants.PRODUCT_LIST_DATA_FOUND);
            responseBody.setData(mappingList);
        }
        return responseBody;
    }
}
