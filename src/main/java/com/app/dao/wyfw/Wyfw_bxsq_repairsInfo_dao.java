package com.app.dao.wyfw;

import com.app.dao.yzfw.UserDao;
import com.app.model.wyfw.Wyfw_bxsq_repairsInfo;
import com.app.utils.DateUtil;
import com.app.utils.PageBean;
import com.app.utils.StringUtil;

import java.security.Permission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
public class Wyfw_bxsq_repairsInfo_dao {

    //插入一条业主的报修申请
    public int insertPepairsInfo(Connection con, Wyfw_bxsq_repairsInfo wyfw_bxsq_repairsInfo) throws Exception {
        String sql = "insert into wyfw_bxsq_repairsinfo(title,content,picUrl_beforeRepairs,userId,userName,bxTime) values(?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, wyfw_bxsq_repairsInfo.getTitle());
        pstmt.setString(2, wyfw_bxsq_repairsInfo.getContent());
        pstmt.setString(3, wyfw_bxsq_repairsInfo.getPicUrl_beforeRepairs());
        pstmt.setInt(4, wyfw_bxsq_repairsInfo.getUserId());
        pstmt.setString(5, wyfw_bxsq_repairsInfo.getUserName());
        pstmt.setString(6,DateUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss"));
        return pstmt.executeUpdate();
    }

    //查询各种(根据param参数)维修信息
    //param=1表示查询还没有预约维修时间的维修信息
    //param=2表示查询所有已经维修但还没有过评论的信息
    //param=3表示查询某个用户的所有申请记录
    //param=4表示查询已经预约但是还没维修的记录
    //param=5表示查询已经维修了的所有记录
    //param=6表示查询已经有评论内容和星级的全部记录
    public List<Wyfw_bxsq_repairsInfo> getDates(Connection con, int userId, int param, PageBean pageBean) throws Exception {
        List<Wyfw_bxsq_repairsInfo> datas = new ArrayList<Wyfw_bxsq_repairsInfo>();
        StringBuffer sql = new StringBuffer("select * from wyfw_bxsq_repairsinfo");

        if (param == 1) {
            sql.append(" where repairsDate is null or repairsDate=''");//如果预约的时间为空(即还没有预约的维修信息)
        }
        if (param == 2) {
            sql.append(" where isRepairsFinish=1 and isComment=0");//维修完成但是还没评论
        }
        if (param == 3) {
            sql.append(" where userId=" + userId);//取出某个用户的所有申请记录信息
        }
        if (param == 4) {
            sql.append(" where repairsDate!='' and isRepairsFinish=0");//param=4表示查询已经预约但是还没维修的记录
        }
        if (param == 5) {
            sql.append(" where isRepairsFinish=1");
        }
        if (param == 6) {
            sql.append(" where isComment=1 and star>0");
        }

        sql.append(" order by id desc");

        if (pageBean != null) {
            sql.append(" limit " + pageBean.getStart() + " , " + pageBean.getPageSize());
        }

        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Wyfw_bxsq_repairsInfo info = new Wyfw_bxsq_repairsInfo();
            info.setId(rs.getInt("id"));
            info.setTitle(rs.getString("title"));
            info.setContent(rs.getString("content"));
            info.setPicUrl_beforeRepairs(rs.getString("picUrl_beforeRepairs"));
            info.setPicUrl_afterRepairs(rs.getString("picUrl_afterRepairs"));
            info.setRepairsDate(rs.getString("repairsDate"));
            info.setComment(rs.getString("comment"));
            info.setIsComment(rs.getInt("isComment"));
            info.setUserId(rs.getInt("userId"));
            info.setStar(rs.getInt("star"));
            info.setUserName(rs.getString("userName"));
            info.setIsRepairsFinish(rs.getInt("isRepairsFinish"));
            UserDao userDao = new UserDao();
            String phone = userDao.getPhoneById(con,rs.getInt("userId"));
            info.setPhone(phone);
            info.setBxTime(rs.getString("bxTime"));
            datas.add(info);
        }
        return datas;
    }

