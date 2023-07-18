package com.xjh.service;

import com.xjh.domain.User;

import java.util.List;

public interface UserService {
    //1.注册
    void register(User user);
    //2.检查用户名是否存在
    User checUserName(String username);
    //3.登陆
    User login(String username,String password);

    List<User> findAll();

    void deleteUserById(int id);

    List<User> findUserBySearch(String search);

    User loginAdmin(String username, String password);

    void updateUserByidAdmin(String username,String password, String email, String gender, int role, int flag, int id);

    User findUserByEmail(String email);

    void activateUser(String email);


    User findUserById(Integer uid);
}
