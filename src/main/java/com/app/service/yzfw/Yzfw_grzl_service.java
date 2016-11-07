package com.app.service.yzfw;

import com.app.dao.yzfw.UserDao;
import com.app.model.User;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
@WebServlet(name = "yzfw_grzl",urlPatterns = "/yzfw/grzl")
public class Yzfw_grzl_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private UserDao dao = new UserDao();
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

        }else if("mp".equals(from)){
            //处理app端相关请求
            try {
                String userId = request.getParameter("userId");
                if (StringUtil.isEmpty(userId)){
                    throw new Exception();
                }
                User user = dao.getData(connection,Integer.parseInt(userId));
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("result",user);
                map.put("status",1);
                String result = mapper.writeValueAsString(map);
                out.print(result);
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
