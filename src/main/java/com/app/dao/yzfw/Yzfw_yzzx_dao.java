package com.app.dao.yzfw;

import com.app.model.User;
import com.app.utils.StringUtil;
import org.apache.log4j.Logger;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2016/7/12.
 */
public class Yzfw_yzzx_dao {
    private Logger logger = Logger.getLogger(this.getClass());
    //插入一条用户的注册信息
    public int insertData(Connection con, User user)throws Exception{
        if (checkPhoneAndIdCardisExist(con,user)){
            logger.error("用户注册失败，原因：该身份证号码已经存在");
            throw new Exception();
        }else {
            String sql = "insert into user(name,phone,idCard,password,address,alias) values(?,?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,user.getName());
            pstmt.setString(2,user.getPhone());
            pstmt.setString(3,user.getIdCard());
            pstmt.setString(4,user.getPassword());
            pstmt.setString(5,user.getAddress());
            pstmt.setString(6,user.getAlias());
            /*pstmt.setInt(7,user.getIsDangYuan());
            pstmt.setString(8,user.getTag());*/
            return pstmt.executeUpdate();
        }
    }

    //用户登录验证(用身份证号码和密码进行登录)
    public User loginCheck(Connection con, User user)throws Exception{
        String sql = "select * from user where idCard=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user.getIdCard());
        pstmt.setString(2,user.getPassword());
        ResultSet rs = pstmt.executeQuery();
        User user1 = null;
        if (rs.next()){
            user1 = new User();
            user1.setId(rs.getInt("id"));
            user1.setName(rs.getString("name"));
            user1.setIdCard(rs.getString("idCard"));
            user1.setAddress(rs.getString("address"));
            user1.setPhone(rs.getString("phone"));
            user1.setPassword(rs.getString("password"));
            user1.setAlias(rs.getString("alias"));
            user1.setHeadPicUrl(rs.getString("headPicUrl"));
            user1.setIsDangYuan(rs.getInt("isDangYuan"));
            user1.setTag(rs.getString("tag"));
            return user1;
        }
        return user1;
    }

    //检查用户的身份证号码是否已经存在
    public boolean checkPhoneAndIdCardisExist(Connection con, User user) throws Exception{
        String sql = "select * from user where idCard='"+user.getIdCard()+"'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return true;
        }
        return false;
    }

    //用户修改密码前验证身份证号码是否存在
    public User checkIdCardBeforeUpdatePassword(Connection con,User user)throws Exception{
        String sql = "select * from user where idCard = '"+user.getIdCard()+"'";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        User resultUser = new User();
        if (rs.next()){
            resultUser.setId(rs.getInt("id"));
            resultUser.setName(rs.getString("name"));
            resultUser.setIdCard(rs.getString("idCard"));
            resultUser.setAddress(rs.getString("address"));
            resultUser.setPhone(rs.getString("phone"));
            resultUser.setPassword(rs.getString("password"));
            resultUser.setAlias(rs.getString("alias"));
            resultUser.setHeadPicUrl(rs.getString("headPicUrl"));
            resultUser.setIsDangYuan(rs.getInt("isDangYuan"));
            resultUser.setTag(rs.getString("tag"));
            return resultUser;
        }
        return null;
    }
}
