package com.orders.repositories;

import com.orders.entities.OrderDetailsResponse;
import com.orders.entities.OrderEntity;
import com.orders.entities.Product_details;
import com.orders.utils.queries.OrderQueries;
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

import static com.orders.utils.queries.OrderQueries.getProductList;

@Slf4j
@Repository
public class OrderRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int saveOrderDetails(int orderId,int productId,int quantity,String orderDate,String deliveryDate) {
        KeyHolder holder = new GeneratedKeyHolder();
        int id;
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(OrderQueries.addOrderQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, orderId);
                    ps.setInt(2, productId);
                    ps.setInt(3, quantity);
                    ps.setString(4, orderDate);
                    ps.setString(5, deliveryDate);
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

    public List<Product_details> getOrderDetails(){
        List<Product_details> mappingList = null;
        try{
            RowMapper<Product_details> rowMapper = (rs, rowNum) -> {
                Product_details mapping = new Product_details();
                mapping.setId(rs.getInt("id"));
                mapping.setOrder_id(rs.getInt("order_id"));
                return mapping;
            };
            mappingList = jdbcTemplate.query(OrderQueries.getOrderList,rowMapper);
        }catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingList;
    }


    public List<OrderEntity> getMappingDetailsById(int ordId){
        List<OrderEntity> proData = null;
        try {
            RowMapper<OrderEntity> rowMapper = new RowMapper<OrderEntity>() {
                @Override
                public OrderEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OrderEntity proData = new OrderEntity();
                    proData.setOrderId(rs.getInt("order_id"));
                    proData.setProductId(rs.getInt("product_id"));
                    proData.setQuantity(rs.getInt("quantity"));
                    proData.setOrderDate(rs.getString("order_date"));
                    proData.setDeliveryDate(rs.getString("delivery_date"));

                    return proData;
                }
            };
            proData = jdbcTemplate.query(getProductList, new Object[]{ordId}, rowMapper);
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return proData;
    }

}
