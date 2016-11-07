package com.app.service.sqfw;

import com.app.dao.sqfw.Shequ_Dao;
import com.app.model.sqfw.Shequ;
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
 * Created by Administrator on 2016/7/24.
 */
@WebServlet(name = "shequ",urlPatterns = "/sqfw/shequ")
public class Shequ_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Shequ_Dao dao = new Shequ_Dao();
    private JdbcPool pool = JdbcPool.getJdbcPool();
    private Connection connection = pool.getConnection();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String from = request.getParameter("from");
        PrintWriter out = response.getWriter();

        if(StringUtil.isEmpty(from)){
            //处理网页上的相关请求
            //这里处理网页上的相关请求
            try {
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60*60*10);
                String action = request.getParameter("action")+"";
                if (action.equals("login")){
                    //用户登录
                    String userName = request.getParameter("userName");
                    String password = request.getParameter("password");
                    Shequ shequ = new Shequ();
                    shequ.setName(userName);
                    shequ.setPassword(password);
                    boolean isSuccess = dao.isLoginSuccess(connection,shequ);
                    Map<String,String> map = new HashMap<String, String>();

                    if (isSuccess){
                        map.put("result","true");
                        session.setAttribute("currentUser",shequ);
                        session.setAttribute("unique","shequ");
                    }else {
                        map.put("result","false");
                        map.put("loginError","用户名或密码错误");
                    }
                    String result = mapper.writeValueAsString(map);
                    out.print(result);
                    out.flush();
                    out.close();
                }else if (action.equals("regist")){
                    //用户注册
                    String userName = request.getParameter("userName");
                    String password = request.getParameter("password");
                    Shequ shequ = new Shequ();
                    shequ.setName(userName);
                    shequ.setPassword(password);
                    int temp = dao.insertOrUpdateData(connection,shequ);
                    Map<String,String> map = new HashMap<String, String>();
                    if (temp==-1){
                        map.put("result","false");
                        map.put("registError","用户名已经存在");
                    }else {
                        map.put("result","true");
                        session.setAttribute("currentUser",shequ);
                    }
                    String result = mapper.writeValueAsString(map);
                    out.print(result);
                    out.flush();
                    out.close();
                }else if (action.equals("logout")){
                    //用户注销登录
                    session.invalidate();
                    response.sendRedirect("/notify_Web/shequLogin.jsp");
                }else if (action.equals("updatePassword")){
                    String newPass = request.getParameter("newPass");
                    if (StringUtil.isEmpty(newPass)){
                        throw new Exception();
                    }
                    int temp = dao.updatePassword(connection,newPass);
                    Map<String,String> map = new HashMap<String, String>();
                    if (temp>0){
                        map.put("result","true");
                        Shequ shequ = (Shequ) session.getAttribute("currentUser");
                        shequ.setPassword(newPass);
                        session.setAttribute("currentUser",shequ);
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


        }else if("mp".equals(from)){
            //处理app端相关请求

        }else{
            //处理未知请求
            response.sendRedirect("/unknown.jsp");
        }


    }
}
