package com.yhk.web;

import com.google.gson.Gson;
import com.yhk.pojo.Book;
import com.yhk.pojo.Cart;
import com.yhk.pojo.CartItem;
import com.yhk.serivce.BookService;
import com.yhk.serivce.impl.BookServiceImpl;
import com.yhk.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BaseServlet {

    private BookService bookService=new BookServiceImpl();


    /**
     * 加入购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取id
        Integer id = WebUtils.parseInt(req.getParameter("id"),0);
        //根据id查找图书信息
        Book book = bookService.queryBookById(id);
        //将图书信息保存在购物车中
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //需要将cart对象保存到session域中，不能每次加入购物车就创建一个cart
        Cart cart= (Cart) req.getSession().getAttribute("cart");
        if(cart==null){
            cart=new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);

        //保存最后添加的图书名称
        req.getSession().setAttribute("lastName",cartItem.getName());
        //返回购物车总商品数量和最后一个添加的商品名称
        Map<String,Object> resMap=new HashMap<>();
        resMap.put("totalCount",cart.getTotalCount());
        resMap.put("lastName",cartItem.getName());
        //设置响应字符集
        resp.setContentType("text/html;charset=UTF-8");
        //创建Gson对象
        Gson gson=new Gson();
        String json=gson.toJson(resMap);
        resp.getWriter().write(json);
    }

    /**
     * 删除图书
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取要删除的图书id
        Integer id = WebUtils.parseInt(req.getParameter("id"), 0);
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart!=null){
            //根据Id删除商品项
            cart.deleteItem(id);
            //重定向到购物车界面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }


    /**
     * 清空购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void clearCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session域中获取购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart!=null){
            cart.clearCart();
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 修改商品数量
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = WebUtils.parseInt(req.getParameter("id"), 0);
        Integer count = WebUtils.parseInt(req.getParameter("count"), 1);
        //获取session域中的cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            cart.updateCount(id, count);
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }
}
