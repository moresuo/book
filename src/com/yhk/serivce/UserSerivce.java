package com.yhk.serivce;

import com.yhk.pojo.User;

public interface UserSerivce {
    /**
     * 注册用户
     * @param user
     */
    public void registUser(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    public User login(User user);

    /**
     * 检查用户名是否可用
     * @param username
     * @return 返回true表示用户名已存在，否则不存在
     */
    public boolean existsUsername(String username);
}
