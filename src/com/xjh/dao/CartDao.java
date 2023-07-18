package com.xjh.dao;

import com.xjh.domain.Cart;

import java.util.List;

public interface CartDao {
    Cart findByUidAndPid(int uid, int pid);

    void add(Cart cart);

    void update(Cart cart);

    List<Cart> findByUid(int uid);

    void delete(int uid, int goodsId);

    void deleteByUid(int id);

    void deleteByUid2(int id);
}
