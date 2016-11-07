package com.app.listener;

import com.app.utils.ExceptionUtil;
import com.app.utils.JdbcPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by Administrator on 2016/8/6.
 */
@WebListener
public class ApplicationListener implements ServletContextListener{
    private Logger logger = Logger.getLogger(this.getClass().getName());
    JdbcPool pool = null;
    //启动服务器时调用
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("启动应用");
        pool = JdbcPool.getJdbcPool();
    }

    //关闭服务器时调用
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        pool.t1.interrupt();
        logger.info("额外线程关闭");
    }
}
