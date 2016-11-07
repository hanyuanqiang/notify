package com.app.dao.yzfw;

import com.app.model.yzfw.Yzfw_llhd;
import com.app.utils.DateUtil;
import com.app.utils.PageBean;
import com.app.utils.StringUtil;
import sun.reflect.annotation.ExceptionProxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/7/2.
 */
public class Yzfw_llhd_dao {

    //获取所有的邻里互动信息,id=-1表示查询所有，否则表示查询指定id的详细信息
    public List<Yzfw_llhd> getDatas(Connection con, int id, PageBean pageBean)throws Exception{
        List<Yzfw_llhd> datas = new ArrayList<Yzfw_llhd>();
        StringBuffer sql= new StringBuffer("SELECT * FROM yzfw_llhd");
        if (id!=-1){
            sql.append(" where id="+id);
        }
        sql.append(" ORDER BY id DESC");
        if (pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Yzfw_llhd llhd = new Yzfw_llhd();
            llhd.setId(rs.getInt("id"));
            llhd.setName(rs.getString("name"));
            llhd.setContent(rs.getString("content"));
            llhd.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            llhd.setPicUrl(rs.getString("picUrl"));
            llhd.setTitle(rs.getString("title"));
            llhd.setType(rs.getString("type"));
            datas.add(llhd);
        }
        return datas;
    }

    //获取指定类别的信息
    public List<Yzfw_llhd> getDatasByType(Connection con, PageBean pageBean, String contentType)throws Exception{
        List<Yzfw_llhd> datas = new ArrayList<Yzfw_llhd>();
        StringBuffer sql= new StringBuffer("SELECT * FROM yzfw_llhd");
        if(StringUtil.isNotEmpty(contentType)){
            sql.append(" where type='"+contentType+"'");
        }
        sql.append(" ORDER BY id DESC");
        if (pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Yzfw_llhd llhd = new Yzfw_llhd();
            llhd.setId(rs.getInt("id"));
            llhd.setName(rs.getString("name"));
            llhd.setContent(rs.getString("content"));
            llhd.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            llhd.setPicUrl(rs.getString("picUrl"));
            llhd.setTitle(rs.getString("title"));
            llhd.setType(rs.getString("type"));
            datas.add(llhd);
        }
        return datas;
    }



    //插入一条邻里互动信息
    public int insertData(Connection con,Yzfw_llhd llhd)throws Exception{

        String sql = "insert into yzfw_llhd(name,title,content,type) values(?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,llhd.getName());
        pstmt.setString(2,llhd.getTitle());
        String content = "";
        if (StringUtil.isNotEmpty(llhd.getPicUrl())){
            String imgHtml = "<img src=\""+llhd.getPicUrl()+"\"/>";
            content = "<div>"+imgHtml+"</div>";
        }
        content+=llhd.getContent();
        pstmt.setString(3,content);
        pstmt.setString(4,llhd.getType());
        return pstmt.executeUpdate();
    }

    //获取所有记录的总数
    public int getDatasCount(Connection con ,String contentType) throws Exception{
        StringBuffer sql = new StringBuffer("select count(*) as counts from yzfw_llhd");
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
        String sql = "delete from yzfw_llhd where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

    /*public static void main(String[] args) throws Exception{
        Yzfw_llhd_dao dao = new Yzfw_llhd_dao();
        //插入一条数据
        *//*Yzfw_llhd llhd = new Yzfw_llhd();
        llhd.setTitle("下午去西湖");
        llhd.setContent("下午有没有人一起去西湖的，有的话一起拼车啊");
        llhd.setName("陈XX");
        llhd.setPicUrl("/yzfw/xxx.png");
        dao.insertData(connection,llhd);
        System.out.print("插入数据成功");*//*

        //查询数据库中的数据
        List<Yzfw_llhd> datas = dao.getDatas(connection,-1);
        Iterator<Yzfw_llhd> iterator = datas.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next().getTitle());
        }
    }*/
}
