package com.app.dao.yzfw;

import com.app.model.yzfw.Yzfw_xfzj;
import com.app.utils.DateUtil;
import com.app.utils.PageBean;
import com.app.utils.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/3.
 */
public class Yzfw_xfzj_dao {

    //获取幸福之家板块中所有的信息,id=-1表示查询所有，否则表示查询指定id的详细信息
    public List<Yzfw_xfzj> getDatas(Connection con, int id, PageBean pageBean)throws Exception{
        List<Yzfw_xfzj> datas = new ArrayList<Yzfw_xfzj>();
        StringBuffer sql = new StringBuffer("select * from yzfw_xfzj");
        if (id!=-1){
            sql.append(" where id="+id);
        }
        sql.append(" order by id desc");
        if (pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs =  pstmt.executeQuery();
        while (rs.next()){
            Yzfw_xfzj xfzj = new Yzfw_xfzj();
            xfzj.setId(rs.getInt("id"));
            xfzj.setTitle(rs.getString("title"));
            xfzj.setContent(rs.getString("content"));
            xfzj.setPicUrl(rs.getString("picUrl"));
            xfzj.setName(rs.getString("name"));
            xfzj.setType(rs.getString("type"));
            xfzj.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            datas.add(xfzj);
        }
        return datas;
    }


    //根据类型获取数据
    public List<Yzfw_xfzj> getDatasByType(Connection con, PageBean pageBean, String contentType)throws Exception{
        List<Yzfw_xfzj> datas = new ArrayList<Yzfw_xfzj>();
        StringBuffer sql = new StringBuffer("select * from yzfw_xfzj");
        if(StringUtil.isNotEmpty(contentType)){
            sql.append(" where type='"+contentType+"'");
        }
        sql.append(" order by id desc");
        if (pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs =  pstmt.executeQuery();
        while (rs.next()){
            Yzfw_xfzj xfzj = new Yzfw_xfzj();
            xfzj.setId(rs.getInt("id"));
            xfzj.setTitle(rs.getString("title"));
            xfzj.setContent(rs.getString("content"));
            xfzj.setPicUrl(rs.getString("picUrl"));
            xfzj.setName(rs.getString("name"));
            xfzj.setType(rs.getString("type"));
            xfzj.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            datas.add(xfzj);
        }
        return datas;
    }


    //插入一条信息
    public int insertData(Connection con,Yzfw_xfzj xfzj)throws Exception{

        String sql = "insert into yzfw_xfzj(name,title,content,type) values(?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,xfzj.getName());
        pstmt.setString(2,xfzj.getTitle());
        String content = "";
        //把用户上传的图片插入到内容中
        if (StringUtil.isNotEmpty(xfzj.getPicUrl())){
            String imgHtml = "<img src=\""+xfzj.getPicUrl()+"\"/>";
            content = "<div>"+imgHtml+"</div>";
        }
        content+=xfzj.getContent();
        pstmt.setString(3,content);
        pstmt.setString(4,xfzj.getType());
        return pstmt.executeUpdate();
    }


    //删除一条信息
    public int deleteData(Connection con,int id)throws Exception{
        String sql = "delete from yzfw_xfzj where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,id);
        return pstmt.executeUpdate();
    }

    //获取所有记录的总数
    public int getDatasCount(Connection con,String contentType) throws Exception{
        StringBuffer sql = new StringBuffer("select count(*) as counts from yzfw_xfzj");
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


}
