package com.yhk.web;

import com.google.gson.Gson;
import com.yhk.pojo.User;
import com.yhk.serivce.UserSerivce;
import com.yhk.serivce.impl.UserSerivceImpl;
import com.yhk.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {
    private UserSerivce userSerivce=new UserSerivceImpl();

    /**
     * 注销用户信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //销毁session中的数据
        req.getSession().invalidate();
        //重定向到首页
        resp.sendRedirect(req.getContextPath());
    }
    /**
     * 处理登录功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //登录代码
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
            //将用户名保存到session域中，方便在各个页面显示
            req.getSession().setAttribute("loginUser",login);
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }

    /**
     * 处理注册功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //注册代码
        //获取验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //将session中的验证码删除
        req.getSession().invalidate();
        //1、获取请求参数,得到表单项的name值
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");
        User user=  WebUtils.copyParamToBean(req.getParameterMap(),new User());
        //2、验证码是否正确-----先写死，要求为‘abcde’
        if(token!=null&&token.equalsIgnoreCase(code)){
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

    protected void existsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户名
        String username=req.getParameter("username");
        //验证用户是否存在
        boolean isExist = userSerivce.existsUsername(username);
        Map<String,Object> res=new HashMap<>();
        res.put("res",isExist);
        //生成Gson对象
        Gson gson=new Gson();
        //将gson对象转为json对象
        String json = gson.toJson(res);
        resp.getWriter().write(json);
    }

}
