package com.yhk.dao.impl;

import com.yhk.dao.BaseDao;
import com.yhk.dao.UserDao;
import com.yhk.pojo.User;

public class UserDaoImpl extends BaseDao implements UserDao {
    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
    @Override
    public User queryUserByUsername(String name) {
        String sql="select id,username,password,email from t_user where username=?";
        return queryForOne(User.class,sql,name);
    }

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @Override
    public int saveUser(User user) {
       String sql="insert into t_user(username,password,email) values(?,?,?)";
       return update(sql,user.getUsername(),user.getPassword(),user.getEmail());
    }

    /**
     * 根据用户名和密码查询用户信息，用于登录功能
     * @param username
     * @param password
     * @return
     */
    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        String sql = "select id,username,password,email from t_user where username=? and password=?";
        return queryForOne(User.class,sql,username,password);
    }
}
