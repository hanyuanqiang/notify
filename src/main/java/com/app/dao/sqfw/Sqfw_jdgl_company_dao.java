package com.app.dao.sqfw;

import com.app.model.sqfw.Sqfw_jdgl_company;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Sqfw_jdgl_company_dao {

    //-1表示查询所有，0表示未审核，1表示审核通过，2表示审核未通过
    public List<Sqfw_jdgl_company> getDatas(Connection con, int param)throws Exception{
        List<Sqfw_jdgl_company> datas = new ArrayList<Sqfw_jdgl_company>();
        StringBuffer sql = new StringBuffer("select * from sqfw_jdgl_company");
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
            Sqfw_jdgl_company company = new Sqfw_jdgl_company();
            company.setId(rs.getInt("id"));
            company.setLicenseInfo(rs.getString("licenseInfo"));
            company.setLocation(rs.getString("location"));
            company.setPhone(rs.getString("phone"));
            company.setLegalPerson(rs.getString("legalPerson"));
            company.setStatus(rs.getInt("status"));
            datas.add(company);
        }
        return datas;
    }


    //添加一条认证信息
    public int insertData(Connection con,Sqfw_jdgl_company company)throws Exception{
        String sql = "insert into sqfw_jdgl_company(legalPerson,licenseInfo,phone,location) values(?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,company.getLegalPerson());
        pstmt.setString(2,company.getLicenseInfo());
        pstmt.setString(3,company.getPhone());
        pstmt.setString(4,company.getLocation());
        return pstmt.executeUpdate();
    }

    //更新一条认证信息的状态
    public int updateData(Connection con,Sqfw_jdgl_company company)throws Exception{
        String sql = "update sqfw_jdgl_company set status=? WHERE id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,company.getStatus());
        pstmt.setInt(2,company.getId());
        return pstmt.executeUpdate();
    }

    //删除指定id的信息
    public int deleteDataById(Connection con,int id)throws Exception{
        String sql = "delete from sqfw_jdgl_company where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }
}
