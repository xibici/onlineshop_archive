package com.xjh.service.impl;

import com.xjh.dao.CartDao;
import com.xjh.dao.Impl.CartDaoImpl;
import com.xjh.domain.Cart;
import com.xjh.domain.Goods;
import com.xjh.service.CartService;
import com.xjh.service.GoodsService;

import java.util.List;

public class CartServiceImpl implements CartService {
    private CartDao cartDao=new CartDaoImpl();
    @Override
    public Cart findByUidAndPid(int uid, int pid) {
        return cartDao.findByUidAndPid(uid,pid);
    }

    @Override
    public void add(Cart cart) {
        cartDao.add(cart);
    }

    @Override
    public void update(Cart cart) {
        cartDao.update(cart);
    }

    //根据用户id查询购物车商品内容
    @Override
    public List<Cart> findByUid(int uid) {
        List<Cart> carts = cartDao.findByUid(uid);
        if (carts!=null){
            GoodsService goodsService = new GoodsServiceImpl();
            for (Cart cart:carts){
                Goods goods = goodsService.findById(cart.getPid());
                cart.setGoods(goods);
            }
        }
        return carts;
    }

    @Override
    public void delete(int uid, int pid) {
        cartDao.delete(uid,pid);
    }

    @Override
    public void deleteByUid(int id) {
        cartDao.deleteByUid(id);
    }
}
