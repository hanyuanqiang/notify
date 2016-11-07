package com.app.dao.wyfw;

import com.app.model.wyfw.Wuye_detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2016/6/29.
 */
public class Wuye_detail_dao {

    //获取物业公司的详细信息
    public Wuye_detail getDatas(Connection con) throws Exception{
        Wuye_detail detail = null;
        String sql = "select * from wuye_detail";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            detail = new Wuye_detail();
            detail.setId(rs.getInt("id"));
            detail.setHardset(rs.getString("hardset"));
            detail.setMessage(rs.getString("message"));
            detail.setPicUrl(rs.getString("picUrl"));
            detail.setOthers(rs.getString("others"));
            detail.setPerson(rs.getString("person"));
        }
        return detail;
    }

    //插入一条公司信息
    public int insertData (Connection con,Wuye_detail wuye_detail) throws Exception{
        String sql = "insert into wuye_detail VALUES (null,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        if (wuye_detail.getPicUrl()==null){
            pstmt.setString(1,getPicUrl(con));
        }else {
            pstmt.setString(1,wuye_detail.getPicUrl());
        }
        pstmt.setString(2,wuye_detail.getMessage());
        pstmt.setString(3,wuye_detail.getPerson());
        pstmt.setString(4,wuye_detail.getHardset());
        pstmt.setString(5,wuye_detail.getOthers());
        return pstmt.executeUpdate();
    }


    //更新公司的详细信息
    public int updateData (Connection con,Wuye_detail wuye_detail) throws Exception{
        int firstId = getFirstId(con);
        if (firstId==-1){
            return insertData(con,wuye_detail);
        }
        String sql = "update wuye_detail set picUrl=?,message=?,person=?,hardset=?,others=? where id="+getFirstId(con);
        PreparedStatement pstmt = con.prepareStatement(sql);
        if (wuye_detail.getPicUrl()==null){
            pstmt.setString(1,getPicUrl(con));
        }else {
            pstmt.setString(1,wuye_detail.getPicUrl());
        }
        pstmt.setString(2,wuye_detail.getMessage());
        pstmt.setString(3,wuye_detail.getPerson());
        pstmt.setString(4,wuye_detail.getHardset());
        pstmt.setString(5,wuye_detail.getOthers());
        return pstmt.executeUpdate();
    }

    //获取数据库中的picUrl
    public String getPicUrl(Connection con)throws Exception{
        String sql = "select picUrl from wuye_detail where id=1";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getString("picUrl");
        }
        return null;
    }

    //获取第一个物业公司的id
    public int getFirstId(Connection con)throws Exception{
        String sql = "select * from wuye_detail";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("id");
        }
        return -1;
    }
}
