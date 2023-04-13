package com.yhk.filter;

import com.yhk.utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;
import java.rmi.RemoteException;

public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try{
            //所有业务都要经过该filter过滤器
            //所有业务的异常都要抛向该过滤器
            filterChain.doFilter(servletRequest, servletResponse);//执行业务
            //业务成功执行后提交数据
            JdbcUtils.commitAndClose();
        }catch (Exception e){
            //发现异常，进行回滚
            JdbcUtils.rollbackAndClose();
            e.printStackTrace();
            //给服务器抛出异常，跳转到错误页面
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
