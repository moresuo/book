package com.yhk.web;

import com.yhk.pojo.Book;
import com.yhk.pojo.Page;
import com.yhk.serivce.BookService;
import com.yhk.serivce.impl.BookServiceImpl;
import com.yhk.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientBookServlet extends BaseServlet {
    private BookService bookService=new BookServiceImpl();

    /**
     * 处理分页功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数pageNo和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);//默认第一页
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);//默认是4条，但最后一个可能不是4条
        //调用bookService.page(pageNo,pageSize):生成Page对象
        Page<Book> page = bookService.page(pageNo, pageSize);
        //设置url
        page.setUrl("client/bookServlet?action=page");
        //将生成的page对象保存到request域中
        req.setAttribute("page", page);
        //请求转发
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }


    /**
     * 根据价格范围获取分页信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int min = WebUtils.parseInt(req.getParameter("min"), 0);//最小价格
        int max = WebUtils.parseInt(req.getParameter("max"), Integer.MAX_VALUE);//最大价格
        //获取请求参数pageNo和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);//默认第一页
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);//默认是4条，但最后一个可能不是4条
        //调用bookService.page(pageNo,pageSize):生成Page对象
        Page<Book> page = bookService.pageByPrice(pageNo, pageSize,min,max);
        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");
        if(req.getParameter("min")!=null){
            sb.append("&min=").append(req.getParameter("min"));
        }
        if(req.getParameter("max")!=null){
            sb.append("&max=").append(req.getParameter("max"));
        }
        //设置url
        page.setUrl(sb.toString());
        //将生成的page对象保存到request域中
        req.setAttribute("page", page);
        //请求转发
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }
}
