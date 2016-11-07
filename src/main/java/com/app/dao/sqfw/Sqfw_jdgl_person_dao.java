package com.app.dao.sqfw;

import com.app.model.sqfw.Sqfw_jdgl_person;

import java.security.Permission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Sqfw_jdgl_person_dao {

    //param=0表示获取社区还没有审核的信息
    //param=1表示获取社区审核通过的信息
    //param=2表示获取社区审核后不通过的信息
    //param=-1表示获取所有的信息
    public List<Sqfw_jdgl_person> getDatas(Connection con,int param)throws Exception{
        List<Sqfw_jdgl_person> datas = new ArrayList<Sqfw_jdgl_person>();
        StringBuffer sql = new StringBuffer("select * from sqfw_jdgl_person");
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
            Sqfw_jdgl_person person = new Sqfw_jdgl_person();
            person.setId(rs.getInt("id"));
            person.setPhone(rs.getString("phone"));
            person.setIdCardInfo(rs.getString("idCardInfo"));
            person.setStatus(rs.getInt("status"));
            person.setWorkContent(rs.getString("workContent"));
            person.setName(rs.getString("name"));
            datas.add(person);
        }
        return datas;
    }

    //根据Id获取指定信息
    public Sqfw_jdgl_person getData(Connection con,int id)throws Exception{
        String sql = "select * from sqfw_jdgl_person where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Sqfw_jdgl_person person = null;
        if (rs.next()){
            person = new Sqfw_jdgl_person();
            person.setId(rs.getInt("id"));
            person.setPhone(rs.getString("phone"));
            person.setIdCardInfo(rs.getString("idCardInfo"));
            person.setStatus(rs.getInt("status"));
            person.setWorkContent(rs.getString("workContent"));
            person.setName(rs.getString("name"));
        }
        return person;
    }

    //添加一条认证信息
    public int insertData(Connection con,Sqfw_jdgl_person person)throws Exception{
        String sql = "insert into sqfw_jdgl_person(idCardInfo,phone,workContent,name) values(?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,person.getIdCardInfo());
        pstmt.setString(2,person.getPhone());
        pstmt.setString(3,person.getWorkContent());
        pstmt.setString(4,person.getName());
        return pstmt.executeUpdate();
    }

    //更新一条认证信息的状态
    public int updateData(Connection con,Sqfw_jdgl_person person)throws Exception{
        String sql = "update sqfw_jdgl_person set status=? WHERE id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1,person.getStatus());
        pstmt.setInt(2,person.getId());
        return pstmt.executeUpdate();
    }

    //删除指定id的信息
    public int deleteDataById(Connection con,int id)throws Exception{
        String sql = "delete from sqfw_jdgl_person where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }
}
