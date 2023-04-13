package com.yhk.web;

import com.yhk.pojo.Cart;
import com.yhk.pojo.User;
import com.yhk.serivce.OrderService;
import com.yhk.serivce.impl.OrderServiceImpl;
import com.yhk.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderServlet extends BaseServlet {

    private OrderService orderService=new OrderServiceImpl();
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session域中获取cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //从session域中获取loginUser对象
        User user = (User) req.getSession().getAttribute("loginUser");
        if(user==null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            return;
        }
        Integer userId=user.getId();

        String orderId= orderService.createOrder(cart, userId);
        JdbcUtils.commitAndClose();//提交事务


        //req.setAttribute("orderId", orderId);
        req.getSession().setAttribute("orderId",orderId);
//        req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req,resp);
        //应使用重定向，防止重复提交
        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }
}
