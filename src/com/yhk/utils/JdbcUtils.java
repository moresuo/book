package com.yhk.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource dataSource;
    //使用ThreadLocal保存某个线程的一个特定数据，在这个线程当中的任何位置，任何时刻，都可以访问该数据
    //可以使用ThreadLocal保存数据库连接，为了实现事务功能所有业务必须共享同一个数据库连接
    private static ThreadLocal<Connection> conn=new ThreadLocal<>();
    public static void main(String[] args) {

    }
    static{

        try {
            Properties properties = new Properties();
            //读取jdbc.properties属性配置文件
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //从流中加载数据
            properties.load(inputStream);
            dataSource= (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取数据库连接
     * @return 如果返回null,说明获取连接失败
     */
    public static Connection getConnection(){
        Connection connection = conn.get();
        if(connection==null){
            try {
                connection= dataSource.getConnection();
                conn.set(connection);
                connection.setAutoCommit(false);//关闭自动提交
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    /**
     * 提交并关闭数据库
     */
    public static void commitAndClose(){
        Connection connection=conn.get();
        if(connection!=null){
            try {
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        conn.remove();
    }

    /**
     * 回滚并关闭数据库
     */
    public static void rollbackAndClose(){
        Connection connection=conn.get();
        if(connection!=null){
            try {
                connection.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        conn.remove();//一定要执行remove操作
    }

}
