package com.app.service.sqfw;

import com.app.dao.sqfw.Sqfw_hdzj_info_dao;
import com.app.dao.sqfw.Sqfw_hdzj_participants_dao;
import com.app.model.sqfw.Sqfw_hdzj_info;
import com.app.model.sqfw.Sqfw_hdzj_participants;
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
@WebServlet(name = "sqfw_hdzj",urlPatterns = "/sqfw/hdzj")
public class Sqfw_hdzj_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Sqfw_hdzj_info_dao info_dao = new Sqfw_hdzj_info_dao();
    private Sqfw_hdzj_participants_dao participants_dao = new Sqfw_hdzj_participants_dao();
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
                if (action.equals("publish")){
                    //发布一条活动召集信息
                    String title = request.getParameter("title");
                    String contentTemp = request.getParameter("content");
                    String content = PicUtil.replaceImgSrc(request,response,contentTemp);
                    Sqfw_hdzj_info info = new Sqfw_hdzj_info();
                    info.setTitle(title);
                    info.setContent(content);
                    info_dao.insertData(connection,info);

                }else if (action.equals("showOne")){
                    //获取指定id的活动信息
                    String idStr = request.getParameter("id");
                    if (StringUtil.isEmpty(idStr)){
                        throw new Exception();
                    }
                    Sqfw_hdzj_info info = info_dao.getDataById(connection,Integer.parseInt(idStr));
                    String result = mapper.writeValueAsString(info);
                    out.print(result);
                    out.flush();
                    out.close();
                    tag=false;
                }else if (action.equals("showParticipants")){
                    //获取某个活动的报名情况
                    String infoIdStr = request.getParameter("infoId");
                    if (StringUtil.isEmpty(infoIdStr)){
                        throw new Exception();
                    }
                    List<Sqfw_hdzj_participants> participants= participants_dao.getDatas(connection,Integer.parseInt(infoIdStr),pageBean);
                    request.setAttribute("participantsDatas",participants);
                    String pageCode = PageCode.getPageCode(participants_dao.getDatasCount(connection),
                            pageBean.getCurrentPage(), pageBean.getPageSize(),
                            "/sqfw/hdzj","&action=showParticipants&infoId="+Integer.parseInt(infoIdStr));
                    request.setAttribute("includePage","/shequ/sqfw_hdzj_participants.jsp");
                    request.setAttribute("pageCode",pageCode);
                    request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
                    tag=false;
                }else if (action.equals("deleteParticipant")){
                    //删除某个报名的人员
                    String infoIdStr = request.getParameter("infoId");
                    String idStr = request.getParameter("id");
                    if (StringUtil.isEmpty(idStr)){
                        throw new Exception();
                    }
                    participants_dao.deleteData(connection,Integer.parseInt(idStr));
                    List<Sqfw_hdzj_participants> participants= participants_dao.getDatas(connection,Integer.parseInt(infoIdStr),pageBean);
                    request.setAttribute("participantsDatas",participants);
                    String pageCode = PageCode.getPageCode(participants_dao.getDatasCount(connection),
                            pageBean.getCurrentPage(), pageBean.getPageSize(),
                            "/sqfw/hdzj","&action=showParticipants&infoId="+Integer.parseInt(infoIdStr));
                    request.setAttribute("includePage","/shequ/sqfw_hdzj_participants.jsp");
                    request.setAttribute("pageCode",pageCode);
                    request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
                    tag=false;

                }else if(action.equals("deleteHuoDong")){
                    String id = request.getParameter("id");
                    if (StringUtil.isEmpty(id)){
                        throw new Exception();
                    }
                    info_dao.deleteById(connection,Integer.parseInt(id));
                }
                if (tag){
                    List<Sqfw_hdzj_info> hdzjDatas= info_dao.getDatas(connection,pageBean);
                    String pageCode = PageCode.getPageCode(info_dao.getDatasCount(connection),
                            pageBean.getCurrentPage(), pageBean.getPageSize(),
                            "/sqfw/hdzj","");
                    request.setAttribute("includePage","/shequ/sqfw_hdzj.jsp");
                    request.setAttribute("hdzjDatas",hdzjDatas);
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

            String action = request.getParameter("action");
            if(action.equals("getInfos")){
                getInfos(request,response);
            }else if (action.equals("registration")){
                userRegistration(request,response);
            }

        }else{
            //处理未知请求
            response.sendRedirect("/unknown.jsp");
        }


    }

    //手机用户获取活动召集信息
    public void getInfos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            List<Sqfw_hdzj_info> datas = info_dao.getDatas(connection,null);
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
    }

    //用户报名
    public void userRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try{
            String infoId = request.getParameter("id");
            String name = request.getParameter("name");
            String idCard = request.getParameter("idCard");
            String phone = request.getParameter("phone");
            if (StringUtil.isEmpty(idCard)||StringUtil.isEmpty(infoId)||
                    StringUtil.isEmpty(name)||StringUtil.isEmpty(phone)){
                throw new Exception();
            }
            Sqfw_hdzj_participants participants = new Sqfw_hdzj_participants();
            participants.setInfoId(Integer.parseInt(infoId));
            participants.setName(name);
            participants.setIdCard(idCard);
            participants.setPhone(phone);
            participants_dao.insertData(connection,participants);
            out.print("{\"status\":1}");
        }catch (Exception e){
            out.print("{\"status\":0}");
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }
}
