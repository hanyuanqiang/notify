package com.app.dao.sqfw;

import com.app.model.sqfw.Sqfw_hdzj_participants;
import com.app.utils.PageBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Sqfw_hdzj_participants_dao {

    //获取某个活动的报名的人员信息
    public List<Sqfw_hdzj_participants> getDatas(Connection con, int infoId, PageBean pageBean)throws Exception{
        List<Sqfw_hdzj_participants> datas = new ArrayList<Sqfw_hdzj_participants>();
        StringBuffer sql = new StringBuffer("select * from sqfw_hdzj_participants where infoId="+infoId);
        if(pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Sqfw_hdzj_participants participants = new Sqfw_hdzj_participants();
            participants.setId(rs.getInt("id"));
            participants.setName(rs.getString("name"));
            participants.setIdCard(rs.getString("idCard"));
            participants.setPhone(rs.getString("phone"));
            participants.setInfoId(infoId);
            datas.add(participants);
        }
        return datas;
    }


    //插入一条报名信息
    public int insertData(Connection con,Sqfw_hdzj_participants participants)throws Exception{
        String sql = "insert into sqfw_hdzj_participants(infoId,name,idCard,phone) values(?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,participants.getInfoId());
        pstmt.setString(2,participants.getName());
        pstmt.setString(3,participants.getIdCard());
        pstmt.setString(4,participants.getPhone());
        return pstmt.executeUpdate();
    }


    //获取所有记录的总数
    public int getDatasCount(Connection con) throws Exception{
        String sql = "select count(*) as counts from sqfw_hdzj_participants";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }

    //删除指定人员
    public int deleteData(Connection con,int id)throws Exception{
        String sql = "delete from sqfw_hdzj_participants where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }


}