    //报表查看中的查询操作
    public List<Wyfw_bxsq_repairsInfo> getSearchDatas(Connection con, Wyfw_bxsq_repairsInfo info1)throws Exception{
        List<Wyfw_bxsq_repairsInfo> datas = new ArrayList<Wyfw_bxsq_repairsInfo>();
        StringBuffer sql = new StringBuffer("select * from wyfw_bxsq_repairsinfo");
        if (StringUtil.isNotEmpty(info1.getUserName())){
            sql.append(" and userName='"+info1.getUserName()+"'");
        }
        if (StringUtil.isNotEmpty(info1.getBxTime())){
            sql.append(" and bxTime like '"+info1.getBxTime()+"%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and","where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Wyfw_bxsq_repairsInfo info = new Wyfw_bxsq_repairsInfo();
            info.setId(rs.getInt("id"));
            info.setTitle(rs.getString("title"));
            info.setContent(rs.getString("content"));
            info.setPicUrl_beforeRepairs(rs.getString("picUrl_beforeRepairs"));
            info.setPicUrl_afterRepairs(rs.getString("picUrl_afterRepairs"));
            info.setRepairsDate(rs.getString("repairsDate"));
            info.setComment(rs.getString("comment"));
            info.setIsComment(rs.getInt("isComment"));
            info.setUserId(rs.getInt("userId"));
            info.setStar(rs.getInt("star"));
            info.setUserName(rs.getString("userName"));
            info.setIsRepairsFinish(rs.getInt("isRepairsFinish"));
            UserDao userDao = new UserDao();
            String phone = userDao.getPhoneById(con,rs.getInt("userId"));
            info.setPhone(phone);
            info.setBxTime(rs.getString("bxTime"));
            datas.add(info);
        }
        return datas;
    }


    //param=1表示物业公司发布预约上门维修的时间，param=2表示用户对该次维修进行评价
    public int updatePepairsInfo(Connection con, int param, Wyfw_bxsq_repairsInfo wyfw_bxsq_repairsInfo) throws Exception {
        if (param == 1) {
            //更新该条维修信息的预约维修信息
            String sql = "update wyfw_bxsq_repairsinfo set repairsDate=? where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, wyfw_bxsq_repairsInfo.getRepairsDate());
            pstmt.setInt(2, wyfw_bxsq_repairsInfo.getId());
            return pstmt.executeUpdate();
        } else if (param == 2) {
            //用户对本次维修进行评价
            String sql = "update wyfw_bxsq_repairsinfo set comment=?,picUrl_afterRepairs=?,star=?,isComment=1 where id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, wyfw_bxsq_repairsInfo.getComment());
            pstmt.setString(2, wyfw_bxsq_repairsInfo.getPicUrl_afterRepairs());
            pstmt.setInt(3, wyfw_bxsq_repairsInfo.getStar());
            pstmt.setInt(4, wyfw_bxsq_repairsInfo.getId());
            return pstmt.executeUpdate();

        } else {
            return -1;
        }


    }


    //查询某个用户已经维修但还没评论的维修信息的个数
    public int getCount_noComment(Connection con, int userId) throws Exception {
        String sql = "select * from wyfw_bxsq_repairsinfo where isRepairsFinish=1 and comment is null and userId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return 1;
        }
        return 0;
    }

