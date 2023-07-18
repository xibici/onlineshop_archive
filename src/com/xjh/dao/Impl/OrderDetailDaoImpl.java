package com.xjh.dao.Impl;

import com.xjh.dao.OrderDetailDao;
import com.xjh.domain.OrderDetail;
import com.xjh.utils.DataSourceUtils;
import com.xjh.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public void add(OrderDetail od) {
        QueryRunner qr = new QueryRunner();
        JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
        //c3p0 执行事务
        try {
            qr.update(DataSourceUtils.getConnection(),"insert into tb_orderdetail(oid,pid,num,money)values(?,?,?,?)"
                    ,od.getOid(),od.getPid(),od.getNum(),od.getMoney());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加订单详情失败",e);
        }
    }


    @Override
    public List<OrderDetail> findAllorderDetail(String oid) {
        return template.query("select * from tb_orderdetail where oid=?",new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class),oid);
    }
}
