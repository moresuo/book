package com.yhk.serivce.impl;

import com.yhk.dao.UserDao;
import com.yhk.dao.impl.UserDaoImpl;
import com.yhk.pojo.User;
import com.yhk.serivce.UserSerivce;

public class UserSerivceImpl implements UserSerivce {
    private UserDao userDao=new UserDaoImpl();

    /**
     * 注册用户信息
     * @param user
     */
    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    /**
     * 登录业务
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
       if(userDao.queryUserByUsername(username)==null){
           //等于null说明没查到，说明用户名可用
           return false;
       }
       return true;
    }
}
