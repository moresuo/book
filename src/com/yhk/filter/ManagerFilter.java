package com.yhk.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ManagerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取session域中的user对象
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        Object user = httpServletRequest.getSession().getAttribute("loginUser");
        if(user==null){
            httpServletRequest.getRequestDispatcher("/pages/user/login.jsp").forward(servletRequest, servletResponse);
        }else{
            //放行
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
