package com.app.dao.sqfw;

import com.app.model.sqfw.Sqfw_jdgl_merchant;
import com.app.model.sqfw.Sqfw_jdgl_person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Sqfw_jdgl_merchant_dao {

    //0表示未审核，1表示审核通过，2表示审核未通过
    public List<Sqfw_jdgl_merchant> getDatas(Connection con, int param)throws Exception{
        List<Sqfw_jdgl_merchant> datas = new ArrayList<Sqfw_jdgl_merchant>();
        StringBuffer sql = new StringBuffer("select * from sqfw_jdgl_merchant");
        if(param==0){
            sql.append(" where status=0");
        }else if(param==1){
            sql.append(" where status=1");
        }else if(param==2){
            sql.append(" where status=2");
        }else if(param==-1){
        }else{
            throw new Exception();
        }
        sql.append(" order by id desc");

        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Sqfw_jdgl_merchant merchant = new Sqfw_jdgl_merchant();
            merchant.setId(rs.getInt("id"));
            merchant.setName(rs.getString("name"));
            merchant.setLicenseInfo(rs.getString("licenseInfo"));
            merchant.setStatus(rs.getInt("status"));
            merchant.setPrincipal(rs.getString("principal"));
            merchant.setLocation(rs.getString("location"));
            datas.add(merchant);
        }
        return datas;
    }


    //添加一条认证信息
    public int insertData(Connection con,Sqfw_jdgl_merchant merchant)throws Exception{
        String sql = "insert into sqfw_jdgl_merchant(name,licenseInfo,principal,location) values(?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,merchant.getName());
        pstmt.setString(2,merchant.getLicenseInfo());
        pstmt.setString(3,merchant.getPrincipal());
        pstmt.setString(4,merchant.getLocation());
        return pstmt.executeUpdate();
    }

    //更新一条认证信息的状态
    public int updateData(Connection con,Sqfw_jdgl_merchant merchant)throws Exception{
        String sql = "update sqfw_jdgl_merchant set status=? WHERE id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,merchant.getStatus());
        pstmt.setInt(2,merchant.getId());
        return pstmt.executeUpdate();
    }

    //删除指定id的信息
    public int deleteDataById(Connection con,int id)throws Exception{
        String sql = "delete from sqfw_jdgl_merchant where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }
}
