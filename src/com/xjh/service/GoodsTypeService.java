package com.xjh.service;

import com.xjh.domain.GoodsType;

import java.util.List;

public interface GoodsTypeService {
    List<GoodsType> findTypeByLevel(int level);

    List<GoodsType> findAll();

    List<GoodsType> findGoodsTypeBySearch(String search);

    void delGoodsTypeByPidAdmin(int ptid);

    void updateGoodsTypeByPid(String name, int level, int parent, int id);

    void addGoodsType(String typename, int level, int parent);


}
