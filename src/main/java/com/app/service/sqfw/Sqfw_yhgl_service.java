package com.app.service.sqfw;

import com.app.dao.yzfw.UserDao;
import com.app.model.User;
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
import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
@WebServlet(name = "sqfw_yhgl",urlPatterns = "/sqfw/yhgl")
public class Sqfw_yhgl_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private JdbcPool pool = JdbcPool.getJdbcPool();
    private Connection connection = pool.getConnection();

    private UserDao dao = new UserDao();
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
                String action = request.getParameter("action");
                if ("delete".equals(action)){
                    String id = request.getParameter("id");
                    if (StringUtil.isNotEmpty(id)){
                        dao.deleteUserById(connection,Integer.parseInt(id));
                    }
                }else if ("changeRole".equals(action)){
                    User u = new User();
                    String id = request.getParameter("id");
                    u.setId(Integer.parseInt(id));
                    u.setIsDangYuan(1);
                    dao.updateData(connection,u);
                }else if("recoverRole".equals(action)){
                    User u = new User();
                    String id = request.getParameter("id");
                    u.setId(Integer.parseInt(id));
                    dao.updateData(connection,u);
                } else if ("search".equals(action)) {
                    User u = new User();
                    String isDangYuan = request.getParameter("isDangYuan");
                    request.setAttribute("isDangYuan",isDangYuan);
                    String idCard = request.getParameter("idCard");
                    request.setAttribute("idCard",idCard);
                    String name = request.getParameter("name");
                    request.setAttribute("name",name);
                    if (!(StringUtil.isEmpty(name)&&StringUtil.isEmpty(idCard)&&"-1".equals(isDangYuan))){
                        u.setIsDangYuan(Integer.parseInt(isDangYuan));
                        u.setIdCard(idCard);
                        u.setName(name);
                        List<User> users = dao.searchUsers(connection,u);
                        request.setAttribute("users",users);
                        request.setAttribute("includePage","/shequ/sqfw_yhgl.jsp");
                        request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
                        tag = false;
                    }
                }
                if (tag){
                    //如果是查询操作则不会进入下面代码
                    int currentPage = 1;
                    if(request.getParameter("currentPage")!=null){
                        currentPage = Integer.parseInt(request.getParameter("currentPage"));
                    }
                    PageBean pageBean = new PageBean(currentPage, Integer.parseInt(PropertiesUtil.getValue("pageSize")));
                    List<User> users = dao.getAllUsers(connection,pageBean);
                    request.setAttribute("pageCode", PageCode.getPageCode(dao.getDatasCount(connection),
                            pageBean.getCurrentPage(),pageBean.getPageSize(),"/sqfw/yhgl",""));
                    request.setAttribute("users",users);
                    request.setAttribute("includePage","/shequ/sqfw_yhgl.jsp");
                    request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
                }


            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }

        }else if("mp".equals(from)){
            //处理app端相关请求

        }else{
            //处理未知请求
            response.sendRedirect("/unknown.jsp");
        }


    }

}
