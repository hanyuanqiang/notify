package com.app.dao.sqfw;

import com.app.model.sqfw.Sqfw_hdzj_info;
import com.app.utils.DateUtil;
import com.app.utils.PageBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Sqfw_hdzj_info_dao {

    //获取所有活动召集信息
    public List<Sqfw_hdzj_info> getDatas(Connection con, PageBean pageBean)throws Exception{
        List<Sqfw_hdzj_info> datas = new ArrayList<Sqfw_hdzj_info>();
        StringBuffer sql = new StringBuffer("select * from sqfw_hdzj_info order by publishDate desc");
        if(pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Sqfw_hdzj_info info = new Sqfw_hdzj_info();
            info.setId(rs.getInt("id"));
            info.setTitle(rs.getString("title"));
            info.setContent(rs.getString("content"));
            info.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            info.setPicUrl(rs.getString("picUrl"));
            datas.add(info);
        }
        return datas;
    }

    //按指定Id进行查询
    public Sqfw_hdzj_info getDataById(Connection con,int id) throws Exception{
        String sql = "select * from sqfw_hdzj_info where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Sqfw_hdzj_info info = null;
        if (rs.next()){
            info = new Sqfw_hdzj_info();
            info.setId(rs.getInt("id"));
            info.setTitle(rs.getString("title"));
            info.setContent(rs.getString("content"));
            info.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            info.setPicUrl(rs.getString("picUrl"));
        }
        return info;
    }

    //插入一条活动召集信息
    public int insertData(Connection con,Sqfw_hdzj_info info)throws Exception{
        String sql = "insert into sqfw_hdzj_info(title,content,picUrl) VALUES (?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,info.getTitle());
        pstmt.setString(2,info.getContent());
        pstmt.setString(3,info.getPicUrl());
        return pstmt.executeUpdate();
    }

    //获取所有记录的总数
    public int getDatasCount(Connection con) throws Exception{
        String sql = "select count(*) as counts from sqfw_hdzj_info";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }

    //根据id删除某条活动
    public int deleteById(Connection con,int id)throws Exception{
        String sql = "delete from sqfw_hdzj_info where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

}
