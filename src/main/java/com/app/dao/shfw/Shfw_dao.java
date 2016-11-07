package com.app.dao.shfw;

import com.app.model.shfw.Shfw;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by Administrator on 2016/7/3.
 */
public class Shfw_dao {

    //按类别获取搜有的生活服务信息
    public Map<String,List<Shfw>> getDatas(Connection con)throws Exception{
        Map<String,List<Shfw>> datas = new HashMap<String, List<Shfw>>();
        String sql = "select * from shfw";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs  = pstmt.executeQuery();
        while (rs.next()){
            Shfw shfw = new Shfw();
            shfw.setId(rs.getInt("id"));
            shfw.setTitle(rs.getString("title"));
            shfw.setContent(rs.getString("content"));
            shfw.setPicUrl(rs.getString("picUrl"));
            shfw.setPhone(rs.getString("phone"));
            String type = rs.getString("type");
            shfw.setType(type);
            if(datas.containsKey(type)){
                datas.get(type).add(shfw);
            }else{
                List<Shfw> list = new ArrayList<Shfw>();
                list.add(shfw);
                datas.put(type,list);
            }
        }
        return datas;
    }

    //获取指定id的信息
    public Shfw getDataById(Connection con,int id)throws Exception{
        String sql = "select * from shfw where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        Shfw shfw = null;
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            shfw = new Shfw();
            shfw.setId(rs.getInt("id"));
            shfw.setTitle(rs.getString("title"));
            shfw.setContent(rs.getString("content"));
            shfw.setPicUrl(rs.getString("picUrl"));
            shfw.setPhone(rs.getString("phone"));
            shfw.setType(rs.getString("type"));
        }
        return shfw;
    }

    //删除指定id的信息
    public int deleteDataById(Connection con,int id)throws Exception{
        String sql = "delete from shfw where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }


    //插入一条生活服务信息
    public int insertData(Connection con,Shfw shfw)throws Exception{
        String sql = "insert into shfw(type,title,content,picUrl,phone) values(?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,shfw.getType());
        pstmt.setString(2,shfw.getTitle());
        pstmt.setString(3,shfw.getContent());
        pstmt.setString(4,shfw.getPicUrl());
        pstmt.setString(5,shfw.getPhone());
        return pstmt.executeUpdate();
    }

}
