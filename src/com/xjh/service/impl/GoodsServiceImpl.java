package com.xjh.service.impl;

import com.xjh.dao.GoodsDao;
import com.xjh.dao.GoodsTypeDao;
import com.xjh.dao.Impl.GoodsDaoImpl;
import com.xjh.dao.Impl.GoodsTypeDaoImpl;
import com.xjh.domain.Goods;
import com.xjh.domain.GoodsType;
import com.xjh.domain.PageBean;
import com.xjh.service.GoodsService;
import com.xjh.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GoodsServiceImpl implements GoodsService {
    GoodsDao goodsDao=new GoodsDaoImpl();
    @Override
    public PageBean<Goods> findPageByWhere(int pageNum,int pageSize,String condition) {

        long totalSize=goodsDao.getCount(condition);//货物总数
        List<Goods> data =goodsDao.findPageByWhere(pageNum,pageSize,condition);//每页列表显示数据

        PageBean<Goods> pageBean=new PageBean<>(pageNum,pageSize,totalSize,data );
        return pageBean;
    }

    @Override
    public PageBean<Goods> getGoodsListBySearchLimit(int pageNum, int pageSize, String condition) {
        long totalSize=goodsDao.getCount(condition);//货物总数
        ////name like %小明%
        List<Goods> data =goodsDao.getGoodsListBySearchLimit(pageNum,pageSize,condition);//每页列表显示数据

        PageBean<Goods> pageBean=new PageBean<>(pageNum,pageSize,totalSize,data );
        return pageBean;

    }

    //根据id获取商品
    @Override
    public Goods findById(int gid) {
        Goods goods = goodsDao.findById(gid);
        //查询商品的类型id查询商品类型,
        GoodsTypeDao goodsTypeDao = new GoodsTypeDaoImpl();
        GoodsType goodsType=goodsTypeDao.findById(goods.getTypeid());
        goods.setGoodsType(goodsType);
        return goods;
    }

    @Override
    public List<Goods> findAll() {
        return goodsDao.findAll();
    }

    @Override
    public void delGoodsByPidAdmin(String pid) {
        goodsDao.delGoodsByPidAdmin(Integer.parseInt(pid));
    }

    @Override
    public void updateGoodsByPidAdmin(String name, BigDecimal price, int typeid, int pid, String filename) {
        if (StringUtils.isEmpty(filename)){
            Goods goods = goodsDao.findById(pid);
            filename = goods.getPicture();
        }
        goodsDao.updateGoodsByPidAdmin(name,price,typeid,pid,filename);
    }

    @Override
    public List<Goods> findGoodsBySearch(String search) {
        return goodsDao.findGoodsBySearch(search);
    }

    @Override
    public void addGoodsAdmin(String name, BigDecimal price, int typeid, int star, Date pubdate, String picture, String intro) {
        goodsDao.addGoodsAdmin( name,  price,  typeid,  star,  pubdate,  picture,  intro);
    }




}
