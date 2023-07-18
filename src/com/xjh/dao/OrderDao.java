package com.xjh.dao;

import com.xjh.domain.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao {
    void add(Order order);

    List<Order> findAll();

    List<Order> findByUid(int uid);


    Order findOrderById(String id);

    void updateOrderStatus(String r6_order,String status);

    List<Order> findOrderListBySearch(String userId, String status);

    void updateOrderById(BigDecimal money, int status, String id);

    void delOrderById(String oid);
}
