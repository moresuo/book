package com.yhk.web;

import com.yhk.pojo.User;
import com.yhk.serivce.UserSerivce;
import com.yhk.serivce.impl.UserSerivceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistServlet extends HttpServlet {
    //web层获取用户输入的参数，然后调用业务层方法实现业务
    private UserSerivce userSerivce=new UserSerivceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、获取请求参数,得到表单项的name值
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");
        //2、验证码是否正确-----先写死，要求为‘abcde’
        if("abcde".equalsIgnoreCase(code)){
            //检查用户名是否可用
            if(userSerivce.existsUsername(username)){
                //存在，不可用
                System.out.println("用户名[" + username + "]已存在");
                //回显错误信息
                req.setAttribute("msg", "用户名以存在");
                req.setAttribute("username",username);
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }else{
                //不存在，可用
                userSerivce.registUser(new User(null, username, password, email));
                //跳转到注册成功页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);

            }
        }else{
            //把回显信息保存到request域中
            req.setAttribute("msg","验证码错误");
            //回显用户名和邮箱
            req.setAttribute("username", username);
            req.setAttribute("email",email);
            //验证码不正确，调回注册页面
            System.out.println("验证码["+code+"]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }


    }
}
