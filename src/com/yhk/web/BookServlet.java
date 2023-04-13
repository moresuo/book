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
import java.util.List;

public class BookServlet extends BaseServlet {
    private BookService bookService=new BookServiceImpl();

    /**
     * 添加图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        //从页面获取图书信息封装到一个Book对象
        Book book= WebUtils.copyParamToBean(req.getParameterMap(),new Book());
        //向数据库添加book
        bookService.addBook(book);
        //添加之后从新返回列表页面，刷新数据
        //不能使用从定向，从定向是一次请求，页面刷新会再次添加
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+(pageNo+1));
    }
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        //获取请求删除的id
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //根据id删除图书
        bookService.deleteBookById(id);
        //返回列表页面，刷新图书
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+pageNo);
    }

    /**
     * 根据id获取book
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取书籍id
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //根据Id查找书籍
        Book book = bookService.queryBookById(id);
        //把book对象存入request域
        req.setAttribute("book", book);
        //请求转发到修改页面，显示当前要修改的书籍
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        //将修改的图书信息封装成一个信息book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());
        //根据book的id修改图书
        bookService.updateBook(book);
        //修改完毕后，从定向到列表展示页面
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+pageNo);
    }

    /**
     * 获取数据库中所有图书信息，展示在页面上
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = bookService.queryBooks();
        req.setAttribute("books", books);
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

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
        page.setUrl("manager/bookServlet?action=page");
        //将生成的page对象保存到request域中
        req.setAttribute("page", page);
        //请求转发
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }


}
