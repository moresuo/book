package com.yhk.web;

import com.yhk.pojo.User;
import com.yhk.serivce.UserSerivce;
import com.yhk.serivce.impl.UserSerivceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends BaseServlet {
    private UserSerivce userSerivce=new UserSerivceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //1、获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //2、调用Service处理业务
        User login = userSerivce.login(new User(null, username, password, null));
        if(login==null){
            //登陆失败
            //把表单项信息保存到request域中
            req.setAttribute("msg","用户名或密码错误");
            req.setAttribute("username",username);
            //跳回登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        }else{
            //登陆成功
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }

    }
}
