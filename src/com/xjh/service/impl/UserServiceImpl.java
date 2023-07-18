package com.xjh.service.impl;

import com.xjh.dao.Impl.UserDaoImpl;
import com.xjh.dao.UserDao;
import com.xjh.domain.User;
import com.xjh.service.UserService;
import com.xjh.utils.EmailUtils;
import com.xjh.utils.Md5Utils;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public void register(User user) {
        //密码加密
        user.setPassword(Md5Utils.md5(user.getPassword()));
        userDao.add(user);
        //发送邮件
        System.out.println("user:"+user);
        EmailUtils.sendEmail(user);

    }

    //Service层校验输入的用户名是否存在
    @Override
    public User checUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User login(String username, String password) {
        //sql中密码加密 需要解密
        password=Md5Utils.md5(password);
        return userDao.findByUserNameAndPassword(username,password);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void deleteUserById(int id) {
        userDao.delete(id);
    }

    @Override
    public List<User> findUserBySearch(String search) {
        return userDao.findUserBySearch(search);
    }

    @Override
    public User loginAdmin(String username, String password) {
        return userDao.findByUserNameAndPasswordAdmin(username,password);
    }

    @Override
    public void updateUserByidAdmin(String username,String password, String email, String gender, int role, int flag, int id) {
        //如果密码没有更改 不加密
        User user = userDao.findUserById(id);
        if (!password.equals(user.getPassword())){
            //密码不等于取得密码  密码修改过
            //md5加密
            password=Md5Utils.md5(password);
        }
        userDao.updateUserByidAdmin(username,password, email, gender, role,flag,id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public void activateUser(String email) {
        userDao.activateUser(email);
    }

    @Override
    public User findUserById(Integer uid) {
        return userDao.findUserById(uid);
    }
}
