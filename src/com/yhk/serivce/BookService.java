package com.yhk.serivce;

import com.yhk.pojo.Book;
import com.yhk.pojo.Page;

import java.util.List;

public interface BookService {
    //添加图书
    public void addBook(Book book);
    //删除图书
    public void deleteBookById(Integer id);
    //修改图书
    public void updateBook(Book book);
    //查询图书,通过图书编号
    public Book queryBookById(Integer id);
    //查询所有图书
    public List<Book> queryBooks();
    //生成page对象
    Page<Book> page(int pageNo, int pageSize);
    //根据价格区间生成page对象
    Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max);
}
