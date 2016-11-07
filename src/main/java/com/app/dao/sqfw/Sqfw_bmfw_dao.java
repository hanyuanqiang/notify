package com.app.dao.sqfw;

import com.app.model.sqfw.Sqfw_bmfw;
import com.app.model.sqfw.Sqfw_hdly;
import com.app.utils.DateUtil;
import com.app.utils.PageBean;
import com.app.utils.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Sqfw_bmfw_dao {

    //id=-1表示获取所有的便民服务信息,否则获取指定id的信息
    public List<Sqfw_bmfw> getDatas(Connection con, int id, PageBean pageBean)throws Exception{
        List<Sqfw_bmfw> datas = new ArrayList<Sqfw_bmfw>();
        //String sql = "select * from sqfw_bmfw";
        StringBuffer sql = new StringBuffer("select * from sqfw_bmfw");

        if(id!=-1){
          sql.append(" where id="+id);
        }
        sql.append(" order by id desc");

        if (pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Sqfw_bmfw bmfw = new Sqfw_bmfw();
            bmfw.setId(rs.getInt("id"));
            bmfw.setTitle(rs.getString("title"));
            bmfw.setContent(rs.getString("content"));
            bmfw.setType(rs.getString("type"));
            bmfw.setPicUrl(rs.getString("picUrl"));
            bmfw.setVideoUrl(rs.getString("videoUrl"));
            bmfw.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            datas.add(bmfw);
        }
        return datas;
    }

    //获取指定类别的数据
    public List<Sqfw_bmfw> getDatasByType(Connection con, PageBean pageBean, String contentType)throws Exception{
        List<Sqfw_bmfw> datas = new ArrayList<Sqfw_bmfw>();
        StringBuffer sql = new StringBuffer("select * from sqfw_bmfw");

        if(StringUtil.isNotEmpty(contentType)){
            sql.append(" where type='"+contentType+"'");
        }
        sql.append(" order by id desc");

        if (pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Sqfw_bmfw bmfw = new Sqfw_bmfw();
            bmfw.setId(rs.getInt("id"));
            bmfw.setTitle(rs.getString("title"));
            bmfw.setContent(rs.getString("content"));
            bmfw.setType(rs.getString("type"));
            bmfw.setPicUrl(rs.getString("picUrl"));
            bmfw.setVideoUrl(rs.getString("videoUrl"));
            bmfw.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            datas.add(bmfw);
        }
        return datas;
    }

    //插入一条便民服务信息
    public int insertDate(Connection con,Sqfw_bmfw bmfw)throws Exception{
        String sql = "insert into sqfw_bmfw(title,content,type,picUrl,videoUrl) values(?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,bmfw.getTitle());
        pstmt.setString(2,bmfw.getContent());
        pstmt.setString(3,bmfw.getType());
        pstmt.setString(4,bmfw.getPicUrl());
        pstmt.setString(5,bmfw.getVideoUrl());
        return pstmt.executeUpdate();
    }



    //获取所有记录的总数
    public int getDatasCount(Connection con,String contentType) throws Exception{
        StringBuffer sql = new StringBuffer("select count(*) as counts from sqfw_bmfw");
        if(StringUtil.isNotEmpty(contentType)){
            sql.append(" where type='"+contentType+"'");
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }

    //根据id删除指定信息
    public int deleteById(Connection con,int id)throws Exception{
        String sql = "delete from sqfw_bmfw where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }


}
