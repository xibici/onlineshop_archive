package com.xjh.dao.Impl;

import com.xjh.dao.UserDao;
import com.xjh.domain.User;
import com.xjh.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<User> findAll() {
        try {
            return template.query("select * from tb_user",new BeanPropertyRowMapper<User>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询用户失败",e);
        }
    }

    @Override
    public User findById(Integer id) {
        User user=null;
        try {
            user=template.queryForObject("select * from tb_user where id= ?",new BeanPropertyRowMapper<User>(User.class),id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据id查询用户失败",e);
        }
        return user;
    }


    @Override
    public User findByUserNameAndPassword(String username, String password) {
        User user=null;
        try {
            user=template.queryForObject("select * from tb_user where username= ? and password = ?",new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (Exception e) {

        }
        return user;
    }

    //Dao层查询注册输入的用户名是否存在
    @Override
    public User findByUserName(String username) {
        User user=null;
        try {
            user= template.queryForObject("select * from tb_user where username= ? ",new BeanPropertyRowMapper<User>(User.class),username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据用户名查询用户失败",e);
        }
        return user;
    }

    @Override
    public void add(User user) {
        Object[] params={user.getUsername(),user.getPassword(),user.getEmail(),user.getGender(),user.getFlag(),user.getRole(),user.getCode()};
        try {
            template.update("insert into tb_user(username,password,email,gender,flag,role,code)values (?,?,?,?,?,?,?)",params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加用户失败",e);
        }
    }

    @Override
    public void update(User user) {
        Object[] params={user.getUsername(),user.getPassword(),user.getEmail(),user.getGender(),user.getFlag(),user.getId()};
        try {
            template.update("update tb_user set username=?,password=?,email=?,gender=?,flag=? where id = ?",params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新用户失败",e);
        }
    }

    @Override
    public void delete(int id) {
          try {
            template.update("delete from tb_user where id=?",id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除用户失败",e);
        }
    }

    @Override
    public List<User> findUserBySearch(String search) {
        //模糊查询
        search="%"+search+"%";

        return template.query("select * from tb_user where username like ?",new BeanPropertyRowMapper<User>(User.class),search);
    }

    @Override
    public User findByUserNameAndPasswordAdmin(String username, String password) {
        User user=null;
        try {
            user=template.queryForObject("SELECT * FROM tb_user WHERE username=? AND PASSWORD=?",
                    new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    //管理员
    @Override
    public void updateUserByidAdmin(String username,String password, String email, String gender, int role, int flag, int id) {
        template.update("update tb_user set username =?,password=?,email=?,gender=?,role=?,flag=?  where id=?"
                , username, password,email, gender,role,flag,id);

    }


    @Override
    public User findUserByEmail(String email) {
        return template.queryForObject("select * from tb_user where email =?",new BeanPropertyRowMapper<User>(User.class),email);
    }

    @Override
    public void activateUser(String email) {
        template.update("update tb_user set flag =?  where email=?",1,email);

    }

    @Override
    public User findUserById(Integer id) {
        return template.queryForObject("select * from tb_user where id=?",new BeanPropertyRowMapper<User>(User.class),id);
    }
}
