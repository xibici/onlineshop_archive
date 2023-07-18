package com.xjh.dao.Impl;

import com.xjh.dao.OrderDao;
import com.xjh.domain.Order;
import com.xjh.utils.DataSourceUtils;
import com.xjh.utils.JDBCUtils;
import com.xjh.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void add(Order order) {
        QueryRunner qr = new QueryRunner();
        //c3p0 执行事务
        try {
            qr.update(DataSourceUtils.getConnection(),"insert into tb_order(id,uid,money,status,time,aid)values(?,?,?,?,?,?)"
                    ,order.getId(),order.getUid(),order.getMoney(),order.getStatus(),order.getTime(),order.getAid());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加订单失败",e);
        }
    }

    @Override
    public List<Order> findAll() {

        List<Order> orderList = template.query("select * from tb_order", new BeanPropertyRowMapper<Order>(Order.class));
        return orderList;
    }

    //数据库查询当前用户所有的订单
    @Override
    public List<Order> findByUid(int uid) {
        List<Order> orderList = template.query("select * from tb_order where uid=?", new BeanPropertyRowMapper<Order>(Order.class),uid);
        return orderList;
    }

    @Override
    public Order findOrderById(String id) {
        return template.queryForObject("select * from tb_order where id=?", new BeanPropertyRowMapper<Order>(Order.class),id);

    }

    @Override
    public void updateOrderStatus(String oid,String status) {
        try {
            int statusInt = Integer.parseInt(status);
            System.out.println(oid+":"+status);
            template.update("update tb_order set status =? where id=?",statusInt,oid);
            System.out.println("2");
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException("更新订单状态失败",e);
        }
    }

    @Override
    public List<Order> findOrderListBySearch(String userId, String status) {
        List<Order> orderList=null;
        //status为-1等于空
        if (StringUtils.isEmpty(userId)&&Integer.parseInt(status)!=-1){
            //userId为空 状态不为空,只查询状态
            orderList = template.query("select * from tb_order where status=?", new BeanPropertyRowMapper<Order>(Order.class),status);
        }
        if (Integer.parseInt(status)==-1&&!StringUtils.isEmpty(userId)){
            //status状态为空 uid不为空,只查询userId
            int uid = Integer.parseInt(userId);
            orderList = template.query("select * from tb_order where uid=?", new BeanPropertyRowMapper<Order>(Order.class),uid);
        }
        if (Integer.parseInt(status)==-1&&StringUtils.isEmpty(userId)){
            //都为空
            orderList = template.query("select * from tb_order", new BeanPropertyRowMapper<Order>(Order.class));
        }
        if (Integer.parseInt(status)!=-1&&!StringUtils.isEmpty(userId)){
            //都不为空
            int uid = Integer.parseInt(userId);
            orderList = template.query("select * from tb_order where uid=? and status=?", new BeanPropertyRowMapper<Order>(Order.class),uid,status);
        }


        return orderList;
    }

    @Override
    public void updateOrderById(BigDecimal money, int status, String id) {
        template.update("update tb_order set money=? ,status =? where id=?",money,status,id);
    }

    @Override
    public void delOrderById(String oid) {
        template.update("delete from tb_order where id=?",oid);
    }


}
