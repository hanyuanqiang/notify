package com.app.dao.yzfw;

import com.app.model.User;
import com.app.utils.PageBean;
import com.app.utils.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/26.
 */
public class UserDao {

    //获取某个用户的详细信息
    public User getData(Connection con,int userId)throws Exception{
        User user = null;
        String sql = "select * from user where id="+userId;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setIdCard(rs.getString("idCard"));
            user.setAddress(rs.getString("address"));
            user.setPhone(rs.getString("phone"));
            user.setPassword(rs.getString("password"));
            user.setAlias(rs.getString("alias"));
            user.setHeadPicUrl(rs.getString("headPicUrl"));
            user.setIsDangYuan(rs.getInt("isDangYuan"));
            user.setTag(rs.getString("tag"));
        }
        if (user==null){
            throw new Exception();
        }else{
            return user;
        }
    }

    //修改某个用户的信息
    public User updateData(Connection con,User user)throws Exception{
        /*String sql = "update user set phone=?,address=?,headPicUrl=? where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getPhone());
        pstmt.setString(2,user.getAddress());
        pstmt.setString(3,user.getHeadPicUrl());
        pstmt.setInt(4,user.getId());
        pstmt.executeUpdate();
        return getData(con,user.getId());*/
        boolean temp = true;
        StringBuffer sql = new StringBuffer("update user set");

        //更改电话号码
        if (StringUtil.isNotEmpty(user.getPhone())){
            if (temp){
                sql.append(" phone='"+user.getPhone()+"'");
            }
            temp=false;
        }

        //更改地址
        if (StringUtil.isNotEmpty(user.getAddress())){
            if (temp){
                sql.append(" address='"+user.getAddress()+"'");
            }else {
                sql.append(" ,address='"+user.getAddress()+"'");
            }
            temp=false;
        }

        //更改头像
        if (StringUtil.isNotEmpty(user.getHeadPicUrl())){
            if (temp){
                sql.append(" headPicUrl='"+user.getHeadPicUrl()+"'");
            }else {
                sql.append(" ,headPicUrl='"+user.getHeadPicUrl()+"'");
            }
            temp=false;
        }

        //更改是否党员
        if (StringUtil.isNotEmpty(user.getIsDangYuan()+"")){
            String str = user.getIsDangYuan()+"";
            if (temp){
                if ("1".equals(str)){
                    sql.append(" isDangYuan=1,tag='dangyuantag'");
                }else {
                    sql.append(" isDangYuan=0,tag=''");
                }
            }else {
                if ("1".equals(str)){
                    sql.append(" ,isDangYuan=1,tag='dangyuantag'");
                }else {
                    sql.append(" ,isDangYuan=0,tag=''");
                }
            }
            temp=false;
        }

        sql.append(" where id="+user.getId());
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        if (!temp){
            //上面五个条件必须满足一个条件才行
            pstmt.executeUpdate();
        }
        return getData(con,user.getId());

    }

    //用户修改密码
    public User updatePassword(Connection con,User user)throws Exception{
        String sql = "update user set password=? where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getPassword());
        pstmt.setInt(2,user.getId());
        pstmt.executeUpdate();
        return getData(con,user.getId());
    }

    //根据id获取用户名
    public String getUserNameById(Connection con,int id)throws Exception{
        String sql = "select * from user where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getString("name");
        }
        return null;
    }

    //根据id获取联系方式
    public String getPhoneById(Connection con,int id)throws Exception{
        String sql = "select phone from user where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getString("phone");
        }
        return null;
    }

    //获取所有用户的信息
    public List<User> getAllUsers(Connection con, PageBean pageBean)throws Exception{
        List<User> users = new ArrayList<User>();
        StringBuffer sql = new StringBuffer("select * from user");
        if (pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+" , "+pageBean.getPageSize());
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setIdCard(rs.getString("idCard"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setPassword(rs.getString("password"));
            user.setAlias(rs.getString("alias"));
            user.setHeadPicUrl(rs.getString("headPicUrl"));
            user.setIsDangYuan(rs.getInt("isDangYuan"));
            user.setTag(rs.getString("tag"));
            users.add(user);
        }
        return users;
    }

    //获取用户总数
    public int getDatasCount(Connection con) throws Exception{
        String sql = "select count(*) as counts from user";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }

    //删除指定id用户
    public int deleteUserById(Connection con,int id)throws Exception{
        String sql = "delete from user where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

    //管理员进行搜索操作
    public List<User> searchUsers(Connection con,User user1)throws Exception{
        List<User> users = new ArrayList<User>();
        StringBuffer sql = new StringBuffer("select * from user");

        if (StringUtil.isNotEmpty(user1.getName())){
            sql.append(" and name like '%"+user1.getName()+"%'");
        }

        if (StringUtil.isNotEmpty(user1.getIdCard())){
            sql.append(" and idCard like '%"+user1.getIdCard()+"%'");
        }

        if (user1.getIsDangYuan()!=-1){
            sql.append(" and isDangYuan="+user1.getIsDangYuan());
        }

        PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and","where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setIdCard(rs.getString("idCard"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setPassword(rs.getString("password"));
            user.setAlias(rs.getString("alias"));
            user.setHeadPicUrl(rs.getString("headPicUrl"));
            user.setIsDangYuan(rs.getInt("isDangYuan"));
            user.setTag(rs.getString("tag"));
            users.add(user);
        }
        return users;
    }

    //根据id判断某个用户是否为党员
    public static boolean judgeIsDangYuanById(Connection con,int id)throws Exception{
        String sql = "select isDangYuan from user where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            if ("1".equals(rs.getString("isDangYuan"))){
                return true;
            }
        }
        return false;
    }

    //根据id获取用户的alias
    public String getAliasById(Connection con,int id)throws Exception{
        String sql = "select alias from user where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getString("alias");
        }
        return null;
    }
}
