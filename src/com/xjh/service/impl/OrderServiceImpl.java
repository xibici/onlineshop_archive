package com.xjh.service.impl;

import com.xjh.dao.CartDao;
import com.xjh.dao.Impl.CartDaoImpl;
import com.xjh.dao.Impl.OrderDaoImpl;
import com.xjh.dao.Impl.OrderDetailDaoImpl;
import com.xjh.dao.OrderDao;
import com.xjh.dao.OrderDetailDao;
import com.xjh.domain.Order;
import com.xjh.domain.OrderDetail;
import com.xjh.service.OrderService;
import com.xjh.utils.DataSourceUtils;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private  OrderDao orderDao=new OrderDaoImpl();
    private  OrderDetailDao OrderDetailDao=new OrderDetailDaoImpl();
    @Override
    public void saveOrder(Order order, List<OrderDetail> orderDetails) {
        try {//1.开启事务
            DataSourceUtils.startTransaction();
            //2.调用dao向order表添加订单 创建新订单
            orderDao.add(order);
            //3.向订单详情表添加数据
            OrderDetailDao orderDetailDao=new OrderDetailDaoImpl();
            for (OrderDetail od:orderDetails){
                //用订单详情数组 创建多个表
                orderDetailDao.add(od);
            }

            //4.清空购物车数据
            CartDao cartDao=new CartDaoImpl();
            cartDao.deleteByUid(order.getUid());

            //5.提交
            DataSourceUtils.commit();


        } catch (Exception e) {
            e.printStackTrace();
            try {
                DataSourceUtils.rollback();//回滚
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                DataSourceUtils.close();//关闭连接
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    //获取当前用户订单
    @Override
    public List<Order> getOrderList(int uid) {
        return orderDao.findByUid(uid);
    }

    //根据订单id获取所有订单详情
    @Override
    public List<OrderDetail> findAllorderDetail(String oid) {
        return OrderDetailDao.findAllorderDetail(oid);
    }

    //根据订单id获取订单
    @Override
    public Order findOrderById(String oid) {
            return orderDao.findOrderById(oid);
    }

    @Override
    public void updateOrderStatus(String r6_order, String status) {
        orderDao.updateOrderStatus(r6_order,status);
    }

    @Override
    public List<Order> findOrderListBySearch(String userId, String status) {
        return orderDao.findOrderListBySearch(userId, status);
    }

    @Override
    public void updateOrderById(BigDecimal money, int status, String id) {
        orderDao.updateOrderById(money,status, id);
    }

    @Override
    public void delOrderById(String oid) {
        orderDao.delOrderById(oid);
    }
}
