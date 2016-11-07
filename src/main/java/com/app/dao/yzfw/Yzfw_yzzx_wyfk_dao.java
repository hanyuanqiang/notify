package com.app.dao.yzfw;

import com.app.model.wyfw.Wyfw_tsjy;
import com.app.utils.DateUtil;

import java.security.Permission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/7/2.
 */
public class Yzfw_yzzx_wyfk_dao {

    //获取物业服务中投诉建议的信息
    public List<Wyfw_tsjy> getDatas(Connection con,int userId)throws Exception{
        //List<Integer> ids = new ArrayList<Integer>();
        List<Wyfw_tsjy> datas = new ArrayList<Wyfw_tsjy>();
        //String sql = "select * from wyfw_tsjy where wuyeReplay is not null and isWuyePush=0 and userId="+userId;
        String sql = "select * from wyfw_tsjy where wuyeReplay is not null and userId="+userId;
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            Wyfw_tsjy tsjy = new Wyfw_tsjy();
            tsjy.setId(rs.getInt("id"));
            tsjy.setUserId(rs.getInt("userId"));
            tsjy.setComplainContent(rs.getString("complainContent"));
            tsjy.setComplainDate(DateUtil.cutString(rs.getString("complainDate")));
            tsjy.setWuyeReplay(rs.getString("wuyeReplay"));
            //ids.add(rs.getInt("id"));
            datas.add(tsjy);
        }
        /*if (ids.size()>0){
            this.updateDatas(connection,ids);
        }*/
        return datas;

    }

    //根据Id把一些信息设置为已经推送到物业反馈中(即isWuyePush=1)
    public static int updateDatas(Connection con,List<Integer> ids)throws Exception{
        StringBuffer sql = new StringBuffer("update wyfw_tsjy set isWuyePush=1 where id in (");
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

    /*public static void main(String[] args) throws Exception{
        Yzfw_yzzx_wyfk_dao dao  = new Yzfw_yzzx_wyfk_dao();
        List<Wyfw_tsjy> tsjy = dao.getDatas(connection,1);
        Iterator<Wyfw_tsjy> iterator = tsjy.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getWuyeReplay());
        }
    }*/
}
