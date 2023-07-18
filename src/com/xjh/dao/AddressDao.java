package com.xjh.dao;

import com.xjh.domain.Address;

import java.util.List;

public interface AddressDao {
    List<Address> findByUid(int uid);

    void add(Address address);

    void updateDefault(int aid, int uid);

    void delete(int id);

    void update(Address address);

    Address findById(int id);
}
