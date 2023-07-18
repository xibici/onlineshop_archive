package com.xjh.service;

import com.xjh.domain.Order;
import com.xjh.domain.OrderDetail;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    void saveOrder(Order order, List<OrderDetail> orderDetails);

    List<Order> findAll();

    List<Order> getOrderList(int uid);

    List<OrderDetail> findAllorderDetail(String oid);

    Order findOrderById(String oid);

    void updateOrderStatus(String oid, String status);

    List<Order> findOrderListBySearch(String userId, String status);

    void updateOrderById(BigDecimal money, int status, String id);

    void delOrderById(String oid);
}
