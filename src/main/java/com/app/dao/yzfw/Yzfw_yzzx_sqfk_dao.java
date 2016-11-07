package com.app.dao.yzfw;

import com.app.dao.djfw.Djfw_djzs_dao;
import com.app.model.djfw.Djfw_djzs_data;
import com.app.model.wyfw.Wyfw_tsjy;
import com.app.utils.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/7/2.
 */
public class Yzfw_yzzx_sqfk_dao {


    //获取党建助手中社区的回复
    public List<Djfw_djzs_data> getDjzsDatas(Connection con,int userId)throws Exception{
        //List<Integer> ids = new ArrayList<Integer>();
        List<Djfw_djzs_data> datas = new ArrayList<Djfw_djzs_data>();
        //String sql = "select * from djfw_djzs_ask where status=1 and isPush=0 and userId="+userId;
        String sql = "select * from djfw_djzs_ask where status=1 and userId="+userId+" ORDER BY timestamp DESC";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Djfw_djzs_data data = new Djfw_djzs_data();
            data.setId(rs.getInt("id"));
            data.setResponse(rs.getString("response"));
            data.setStatus(rs.getInt("status"));
            data.setContent(rs.getString("content"));
            data.setUserId(rs.getInt("userId"));
            data.setTimestamp(DateUtil.cutString(rs.getString("timestamp")));
            data.setIsPush(rs.getInt("isPush"));
            data.setTitle(rs.getString("title"));
            datas.add(data);
            //ids.add(rs.getInt("id"));
        }
        //如果查到社区已经回复但又还没推送的消息，则把isPush设为1(即已经推送过了)
        /*if (ids.size()>0){
            this.updateDjzsDatas(connection,ids,0);
        }*/
        return datas;
    }

    //获取物业服务中的投诉建议
    public List<Wyfw_tsjy> getTsjyDatas(Connection con,int userId)throws Exception{
        //List<Integer> ids = new ArrayList<Integer>();
        List<Wyfw_tsjy> datas = new ArrayList<Wyfw_tsjy>();
        //String sql = "select * from wyfw_tsjy where shequReplay is not null and isShequPush=0 and userId="+userId;
        String sql = "select * from wyfw_tsjy where shequReplay is not null and userId="+userId+" ORDER BY complainDate DESC";
        PreparedStatement pstmt =  con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Wyfw_tsjy tsjy = new Wyfw_tsjy();
            tsjy.setId(rs.getInt("id"));
            tsjy.setUserId(rs.getInt("userId"));
            tsjy.setComplainContent(rs.getString("complainContent"));
            tsjy.setComplainDate(DateUtil.cutString(rs.getString("complainDate")));
            tsjy.setShequReplay(rs.getString("shequReplay"));
            datas.add(tsjy);
            //ids.add(rs.getInt("id"));
        }
        /*if(ids.size()>0){
            this.updateDjzsDatas(connection,ids,1);//每次查询到数据后都会把本条数据设为已经推送过的数据(即isShequPush=1)
        }*/

        return datas;

    }



    //把一些信息更新为已经推送过了
    //param=0表示更新党建助手中的信息
    //param=1表示更新投诉建议中的信息
    public static int updateDjzsDatas(Connection con,List<Integer> ids,int param)throws Exception{
        String s = "";
        if(param==0){
            s="update djfw_djzs_ask set isPush=1 where id in (";
        }else if(param==1){
            s="update wyfw_tsjy set isShequPush=1 where id in (";
        }

        StringBuffer sql = new StringBuffer(s);
        for(int i=0;i<ids.size();i++){
            if(i==ids.size()-1){
                sql.append(ids.get(i));
            }else{
                sql.append(ids.get(i)+",");
            }
        }
        sql.append(")");
        PreparedStatement pstmt = con.prepareStatement(sql.toString());
        return pstmt.executeUpdate();
    }



    public static void main(String[] args) throws Exception{
        Yzfw_yzzx_sqfk_dao dao = new Yzfw_yzzx_sqfk_dao();
        //获取还没有推送过的，社区人员回复了的某个用户的咨询信息。
        /*List<Djfw_djzs_data> datas = dao.getDjzsDatas(connection,1);
        Iterator<Djfw_djzs_data> iterator = datas.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getTitle());
        }*/

        //获取投诉建议中的社区人员已经回复但是还没有推送到社区反馈中的信息
        /*List<Wyfw_tsjy> datas = dao.getTsjyDatas(connection,1);
        Iterator<Wyfw_tsjy> iterator = datas.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getShequReplay());
        }*/
    }
}
