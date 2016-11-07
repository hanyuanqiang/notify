package com.app.dao.sqfw;

import com.app.model.sqfw.Shequ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2016/7/24.
 */
public class Shequ_Dao {

    //插入或更新一条社区信息
    public int insertOrUpdateData(Connection con, Shequ shequ)throws Exception{
        boolean isNameExist = nameIsExist(con,shequ.getName());
        if (isNameExist){
            return -1;
        }
        int id = getFirstId(con);
        if (id==-1){
            String sql = "insert into shequ values(null,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,shequ.getName());
            pstmt.setString(2,shequ.getPassword());
            return pstmt.executeUpdate();
        }else {
            String sql = "update shequ set password=?,name=? where id="+id;
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,shequ.getPassword());
            pstmt.setString(2,shequ.getName());
            return pstmt.executeUpdate();
        }
    }


    //判断社区登录是否成功(跟第一条社区相比)
    public boolean isLoginSuccess(Connection con,Shequ sheuq)throws Exception{
        int id = getFirstId(con);
        if (id==-1){
            throw new Exception();
        }
        String sql = "select * from shequ where name=? and password=? and id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,sheuq.getName());
        pstmt.setString(2,sheuq.getPassword());
        pstmt.setInt(3,id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return true;
        }
        return false;
    }


    //获取第一个社区的id
    public int getFirstId(Connection con)throws Exception{
        String sql = "select * from shequ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("id");
        }
        return -1;
    }

    //查询指定社区名称是否已经存在
    public boolean nameIsExist(Connection con,String name)throws Exception{
        String sql = "select * from shequ where name='"+name+"'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return true;
        }
        return false;
    }

    //社区修改密码
    public int updatePassword(Connection con,String password)throws Exception{
        String sql = "update shequ set password='"+password+"' where id="+getFirstId(con);
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

}
