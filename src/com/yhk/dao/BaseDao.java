package com.yhk.dao;

import com.yhk.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao {
    //使用Dbutils操纵数据库
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * 用来执行insert/update/delete语句
     * @return 如果返回-1，说明执行失败
     */
    public int update(String sql,Object...args){
        Connection connection= JdbcUtils.getConnection();
        try {
            return queryRunner.update(connection, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回一个javaBean对象
     * @param type 返回对象类型
     * @param sql 执行的sql语句
     * @param args sql对应的参数
     * @param <T> 返回类型的泛型
     * @return
     */
    public <T> T queryForOne(Class<T> type,String sql,Object...args){
        Connection connection=JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param type
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T>List<T> queryForList(Class<T> type,String sql,Object...args){
        Connection connection=JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanListHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行返回一行一列的数据，单个数据，只能是基本数据类型，因为数据库的规定
     * @param sql
     * @param args
     * @return
     */
    public Object queryForSingleValue(String sql,Object...args){
        Connection connection=JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new ScalarHandler(),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
