package com.app.service.wyfw;

import com.app.dao.wyfw.Wyfw_tsjy_dao;
import com.app.dao.yzfw.UserDao;
import com.app.model.wyfw.Wyfw_bxsq_repairsInfo;
import com.app.model.wyfw.Wyfw_tsjy;
import com.app.utils.ExceptionUtil;
import com.app.utils.JPushUtil;
import com.app.utils.JdbcPool;
import com.app.utils.StringUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.InternCache;

import javax.persistence.criteria.CriteriaBuilder;
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
@WebServlet(name = "wyfw_tsjy",urlPatterns = "/wyfw/tsjy")
public class Wyfw_tsjy_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Wyfw_tsjy_dao dao = new Wyfw_tsjy_dao();
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
            try {

                String action = request.getParameter("action")+"";
                if (action.equals("shequ")){
                    String handle = request.getParameter("handle")+"";
                    if ("delete".equals(handle)){
                        String id = request.getParameter("id");
                        dao.deleteDataById(connection,Integer.parseInt(id));
                    }
                    if (handle.equals("addReplay")){
                        String id = request.getParameter("id");
                        if (StringUtil.isEmpty(id)){
                            throw new Exception();
                        }
                        String shequReplay = request.getParameter("shequReplay");
                        Wyfw_tsjy tsjy = new Wyfw_tsjy();
                        tsjy.setId(Integer.parseInt(id));
                        tsjy.setShequReplay(shequReplay);
                        dao.updateData(connection,tsjy,2);

                        //获取社区回复的当前实体
                        Wyfw_tsjy tsjy1 = dao.getDataById(connection,Integer.parseInt(id));
                        String json = mapper.writeValueAsString(tsjy1);
                        JPushUtil.pushToOne("社区反馈","社区回复了你的投诉建议",dao.getUserAliasById(connection,Integer.parseInt(id)),json);
                    }
                    if (handle.equals("showDetail")){
                        String id = request.getParameter("id");
                        if (StringUtil.isEmpty(id)){
                            throw new Exception();
                        }
                        Wyfw_tsjy tsjy = dao.getDataById(connection, Integer.parseInt(id));
                        request.setAttribute("data",tsjy);
                        request.setAttribute("includePage","/shequ/wyfw_tsjy_detail.jsp");
                    }else {

                        List<Wyfw_tsjy> noShequReplay = dao.getDatasByCondition(connection,false,0);
                        List<Wyfw_tsjy> haveShequReplay = dao.getDatasByCondition(connection,true,0);
                        request.setAttribute("noShequReplay",noShequReplay);
                        request.setAttribute("haveShequReplay",haveShequReplay);
                        request.setAttribute("includePage","/shequ/wyfw_tsjy.jsp");
                    }
                    request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
                }else if (action.equals("wuye")){
                    String handle = request.getParameter("handle")+"";
                    if ("delete".equals(handle)){
                        String id = request.getParameter("id");
                        dao.deleteDataById(connection,Integer.parseInt(id));
                    }

                    if (handle.equals("addReplay")){
                        String id = request.getParameter("id");
                        if (StringUtil.isEmpty(id)){
                            throw new Exception();
                        }
                        String wuyeReplay = request.getParameter("wuyeReplay");
                        Wyfw_tsjy tsjy = new Wyfw_tsjy();
                        tsjy.setId(Integer.parseInt(id));
                        tsjy.setWuyeReplay(wuyeReplay);
                        dao.updateData(connection,tsjy,1);
                        //获取物业回复的当前实体
                        Wyfw_tsjy tsjy1 = dao.getDataById(connection,Integer.parseInt(id));
                        String json = mapper.writeValueAsString(tsjy1);
                        JPushUtil.pushToOne("物业反馈","物业回复了你的投诉建议",dao.getUserAliasById(connection,Integer.parseInt(id)),json);
                    }
                    if (handle.equals("showDetail")){
                        String id = request.getParameter("id");
                        if (StringUtil.isEmpty(id)){
                            throw new Exception();
                        }
                        Wyfw_tsjy tsjy = dao.getDataById(connection, Integer.parseInt(id));
                        request.setAttribute("data",tsjy);
                        request.setAttribute("includePage","/wuye/wyfw_tsjy_detail.jsp");
                    }else {

                        List<Wyfw_tsjy> noWuyeReplay = dao.getDatasByCondition(connection,false,1);
                        List<Wyfw_tsjy> haveWuyeReplay = dao.getDatasByCondition(connection,true,1);
                        request.setAttribute("noWuyeReplay",noWuyeReplay);
                        request.setAttribute("haveWuyeReplay",haveWuyeReplay);
                        request.setAttribute("includePage","/wuye/wyfw_tsjy.jsp");
                    }
                    request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
                }
            }catch (Exception e){
                logger.error(ExceptionUtil.getStackTrace(e));
            }finally {
                pool.returnConnection(connection);
            }


        }else if("mp".equals(from)){
            //处理app端相关请求

            String action = request.getParameter("action");
            //增加一条投诉信息
            if(action.equals("add")){
                try {
                    String userId = request.getParameter("userId");
                    String userName = userDao.getUserNameById(connection,Integer.parseInt(userId));
                    String complainContent = request.getParameter("complainContent");
                    if (StringUtil.isEmpty(userId)||StringUtil.isEmpty(complainContent)){
                        throw new Exception();
                    }

                    Wyfw_tsjy tsjy = new Wyfw_tsjy();
                    tsjy.setUserId(Integer.parseInt(userId));
                    tsjy.setComplainContent(complainContent);
                    tsjy.setUserName(userName);
                    dao.insertData(connection,tsjy);
                    out.print("{\"status\":1}");
                }catch (Exception e){
                    out.print("{\"status\":0}");
                    logger.error(ExceptionUtil.getStackTrace(e));
                }finally {
                    pool.returnConnection(connection);
                }

            }else if(action.equals("get")){
                //查询某个用户的投诉信息
                try{
                    String userId = request.getParameter("userId");
                    if (StringUtil.isEmpty(userId)){
                        throw new Exception();
                    }
                    List<Wyfw_tsjy> datas = dao.getDatas(connection, Integer.parseInt(userId));
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

        }else{
            //处理未知请求
            response.sendRedirect("/unknown.jsp");
        }

    }
}
