package com.xjh.service;

import com.xjh.domain.Cart;

import java.util.List;

public interface CartService {
    Cart findByUidAndPid(int uid, int pid);//uid和商品id

    void add(Cart cart);

    void update(Cart cart);

    List<Cart> findByUid(int id);

    void delete(int uid, int pid);

    void deleteByUid(int id);
}
