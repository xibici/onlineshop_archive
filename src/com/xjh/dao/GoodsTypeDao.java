package com.xjh.dao;

import com.xjh.domain.GoodsType;

import java.util.List;

public interface GoodsTypeDao {
    List<GoodsType> findByLevel(int level);

    GoodsType findById(int typeid);

    List<GoodsType> findAll();

    List<GoodsType> findGoodsTypeBySearch(String search);

    void delGoodsTypeByPidAdmin(int ptid);

    void updateGoodsTypeByPid(String name, int level, int parent, int id);

    void addGoodsType(String typename, int level, int parent);
}
