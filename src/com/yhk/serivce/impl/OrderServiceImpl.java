package com.yhk.serivce.impl;

import com.yhk.dao.BookDao;
import com.yhk.dao.OrderDao;
import com.yhk.dao.OrderItemDao;
import com.yhk.dao.impl.BookDaoImpl;
import com.yhk.dao.impl.OderDaoImpl;
import com.yhk.dao.impl.OrderItemDaoImpl;
import com.yhk.pojo.*;
import com.yhk.serivce.OrderService;

import java.util.Date;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao=new OderDaoImpl();
    private OrderItemDao orderItemDao=new OrderItemDaoImpl();
    private BookDao bookDao=new BookDaoImpl();
    @Override
    public String createOrder(Cart cart, Integer userId) {
        //订单号唯一
        String order_id = System.currentTimeMillis() + "" + userId;
        //创建订单对象
        Order order = new Order(order_id, new Date(), cart.getTotalPrice(), 0, userId);
        orderDao.saveOrder(order);
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            CartItem cartItem = entry.getValue();
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), order_id);
            //保存进数据库
            orderItemDao.saveOrderItem(orderItem);
            //每件商品生成订单，对应库存减一，销售加一
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setStock(book.getStock() - cartItem.getCount());
            book.setSales(book.getSales() + cartItem.getCount());
            //修改图书信息
            bookDao.updateBook(book);
        }
        //结账之后生成购物订单,清空购物车
        cart.clearCart();

        return order_id;

    }
}
