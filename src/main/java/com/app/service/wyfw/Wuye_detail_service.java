package com.app.service.wyfw;

import com.app.dao.wyfw.Wuye_detail_dao;
import com.app.dao.wyfw.Wyfw_bxsq_repairsInfo_dao;
import com.app.model.wyfw.Wuye_detail;
import com.app.model.wyfw.Wyfw_bxsq_repairsInfo;
import com.app.utils.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
@WebServlet(name = "Wuye_detail_service",urlPatterns = "/wyfw/detail")
@MultipartConfig
public class Wuye_detail_service extends HttpServlet{

    private Wuye_detail_dao dao = new Wuye_detail_dao();
    private ObjectMapper mapper = new ObjectMapper();
    private Wyfw_bxsq_repairsInfo_dao info_dao = new Wyfw_bxsq_repairsInfo_dao();
    private Logger logger = Logger.getLogger(this.getClass());
    private JdbcPool pool = JdbcPool.getJdbcPool();
    private Connection connection = pool.getConnection();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String from = request.getParameter("from");
        PrintWriter out = response.getWriter();

        if(StringUtil.isEmpty(from)){
            //这里处理网页上的相关请求

            String page = request.getParameter("page")+"";
            try {
                String action = request.getParameter("action")+"";

                if (action.equals("show")){
                    //展示物业公司的一些基本信息
                    Wuye_detail detail = dao.getDatas(connection);
                    request.setAttribute("data",detail);
                    request.setAttribute("includePage","/wuye/wuye_detail_show.jsp");
                    if (page.equals("shequ")){
                        request.setAttribute("page",page);
                        List<Wyfw_bxsq_repairsInfo> comments = info_dao.getDates(connection,-1,6,null);
                        request.setAttribute("comments",comments);
                    }
                }else if (action.equals("updatePage")){
                    //进入基本信息编辑页面
                    Wuye_detail detail = dao.getDatas(connection);
                    request.setAttribute("data",detail);
                    request.setAttribute("includePage","/wuye/wuye_detail_updatePage.jsp");
                }else if (action.equals("update")){
                    //更新基本信息
                    String message = request.getParameter("message");
                    String person = request.getParameter("person");
                    String hardset = request.getParameter("hardset");
                    String others = request.getParameter("others");
                    String picUrl = PicUtil.getPicUrl(request,response,this);
                    Wuye_detail detail = new Wuye_detail();
                    detail.setMessage(message);
                    detail.setPerson(person);
                    detail.setHardset(hardset);
                    detail.setOthers(others);
                    detail.setPicUrl(picUrl);
                    dao.updateData(connection,detail);
                    detail = dao.getDatas(connection);
                    request.setAttribute("data",detail);
                    request.setAttribute("includePage","/wuye/wuye_detail_show.jsp");
                }
                if (page.equals("shequ")){
                    request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
                }else {
                    request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
                }
            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }

        }else if(from.equals("mp")){
            //这里处理手机app上的相关请求
            try{
                Wuye_detail wuye_detail = dao.getDatas(connection);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("status",1);
                map.put("result",wuye_detail);
                String result = mapper.writeValueAsString(map);
                out.print(result);
            }catch(Exception e){
                out.print("{\"status\":0}");
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }
        }else{
            response.sendRedirect("/unknown.jsp");
        }


    }
}
