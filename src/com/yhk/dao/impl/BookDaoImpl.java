package com.yhk.dao.impl;

import com.yhk.dao.BaseDao;
import com.yhk.dao.BookDao;
import com.yhk.pojo.Book;

import java.util.List;

public class BookDaoImpl extends BaseDao implements BookDao {
    @Override
    public int addBook(Book book) {
        //添加图书sql语句
        String sql = "insert into t_book(id,name,author,price,sales,stock,img_path) values(?,?,?,?,?,?,?)";
        return update(sql,null,book.getName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath());
    }

    @Override
    public int deleteBookById(Integer id) {
        //删除图书sql语句
        String sql="delete from t_book where id=?";
        return update(sql,id);
    }

    @Override
    public int updateBook(Book book) {
       //更新图书信息
        String sql="update t_book set name=?,author=?,price=?,sales=?,stock=?,img_path=? where id=?";
        return update(sql,book.getName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath(),book.getId());
    }

    @Override
    public Book queryBookById(Integer id) {
       //查询图书
        String sql="select id,name,author,price,sales,stock,img_path from t_book where id=?";
        return queryForOne(Book.class,sql,id);
    }

    @Override
    public List<Book> queryBooks() {
        //查询全部图书
        String sql="select id,name,author,price,sales,stock,img_path from t_book";
        return queryForList(Book.class,sql);
    }

    @Override
    public Integer queryForPageTotalCount() {
        //查询总记录数
        String sql="select count(*) from t_book";
        Number count = (Number) queryForSingleValue(sql);
        return count.intValue();
    }

    @Override
    public List<Book> queryForPageItems(int begin, int pageSize) {
        String sql = "select id,name,author,price,sales,stock,img_path from t_book limit ?,?";
        List<Book> books = queryForList(Book.class, sql, begin, pageSize);
        return books;
    }

    @Override
    public Integer queryForPageTotalCountByPrice(int min, int max) {
        String sql="select count(*) from t_book where price between ? and ?";
        Number count = (Number) queryForSingleValue(sql, min, max);
        return count.intValue();
    }

    @Override
    public List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max) {
        String sql="select id,name,author,price,sales,stock,img_path from t_book where price between ? and ? limit ?,?";
        List<Book> books = queryForList(Book.class, sql, min, max, begin, pageSize);
        return books;
    }
}
