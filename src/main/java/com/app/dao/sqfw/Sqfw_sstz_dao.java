package com.app.dao.sqfw;

import com.app.model.sqfw.Sqfw_sstz;
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
public class Sqfw_sstz_dao {

    //获取所有的实时通知
    public List<Sqfw_sstz> getDatas(Connection con, PageBean pageBean,String contentType)throws Exception{
        List<Sqfw_sstz> datas = new ArrayList<Sqfw_sstz>();
        //String sql = "select * from sqfw_sstz";
        StringBuffer sql = new StringBuffer("select * from sqfw_sstz");
        if(StringUtil.isNotEmpty(contentType)){
            sql.append(" where type='"+contentType+"'");
        }
        sql.append(" order by id desc");
        if(pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Sqfw_sstz sstz = new Sqfw_sstz();
            sstz.setId(rs.getInt("id"));
            sstz.setType(rs.getString("type"));
            sstz.setTitle(rs.getString("title"));
            sstz.setContent(rs.getString("content"));
            sstz.setPicUrl(rs.getString("picUrl"));
            sstz.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            sstz.setIsPush(rs.getInt("isPush"));
            datas.add(sstz);
        }
        return datas;
    }


    //向数据库中插入一条实时通知的内容
    public int insertData(Connection con,Sqfw_sstz sstz)throws  Exception{
        String sql = "insert into sqfw_sstz(title,content,picUrl,type) values(?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,sstz.getTitle());
        pstmt.setString(2,sstz.getContent());
        pstmt.setString(3,sstz.getPicUrl());
        pstmt.setString(4,sstz.getType());
        return pstmt.executeUpdate();
    }

    //获取所有记录的总数
    public int getDatasCount(Connection con,String contentType) throws Exception{
        StringBuffer sql = new StringBuffer("select count(*) as counts from sqfw_sstz");
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

    //根据Id获取数据
    public Sqfw_sstz getOneData(Connection con,int id)throws Exception{
        String sql = "select * from sqfw_sstz where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Sqfw_sstz sstz = new Sqfw_sstz();
        if (rs.next()){
            sstz.setId(rs.getInt("id"));
            sstz.setType(rs.getString("type"));
            sstz.setTitle(rs.getString("title"));
            sstz.setContent(rs.getString("content"));
            sstz.setPicUrl(rs.getString("picUrl"));
            sstz.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            sstz.setIsPush(rs.getInt("isPush"));
        }
        return sstz;
    }

    public Sqfw_sstz getNewestData(Connection con)throws Exception{
        String sql = "select * from sqfw_sstz order by id desc limit 0,1";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Sqfw_sstz sstz = new Sqfw_sstz();
        if (rs.next()){
            sstz.setId(rs.getInt("id"));
            sstz.setType(rs.getString("type"));
            sstz.setTitle(rs.getString("title"));
            sstz.setContent(rs.getString("content"));
            sstz.setPicUrl(rs.getString("picUrl"));
            sstz.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            sstz.setIsPush(rs.getInt("isPush"));
        }
        return sstz;
    }


    //删除指定id的实时通知内容
    public int deleteById(Connection con,int id)throws Exception{
        String sql = "delete from sqfw_sstz where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

}
