package com.app.service.shfw;

import com.app.dao.shfw.Shfw_dao;
import com.app.model.shfw.Shfw;
import com.app.utils.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;

import javax.persistence.criteria.CriteriaBuilder;
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
@WebServlet(name = "shfw",urlPatterns = "/shfw")
public class Shfw_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Shfw_dao dao = new Shfw_dao();
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
            String handle = request.getParameter("handle")+"";
            if (handle.equals("cyl")){
                //处理餐饮类信息
                cylHandle(request,response);
            }else if (handle.equals("fwl")){
                //处理服务类信息
                fwlHandle(request,response);
            }else if (handle.equals("xsl")){
                //处理行宿类信息
                xslHandle(request,response);
            }else if (handle.equals("add")){
                //增加一条服务类信息
                addHandle(request,response);
            }



        }else if("mp".equals(from)){
            //处理app端相关请求
            try {
                Map<String,List<com.app.model.shfw.Shfw>> datas = dao.getDatas(connection);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("result",datas);
                map.put("status",1);
                String result = mapper.writeValueAsString(map);
                out.print(result);
            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
                out.print("{\"status\":0}");
            }finally {
                pool.returnConnection(connection);
            }

        }else{
            //处理未知请求
            response.sendRedirect("/unknown.jsp");
        }


    }

    public void cylHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        boolean tag = true;
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("showDetail")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Shfw shfw = dao.getDataById(connection, Integer.parseInt(idStr));
                String result = mapper.writeValueAsString(shfw);
                out.print(result);
                out.flush();
                out.close();
                tag = false;
            }else if (action.equals("delete")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                dao.deleteDataById(connection,Integer.parseInt(idStr));
            }

            if (tag){
                Map<String,List<Shfw>> map = dao.getDatas(connection);
                List<Shfw> cylDatas = map.get("餐饮类");
                request.setAttribute("cylDatas",cylDatas);
                request.setAttribute("dataListPage","/wuye/shfw_cyl.jsp");
                request.setAttribute("includePage","/wuye/shfw.jsp");
                request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    public void fwlHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        boolean tag = true;
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("showDetail")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Shfw shfw = dao.getDataById(connection, Integer.parseInt(idStr));
                String result = mapper.writeValueAsString(shfw);
                out.print(result);
                out.flush();
                out.close();
                tag = false;
            }else if (action.equals("delete")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                dao.deleteDataById(connection,Integer.parseInt(idStr));
            }

            if (tag){
                Map<String,List<Shfw>> map = dao.getDatas(connection);
                List<Shfw> cylDatas = map.get("服务类");
                request.setAttribute("fwlDatas",cylDatas);
                request.setAttribute("dataListPage","/wuye/shfw_fwl.jsp");
                request.setAttribute("includePage","/wuye/shfw.jsp");
                request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    public void xslHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        boolean tag = true;
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("showDetail")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Shfw shfw = dao.getDataById(connection, Integer.parseInt(idStr));
                String result = mapper.writeValueAsString(shfw);
                out.print(result);
                out.flush();
                out.close();
                tag = false;
            }else if (action.equals("delete")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                dao.deleteDataById(connection,Integer.parseInt(idStr));
            }

            if (tag){
                Map<String,List<Shfw>> map = dao.getDatas(connection);
                List<Shfw> cylDatas = map.get("行宿类");
                request.setAttribute("xslDatas",cylDatas);
                request.setAttribute("dataListPage","/wuye/shfw_xsl.jsp");
                request.setAttribute("includePage","/wuye/shfw.jsp");
                request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    public void addHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean tag = true;
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("showAddPage")){
                request.setAttribute("dataListPage","/wuye/shfw_add.jsp");
            }else if (action.equals("addData")){
                String title = request.getParameter("title");
                String contentTemp = request.getParameter("content");
                String content = PicUtil.replaceImgSrc(request,response,contentTemp);
                String type = request.getParameter("type");
                String phone = request.getParameter("phone");
                if (StringUtil.isEmpty(title)||StringUtil.isEmpty(contentTemp)||
                        StringUtil.isEmpty(type)||StringUtil.isEmpty(phone)){
                    throw new Exception();
                }
                Shfw shfw = new Shfw();
                shfw.setPhone(phone);
                shfw.setContent(content);
                shfw.setTitle(title);
                shfw.setType(type);
                dao.insertData(connection,shfw);
                tag = false;
                if (type.equals("餐饮类")){
                    cylHandle(request,response);
                }else if (type.equals("服务类")){
                    fwlHandle(request,response);
                }else if (type.equals("行宿类")){
                    xslHandle(request,response);
                }
            }
            if (tag){
                request.setAttribute("includePage","/wuye/shfw.jsp");
                request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
            }

        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }
}
