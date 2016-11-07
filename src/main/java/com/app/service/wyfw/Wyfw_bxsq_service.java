package com.app.service.wyfw;

import com.app.dao.wyfw.Wyfw_bxsq_repairsInfo_dao;
import com.app.dao.yzfw.UserDao;
import com.app.model.wyfw.Wyfw_bxsq_repairsInfo;
import com.app.utils.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
@WebServlet(name = "wyfw_bxsq",urlPatterns = "/wyfw/bxsq")
@MultipartConfig
public class Wyfw_bxsq_service extends HttpServlet{

    private Wyfw_bxsq_repairsInfo_dao dao = new Wyfw_bxsq_repairsInfo_dao();
    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private UserDao userDao = new UserDao();
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
            if (handle.equals("noYuyue")){
                noYuyueHandle(request,response);
            }else if (handle.equals("haveYuyue")){
                haveYuyueHandle(request,response);
            }else if (handle.equals("repairsFinish")){
                repairsFinishHandle(request,response);
            }else if (handle.equals("report")){
                reportHandle(request,response);
            }

        }else if("mp".equals(from)){
            //处理app端相关请求

            String action = request.getParameter("action");
            if(action.equals("insert")){
                insertNewDatas(request,response);
            }else if(action.equals("search")){
                searchDatas(request,response);
            }else if(action.equals("addComment")){
                addComment(request,response);
            }

        }else{
            //处理未知请求
            response.sendRedirect("/unknown.jsp");
        }

    }

    //报表形式显示报修信息
    public void reportHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        if ("delete".equals(action)){
            String id = request.getParameter("id");
            try {
                dao.deleteById(connection,Integer.parseInt(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            int currentPage = 1;
            if(request.getParameter("currentPage")!=null){
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            }
            //pageBean的两个参数分别是当前页和每页的记录数
            PageBean pageBean = new PageBean(currentPage,Integer.parseInt(PropertiesUtil.getValue("pageSize")));

            List<Wyfw_bxsq_repairsInfo> reportDatas =null;
            String pageCode = "";

            String tag = request.getParameter("tag");
            //若用户进行查询操作，则进入下面选择语句
            if ("search".equals(tag)){
                String userName = request.getParameter("userName");
                String bxTime = request.getParameter("bxTime");
                request.setAttribute("userName",userName);
                request.setAttribute("bxTime",bxTime);
                Wyfw_bxsq_repairsInfo info = new Wyfw_bxsq_repairsInfo();
                info.setUserName(userName);
                info.setBxTime(bxTime);
                reportDatas = dao.getSearchDatas(connection,info);
                pageCode = PageCode.getPageCode(dao.getDatasCount(connection),
                        pageBean.getCurrentPage(),pageBean.getPageSize(),"/wyfw/bxsq","&handle=report&tag=search&userName="+userName+"&bxTime="+bxTime);
            }else{
                reportDatas = dao.getDates(connection,-1,-1,pageBean);
                pageCode = PageCode.getPageCode(dao.getDatasCount(connection),
                        pageBean.getCurrentPage(),pageBean.getPageSize(),"/wyfw/bxsq","&handle=report");
            }

            request.setAttribute("reportDatas",reportDatas);
            request.setAttribute("pageCode",pageCode);
            request.setAttribute("dataListPage","/wuye/wyfw_bxsq_report.jsp");
            request.setAttribute("includePage","/wuye/wyfw_bxsq.jsp");
            request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }



    //处理已经预约的报修信息
    public void haveYuyueHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        boolean tag = true;
        try{
            String action=request.getParameter("action")+"";
            if (action.equals("showDetail")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Wyfw_bxsq_repairsInfo info = dao.getDataById(connection,Integer.parseInt(idStr));
                String result = mapper.writeValueAsString(info);
                out.print(result);
                out.flush();
                out.close();
                tag = false;
            }else if (action.equals("repairsFinish")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                //把某条维修信息设为维修完成状态
                dao.repairsFinish(connection,Integer.parseInt(idStr));
                out.print("{\"status\":1}");
                out.flush();
                out.close();
                tag = false;
                //JPushUtil.pushWithAlias("你的本次报修已完成，快去评价吧！","物业消息",dao.getUserAliasById(connection,Integer.parseInt(idStr)));
            }
            if (tag){
                //获取已经预约但是还没维修的信息
                List<Wyfw_bxsq_repairsInfo> infos = dao.getDates(connection,-1,4,null);
                request.setAttribute("haveYuyueDatas",infos);
                request.setAttribute("dataListPage","/wuye/wyfw_bxsq_haveYuyue.jsp");
                request.setAttribute("includePage","/wuye/wyfw_bxsq.jsp");
                request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }


    //获取还没预约的报修记录
    public void noYuyueHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        boolean tag = true;
        try{
            String action=request.getParameter("action")+"";
            if (action.equals("showDetail")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Wyfw_bxsq_repairsInfo info = dao.getDataById(connection,Integer.parseInt(idStr));
                String result = mapper.writeValueAsString(info);
                out.print(result);
                out.flush();
                out.close();
                tag = false;
            }else if (action.equals("yuyue")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                String repairsDate = request.getParameter("repairsDate");
                Wyfw_bxsq_repairsInfo info = new Wyfw_bxsq_repairsInfo();
                info.setId(Integer.parseInt(idStr));
                info.setRepairsDate(repairsDate);
                dao.updatePepairsInfo(connection,1,info);

                //通知用户物业已经预约了维修日期
                //JPushUtil.pushWithAlias("物业预约了你的报修申请","物业消息",dao.getUserAliasById(connection,Integer.parseInt(idStr)));
            }else if(action.equals("delete")){
                String id = request.getParameter("id");
                if (StringUtil.isEmpty(id)){
                    throw new Exception();
                }
                dao.deleteById(connection,Integer.parseInt(id));
            }
            if (tag){
                List<Wyfw_bxsq_repairsInfo> infos = dao.getDates(connection,-1,1,null);
                request.setAttribute("noYuyueDatas",infos);
                request.setAttribute("dataListPage","/wuye/wyfw_bxsq_noYuyue.jsp");
                request.setAttribute("includePage","/wuye/wyfw_bxsq.jsp");
                request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }

    }



    public void repairsFinishHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("showDetail")){
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Wyfw_bxsq_repairsInfo info = dao.getDataById(connection,Integer.parseInt(idStr));
                String result = mapper.writeValueAsString(info);
                out.print(result);
                out.flush();
                out.close();
            }else {

                //设置从前台传过来的当前页数，如果没有该参数则默认是第一页
                int currentPage = 1;
                if(request.getParameter("currentPage")!=null){
                    currentPage = Integer.parseInt(request.getParameter("currentPage"));
                }
                //pageBean的两个参数分别是当前页和每页的记录数
                PageBean pageBean = new PageBean(currentPage,Integer.parseInt(PropertiesUtil.getValue("pageSize")));

                List<Wyfw_bxsq_repairsInfo> repairsFinishDatas = dao.getDates(connection,-1,5,pageBean);
                request.setAttribute("repairsFinishDatas",repairsFinishDatas);
                String pageCode = PageCode.getPageCode(dao.getCountsOfRepairsFinish(connection),
                        pageBean.getCurrentPage(),pageBean.getPageSize(),"/wyfw/bxsq","&handle=repairsFinish");
                request.setAttribute("pageCode",pageCode);
                request.setAttribute("dataListPage","/wuye/wyfw_bxsq_repairsFinish.jsp");
                request.setAttribute("includePage","/wuye/wyfw_bxsq.jsp");
                request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }

    }

    //业主插入一条报修信息，进入下面方法执行
    public void insertNewDatas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        try {
            //获取用户ID，标题，内容
            String userId = request.getParameter("userId");
            logger.info("报修申请userId："+userId);
            String title = request.getParameter("title");
            logger.info("报修申请title："+title);
            String contentTemp = request.getParameter("content");
            logger.info("报修申请contentTemp："+contentTemp);
            String content = PicUtil.replaceImgSrc(request,response,contentTemp);
            String userName = userDao.getUserNameById(connection,Integer.parseInt(userId));
            logger.info("报修申请userName："+userName);
            if (StringUtil.isEmpty(userId)||StringUtil.isEmpty(title)||
                    StringUtil.isEmpty(contentTemp)||StringUtil.isEmpty(userName)){
                throw new Exception();
            }

            //生成图片的url
            String picUrl_beforeRepairs = PicUtil.getPicUrl(request,response,this);
            logger.info("报修申请picUrl_beforeRepairs："+picUrl_beforeRepairs);

            Wyfw_bxsq_repairsInfo repairsInfo = new Wyfw_bxsq_repairsInfo();
            repairsInfo.setUserId(Integer.parseInt(userId));
            repairsInfo.setTitle(title);
            repairsInfo.setContent(content);
            repairsInfo.setPicUrl_beforeRepairs(picUrl_beforeRepairs);
            repairsInfo.setUserName(userName);
            int temp = dao.insertPepairsInfo(connection,repairsInfo);
            logger.info("报修申请执行记录数："+temp);
            //如果插入数据成功，则输入status为1
            if (temp>0){
                out.print("{\"status\":1}");
            }else {
                out.print("{\"status\":0}");
            }
        }catch (Exception e){
            out.print("{\"status\":0}");
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }

    }

    //查询某个用户的申请记录
    public void searchDatas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        try {
            //获取传过来的userId
            String userIdStr = request.getParameter("userId");
            if(StringUtil.isEmpty(userIdStr)){
                out.print("{\"status\":0}");
                return;
            }
            int userId = Integer.parseInt(userIdStr);

            List<Wyfw_bxsq_repairsInfo> datas = dao.getDates(connection,userId,3,null);
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

    public void addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        try {
            String comment = request.getParameter("comment");
            String star = request.getParameter("star");
            String id = request.getParameter("id");

            //获取图片url
            String picUrl_afterRepairs = PicUtil.getPicUrl(request,response,this);
            if (StringUtil.isEmpty(id)){
                throw new Exception();
            }
            if (StringUtil.isEmpty(comment)&&StringUtil.isEmpty(star)&&
                    StringUtil.isEmpty(picUrl_afterRepairs)){
                throw new Exception();
            }

            Wyfw_bxsq_repairsInfo info = new Wyfw_bxsq_repairsInfo();
            info.setId(Integer.parseInt(id));
            info.setComment(comment);
            info.setPicUrl_afterRepairs(picUrl_afterRepairs);
            info.setStar(Integer.parseInt(star));
            dao.updatePepairsInfo(connection,2,info);
            out.print("{\"status\":1}");
        }catch (Exception e){
            out.print("{\"status\":0}");
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }
}
