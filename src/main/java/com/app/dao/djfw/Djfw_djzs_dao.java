package com.app.dao.djfw;

import com.app.model.djfw.Djfw_djzs_data;
import com.app.utils.DateUtil;
import com.app.utils.JdbcPool;
import com.app.utils.PageBean;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/26.
 * 党建助手 | 党建服务 功能相关数据操作
 */
public class Djfw_djzs_dao {

    //获取数据库中的所有数据
    /*
    status=0表示还没有回复的信息
    status=1表示已经回复了的信息
    status=2表示已经删除的信息
     */
    public List<Djfw_djzs_data> getDatas(Connection con, PageBean pageBean,int status)throws Exception{
        List<Djfw_djzs_data> datas = new ArrayList<Djfw_djzs_data>();
        StringBuffer sql = new StringBuffer("select * from djfw_djzs_ask");
        //按status的不同值进行查询
        if(status==1||status==2||status==0){
            sql.append(" WHERE status="+status);
        }else{
            return null;
        }

        sql.append(" order by timestamp desc");
        if(pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }

        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Djfw_djzs_data data = new Djfw_djzs_data();
            data.setId(rs.getInt("id"));
            data.setUserId(rs.getInt("userId"));
            data.setTitle(rs.getString("title"));
            data.setContent(rs.getString("content"));
            data.setResponse(rs.getString("response"));
            data.setTimestamp(DateUtil.cutString(rs.getString("timestamp")));
            data.setStatus(rs.getInt("status"));
            data.setUserName(rs.getString("userName"));
            datas.add(data);
        }
        return datas;
    }

    //用户通过app端发布消息，存入数据库中。
    public int insertDate(Connection con,Djfw_djzs_data data)throws Exception{
        String sql = "insert into djfw_djzs_ask(userId,title,content,userName) VALUES (?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,data.getUserId());
        pstmt.setString(2,data.getTitle());
        pstmt.setString(3,data.getContent());
        pstmt.setString(4,getUserNameById(con,data.getUserId()));
        return pstmt.executeUpdate();
    }

    //查找指定状态的数据的总数
    public int getDatasCount(Connection con ,int status) throws Exception{
        String sql = "select count(*) as counts from djfw_djzs_ask WHERE status=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,status);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }


    //更新一条信息
    public int updateDatas(Connection con,Djfw_djzs_data data)throws Exception{
        StringBuffer sql = new StringBuffer("update djfw_djzs_ask set");
        //status=1表示添加回复
        if(data.getStatus()==1){
            sql.append(" response='"+data.getResponse()+"',status="+data.getStatus()+" where id="+data.getId());
        }else if(data.getStatus()==2){
            sql.append(" status="+data.getStatus()+" where id="+data.getId());//status=2表示删除该条信息
        }else {
            throw new Exception();
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        return pstmt.executeUpdate();
    }

    //按指定Id获取数据
    public Djfw_djzs_data getDataById(Connection con,int id)throws Exception{
        StringBuffer sql = new StringBuffer("select * from djfw_djzs_ask where id="+id);
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        Djfw_djzs_data data = null;
        if (rs.next()){
            data = new Djfw_djzs_data();
            data.setId(rs.getInt("id"));
            data.setUserId(rs.getInt("userId"));
            data.setTitle(rs.getString("title"));
            data.setContent(rs.getString("content"));
            data.setResponse(rs.getString("response"));
            data.setTimestamp(DateUtil.cutString(rs.getString("timestamp")));
            data.setStatus(rs.getInt("status"));
            data.setUserName(rs.getString("userName"));
        }
        return data;
    }


    //根据id获取userName
    public String getUserNameById(Connection con,int userId)throws Exception{
        String sql = "select name from user where id="+userId;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getString("name");
        }
        return null;

    }

    //根据id获取用户的alias
    public String getUserAliasById(Connection con,int id)throws Exception{
        String sql1 = "select userId from djfw_djzs_ask where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql1);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            int userId = rs.getInt("userId");
            String sql2 = "select alias from user where id="+userId;
            pstmt = con.prepareStatement(sql2);
            rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getString("alias");
            }
        }
        return null;
    }

    //删除指定id的数据
    public int deleteDataById(Connection con,int id)throws Exception{
        String sql = "delete from djfw_djzs_ask where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

    /*public static void main(String[] args) throws Exception{
        Djfw_djzs_dao dao = new Djfw_djzs_dao();
        JdbcPool pool = JdbcPool.getJdbcPool();
        Connection con = pool.getConnection();
        String alias = dao.getUserAliasById(con,1);
        System.out.print(alias);
    }*/
}
