package com.app.service.sqfw;

import com.app.dao.sqfw.Sqfw_bmfw_dao;
import com.app.model.sqfw.Sqfw_bmfw;
import com.app.model.sqfw.Sqfw_hdly;
import com.app.utils.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

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
@WebServlet(name = "sqfw_bmfw",urlPatterns = "/sqfw/bmfw")
public class Sqfw_bmfw_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Sqfw_bmfw_dao dao = new Sqfw_bmfw_dao();
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
            boolean tag = true;
            try {
                int currentPage = 1;
                if(request.getParameter("currentPage")!=null){
                    currentPage = Integer.parseInt(request.getParameter("currentPage"));
                }
                PageBean pageBean = new PageBean(currentPage, Integer.parseInt(PropertiesUtil.getValue("pageSize")));

                String action = request.getParameter("action");
                if ("showOne".equals(action)){
                    //查看指定id便民信息的详情
                    //获取指定id的信息(ajax)
                    String idStr = request.getParameter("id");
                    if(StringUtil.isEmpty(idStr)){
                        throw new Exception();
                    }
                    Sqfw_bmfw bmfw = dao.getDatas(connection,Integer.parseInt(idStr),null).get(0);
                    String result = mapper.writeValueAsString(bmfw);
                    out.print(result);
                    out.flush();
                    out.close();
                    tag = false;

                }else if ("publish".equals(action)){
                    //社区人员发布一条便民信息
                    String title = request.getParameter("title");
                    String contentTemp = request.getParameter("content");
                    String content = PicUtil.replaceImgSrc(request,response,contentTemp);
                    String type = request.getParameter("type");
                    Sqfw_bmfw bmfw = new Sqfw_bmfw();
                    bmfw.setTitle(title);
                    bmfw.setContent(content);
                    bmfw.setType(type);
                    dao.insertDate(connection,bmfw);
                }else if("delete".equals(action)){
                    String id = request.getParameter("id");
                    if (StringUtil.isEmpty(id)){
                        throw new Exception();
                    }
                    dao.deleteById(connection,Integer.parseInt(id));
                }
                if (tag){

                    String contentType = request.getParameter("contentType");
                    request.setAttribute("contentType",contentType);

                    List<Sqfw_bmfw> datas = null;
                    if (StringUtil.isNotEmpty(contentType)){
                        //按分类查找
                        datas = dao.getDatasByType(connection,pageBean,contentType);
                    }else {
                        //查找所有
                        datas = dao.getDatas(connection,-1,pageBean);
                    }

                    request.setAttribute("bmfwDatas",datas);
                    String pageCode = PageCode.getPageCode(dao.getDatasCount(connection,contentType),
                            pageBean.getCurrentPage(),pageBean.getPageSize(),"/sqfw/bmfw","&contentType="+contentType);
                    request.setAttribute("includePage","/shequ/sqfw_bmfw.jsp");
                    request.setAttribute("pageCode",pageCode);
                    request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
                }


            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }


        }else if("mp".equals(from)){
            //处理app端相关请求

            try{
                String id = request.getParameter("id");
                //如果id为空，则表示查询所有的便民服务信息，否则查询指定id的信息
                if (StringUtil.isEmpty(id)){
                    List<Sqfw_bmfw> datas = dao.getDatas(connection,-1,null);
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("result",datas);
                    map.put("status",1);
                    String result = mapper.writeValueAsString(map);
                    out.print(result);
                }else {
                    List<Sqfw_bmfw> datas = dao.getDatas(connection,Integer.parseInt(id),null);
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("result",datas.get(0));
                    map.put("status",1);
                    String result = mapper.writeValueAsString(map);
                    out.print(result);
                }
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
}
