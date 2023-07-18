package com.xjh.service;

import com.xjh.domain.Goods;
import com.xjh.domain.PageBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface GoodsService {
    PageBean<Goods> findPageByWhere(int pageNum,int pageSize,String condition);

    Goods findById(int gid);

    List<Goods> findAll();

    void delGoodsByPidAdmin(String pid);

    void updateGoodsByPidAdmin(String name, BigDecimal price, int typeid, int pid, String filename);

    List<Goods> findGoodsBySearch(String search);

    void addGoodsAdmin(String name, BigDecimal price, int typeid, int star, Date pubdate, String picture, String intro);


    PageBean<Goods> getGoodsListBySearchLimit(int pageNum, int pageSize, String condition);
}
