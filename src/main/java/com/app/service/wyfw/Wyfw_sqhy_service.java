package com.app.service.wyfw;

import com.app.dao.wyfw.Wyfw_sqhy_dao;
import com.app.model.wyfw.Wyfw_sqhy;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
@WebServlet(name = "wyfw_sqhy",urlPatterns = "/wyfw/sqhy")
public class Wyfw_sqhy_service extends HttpServlet{

    private Wyfw_sqhy_dao dao = new Wyfw_sqhy_dao();
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
            //处理网页上的相关请求

            try {
                String action = request.getParameter("action")+"";
                if (action.equals("delete")){
                    String idStr = request.getParameter("id");
                    if (StringUtil.isEmpty(idStr)){
                        throw new Exception();
                    }
                    dao.deleteDataById(connection,Integer.parseInt(idStr));
                }else if (action.equals("add")){
                    String phoneName = request.getParameter("phoneName");
                    String phoneNumber = request.getParameter("phoneNumber");
                    String type = request.getParameter("type");
                    Wyfw_sqhy sqhy = new Wyfw_sqhy();
                    sqhy.setPhoneNumber(phoneNumber);
                    sqhy.setPhoneName(phoneName);
                    sqhy.setType(type);
                    dao.insertData(connection,sqhy);
                }
                Map<String,List<Wyfw_sqhy>> maps = dao.getDatas(connection);
                request.setAttribute("maps",maps);
                request.setAttribute("includePage","/wuye/wyfw_sqhy.jsp");
                request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }

        }else if("mp".equals(from)){
            //处理app端相关请求

            try {

                Map<String,List<Wyfw_sqhy>> datas  = dao.getDatas(connection);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("status",1);
                map.put("result",datas);
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
