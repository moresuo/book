package com.yhk.dao;

import com.yhk.pojo.Book;

import java.util.List;

public interface BookDao {
    //添加图书信息至数据库表
    public int addBook(Book book);
    //从数据库表中删除图书信息,通过图书编号
    public int deleteBookById(Integer id);
    //修改图书信息
    public int updateBook(Book book);
    //查询图书,通过图书编号,查询单个图书信息
    public Book queryBookById(Integer id);
    //查询多条图书信息
    public List<Book> queryBooks();
    //查询总记录数
    Integer queryForPageTotalCount();
    //查询当前页数据
    List<Book> queryForPageItems(int begin, int pageSize);
    //获取价格区间内的条数
    Integer queryForPageTotalCountByPrice(int min, int max);
    //获取在价格区间的图书信息
    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);
}
