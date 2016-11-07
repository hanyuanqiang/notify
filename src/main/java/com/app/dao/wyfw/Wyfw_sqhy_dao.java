package com.app.dao.wyfw;

import com.app.model.wyfw.Wyfw_sqhy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
public class Wyfw_sqhy_dao {
    //获取社区黄页中的生活服务信息
    public Map<String,List<Wyfw_sqhy>> getDatas(Connection con)throws Exception{
        Map<String,List<Wyfw_sqhy>> maps = new HashMap<String, List<Wyfw_sqhy>>();
        String sql1 = "select * from wyfw_sqhy";
        PreparedStatement pstmt = con.prepareStatement(sql1);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Wyfw_sqhy sqhy = new Wyfw_sqhy();
            sqhy.setId(rs.getInt("id"));
            sqhy.setPhoneName(rs.getString("phoneName"));
            sqhy.setPhoneNumber(rs.getString("phoneNumber"));
            String type = rs.getString("type");
            sqhy.setType(type);

            if(maps.containsKey(type)){
                maps.get(type).add(sqhy);
            }else{
                List<Wyfw_sqhy> list = new ArrayList<Wyfw_sqhy>();
                list.add(sqhy);
                maps.put(type, list);
            }
        }
        return maps;
    }

    //向数据库中插入一条社区黄页数据
    public int insertData(Connection con,Wyfw_sqhy sqhy)throws Exception{
        String sql = "insert into wyfw_sqhy values(null,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,sqhy.getType());
        pstmt.setString(2,sqhy.getPhoneName());
        pstmt.setString(3,sqhy.getPhoneNumber());
        return pstmt.executeUpdate();
    }

    //根据id删除指定的信息
    public int deleteDataById(Connection con,int id)throws Exception{
        String sql = "delete from wyfw_sqhy where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }
}
