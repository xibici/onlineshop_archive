package com.xjh.dao;

import com.xjh.domain.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findById(Integer id);

    User findByUserNameAndPassword(String username, String password);

    User findByUserName(String username);

    void add(User user);

    void update(User user);

    void delete(int id);

    List<User> findUserBySearch(String search);

    User findByUserNameAndPasswordAdmin(String username, String password);

    void updateUserByidAdmin(String username, String password, String email, String gender, int role, int flag, int id);

    User findUserByEmail(String email);

    void activateUser(String email);

    User findUserById(Integer id);



}