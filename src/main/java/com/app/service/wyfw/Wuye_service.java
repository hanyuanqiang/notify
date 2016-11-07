package com.app.service.wyfw;

import com.app.dao.wyfw.Wuye_dao;
import com.app.model.wyfw.Wuye;
import com.app.utils.ExceptionUtil;
import com.app.utils.JdbcPool;
import com.app.utils.StringUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
@WebServlet(name = "wuye",urlPatterns = "/wyfw/wuye")
public class Wuye_service extends HttpServlet{

    private Wuye_dao dao = new Wuye_dao();
    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private JdbcPool pool = JdbcPool.getJdbcPool();
    private Connection connection = pool.getConnection();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String from = request.getParameter("from");
        PrintWriter out = response.getWriter();

        if(StringUtil.isEmpty(from)){
            //这里处理网页上的相关请求
            try {
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60*60*10);
                String action = request.getParameter("action")+"";
                if (action.equals("login")){
                    String userName = request.getParameter("userName");
                    String password = request.getParameter("password");
                    Wuye wuye = new Wuye();
                    wuye.setName(userName);
                    wuye.setPassword(password);
                    boolean isSuccess = dao.isLoginSuccess(connection,wuye);
                    Map<String,String> map = new HashMap<String, String>();

                    if (isSuccess){
                        map.put("result","true");
                        session.setAttribute("currentUser",wuye);
                        session.setAttribute("unique","wuye");
                    }else {
                        map.put("result","false");
                        map.put("loginError","用户名或密码错误");
                    }
                    String result = mapper.writeValueAsString(map);
                    out.print(result);
                    out.flush();
                    out.close();
                }else if (action.equals("regist")){
                    String userName = request.getParameter("userName");
                    String password = request.getParameter("password");
                    Wuye wuye = new Wuye();
                    wuye.setName(userName);
                    wuye.setPassword(password);
                    int temp = dao.insertOrUpdateData(connection,wuye);
                    Map<String,String> map = new HashMap<String, String>();
                    if (temp==-1){
                        map.put("result","false");
                        map.put("registError","用户名已经存在");
                    }else {
                        map.put("result","true");
                        session.setAttribute("currentUser",wuye);
                    }
                    String result = mapper.writeValueAsString(map);
                    out.print(result);
                    out.flush();
                    out.close();
                }else if (action.equals("logout")){
                    //用户注销登录
                    session.invalidate();
                    response.sendRedirect("/notify_Web/wuyeLogin.jsp");
                }else if (action.equals("updatePassword")){
                    String newPass = request.getParameter("newPass");
                    if (StringUtil.isEmpty(newPass)){
                        throw new Exception();
                    }
                    int temp = dao.updatePassword(connection,newPass);
                    Map<String,String> map = new HashMap<String, String>();
                    if (temp>0){
                        map.put("result","true");
                        Wuye wuye = (Wuye) session.getAttribute("currentUser");
                        wuye.setPassword(newPass);
                        session.setAttribute("currentUser",wuye);
                    }else{
                        map.put("result","false");
                        map.put("updateError","修改密码失败");
                    }
                    String result = mapper.writeValueAsString(map);
                    out.print(result);
                    out.flush();
                    out.close();

                }
            }catch (Exception e){
                Map<String,String> map = new HashMap<String, String>();
                map.put("result","false");
                map.put("loginError","用户名或密码错误");
                String result = mapper.writeValueAsString(map);
                out.print(result);
                out.flush();
                out.close();
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }
        }else if(from.equals("mp")){
            //这里处理手机app上的相关请求

        }else{
            response.sendRedirect("/unknown.jsp");
        }


    }
}
