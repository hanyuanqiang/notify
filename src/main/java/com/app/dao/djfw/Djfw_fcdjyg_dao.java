package com.app.dao.djfw;

import com.app.dao.yzfw.UserDao;
import com.app.model.djfw.Djfw_fcdjyg;
import com.app.utils.DateUtil;
import com.app.utils.PageBean;
import com.app.utils.StringUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Djfw_fcdjyg_dao {

    /*
    获取“方寸党建阳光”模块的所有文章
     */
    public List<Djfw_fcdjyg> getDatas(Connection con, PageBean pageBean,String contentType,int userId) throws Exception{
        List<Djfw_fcdjyg> datas = new ArrayList<Djfw_fcdjyg>();
        StringBuffer sql = new StringBuffer("select * from djfw_fcdjyg");

        if (userId!=-1){
            if (UserDao.judgeIsDangYuanById(con,userId)){
                //如果是党员
                sql.append(" where whoLook in ('0','1') or userId="+userId);
            }else {
                //不是党员
                sql.append(" where whoLook='0'");
            }
        }

        if (StringUtil.isNotEmpty(contentType)){
            sql.append(" where type='"+contentType+"'");
        }
        sql.append("  order by publishDate desc");
        if(pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Djfw_fcdjyg data = new Djfw_fcdjyg();
            data.setId(rs.getInt("id"));
            data.setTitle(rs.getString("title"));
            data.setContent(rs.getString("content"));
            data.setPicUrl(rs.getString("picUrl"));
            data.setType(rs.getString("type"));
            data.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            data.setWhoLook(rs.getString("whoLook"));
            data.setUserId(rs.getInt("userId"));
            datas.add(data);
        }
        return datas;
    }


    /*
    存储社区工作人员发布的带标签的文章
     */
    public int insertData(Connection con,Djfw_fcdjyg fcdjyg) throws Exception{

        String sql = "insert into djfw_fcdjyg(id,title,content,picUrl,type,publishDate,whoLook,userId) values(null,?,?,?,?,now(),?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,fcdjyg.getTitle());
        pstmt.setString(2,fcdjyg.getContent());
        pstmt.setString(3,fcdjyg.getPicUrl());
        pstmt.setString(4,fcdjyg.getType());
        pstmt.setString(5,fcdjyg.getWhoLook());
        pstmt.setInt(6,fcdjyg.getUserId());
        return pstmt.executeUpdate();
    }

    //获取所有记录的总数,可以按类别获取
    public int getDatasCount(Connection con,String contentType) throws Exception{
        StringBuffer sql = new StringBuffer("select count(*) as counts from djfw_fcdjyg");

        if (StringUtil.isNotEmpty(contentType)){
            sql.append(" where type='"+contentType+"'");
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }

    //按指定Id进行查询
    public Djfw_fcdjyg getDataById(Connection con,int id) throws Exception{
        String sql = "select * from djfw_fcdjyg where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Djfw_fcdjyg data = null;
        if (rs.next()){
            data = new Djfw_fcdjyg();
            data.setId(rs.getInt("id"));
            data.setTitle(rs.getString("title"));
            data.setContent(rs.getString("content"));
            data.setPicUrl(rs.getString("picUrl"));
            data.setType(rs.getString("type"));
            data.setPublishDate(DateUtil.cutString(rs.getString("publishDate")));
            data.setWhoLook(rs.getString("whoLook"));
            data.setUserId(rs.getInt("userId"));
        }
        return data;
    }

    //根据id删除文章
    public int deleteById(Connection con,int id)throws Exception{
        String sql = "delete from djfw_fcdjyg where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

}
