package com.xjh.service;

import com.xjh.domain.Address;

import java.util.List;

public interface AddressService {
    List<Address> findByUid(int uid);

    void add(Address address);

    void updateDefault(int aid,int uid);//aid地址id  uid用户id

    void delete(int id);

    void update(Address address);

    Address findById(int id);
}
