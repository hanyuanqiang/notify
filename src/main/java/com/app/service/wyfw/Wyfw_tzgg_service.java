package com.app.service.wyfw;

import com.app.dao.wyfw.Wyfw_tzgg_dao;
import com.app.model.wyfw.Wyfw_tzgg;
import com.app.utils.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
@WebServlet(name = "wyfw_tzgg",urlPatterns = "/wyfw/tzgg")
public class Wyfw_tzgg_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Wyfw_tzgg_dao dao = new Wyfw_tzgg_dao();
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
            //这里处理网页上传来的请求

            try {
                String action = request.getParameter("action");
                if(action.equals("publish")){
                    //如果返回true表示插入一条通知公告成功，进而跳转页面进行显示，否则表示仅仅跳转到要发布的页面
                    if (publish(request,response)){
                        action="showAll";
                    }
                }
                if (action.equals("delete")){
                    String id = request.getParameter("id");
                    if (StringUtil.isEmpty(id)){
                        throw new Exception();
                    }
                    dao.deleteById(connection,Integer.parseInt(id));
                    action = "showAll";
                }

                if (action.equals("showAll")){
                    showAll(request,response);
                }
                if (action.equals("showOne")){
                    showOne(request,response);
                }
                request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }


        }else if("mp".equals(from)){
            //这里处理手机客户端传来的请求

            try {

                List<Wyfw_tzgg> datas  = dao.getDatas(connection,null);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("status",1);
                map.put("result",datas);
                String result = mapper.writeValueAsString(map);
                out.print(result);
            }catch (Exception e){
                out.print("{\"status\":0}");
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }

        }else{
            //处理未知请求
            response.sendRedirect("/unknown.jsp");
        }
    }

    public boolean publish(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String contentTemp = request.getParameter("content");
            String content = PicUtil.replaceImgSrc(request,response,contentTemp);
            //如果以下字段为空表示跳转页面
            if (StringUtil.isEmpty(title)||
                    StringUtil.isEmpty(content)){
                request.setAttribute("includePage","/wuye/wyfw_tzgg_publish.jsp");
                return false;
            }
            String picUrl = request.getParameter("picUrl");
            Wyfw_tzgg tzgg = new Wyfw_tzgg();
            tzgg.setPicUrl(picUrl);
            tzgg.setTitle(title);
            tzgg.setContent(content);
            dao.insertData(connection,tzgg);
            tzgg.setPublishDate(DateUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss"));
            String json = mapper.writeValueAsString(tzgg);
            JPushUtil.pushToAll("物业通知","物业发布了新的通知公告",json);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
        return true;
    }

    public void showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int currentPage = 1;
            if(request.getParameter("currentPage")!=null){
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            }
            PageBean pageBean = new PageBean(currentPage,Integer.parseInt(PropertiesUtil.getValue("pageSize")));
            //获取分页代码
            String pageCode = PageCode.getPageCode(dao.getDatasCount(connection),
                    pageBean.getCurrentPage(), pageBean.getPageSize(),"/wyfw/tzgg","&action=showAll");
            List<Wyfw_tzgg> datas = dao.getDatas(connection,pageBean);
            request.setAttribute("includePage","/wuye/wyfw_tzgg_showAll.jsp");
            request.setAttribute("datas",datas);
            request.setAttribute("pageCode",pageCode);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    public void showOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            if (StringUtil.isEmpty(id)){
                response.getWriter().print("访问出错");
            }
            Wyfw_tzgg data = dao.getOneData(connection,Integer.parseInt(id));
            request.setAttribute("data",data);
            request.setAttribute("includePage","/wuye/wyfw_tzgg_showOne.jsp");
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }
}
