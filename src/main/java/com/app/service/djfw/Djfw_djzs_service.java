package com.app.service.djfw;

import com.app.dao.djfw.Djfw_djzs_dao;
import com.app.model.djfw.Djfw_djzs_data;
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
 * Created by Administrator on 2016/6/26.
 */
@WebServlet(name = "djfw_djzs",urlPatterns = "/djfw/djzs")
public class Djfw_djzs_service extends HttpServlet{

    private Djfw_djzs_dao djzs_dao = new Djfw_djzs_dao();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
    private JdbcPool pool = JdbcPool.getJdbcPool();
    private Connection connection = pool.getConnection();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //该参数用于判断请求是否来自app，如果from为null表示是网页请求，from=from表示app请求，否则是非法请求

        //from参数仅仅用于判断请求是来自网页还是手机客户端
        String from = request.getParameter("from");

        PrintWriter pw = response.getWriter();

        //如果from为null，表示该请求来自网页；否则该请求来自手机客户端
        if (from==null){

            //设置从前台传过来的当前页数，如果没有该参数则默认是第一页
            int currentPage = 1;
            if(request.getParameter("currentPage")!=null){
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            }

            //设置从前台传来的status，0表示处理未回答问题，1表示处理已回答问题，2表示处理已删除问题，否则默认显示未回答问题
            int status = 0;
            if(request.getParameter("status")!=null){
                status = Integer.parseInt(request.getParameter("status"));
            }

            //pageBean的两个参数分别是当前页和每页的记录数
            PageBean pageBean = new PageBean(currentPage,Integer.parseInt(PropertiesUtil.getValue("pageSize")));

            //根据status的值来进行处理不同的请求
            //status=0表示处理未回答问题(进行回答和删除操作)
            //status=1表示处理已回答问题（显示数据）
            //status=2表示处理已回答问题（显示数据）
            if(status==0){
                noResponseDatas(request,response,pageBean);
            }else if(status==1){
                haveResponseDatas(request,response,pageBean);
            }else if(status==2){
                deleteDatas(request,response,pageBean);
            }

            request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);

        }else if("mp".equals(from)){
            //以下代码处理app请求


            String userId = request.getParameter("userId");
            //如果userId==null，表示获取党建信息列表;否则表示用户进行问题咨询
            if (userId==null){

                //获取全部问题
                try {
                    List<Djfw_djzs_data> datas = djzs_dao.getDatas(connection,null,1);

                    //这是辅助设置，控制格式化输出。
                    // mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, Boolean.TRUE);
                    //使用jackson的ObjectMapper 的writeValueAsString方法可以把pojo类输出成json字符串
                    Map<String,Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("status","1");
                    resultMap.put("result",datas);
                    String resultjson = mapper.writeValueAsString(resultMap);
                    pw.print(resultjson);
                } catch (Exception e) {
                    pw.print("{\"status\":0}");
                    logger.error(ExceptionUtil.getStackTrace(e));
                }finally {
                    pool.returnConnection(connection);
                }
            }else{
                try {
                    //进行问题的咨询
                    String title = request.getParameter("title");
                    String content = request.getParameter("content");
                    if (StringUtil.isEmpty(title)||StringUtil.isEmpty(content)){
                        throw new Exception();
                    }
                    Djfw_djzs_data data = new Djfw_djzs_data();
                    data.setTitle(title);
                    data.setContent(content);
                    data.setUserId(Integer.parseInt(userId));
                    djzs_dao.insertDate(connection,data);
                    pw.print("{\"status\":1}");
                } catch (Exception e) {
                    pw.print("{\"status\":0}");
                    logger.error(ExceptionUtil.getStackTrace(e));
                }finally {
                    pool.returnConnection(connection);
                }
            }

        }else{
            response.sendRedirect("/unknown.jsp");
        }
    }

    //处理未回答问题
    public void noResponseDatas(HttpServletRequest request, HttpServletResponse response ,PageBean pageBean) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        try {

            String strId = request.getParameter("id");
            //如果id不为空，则进行该条信息的更新操作
            if(strId!=null){
                int id = Integer.parseInt(strId);
                //获取前台传过来的type参数，如果type为response表示进行问题的回答，type为delete表示删除问题
                String type = request.getParameter("type");
                Djfw_djzs_data data = new Djfw_djzs_data();
                data.setId(id);
                //如果传过来的参数是response的话进行添加评论操作，如果参数值是delete的话，则进行删除操作。
                if(type.equals("response")){
                    String responseTemp = request.getParameter("response");
                    String responseText = PicUtil.replaceImgSrc(request,response,responseTemp);
                    data.setResponse(responseText); //添加回答的内容
                    data.setStatus(1);  //1表示该条信息已经被评论了
                    djzs_dao.updateDatas(connection,data);


                    Djfw_djzs_data djfw_djzs_data = djzs_dao.getDataById(connection,id);
                    String json = mapper.writeValueAsString(djfw_djzs_data);
                    //社区人员回复用户评论后要推送提醒用户问题已回复
                    JPushUtil.pushToOne("社区回复","社区人员回复了您的党建提问",djzs_dao.getUserAliasById(connection,id),json);
                }else if(type.equals("delete")){
                    data.setStatus(2);  //2表示该信息已被删除
                    djzs_dao.updateDatas(connection,data);    //更新数据
                }
            }

            //获取还未回答的问题
            List<Djfw_djzs_data> noResponseDatas = djzs_dao.getDatas(connection,pageBean,0);
            request.setAttribute("noResponseDatas",noResponseDatas);

            //下面方法的四个参数分别是：要分页的总记录数、当前页码、每页的记录数、页码的url中要加的参数
            String pageCode = PageCode.getPageCode(djzs_dao.getDatasCount(connection,0),pageBean.getCurrentPage(),Integer.parseInt(PropertiesUtil.getValue("pageSize")),"/djfw/djzs","&status=0");
            //向前端传入分页代码
            request.setAttribute("pageCode", pageCode);
            request.setAttribute("dataListPage","/shequ/djfw_djzs_noResponse.jsp");
            request.setAttribute("includePage","/shequ/djfw_djzs.jsp");

        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            out.print("访问异常");
        }finally {
            pool.returnConnection(connection);
        }

    }

    //处理已回答问题（获取已回答问题列表）
    public void haveResponseDatas(HttpServletRequest request, HttpServletResponse response ,PageBean pageBean) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        try {
            String idStr = request.getParameter("id");
            String action = request.getParameter("action");
            if ("delete".equals(action)&&StringUtil.isNotEmpty(idStr)){
                Djfw_djzs_data data = new Djfw_djzs_data();
                data.setId(Integer.parseInt(idStr));
                data.setStatus(2);  //2表示该信息已被删除
                djzs_dao.updateDatas(connection,data);    //更新数据
                idStr=null;
            }

            if (StringUtil.isEmpty(idStr)){
                //获取所有已经回答的问题
                List<Djfw_djzs_data> haveResponseDatas = djzs_dao.getDatas(connection,pageBean,1);
                request.setAttribute("haveResponseDatas",haveResponseDatas);
                request.setAttribute("pageCode", PageCode.getPageCode(djzs_dao.getDatasCount(connection,1),pageBean.getCurrentPage(),Integer.parseInt(PropertiesUtil.getValue("pageSize")),"/djfw/djzs","&status=1"));
                request.setAttribute("dataListPage","/shequ/djfw_djzs_haveResponse.jsp");
                request.setAttribute("includePage","/shequ/djfw_djzs.jsp");
            }else {
                //按指定Id查询
                Djfw_djzs_data data = djzs_dao.getDataById(connection,Integer.parseInt(idStr));
                if (data==null){
                    throw new Exception();
                }
                String result = mapper.writeValueAsString(data);
                out.print(result);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            out.print("系统异常");
        }finally {
            pool.returnConnection(connection);
        }

    }

    //处理已删除问题（获取已删除问题列表）
    public void deleteDatas(HttpServletRequest request, HttpServletResponse response ,PageBean pageBean) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        try {
            String idStr = request.getParameter("id");
            String action = request.getParameter("action");
            if ("delete".equals(action)){
                if (StringUtil.isNotEmpty(idStr)){
                    djzs_dao.deleteDataById(connection,Integer.parseInt(idStr));
                    idStr=null;
                }
            }
            if (StringUtil.isEmpty(idStr)){
                //获取已删除问题列表
                List<Djfw_djzs_data> deleteDatas = djzs_dao.getDatas(connection,pageBean,2);
                request.setAttribute("deleteDatas",deleteDatas);
                request.setAttribute("pageCode", PageCode.getPageCode(djzs_dao.getDatasCount(connection,2),pageBean.getCurrentPage(),Integer.parseInt(PropertiesUtil.getValue("pageSize")),"/djfw/djzs","&status=2"));
                request.setAttribute("dataListPage","/shequ/djfw_djzs_delete.jsp");
                request.setAttribute("includePage","/shequ/djfw_djzs.jsp");
            }else {
                //按指定Id查询
                Djfw_djzs_data data = djzs_dao.getDataById(connection,Integer.parseInt(idStr));
                if (data==null){
                    throw new Exception();
                }
                String result = mapper.writeValueAsString(data);
                out.print(result);
                out.flush();
                out.close();
            }

        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            out.print("系统异常");
        }finally {
            pool.returnConnection(connection);
        }

    }
}
