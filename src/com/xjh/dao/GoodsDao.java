package com.xjh.dao;

import com.xjh.domain.Goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface GoodsDao {
    long getCount(String condition);

    List<Goods> findPageByWhere(int pageNum,int pageSize,String condition);

    Goods findById(int gid);

    List<Goods> findAll();

    void delGoodsByPidAdmin(int pid);

    void updateGoodsByPidAdmin(String name, BigDecimal price, int typeid, int pid, String filename);

    List<Goods> findGoodsBySearch(String search);

    void addGoodsAdmin(String name, BigDecimal price, int typeid, int star, Date pubdate, String picture, String intro);

    List<Goods> getGoodsListBySearchLimit(int pageNum, int pageSize, String condition);
}
