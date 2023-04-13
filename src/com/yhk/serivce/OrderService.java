package com.yhk.serivce;

import com.yhk.pojo.Cart;

public interface OrderService {
    //生成订单
    String createOrder(Cart cart,Integer userId);
}
