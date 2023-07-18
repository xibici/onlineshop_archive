package com.xjh.service.impl;

import com.xjh.dao.AddressDao;
import com.xjh.dao.Impl.AddressDaoImpl;
import com.xjh.domain.Address;
import com.xjh.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    AddressDao addressDao=new AddressDaoImpl();
    //查找用户地址
    @Override
    public List<Address> findByUid(int uid) {
        return addressDao.findByUid(uid);
    }

    @Override
    public void add(Address address) {
        addressDao.add(address);
    }

    @Override
    public void updateDefault(int aid,int uid) {
        addressDao.updateDefault(aid,uid);
    }

    @Override
    public void delete(int id) {
        addressDao.delete(id);
    }

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }

    @Override
    public Address findById(int id) {
        return addressDao.findById( id);
    }
}
