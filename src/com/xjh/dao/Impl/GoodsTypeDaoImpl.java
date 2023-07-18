package com.xjh.dao.Impl;

import com.xjh.dao.GoodsTypeDao;
import com.xjh.domain.GoodsType;
import com.xjh.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class GoodsTypeDaoImpl implements GoodsTypeDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<GoodsType> findByLevel(int level) {
        try {
            return template.query("select * from tb_goods_type where level= ?",new BeanPropertyRowMapper<GoodsType>(GoodsType.class),level);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("查询商品类型失败",e);
        }
    }

    @Override
    public GoodsType findById(int typeid) {
        try {//返回一个
            return template.queryForObject("select * from tb_goods_type where id= ? ",new BeanPropertyRowMapper<GoodsType>(GoodsType.class),typeid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("根据id查询商品类型失败",e);
        }
    }

    @Override
    public List<GoodsType> findAll() {
        return template.query("select * from tb_goods_type",new BeanPropertyRowMapper<GoodsType>(GoodsType.class));
    }

    @Override
    public List<GoodsType> findGoodsTypeBySearch(String search) {
        search="%"+search+"%";
        try {
        return template.query("select * from tb_goods_type where name like ?",new BeanPropertyRowMapper<GoodsType>(GoodsType.class),search);
               } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("根据搜索名称查询商品失败或不存在",e);
        }
    }

    @Override
    public void delGoodsTypeByPidAdmin(int ptid) {
        System.out.println(ptid);
        template.update("delete from tb_goods_type where id=?",ptid);
    }

    @Override
    public void updateGoodsTypeByPid(String name, int level, int parent, int id) {
         //update tb_goods set name =?,level=?,parent=? where id=?
        template.update("update tb_goods_type set name =?,level=?,parent=? where id=?",name,level,parent,id);

    }

    @Override
    public void addGoodsType(String typename, int level, int parent) {
        try {
            template.update("insert into tb_goods_type(id,name,level,Parent) values (?,?,?,?)",
                    null,typename,level, parent);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("添加商品失败不存在",e);
        }
    }
}
