package com.app.service.yzfw;

import com.app.dao.yzfw.Yzfw_xfzj_dao;
import com.app.model.yzfw.Yzfw_xfzj;
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
@WebServlet(name = "yzfw_xfzj",urlPatterns = "/yzfw/xfzj")
@MultipartConfig
public class Yzfw_xfzj_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Yzfw_xfzj_dao dao = new Yzfw_xfzj_dao();
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
                if (request.getParameter("currentPage") != null) {
                    currentPage = Integer.parseInt(request.getParameter("currentPage"));
                }
                PageBean pageBean = new PageBean(currentPage, Integer.parseInt(PropertiesUtil.getValue("pageSize")));

                String action = request.getParameter("action");
                if ("showOne".equals(action)) {
                    //查看某个幸福之家的详情
                    //获取指定id的信息(ajax)
                    String idStr = request.getParameter("id");
                    if (StringUtil.isEmpty(idStr)) {
                        throw new Exception();
                    }
                    Yzfw_xfzj llhd = dao.getDatas(connection, Integer.parseInt(idStr), null).get(0);
                    String result = mapper.writeValueAsString(llhd);
                    out.print(result);
                    out.flush();
                    out.close();
                    tag = false;

                } else if ("publish".equals(action)) {
                    //社区人员发布一条便民信息
                    String name = request.getParameter("name");
                    String title = request.getParameter("title");
                    String contentTemp = request.getParameter("content");
                    String content = PicUtil.replaceImgSrc(request, response, contentTemp);
                    String type = request.getParameter("type");
                    Yzfw_xfzj xfzj = new Yzfw_xfzj();
                    xfzj.setName(name);
                    xfzj.setTitle(title);
                    xfzj.setContent(content);
                    xfzj.setType(type);
                    dao.insertData(connection,xfzj);
                }else if ("delete".equals(action)){
                    String id = request.getParameter("id");
                    if (StringUtil.isEmpty(id)){
                        throw new Exception();
                    }
                    dao.deleteData(connection,Integer.parseInt(id));
                }
                if (tag) {

                    String contentType = request.getParameter("contentType");
                    request.setAttribute("contentType",contentType);
                    List<Yzfw_xfzj> datas = null;

                    if (StringUtil.isNotEmpty(contentType)){
                        //按分类查找
                        datas = dao.getDatasByType(connection,pageBean,contentType);
                    }else {
                        //查找所有
                        datas = dao.getDatas(connection, -1, pageBean);
                    }

                    request.setAttribute("xfzjDatas", datas);
                    String mark = request.getParameter("mark")+"";
                    if (!mark.equals("shequ")&&!mark.equals("wuye")){
                        throw new Exception();
                    }
                    String pageCode = PageCode.getPageCode(dao.getDatasCount(connection,contentType),
                            pageBean.getCurrentPage(), pageBean.getPageSize(), "/yzfw/xfzj", "&mark="+mark+"&contentType="+contentType);
                    request.setAttribute("includePage", "/shequ/yzfw_xfzj.jsp");
                    request.setAttribute("pageCode", pageCode);
                    if (mark.equals("shequ")){
                        request.getRequestDispatcher("/shequMainPage.jsp").forward(request, response);
                    }else if (mark.equals("wuye")){
                        request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request, response);
                    }
                }
            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }


        }else if("mp".equals(from)){
            //处理app端相关请求

            try {

                String action = request.getParameter("action");
                if (action.equals("add")){
                    //插入一条幸福之家信息
                    addData(request,response);
                }else if (action.equals("s_all")){
                    //查询所有的幸福之家信息
                    searchDatas(request,response,-1);
                }else if (action.equals("s_byId")){
                    //查找指定Id的幸福之家信息
                    String id = request.getParameter("id");
                    if (StringUtil.isEmpty(id)){
                        throw new Exception();
                    }
                    searchDatas(request,response,Integer.parseInt(id));
                }else {
                    throw new Exception();
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

    //增加一条幸福之家信息
    public void addData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String name = request.getParameter("name");
            String contentTemp = request.getParameter("content");
            String content = PicUtil.replaceImgSrc(request,response,contentTemp);
            String title = request.getParameter("title");
            String type = request.getParameter("type");
            logger.error("type1:"+type);
            String picUrl = PicUtil.getPicUrl(request,response,this);
            if (StringUtil.isEmpty(title)||StringUtil.isEmpty(contentTemp)||
                    StringUtil.isEmpty(type)||StringUtil.isEmpty(name)){
                throw new Exception();
            }
            Yzfw_xfzj xfzj = new Yzfw_xfzj();
            xfzj.setTitle(title);
            xfzj.setContent(content);
            xfzj.setPicUrl(picUrl);
            xfzj.setName(name);
            xfzj.setType(type);
            dao.insertData(connection,xfzj);
            out.print("{\"status\":1}");
        }catch (Exception e){
            out.print("{\"status\":0}");
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    //根据tag参数不同，按不同条件查询数据，tag=-1表示查询所有，否则按指定Id查询
    public void searchDatas(HttpServletRequest request, HttpServletResponse response,int tag) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {

            List<Yzfw_xfzj> datas = dao.getDatas(connection,tag,null);
            Map<String,Object> map = new HashMap<String, Object>();
            if (tag==-1){
                map.put("result",datas);
            }else {
                map.put("result",datas.get(0));
            }
            map.put("status",1);
            String result = mapper.writeValueAsString(map);
            out.print(result);
        }catch (Exception e){
            out.print("{\"status\":0}");
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }
}
