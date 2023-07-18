package com.xjh.dao.Impl;

import com.xjh.dao.AddressDao;
import com.xjh.domain.Address;
import com.xjh.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AddressDaoImpl implements AddressDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Address> findByUid(int uid) {
        List<Address> list;
        try {
            list= template.query("select * from tb_address where uid=?", new BeanPropertyRowMapper<Address>(Address.class), uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("查询地址失败", e);
        }
        return list;
    }

    @Override
    public void add(Address address) {

        try {
            template.update("insert into tb_address(detail,name,phone,uid,level) values(?,?,?,?,?)",
                    address.getDetail(),address.getName(),address.getPhone(),address.getUid(),address.getLevel());
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("添加地址失败", e);
        }

    }

    @Override
    public void updateDefault(int aid, int uid) {
        //把其他地址的level改成0 aid的地址改成1  level 1代表默认地址 0代表其他地址

        try {//先把所有设为0
            template.update("update tb_address set level=0 where uid=?",uid);
            //然后把特定改为1
            template.update("update tb_address set level=1 where id=?",aid);


        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("更改默认地址失败", e);
        }

    }

    @Override
    public void delete(int id) {
        //删除地址
        try {
            template.update("delete from tb_address where id=?",id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("删除地址失败", e);
        }

    }

    @Override
    public void update(Address address) {
        //修改 更新地址和
        try {
            template.update("update tb_address set name=?,phone=?,detail=? where id =?",
                    address.getName(),address.getPhone(),address.getDetail(),address.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("修改地址失败", e);
        }
    }

    @Override
    public Address findById(int id) {
        return  template.queryForObject("select * from tb_address where id=?", new BeanPropertyRowMapper<Address>(Address.class), id);
    }
}




