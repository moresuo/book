package com.yhk.serivce.impl;

import com.yhk.dao.BookDao;
import com.yhk.dao.impl.BookDaoImpl;
import com.yhk.pojo.Book;
import com.yhk.pojo.Page;
import com.yhk.serivce.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDao bookDao=new BookDaoImpl();

    /**
     * 添加一本书籍
     * @param book
     */
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    /**
     * 根据id删除书籍
     * @param id
     */
    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    /**
     * 修改图书信息
     * @param book
     */
    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    /**
     * 根据id查找数据
     * @param id
     * @return
     */
    @Override
    public Book queryBookById(Integer id) {
       return bookDao.queryBookById(id);
    }

    /**
     * 返回所有书籍
     * @return
     */
    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    /**
     * 返回Page对象
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Page<Book> page=new Page<>();
        //设置每页展示数量
        page.setPageSize(pageSize);
        //获取总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCount();
        //设置总记录数
        page.setPageTotalCount(pageTotalCount);
        //获取总页数
        int pageTotal=pageTotalCount/pageSize;
        if(pageTotalCount%pageSize>0){
            pageTotal++;
        }
        //设置总页数
        page.setPageTotal(pageTotal);
        if(pageNo<1){
            pageNo=1;
        }
        if(pageNo>pageTotal){
            pageNo=pageTotal;
        }
        //设置当前页码
        page.setPageNo(pageNo);
        //获取显示页码的起始位置
        int begin=(pageNo-1)*pageSize;
        //获取展示页面的数据
        List<Book> items = bookDao.queryForPageItems(begin, pageSize);
        //设置当前页数据
        page.setItems(items);
        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<Book> page=new Page<>();
        //设置每页展示数量
        page.setPageSize(pageSize);
        //获取总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCountByPrice(min,max);
        //设置总记录数
        page.setPageTotalCount(pageTotalCount);
        //获取总页数
        int pageTotal=pageTotalCount/pageSize;
        if(pageTotalCount%pageSize>0){
            pageTotal++;
        }
        //设置总页数
        page.setPageTotal(pageTotal);
        if(pageNo<1){
            pageNo=1;
        }
        if(pageNo>pageTotal){
            pageNo=pageTotal;
        }
        //设置当前页码
        page.setPageNo(pageNo);
        //获取显示页码的起始位置
        int begin=(pageNo-1)*pageSize;
        //获取展示页面的数据
        List<Book> items = bookDao.queryForPageItemsByPrice(begin, pageSize,min,max);
        //设置当前页数据
        page.setItems(items);
        return page;
    }
}
