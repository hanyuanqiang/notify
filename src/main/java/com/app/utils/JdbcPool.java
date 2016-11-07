package com.app.utils;


import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Vector;

/**
 * Created by Administrator on 2016/8/5.
 */
public class JdbcPool {
    private String jdbcDriver = "com.mysql.jdbc.Driver";//数据库驱动
    private String dbUrl = "jdbc:mysql://localhost:3306/notify";//数据库url
    private String dbUsername = "root";//数据库用户名
    private String dbPassword = "123456";//数据库密码
    private String testTable = "shequ";
    private int initialConnectionsNum = 10;//连接池初始连接数
    private int maxConnectionsNum = 50;//连接池最大连接数
    private int incrementalConnections = 5;//每次动态添加的连接数
    private Vector<JdbcPooledConnection> connections = null;//向量，存放连接池中的连接，初始为空
    private static JdbcPool JdbcPool = new JdbcPool("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/notify","root","123456");

    private int num = 0;
    private boolean tag = true;
    private Logger logger = Logger.getLogger(this.getClass());
    public static JdbcPool getJdbcPool() {
        return JdbcPool;
    }

    //该线程主要用于定时让线程池中的连接重新连接数据库
    public Thread t1 = new Thread(new Runnable() {
        public void run() {
            try {
                while (true){
                    Thread.sleep(21600000);
                    JdbcPool.reConnectDB();
                    logger.info("-->>刷新一次线程池<<--");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    /*public static void main(String[] args) {

    }*/



    /*无参构造函数*/
    private JdbcPool()
    {

    }

    /*带参数的构造函数
     * 初始化数据库驱动、数据库url、数据库用户名、数据库密码、测试表
     * */
    private JdbcPool(String driver, String url, String name, String pass)
    {
        this.jdbcDriver = driver;
        this.dbUrl = url;
        this.dbUsername = name;
        this.dbPassword = pass;
        //this.testTable = table;
        try {
            //创建线程池
            this.createJdbcPool();
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }
    }

    /*函数，创建连接池*/
    public synchronized void createJdbcPool()
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException
    {
        /*确保连接池为创建，如果已经创建，则保存连接的向量不为空
         * */
        if (this.connections != null)
        {
            return ;
        }
        //驱动器实例化
        Driver driver = (Driver)(Class.forName(this.jdbcDriver).newInstance());
        //注册驱动器
        DriverManager.registerDriver(driver);
        //创建保存连接的向量
        this.connections = new Vector<JdbcPooledConnection>();
        //创建数据库连接
        this.createConnections(this.initialConnectionsNum);
    }

    /*函数，创建数据库连接
     * */
    private void createConnections (int num) throws SQLException
    {
        /*循环创建连接
         * 需要首先检查当前连接数是否已经超出连接池最大连接数
         * */
        for (int i = 0; i < num; ++i)
        {
            //检查
            if (this.connections.size() >= this.maxConnectionsNum)
            {
                return;
            }
            //创建连接
            this.connections.addElement
                    (new JdbcPooledConnection(newConnection()));
        }

    }

    /*函数,创建一个数据库连接*/
    private Connection newConnection() throws SQLException
    {
        /*创建连接*/
        Connection con = DriverManager.getConnection(this.dbUrl,
                this.dbUsername, this.dbPassword);
        /*如果是第一次创建连接，则检查所连接的数据库的允许最大连接数是否小于
         * 我们所设定的最大连接数*/
        if (this.connections.size() == 0)
        {
            DatabaseMetaData metadata = con.getMetaData();
            //得到数据库最大连接数
            int dbMaxConnectionsNum = metadata.getMaxConnections();
            //如果数据库最大连接数更小，则更改我们所设定的连接池最大连接数
            if (dbMaxConnectionsNum > 0
                    && this.maxConnectionsNum > dbMaxConnectionsNum)
            {
                this.maxConnectionsNum = dbMaxConnectionsNum;
            }
        }

        if (tag){
            t1.start();
            logger.info("开启定时重连Connection线程");
        }
        tag=false;
        logger.info("当前连接池中Connection的数量为："+num++);
        return con;
    }

    /*函数，得到一个可用连接
     * */
    public synchronized Connection getConnection ()
    {
        Connection con = null;
        /*检查连接池是否已经建立*/
        if (this.connections == null)
        {
            return con;
        }
        //得到一个可用连接
        try {
            con = this.getFreeConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //如果未找到合适连接，循环等待、查找，知道找到合适连接
        while(con == null)
        {
            this.wait(30);
            try {
                con = this.getFreeConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return con;
    }


    /*函数，得到一个可用连接*/
    private Connection getFreeConnection() throws SQLException
    {
        Connection con = null;
        //查找一个可用连接
        con = this.findFreeConnection();
        //如果未找到可用连接，就建立一些新的连接，再次查找
        if (con == null)
        {
            this.createConnections(this.incrementalConnections);
            //再次查找
            con = this.findFreeConnection();
        }
        return con;
    }


    /*函数，从现有连接中查找一个可用连接
     * 在现有的连接中（向量connections中）找到一个空闲连接，
     * 并测试这个链接是否可用，若不可用则重新建立连接，替换原来的连接*/
    private Connection findFreeConnection () throws SQLException
    {
        Connection con = null;
        for (int i = 0; i < this.connections.size(); ++i)
        {
            JdbcPooledConnection pol = (JdbcPooledConnection)this.connections.get(i);
            if (!pol.isBusy())
            {
                /*如果此链接未被使用，则返回这个连接并，设置正在使用标志*/
                con = pol.getCon();
                pol.setBusy(true);
                /*测试连接是否可用*/
                if (!this.testCon(con))
                {
                    con = this.newConnection();
                    pol.setCon(con);
                }
                break;
            }
        }
        return con;
    }

    /*函数，测试连接是否可用
     * */
    private boolean testCon (Connection con)
    {
        boolean useable = true;
        try
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(*) from " + this.testTable);
            rs.next();
        }
        catch(SQLException e)
        {
            /*上面抛出异常，连接不可用，关闭*/
            useable = false;
            this.closeConnection(con);
        }
        return useable;
    }

    /*函数，将使用完毕的连接放回连接池中
     * */
    public void returnConnection(Connection con)
    {
        /*确保连接池存在*/
        if (this.connections == null)
        {
            return ;
        }
        for (int i = 0; i < this.connections.size(); ++i)
        {
            JdbcPooledConnection JdbcPool = this.connections.get(i);
            //找到相应连接，设置正在使用标志为false
            if (con == JdbcPool.getCon())
            {
                JdbcPool.setBusy(false);
            }
        }

    }

    /*函数，刷新连接池中的连接*/
    public synchronized void refreshConneciontJdbcPool () throws SQLException
    {
        /*确保连接池存在*/
        if (this.connections == null)
        {
            return ;
        }
        for (int i = 0; i < this.connections.size(); ++i)
        {
            JdbcPooledConnection JdbcPool = this.connections.get(i);
            if (JdbcPool.isBusy())
            {
                this.wait(5000);
            }
            this.closeConnection(JdbcPool.getCon());
            JdbcPool.setCon(this.newConnection());
            JdbcPool.setBusy(false);
        }
    }

    /*函数，关闭连接池*/
    public void closeConnectionJdbcPool()
    {
        /*确保连接池存在*/
        if (this.connections == null)
        {
            return ;
        }
        for (int i = 0; i < this.connections.size(); ++i)
        {
            JdbcPooledConnection JdbcPool = this.connections.get(i);
            if (JdbcPool.isBusy())
            {
                this.wait(5000);
            }
            this.closeConnection(JdbcPool.getCon());
            this.connections.remove(i);
        }
        this.connections = null;
    }

    /*函数，暂时无可用连接，进入等待队列等待m秒，再试
     * */
    private void wait(int mSecond)
    {
        try {
            Thread.sleep(mSecond);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return the jdbcDriver
     */
    public String getJdbcDriver() {
        return jdbcDriver;
    }

    /**
     * @param jdbcDriver the jdbcDriver to set
     */
    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    /**
     * @return the dbUrl
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * @param dbUrl the dbUrl to set
     */
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * @return the dbUsername
     */
    public String getDbUsername() {
        return dbUsername;
    }

    /**
     * @param dbUsername the dbUsername to set
     */
    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    /**
     * @return the dbPassword
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * @param dbPassword the dbPassword to set
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     * @return the testTable
     */
    public String getTestTable() {
        return testTable;
    }

    /**
     * @param testTable the testTable to set
     */
    public void setTestTable(String testTable) {
        this.testTable = testTable;
    }

    /**
     * @return the initialConnectionsNum
     */
    public int getInitialConnectionsNum() {
        return initialConnectionsNum;
    }

    /**
     * @param initialConnectionsNum the initialConnectionsNum to set
     */
    public void setInitialConnectionsNum(int initialConnectionsNum) {
        this.initialConnectionsNum = initialConnectionsNum;
    }

    /**
     * @return the maxConnectionsNum
     */
    public int getMaxConnectionsNum() {
        return maxConnectionsNum;
    }

    /**
     * @param maxConnectionsNum the maxConnectionsNum to set
     */
    public void setMaxConnectionsNum(int maxConnectionsNum) {
        this.maxConnectionsNum = maxConnectionsNum;
    }

    /**
     * @return the incrementalConnections
     */
    public int getIncrementalConnections() {
        return incrementalConnections;
    }

    /**
     * @param incrementalConnections the incrementalConnections to set
     */
    public void setIncrementalConnections(int incrementalConnections) {
        this.incrementalConnections = incrementalConnections;
    }

    /**
     * @return the connections
     */
    public Vector<JdbcPooledConnection> getConnections() {
        return connections;
    }

    /**
     * @param connections the connections to set
     */
    public void setConnections(Vector<JdbcPooledConnection> connections) {
        this.connections = connections;
    }

    /*函数，连接使用完毕，关闭连接*/
    private void closeConnection (Connection con)
    {
        try
        {
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    //线程池中的connection中重新连接数据库
    private void reConnectDB(){
        try {
            for (int i = 0; i < this.connections.size(); ++i)
            {
                JdbcPooledConnection jdbcPooledConnection = this.connections.get(i);
                Connection conn = jdbcPooledConnection.con;
                //如果当前连接没有使用，则测试连接，防止断开
                if (!jdbcPooledConnection.isBusy()){
                    JdbcPool.testCon(conn);
                    JdbcPool.returnConnection(conn);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /*内部使用的保存数据库连接的类
     * 两个成员变量：连接、是否正在使用*/
    class JdbcPooledConnection
    {
        private Connection con = null;//连接
        private boolean busy = false;//是否正在使用，默认为非

        /*构造函数*/
        public JdbcPooledConnection(Connection con)
        {
            this.con = con;
        }

        /**
         * @return the con
         */
        public Connection getCon() {
            return con;
        }

        /**
         * @param con the con to set
         */
        public void setCon(Connection con) {
            this.con = con;
        }

        /**
         * @return the busy
         */
        public boolean isBusy() {
            return busy;
        }

        /**
         * @param busy the busy to set
         */
        public void setBusy(boolean busy) {
            this.busy = busy;
        }
    }

}
