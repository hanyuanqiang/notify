package com.app.service.sqfw;

import com.app.dao.sqfw.Sqfw_hdly_dao;
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
@WebServlet(name = "sqfw_hdly",urlPatterns = "/sqfw/hdly")
public class Sqfw_hdly_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Sqfw_hdly_dao dao  = new Sqfw_hdly_dao();
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

                String action = request.getParameter("action")+"";
                if (action.equals("showOne")){
                    //获取指定id的信息(ajax)
                    String idStr = request.getParameter("id");
                    if(StringUtil.isEmpty(idStr)){
                        throw new Exception();
                    }
                    Sqfw_hdly hdly = dao.getDatas(connection,Integer.parseInt(idStr),null).get(0);
                    String result = mapper.writeValueAsString(hdly);
                    out.print(result);
                    out.flush();
                    out.close();
                    tag = false;
                }else if (action.equals("publish")){
                    //发布一条新的活动掠影
                    String title = request.getParameter("title");
                    String contentTemp = request.getParameter("content");
                    String content = PicUtil.replaceImgSrc(request,response,contentTemp);
                    String type = request.getParameter("type");
                    Sqfw_hdly hdly = new Sqfw_hdly();
                    hdly.setTitle(title);
                    hdly.setContent(content);
                    hdly.setType(type);
                    dao.insertDate(connection,hdly);
                }else if (action.equals("delete")){
                    String id = request.getParameter("id");
                    if (StringUtil.isEmpty(id)){
                        throw new Exception();
                    }
                    dao.deleteById(connection,Integer.parseInt(id));
                }
                if (tag){
                    List<Sqfw_hdly> datas = dao.getDatas(connection,-1,pageBean);
                    request.setAttribute("hdlyDatas",datas);
                    String pageCode = PageCode.getPageCode(dao.getDatasCount(connection),
                            pageBean.getCurrentPage(),pageBean.getPageSize(),"/sqfw/hdly","");
                    request.setAttribute("includePage","/shequ/sqfw_hdly.jsp");
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
                //如果id为空，则表示查询所有的活动掠影信息，否则查询指定id的信息
                if (StringUtil.isEmpty(id)){
                    List<Sqfw_hdly> datas = dao.getDatas(connection,-1,null);
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("result",datas);
                    map.put("status",1);
                    String result = mapper.writeValueAsString(map);
                    out.print(result);
                }else {
                    List<Sqfw_hdly> datas = dao.getDatas(connection,Integer.parseInt(id),null);
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
