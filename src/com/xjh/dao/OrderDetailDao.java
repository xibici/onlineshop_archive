package com.xjh.dao;

import com.xjh.domain.OrderDetail;

import java.util.List;

public interface OrderDetailDao {
    void add(OrderDetail od);

    List<OrderDetail> findAllorderDetail(String oid);
}
