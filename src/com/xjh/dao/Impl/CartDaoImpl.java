package com.xjh.dao.Impl;

import com.xjh.dao.CartDao;
import com.xjh.domain.Cart;
import com.xjh.utils.DataSourceUtils;
import com.xjh.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CartDaoImpl implements CartDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Cart findByUidAndPid(int uid, int pid) {//对象list的时候用query 单个对象用queryforobject
        Cart cart = null;
        try {
            cart = template.queryForObject("select * from tb_cart where id= ? and pid= ? ", new BeanPropertyRowMapper<Cart>(Cart.class), uid, pid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return cart;

    }

    @Override
    public void add(Cart cart) {
        try {
            template.update("insert into tb_cart (id,pid,num,money) values(?,?,?,?)",cart.getId(),cart.getPid(),cart.getNum(),cart.getMoney());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加失败",e);
        }

    }

    @Override
    public void update(Cart cart) {
        try {
            template.update("update tb_cart set num=? ,money=? where id=? and pid=?",cart.getNum(),cart.getMoney(),cart.getId(),cart.getPid());
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("更新失败",e);
        }

    }

    @Override
    public List<Cart> findByUid(int uid) {
        List<Cart> carts = null;
        try {
            carts = template.query("select * from tb_cart where id= ?  ", new BeanPropertyRowMapper<Cart>(Cart.class), uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("查询购物车失败",e);
        }
        return carts;

    }

    @Override
    public void delete(int uid, int pid) {
        try {
            template.update("delete from tb_cart where id=? and pid=?",uid,pid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("删除失败",e);
        }
    }

    @Override
    public void deleteByUid(int uid) {
        try {
            template.update("delete from tb_cart where id=?",uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("清空购物车失败",e);
        }
    }


    //事务c3p0
    @Override
    public void deleteByUid2(int uid) {
        QueryRunner qr = new QueryRunner();
        try {
            qr.update(DataSourceUtils.getConnection(),"delete from tb_cart where id=?",uid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("清空事务中购物车失败",e);
        }
    }
}
