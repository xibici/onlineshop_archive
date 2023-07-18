package com.xjh.utils;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/*
 * xjh 2020/3/10 14:18
 * 佛祖保佑，永无BUG!
 */
public class DataSourceUtils {
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> threadLocal;
    static {
        try {
            threadLocal=new ThreadLocal<>();
            Properties properties=new Properties();
            InputStream is=DataSourceUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(is);
            is.close();
            dataSource= (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化连接池失败");
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

    //获取连接
    public static Connection getConnection() throws Exception{
        Connection conn = threadLocal.get();
        if(conn==null){
            conn  = dataSource.getConnection();
            threadLocal.set(conn);
        }
        return conn;
    }
    //开启事物
    public static void startTransaction() throws Exception{
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }
    //提交
    public static void commit() throws Exception{
        Connection conn = getConnection();
        conn.commit();
    }
    //回滚操作
    public static void rollback() throws Exception{
        Connection conn = getConnection();
        conn.rollback();
    }
    //关闭连接
    public static void close() throws Exception{
        Connection conn = getConnection();
        conn.close();
        threadLocal.remove();
    }

}
