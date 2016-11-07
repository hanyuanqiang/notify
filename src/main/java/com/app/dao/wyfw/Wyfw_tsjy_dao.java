package com.app.dao.wyfw;

import com.app.model.wyfw.Wyfw_tsjy;
import com.app.utils.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
public class Wyfw_tsjy_dao {

    //如果userId=-1表示获取所有的投诉建议信息，否则获取指定业主的所有投诉建议信息
    public List<Wyfw_tsjy> getDatas(Connection con,int userId) throws Exception{
        List<Wyfw_tsjy> datas = new ArrayList<Wyfw_tsjy>();
        //String sql = "select * from wyfw_tsjy order by id desc";
        StringBuffer sql = new StringBuffer("select * from wyfw_tsjy");
        if(userId!=-1){
            sql.append(" where userId="+userId);
        }
        sql.append(" order by id desc");
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs  = pstmt.executeQuery();
        while (rs.next()){
            Wyfw_tsjy tsjy = new Wyfw_tsjy();
            tsjy.setId(rs.getInt("id"));
            tsjy.setUserId(rs.getInt("userId"));
            tsjy.setComplainContent(rs.getString("complainContent"));
            tsjy.setComplainDate(DateUtil.cutString(rs.getString("complainDate")));
            tsjy.setWuyeReplay(rs.getString("wuyeReplay"));
            tsjy.setShequReplay(rs.getString("shequReplay"));
            tsjy.setIsWuyePush(rs.getInt("isWuyePush"));
            tsjy.setIsShequPush(rs.getInt("isShequPush"));
            tsjy.setUserName(rs.getString("userName"));
            datas.add(tsjy);
        }
        return datas;
    }

    //获取指定条件的数据,tag=0表示获取社区相关数据，tag=1表示获取物业相关数据
    public List<Wyfw_tsjy> getDatasByCondition(Connection con,boolean isReplay,int tag) throws Exception{
        List<Wyfw_tsjy> datas = new ArrayList<Wyfw_tsjy>();
        StringBuffer sql = new StringBuffer("select * from wyfw_tsjy");
        if (isReplay){
            if (tag==0){
                sql.append(" where shequReplay!=''");
            }else if (tag==1){
                sql.append(" where wuyeReplay!=''");
            }
        }else {
            if (tag==0){
                sql.append(" where shequReplay is null or shequReplay=''");
            }else if (tag==1){
                sql.append(" where wuyeReplay is null or wuyeReplay=''");
            }
        }
        sql.append(" order by id desc");
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Wyfw_tsjy tsjy = new Wyfw_tsjy();
            tsjy.setId(rs.getInt("id"));
            tsjy.setUserId(rs.getInt("userId"));
            tsjy.setUserName(rs.getString("userName"));
            tsjy.setComplainContent(rs.getString("complainContent"));
            tsjy.setComplainDate(rs.getString("complainDate"));
            tsjy.setWuyeReplay(rs.getString("wuyeReplay"));
            tsjy.setShequReplay(rs.getString("shequReplay"));
            tsjy.setIsWuyePush(rs.getInt("isWuyePush"));
            tsjy.setIsShequPush(rs.getInt("isShequPush"));
            datas.add(tsjy);
        }
        return datas;
    }



    //插入一条投诉建议
    public int insertData(Connection con,Wyfw_tsjy tsjy)throws Exception{
        String sql = "insert into wyfw_tsjy(userId,complainContent,userName) values(?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,tsjy.getUserId());
        pstmt.setString(2,tsjy.getComplainContent());
        pstmt.setString(3,tsjy.getUserName());
        return pstmt.executeUpdate();
    }

    //获取指定id的投诉建议信息
    public Wyfw_tsjy getDataById(Connection con,int id)throws Exception{
        String sql = "select * from wyfw_tsjy where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Wyfw_tsjy tsjy = null;
        if (rs.next()){
            tsjy = new Wyfw_tsjy();
            tsjy.setId(rs.getInt("id"));
            tsjy.setUserId(rs.getInt("userId"));
            tsjy.setUserName(rs.getString("userName"));
            tsjy.setComplainContent(rs.getString("complainContent"));
            tsjy.setComplainDate(rs.getString("complainDate"));
            tsjy.setWuyeReplay(rs.getString("wuyeReplay"));
            tsjy.setShequReplay(rs.getString("shequReplay"));
            tsjy.setIsWuyePush(rs.getInt("isWuyePush"));
            tsjy.setIsShequPush(rs.getInt("isShequPush"));
        }
        return tsjy;
    }


    //param=1表示物业人员对投诉建议进行回复
    //param=2表示社区人员对投诉建议进行回复
    public int updateData(Connection con,Wyfw_tsjy tsjy,int param)throws  Exception{
        StringBuffer sql = new StringBuffer("update wyfw_tsjy set");
        if(param==1){
            sql.append(" wuyeReplay=?");
        }else if(param==2){
            sql.append(" shequReplay=?");
        }
        sql.append(" where id="+tsjy.getId());
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        if(param==1){
            pstmt.setString(1,tsjy.getWuyeReplay());
        }else if(param==2){
            pstmt.setString(1,tsjy.getShequReplay());
        }
        return pstmt.executeUpdate();
    }

    //根据id获取用户的alias
    public String getUserAliasById(Connection con,int id)throws Exception{
        String sql1 = "select userId from wyfw_tsjy where id="+id;
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

    //删除指定id的投诉建议信息
    public int deleteDataById(Connection con,int id)throws Exception{
        String sql = "delete from wyfw_tsjy where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

}
