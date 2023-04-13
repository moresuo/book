package com.yhk.dao;

import com.yhk.pojo.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     *
     * @param name
     * @return 如果返回null, 说明没有这个用户，反之亦然
     */
    public User queryUserByUsername(String name);

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    public int saveUser(User user);

    /**
     * 根据用户名和密码查询数据库信息，用于登陆功能
     * @param username
     * @param password
     * @return 如果返回null,说明该用户不存在，用户名或密码错误
     */
    public User queryUserByUsernameAndPassword(String username,String password);

}
