package com.yhk.dao.impl;

import com.yhk.dao.BaseDao;
import com.yhk.dao.OrderDao;
import com.yhk.pojo.Order;

public class OderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql="insert into t_order(order_id,create_time,price,status,user_id) values(?,?,?,?,?)";
        return update(sql,order.getOrderId(),order.getCreateTime(),order.getStatus(),order.getPrice(),order.getUserId());
    }
}
