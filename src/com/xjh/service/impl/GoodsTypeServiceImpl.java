package com.xjh.service.impl;

import com.xjh.dao.GoodsTypeDao;
import com.xjh.dao.Impl.GoodsTypeDaoImpl;
import com.xjh.domain.GoodsType;
import com.xjh.service.GoodsTypeService;
import com.xjh.utils.JedisUtil;
import jdk.jfr.Category;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GoodsTypeServiceImpl implements GoodsTypeService {
    GoodsTypeDao goodsTypeDao=new GoodsTypeDaoImpl();
    @Override
    public List<GoodsType> findTypeByLevel(int level) {
        //redis技术
        //1.从redis中查询,获取redis客户端
        Jedis jedis = JedisUtil.getJedis();
       //1.2因为查询需要顺序 所以用sortedset排序查询
        //1.3查询sortedset的分数cid和值cname
        Set<Tuple> goodsTypes = jedis.zrangeWithScores("goodsType", 0, -1);
        List<GoodsType> gt=null;//数据库中的GoodsType
        //2.判断查询的集合是否为空
        if (goodsTypes==null||goodsTypes.size()==0){
            //3如果数据为空直接从数据库查询,然后存入redis
            System.out.println("从数据库查询");
            //3.1查询数据
            gt=goodsTypeDao.findByLevel(level);
            //3.2将集合数据存入redis的category中的key
            for (int i=0;i<gt.size();i++){
                jedis.zadd("goodsType",gt.get(i).getId(),gt.get(i).getName());
            }

        }else {
            //4.如果不为空,直接返回,将set存入list
            System.out.println("从redis中查询");
            gt=new ArrayList<GoodsType>();
            for (Tuple tuple:goodsTypes){
                GoodsType goodsType = new GoodsType();
                goodsType.setName(tuple.getElement());
                goodsType.setId((int)tuple.getScore());
                gt.add(goodsType);
            }
        }
        //竟然忘了关
        JedisUtil.close(jedis);
        return gt;
      /*  List<Category> list=dao.findAll();
        System.out.println("list:"+list );
        return list;*/


        //return goodsTypeDao.findByLevel(level);
    }

    @Override
    public List<GoodsType> findAll() {
        return goodsTypeDao.findAll();
    }

    @Override
    public List<GoodsType> findGoodsTypeBySearch(String search) {
        return goodsTypeDao.findGoodsTypeBySearch(search);
    }

    @Override
    public void delGoodsTypeByPidAdmin(int ptid) {
        goodsTypeDao.delGoodsTypeByPidAdmin(ptid);
    }

    @Override
    public void updateGoodsTypeByPid(String name, int level, int parent, int id) {
        goodsTypeDao.updateGoodsTypeByPid( name,  level,  parent,  id);
    }

    @Override
    public void addGoodsType(String typename, int level, int parent) {
        goodsTypeDao.addGoodsType(typename,level,parent);
    }
}
