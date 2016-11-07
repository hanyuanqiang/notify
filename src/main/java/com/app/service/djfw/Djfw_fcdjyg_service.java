package com.app.service.djfw;

import com.app.dao.djfw.Djfw_fcdjyg_dao;
import com.app.dao.yzfw.UserDao;
import com.app.model.User;
import com.app.model.djfw.Djfw_fcdjyg;
import com.app.utils.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

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
 * Created by Administrator on 2016/6/27.
 */
@WebServlet(name = "djfw_fcdjyg",urlPatterns = "/djfw/fcdjyg")
public class Djfw_fcdjyg_service extends HttpServlet{

    private Djfw_fcdjyg_dao djfw_fcdjyg_dao = new Djfw_fcdjyg_dao();
    private UserDao userDao = new UserDao();
    private JdbcPool pool = JdbcPool.getJdbcPool();
    private Connection connection = pool.getConnection();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean tag = true;
        request.setCharacterEncoding("utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, Boolean.TRUE);
        response.setContentType("text/html;charset=utf-8");
        //该参数用于判断请求是否来自app，如果from为null表示是网页请求，from=mp表示app请求，否则是非法请求
        String from = request.getParameter("from");
        Logger logger = Logger.getLogger(this.getClass().getName());
        PrintWriter out = response.getWriter();

        if(from==null){
            //处理网页请求
            try{
                String action = request.getParameter("action");
                int currentPage = 1;
                if(request.getParameter("currentPage")!=null){
                    currentPage = Integer.parseInt(request.getParameter("currentPage"));
                }
                PageBean pageBean = new PageBean(currentPage, Integer.parseInt(PropertiesUtil.getValue("pageSize")));
                if("addData".equals(action)){
                    //发布一条方寸党建阳光信息
                    Djfw_fcdjyg fcdjyg = new Djfw_fcdjyg();
                    String title = request.getParameter("title");
                    String contentTemp = request.getParameter("content");
                    String content = PicUtil.replaceImgSrc(request,response,contentTemp);
                    String type = request.getParameter("type");
                    String whoLook = request.getParameter("whoLook");//对谁可见
                    String userIdStr = "";
                    if("2".equals(whoLook)){
                        //表示对指定用户可见
                        userIdStr = request.getParameter("dangYuanId");
                        fcdjyg.setUserId(Integer.parseInt(userIdStr));
                    }
                    fcdjyg.setTitle(title);
                    fcdjyg.setWhoLook(whoLook);
                    fcdjyg.setContent(content);
                    fcdjyg.setPicUrl(request.getParameter("picUrl"));
                    fcdjyg.setType(type);
                    djfw_fcdjyg_dao.insertData(connection,fcdjyg);

                    String json = mapper.writeValueAsString(fcdjyg);
                    if ("0".equals(whoLook)){
                        //推送给所有用户
                        JPushUtil.pushToAll("社区消息","社区发布了新的党建文章",json);
                    }else if ("1".equals(whoLook)){
                        //推送给党员
                        JPushUtil.pushToGroup("社区消息","社区发布一则党员通知","dangyuantag",json);
                    }else if ("2".equals(whoLook)){
                        //推动给指定用户
                        JPushUtil.pushToOne("社区消息","社区给你发了一封私信",userDao.getAliasById(connection,Integer.parseInt(userIdStr)),json);
                    }

                }else if ("showOne".equals(action)){
                    //获取指定ID的信息
                    String idStr = request.getParameter("id");
                    if (StringUtil.isEmpty(idStr)){
                        throw new Exception();
                    }
                    Djfw_fcdjyg fcdjyg = djfw_fcdjyg_dao.getDataById(connection,Integer.parseInt(idStr));
                    if ("2".equals(fcdjyg.getWhoLook())){
                        fcdjyg.setUserName(userDao.getUserNameById(connection,fcdjyg.getUserId()));
                    }
                    if (fcdjyg==null){
                        throw new Exception();
                    }
                    String result = mapper.writeValueAsString(fcdjyg);
                    out.print(result);
                    out.flush();
                    out.close();
                    tag=false;
                }else if ("delete".equals(action)){
                    String id = request.getParameter("id");
                    if (StringUtil.isEmpty(id)){
                        throw new Exception();
                    }
                    djfw_fcdjyg_dao.deleteById(connection,Integer.parseInt(id));
                }else if("loadDangYuan".equals(action)){
                    User user = new User();
                    user.setIsDangYuan(1);
                    List<User> dangyuans = userDao.searchUsers(connection,user);
                    String result = mapper.writeValueAsString(dangyuans);
                    out.print(result);
                    out.flush();
                    out.close();
                    tag=false;
                }
                if (tag){

                    String contentType = request.getParameter("contentType");
                    request.setAttribute("contentType",contentType);
                    request.setAttribute("includePage","/shequ/djfw_fcdjyg.jsp");
                    request.setAttribute("pageCode", PageCode.getPageCode(djfw_fcdjyg_dao.getDatasCount(connection,contentType),
                            pageBean.getCurrentPage(),pageBean.getPageSize(),"/djfw/fcdjyg","&contentType="+contentType));
                    List<Djfw_fcdjyg> datas = djfw_fcdjyg_dao.getDatas(connection,pageBean,contentType,-1);
                    request.setAttribute("fcdjygDatas",datas);
                    request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
                }
            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }

        }else if ("mp".equals(from)){
            try {
                String userId = request.getParameter("userId");
                if (StringUtil.isEmpty(userId)){
                    throw new Exception();
                }
                List<Djfw_fcdjyg> datas = djfw_fcdjyg_dao.getDatas(connection,null,null,Integer.parseInt(userId));
                Map<String,Object> resultMap = new HashMap<String, Object>();
                resultMap.put("status","1");
                resultMap.put("result",datas);
                String resultjson = mapper.writeValueAsString(resultMap);
                out.print(resultjson);
            } catch (Exception e) {
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
