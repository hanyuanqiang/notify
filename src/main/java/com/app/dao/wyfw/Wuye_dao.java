package com.app.dao.wyfw;

import com.app.model.wyfw.Wuye;

import java.security.Permission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2016/6/29.
 */
public class Wuye_dao {

    //插入或更新一条物业信息
    public int insertOrUpdateData(Connection con, Wuye wuye)throws Exception{
        boolean isNameExist = nameIsExist(con,wuye.getName());
        if (isNameExist){
            return -1;
        }
        int id = getFirstId(con);
        if (id==-1){
            String sql = "insert into wuye values(null,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,wuye.getName());
            pstmt.setString(2,wuye.getPassword());
            return pstmt.executeUpdate();
        }else {
            String sql = "update wuye set password=?,name=? where id="+id;
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,wuye.getPassword());
            pstmt.setString(2,wuye.getName());
            return pstmt.executeUpdate();
        }
    }


    //判断物业登录是否成功(跟第一条物业相比)
    public boolean isLoginSuccess(Connection con,Wuye wuye)throws Exception{
        int id = getFirstId(con);
        if (id==-1){
            throw new Exception();
        }
        String sql = "select * from wuye where name=? and password=? and id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,wuye.getName());
        pstmt.setString(2,wuye.getPassword());
        pstmt.setInt(3,id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return true;
        }
        return false;
    }


    //获取第一个物业的id
    public int getFirstId(Connection con)throws Exception{
        String sql = "select * from wuye";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("id");
        }
        return -1;
    }

    //查询指定物业名称是否已经存在
    public boolean nameIsExist(Connection con,String name)throws Exception{
        String sql = "select * from wuye where name='"+name+"'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return true;
        }
        return false;
    }

    //物业修改密码
    public int updatePassword(Connection con,String password)throws Exception{
        String sql = "update wuye set password='"+password+"' where id="+getFirstId(con);
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

}
