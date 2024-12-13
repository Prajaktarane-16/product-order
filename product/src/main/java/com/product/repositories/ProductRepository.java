package com.product.repositories;


import com.product.entities.ProductEntity;
import com.product.utils.queries.ProductQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ProductRepository {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public int saveProductDetails(int productId,String productName,String description,int unitPrice) {
        KeyHolder holder = new GeneratedKeyHolder();
        int id;
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(ProductQueries.addProductQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, productId);
                    ps.setString(2, productName);
                    ps.setString(3, description);
                    ps.setInt(4, unitPrice);
                    return ps;

                }
            }, holder);
            id = holder.getKey().intValue();
        } catch (DataAccessException e) {
            e.printStackTrace();
            id = 0;
        }
        return id;
    }

    public List<ProductEntity> getProductDetails(){
        List<ProductEntity> mappingList = null;
        try{
            RowMapper<ProductEntity> rowMapper = (rs, rowNum) -> {
                ProductEntity mapping = new ProductEntity();
                mapping.setProductId(rs.getInt("product_id"));
                mapping.setProductName(rs.getString("product_name"));
                mapping.setDescription(rs.getString("description"));
                mapping.setUnitPrice(rs.getInt("unit_price"));
                return mapping;
            };
            mappingList = jdbcTemplate.query(ProductQueries.getProductList,rowMapper);
        }catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingList;
    }


    public ProductEntity getProductsData(int prodId){
        ProductEntity proData = null;
        try {
            RowMapper<ProductEntity> rowMapper = new RowMapper<ProductEntity>() {
                @Override
                public ProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ProductEntity proData = new ProductEntity();
                    proData.setProductId(rs.getInt("product_id"));
                    proData.setProductName(rs.getString("product_name"));
                    proData.setDescription(rs.getString("description"));
                    proData.setUnitPrice(rs.getInt("unit_price"));
                    return proData;
                }
            };
            proData = jdbcTemplate.queryForObject(ProductQueries.getSpecificProData, new Object[]{prodId}, rowMapper);
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return proData;
    }



    public Optional<ProductEntity> getMappingDetailsById(int pid){
        Optional<ProductEntity> mappingDetails = null;
        try{
            //lamba is used here
            RowMapper<ProductEntity> rowMapper = (rs, rowNum) -> {
                ProductEntity mappingDetailsModel = new ProductEntity();
                mappingDetailsModel.setProductId(rs.getInt("product_id"));
                mappingDetailsModel.setProductName(rs.getString("product_name"));
                mappingDetailsModel.setDescription(rs.getString("description"));
                mappingDetailsModel.setUnitPrice(rs.getInt("unit_price"));
                return  mappingDetailsModel;
            };
            mappingDetails = jdbcTemplate.query(ProductQueries.getSpecificProData,new Object[]{pid},rowMapper).stream().findFirst();
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingDetails;
    }


}
