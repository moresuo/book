package com.yhk.dao;

import com.yhk.pojo.Order;

public interface OrderDao {
    //保存订单
    int saveOrder(Order order);
}
