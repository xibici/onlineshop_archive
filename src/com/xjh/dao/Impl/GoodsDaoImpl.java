package com.xjh.dao.Impl;

import com.xjh.dao.GoodsDao;
import com.xjh.domain.Goods;
import com.xjh.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GoodsDaoImpl implements GoodsDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public long getCount(String condition) {        //传的是typeId=1;
        String sql="select count(*) from tb_goods";
        if (condition!=null&&condition.trim().length()!=0){
            sql=sql+" where "+condition;// select count(*) from tb_goods where typeId=1
        }   ////name like %小明%

        try {
            return template.queryForObject(sql,Long.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("查询商品个数失败",e);
        }

    }

    @Override
    public List<Goods> findPageByWhere(int pageNum,int pageSize,String condition) {
        //根据typeid获取  列表信息
        String sql="select * from tb_goods";
        if (condition!=null&&condition.trim().length()!=0){
            sql=sql+" where "+condition;// select count(*) from tb_goods where typeId=1
        }
        sql=sql+" order by id limit ? , ? ";
        // select * from tb_goods where typeId=1 order by id limit ? , ? 有条件
        // select * from tb_goods  order by id limit ? , ? 没条件
        try {//返回data
            return template.query(sql,new BeanPropertyRowMapper<Goods>(Goods.class),(pageNum-1)*pageSize,pageSize);
        } catch (DataAccessException e) {//limit需要注意  start=(pageNum-1)*pageSize
            e.printStackTrace();
            throw new RuntimeException("查询商品个数失败",e);
        }
        }

    @Override
    public List<Goods> getGoodsListBySearchLimit(int pageNum, int pageSize, String condition) {
        //根据t查询的名字获取  列表信息
        String sql = "select * from tb_goods";
        if (condition != null && condition.trim().length() != 0) {
            sql = sql + " where " + condition;// select count(*) from tb_goods where name like ?
        }
        sql = sql + " order by id limit ? , ? ";
        System.out.println("查询:"+sql);
        //返回data
         return template.query(sql, new BeanPropertyRowMapper<Goods>(Goods.class), (pageNum - 1) * pageSize, pageSize);


    }
    @Override
    public Goods findById(int gid) {
        try {
            return template.queryForObject("select * from tb_goods where id=?",new BeanPropertyRowMapper<Goods>(Goods.class),gid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("查询商品失败或不存在",e);
        }


    }

    @Override
    public List<Goods> findAll() {
        return template.query("SELECT * FROM tb_goods",new BeanPropertyRowMapper<Goods>(Goods.class));
    }

    @Override
    public void delGoodsByPidAdmin(int pid) {
        template.update("delete from tb_goods where id=?",pid);
    }

    @Override
    public void updateGoodsByPidAdmin(String name, BigDecimal price, int typeid, int pid, String filename) {
        //update tb_cart set num=? ,money=? where id=? and pid=?
        template.update("update tb_goods set name =?,price=?,typeid=?,picture=? where id=?",name,price,typeid,filename,pid);
    }

    @Override
    public List<Goods> findGoodsBySearch(String search) {
        search="%"+search+"%";//后台管理中查询的
        try {
            return template.query("select * from tb_goods where name like ?",new BeanPropertyRowMapper<Goods>(Goods.class),search);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("根据搜索名称查询商品失败或不存在",e);
        }
    }

    @Override
    public void addGoodsAdmin(String name, BigDecimal price, int typeid, int star, Date pubdate, String picture, String intro) {
        try {
            template.update("insert into tb_goods values (?,?,?,?,?,?,?,?)",
                null,name,pubdate, picture,price,star,intro,typeid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("添加商品失败不存在",e);
        }
    }


}

