package com.app.dao.sqfw;

import com.app.model.sqfw.Sqfw_hdly;
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
public class Sqfw_hdly_dao {

    //id=-1时表示查询所有的活动掠影信息,否则按照指定id进行查询
    public List<Sqfw_hdly> getDatas(Connection con,int id,PageBean pageBean)throws  Exception{
        List<Sqfw_hdly> datas = new ArrayList<Sqfw_hdly>();
        StringBuffer sql = new StringBuffer("select * from sqfw_hdly");
        if (id!=-1){
            sql.append(" where id="+id);
        }
        sql.append(" order by id desc");
        if (pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Sqfw_hdly hdly = new Sqfw_hdly();
            hdly.setId(rs.getInt("id"));
            hdly.setTitle(rs.getString("title"));
            hdly.setContent(rs.getString("content"));
            hdly.setPicUrl(rs.getString("picUrl"));
            hdly.setVideoUrl(rs.getString("videoUrl"));
            hdly.setType(rs.getString("type"));
            hdly.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            datas.add(hdly);
        }
        return datas;
    }

    //插入一条活动掠影信息
    public int insertDate(Connection con,Sqfw_hdly hdly)throws Exception{
        String sql = "insert into sqfw_hdly(title,content,picUrl,videoUrl,type) values(?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,hdly.getTitle());
        pstmt.setString(2,hdly.getContent());
        pstmt.setString(3,hdly.getPicUrl());
        pstmt.setString(4,hdly.getVideoUrl());
        pstmt.setString(5,hdly.getType());
        return pstmt.executeUpdate();
    }

    //获取所有记录的总数
    public int getDatasCount(Connection con) throws Exception{
        String sql = "select count(*) as counts from sqfw_hdly";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }

    //根据id删除指定活动掠影
    public int deleteById(Connection con,int id)throws Exception{
        String sql = "delete from sqfw_hdly where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

}
