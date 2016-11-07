package com.app.test;


import com.app.utils.JdbcPool;

import java.sql.Connection;

/**
 * Created by Administrator on 2016/9/12.
 */
public class JdbcPoolTest {

    public static void main(String[] args) throws Exception{
        JdbcPool pool = JdbcPool.getJdbcPool();
        Connection connection = pool.getConnection();
        System.out.println(connection);
        pool.returnConnection(connection);
        Connection con = pool.getConnection();
        System.out.println(con);
    }
}