    //根据id进行查找
    public Wyfw_bxsq_repairsInfo getDataById(Connection con, int id) throws Exception {
        String sql = "select * from wyfw_bxsq_repairsinfo where id=" + id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        Wyfw_bxsq_repairsInfo info = null;
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            info = new Wyfw_bxsq_repairsInfo();
            info.setId(rs.getInt("id"));
            info.setTitle(rs.getString("title"));
            info.setContent(rs.getString("content"));
            info.setPicUrl_beforeRepairs(rs.getString("picUrl_beforeRepairs"));
            info.setPicUrl_afterRepairs(rs.getString("picUrl_afterRepairs"));
            info.setRepairsDate(rs.getString("repairsDate"));
            info.setComment(rs.getString("comment"));
            info.setIsComment(rs.getInt("isComment"));
            info.setUserId(rs.getInt("userId"));
            info.setStar(rs.getInt("star"));
            info.setUserName(rs.getString("userName"));
            info.setIsRepairsFinish(rs.getInt("isRepairsFinish"));
            info.setBxTime(rs.getString("bxTime"));
        }
        return info;
    }


    //维修完成
    public int repairsFinish(Connection con, int id) throws Exception {
        String sql = "update wyfw_bxsq_repairsinfo set isRepairsFinish=1 where id=" + id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

    //获取已经维修完成的所有记录数
    public int getCountsOfRepairsFinish(Connection con)throws Exception{
        String sql = "select count(*) as counts from wyfw_bxsq_repairsinfo where isRepairsFinish=1";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }

    //删除指定id的报修信息
    public int deleteById(Connection con,int id)throws Exception{
        String  sql = "delete from wyfw_bxsq_repairsinfo where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeUpdate();
    }

    //根据id获取用户的alias
    public String getUserAliasById(Connection con,int id)throws Exception{
        String sql1 = "select userId from wyfw_bxsq_repairsinfo where id="+id;
        PreparedStatement pstmt = con.prepareStatement(sql1);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            int userId = rs.getInt("userId");
            String sql2 = "select alias from user where id="+userId;
            pstmt = con.prepareStatement(sql2);
            rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getString("alias");
            }
        }
        return null;
    }

    //获取所有记录的总数
    public int getDatasCount(Connection con) throws Exception{
        String sql = "select count(*) as counts from wyfw_bxsq_repairsinfo";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            return rs.getInt("counts");
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        Wyfw_bxsq_repairsInfo_dao dao = new Wyfw_bxsq_repairsInfo_dao();
        //用户插入一条预约的维修信息
        /*Wyfw_bxsq_repairsInfo info = new Wyfw_bxsq_repairsInfo();
        info.setUserId(1);
        info.setTitle("电路不通");
        info.setContent("主卧的店里不通，找个电工来维修一下");
        info.setPicUrl_beforeRepairs("/wyfw/images/aaa.png");
        dao.insertPepairsInfo(connection,info);
        System.out.print("维修信息插入成功");*/

        //查询还没有预约维修时间的维修信息,即第三个参数是1
        /*List<Wyfw_bxsq_repairsInfo> data1 = dao.getDates(connection,-1,1);
        Iterator<Wyfw_bxsq_repairsInfo> iterator = data1.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getTitle());
        }*/

        //物业部门与业主预约维修时间
        /*Wyfw_bxsq_repairsInfo info = new Wyfw_bxsq_repairsInfo();
        info.setRepairsDate("2016/06/30");
        info.setId(1);
        dao.updatePepairsInfo(connection,1,info);
        System.out.print("预约维修时间完成");*/

        //查询已经维修但是业主没有评论的维修信息
        /*List<Wyfw_bxsq_repairsInfo> data2 = dao.getDates(connection,-1,2);
        Iterator<Wyfw_bxsq_repairsInfo> iterator = data2.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getTitle());
        }*/

        //查询id为1的用户还没评论的维修信息
        //System.out.print(dao.getCount_noComment(connection,1));

        //查询某一个的所有申请记录
        /*List<Wyfw_bxsq_repairsInfo> data3 = dao.getDates(connection,1,3);
        Iterator<Wyfw_bxsq_repairsInfo> iterator = data3.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getTitle());
        }*/

        //用户对本次维修进行评论
        /*Wyfw_bxsq_repairsInfo info = new Wyfw_bxsq_repairsInfo();
        info.setId(1);
        info.setComment("对这次维修很满意，谢谢");
        info.setPicUrl_afterRepairs("/wyfw/images/after.png");
        info.setStar(5);
        dao.updatePepairsInfo(connection,2,info);
        System.out.print("评论完成");*/
    }



}
