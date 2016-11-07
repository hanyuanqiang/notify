package com.app.dao.wyfw;

import com.app.model.wyfw.Wyfw_tzgg;
import com.app.utils.DateUtil;
import com.app.utils.PageBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
public class Wyfw_tzgg_dao {

    //查询物业公司发布的所有的通知公告
    public List<Wyfw_tzgg> getDatas(Connection con, PageBean pageBean) throws Exception{
        List<Wyfw_tzgg> datas = new ArrayList<Wyfw_tzgg>();
        //String sql = "select * from wyfw_tzgg";
        StringBuffer sql = new StringBuffer("select * from wyfw_tzgg order by id desc");
        if (pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Wyfw_tzgg wyfw_tzgg = new Wyfw_tzgg();
            wyfw_tzgg.setId(rs.getInt("id"));
            int a = rs.getInt("id");
            wyfw_tzgg.setTitle(rs.getString("title"));
            wyfw_tzgg.setContent(rs.getString("content"));
            wyfw_tzgg.setPicUrl(rs.getString("picUrl"));
            wyfw_tzgg.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            wyfw_tzgg.setIsPush(rs.getInt("isPush"));
            datas.add(wyfw_tzgg);
        }
        return datas;
    }

    //插入物业公司发布的通知公告
    public int insertData(Connection con,Wyfw_tzgg wyfw_tzgg)throws Exception{
        String sql = "insert into wyfw_tzgg(title,content) values(?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,wyfw_tzgg.getTitle());
        pstmt.setString(2,wyfw_tzgg.getContent());
        return pstmt.executeUpdate();
    }

    //这个方法把已经推送过的通知的状态设为1
    public int updateIsPushStatus(Connection con,int id)throws Exception{
        String sql = "update wyfw_tzgg set isPush=1 where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

    //获取所有记录的总数
    public int getDatasCount(Connection con) throws Exception{
        String sql = "select count(*) as counts from wyfw_tzgg";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }

    //根据id获取某一条信息
    public Wyfw_tzgg getOneData(Connection con,int id)throws Exception{
        String sql = "select * from wyfw_tzgg where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Wyfw_tzgg wyfw_tzgg = null;
        if (rs.next()){
            wyfw_tzgg = new Wyfw_tzgg();
            wyfw_tzgg.setId(rs.getInt("id"));
            wyfw_tzgg.setTitle(rs.getString("title"));
            wyfw_tzgg.setContent(rs.getString("content"));
            wyfw_tzgg.setPicUrl(rs.getString("picUrl"));
            wyfw_tzgg.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            wyfw_tzgg.setId(rs.getInt("isPush"));
        }
        return wyfw_tzgg;
    }

    //根据id删除数据
    public int deleteById(Connection con,int id)throws Exception{
        String sql = "delete from wyfw_tzgg where id="+id;
        PreparedStatement pstmt  = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }
}
