package com.app.service.sqfw;

import com.app.dao.sqfw.Sqfw_sstz_dao;
import com.app.model.sqfw.Sqfw_sstz;
import com.app.utils.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;

import javax.servlet.ServletException;
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
@WebServlet(name = "sqfw_sstz",urlPatterns = "/sqfw/sstz")
public class Sqfw_sstz_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Sqfw_sstz_dao dao = new Sqfw_sstz_dao();
    private JdbcPool pool = JdbcPool.getJdbcPool();
    private Connection connection = pool.getConnection();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String from = request.getParameter("from");
        PrintWriter out = response.getWriter();


        if(StringUtil.isEmpty(from)){
            //处理网页上的相关请求
            try {
                String action = request.getParameter("action");
                if(action.equals("publish")){
                    //如果返回true表示插入一条实时通知成功，进而跳转页面进行显示，否则表示仅仅跳转到要发布的页面
                    if (publish(request,response)){
                        action="showAll";
                    }
                }

                if (action.equals("delete")){
                    delete(request,response);
                    action="showAll";
                }

                if (action.equals("showAll")){
                    showAll(request,response);
                }
                if (action.equals("showOne")){
                    showOne(request,response);
                }
                request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }


        }else if("mp".equals(from)){
            //处理app端相关请求
            //获取实时通知
            try {
                List<Sqfw_sstz> datas = dao.getDatas(connection,null,null);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("result",datas);
                map.put("status",1);
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

    //删除一条实时通知内容
    public boolean delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id =  request.getParameter("id");
            if (StringUtil.isEmpty(id)){
                return false;
            }
            int delNum = dao.deleteById(connection,Integer.parseInt(id));
            if (delNum>0){
                return true;
            }
            return  false;
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
        return true;
    }


    //插入一条实时通知内容
    public boolean publish(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String type = request.getParameter("type");
            String title = request.getParameter("title");
            String contentTemp = request.getParameter("content");
            String content = PicUtil.replaceImgSrc(request,response,contentTemp);
            //如果以下字段为空表示跳转页面
            if (StringUtil.isEmpty(type)||
                    StringUtil.isEmpty(title)||
                    StringUtil.isEmpty(content)){
                request.setAttribute("includePage","/shequ/sstz_publish.jsp");
                return false;
            }
            String picUrl = request.getParameter("picUrl");
            Sqfw_sstz sstz = new Sqfw_sstz();
            sstz.setType(type);
            sstz.setPicUrl(picUrl);
            sstz.setTitle(title);
            sstz.setContent(content);
            int temp  = dao.insertData(connection,sstz);
            //获取最新发布的文章的json
            String json = mapper.writeValueAsString(dao.getNewestData(connection));
            JPushUtil.pushToAll("社区通知","社区发布了新的实时通知",json);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
        return true;
    }

    //按获取指定分页的数据
    public void showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int currentPage = 1;
            if(request.getParameter("currentPage")!=null){
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            }
            PageBean pageBean = new PageBean(currentPage,Integer.parseInt(PropertiesUtil.getValue("pageSize")));

            String contentType = request.getParameter("contentType");
            request.setAttribute("contentType",contentType);

            List<Sqfw_sstz> datas = null;
            if (StringUtil.isNotEmpty(contentType)){
                //获取指定类别的数据
                datas = dao.getDatas(connection,pageBean,contentType);
            }else {
                //获取所有数据
                datas = dao.getDatas(connection,pageBean,contentType);
            }

            //获取分页代码
            String pageCode = PageCode.getPageCode(dao.getDatasCount(connection,contentType),
                    pageBean.getCurrentPage(), pageBean.getPageSize(),"/sqfw/sstz","&action=showAll&&contentType="+contentType);

            request.setAttribute("includePage","/shequ/sstz_showAll.jsp");
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
            Sqfw_sstz data = dao.getOneData(connection,Integer.parseInt(id));
            request.setAttribute("data",data);
            request.setAttribute("includePage","/shequ/sstz_showOne.jsp");
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }
}
