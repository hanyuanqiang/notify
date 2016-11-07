package com.app.service.yzfw;

import com.app.dao.yzfw.UserDao;
import com.app.dao.yzfw.Yzfw_yzzx_dao;
import com.app.dao.yzfw.Yzfw_yzzx_sqfk_dao;
import com.app.dao.yzfw.Yzfw_yzzx_wyfk_dao;
import com.app.model.User;
import com.app.model.djfw.Djfw_djzs_data;
import com.app.model.wyfw.Wyfw_tsjy;
import com.app.utils.*;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;

/**
 * Created by Administrator on 2016/6/29.
 */
@WebServlet(name = "yzfw_yzzx",urlPatterns = "/yzfw/yzzx")
@MultipartConfig
public class Yzfw_yzzx_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Yzfw_yzzx_dao yzzx_dao = new Yzfw_yzzx_dao();
    private Yzfw_yzzx_sqfk_dao sqfk_dao = new Yzfw_yzzx_sqfk_dao();
    private Yzfw_yzzx_wyfk_dao wyfk_dao = new Yzfw_yzzx_wyfk_dao();
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

        }else {
            if ("mp".equals(from)) {
                //处理app端相关请求
                try {

                    String action = request.getParameter("action");
                    if (action.equals("regist")) {
                        //处理用户注册
                        mpRegist(request, response);

                    } else if (action.equals("login")) {
                        //处理用户登录
                        mpLoginCheck(request, response);

                    } else if (action.equals("sqfk")) {
                        //查询社区反馈
                        searchSqfk(request, response);

                    } else if (action.equals("wyfk")) {
                        //查询物业反馈
                        searchWyfk(request, response);

                    } else if (action.equals("checkIdCard")) {
                        //修改密码前进行身份证号码验证
                        checkIdCard(request, response);

                    } else if (action.equals("updatePassword")) {
                        //进行更新密码操作
                        updatePassword(request, response);

                    } else if (action.equals("updatePersonInfo")) {
                        //进行更新个人信息操作
                        updatePersonInfo(request, response);

                    } else {
                        throw new Exception();
                    }

                } catch (Exception e) {
                    out.print("{\"status\":0}");
                    logger.error(ExceptionUtil.getStackTrace(e));
                }finally {
                    pool.returnConnection(connection);
                }

            } else {
                //处理未知请求
                response.sendRedirect("/unknown.jsp");
            }
        }


    }

    //用户进行修改自己的个人资料
    public void updatePersonInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String userId = request.getParameter("id");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String headPicUrl = PicUtil.getPicUrl(request,response,this);
            if (StringUtil.isEmpty(userId)||(StringUtil.isEmpty(phone)&&
                    StringUtil.isEmpty(address)&&StringUtil.isEmpty(headPicUrl))){
                throw new Exception();
            }
            User user = new User();
            user.setHeadPicUrl(headPicUrl);
            user.setId(Integer.parseInt(userId));
            user.setAddress(address);
            user.setPhone(phone);
            User user1 = userDao.updateData(connection,user);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("user",user1);
            map.put("status",1);
            String result = mapper.writeValueAsString(map);
            out.print(result);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
            out.print("{\"status\":0}");
        }finally {
            pool.returnConnection(connection);
        }

    }


    //用户进行修改密码操作
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String verifyCode = request.getParameter("verifyCode")+"";//获取传过来的验证码
            String userId = request.getParameter("userId");//获取传过来的用户id
            String password = request.getParameter("password");//获取传过来的新密码
            if (StringUtil.isEmpty(userId)||StringUtil.isEmpty(password)){
                throw new Exception();
            }
            //从服务器容器中获取生成的验证码信息
            ServletContext application = request.getServletContext();
            String vc = (String) application.getAttribute("verifyCode"+userId);//获取一开始生成的验证码

            //如果验证码正确则进行修改密码操作
            if (verifyCode.equals(vc)){
                //如果验证码正确，则进行修改密码操作
                application.removeAttribute("verifyCode"+userId);//把原来存在application中的验证码移除掉
                User user = new User();
                user.setId(Integer.parseInt(userId));
                user.setPassword(password);
                //进行更新密码操作，并且返回更新后的用户详细信息
                User userAfterUpdatePassword = userDao.updatePassword(connection,user);

                Map<String,Object> map = new HashMap<String, Object>();
                map.put("user",userAfterUpdatePassword);
                map.put("status",1);
                String result = mapper.writeValueAsString(map);
                out.print(result);

            }else {
                out.print("{\"status\":0}");
            }

        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
            out.print("{\"status\":0}");
        }finally {
            pool.returnConnection(connection);
        }
    }


    //修改密码前验证用户idCard
    public void checkIdCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try{
            System.err.println("验证");
            String idCard = request.getParameter("idCard");
            if (StringUtil.isEmpty(idCard)){
                throw new Exception();
            }
            User user = new User();
            user.setIdCard(idCard);
            User resultUser = yzzx_dao.checkIdCardBeforeUpdatePassword(connection,user);

            if (resultUser!=null){

                Map<String,Object> map = new HashMap<String, Object>();
                //随机生成四个数字
                int num1 = (int)(Math.random()*10);
                int num2 = (int)(Math.random()*10);
                int num3 = (int)(Math.random()*10);
                int num4 = (int)(Math.random()*10);
                String verifyCode = ""+num1+num2+num3+num4;
                ServletContext application = request.getServletContext();
                //application.setAttribute("verifyCode",verifyCode);
                application.setAttribute("verifyCode"+user.getId(),verifyCode);
                map.put("verifyCode",verifyCode);
                map.put("user",resultUser);
                map.put("status",1);
                String result = mapper.writeValueAsString(map);
                out.print(result);
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


    //处理用户注册的具体方法
    public void mpRegist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String name = request.getParameter("name"); //用户姓名
            logger.error(name);
            if (StringUtil.isEmpty(name)){
                logger.error("注册时用户名为空");
            }

            String phone = request.getParameter("phone");   //手机号码
            logger.error(phone);
            if (StringUtil.isEmpty(phone)){
                logger.error("注册时手机号为空");
            }

            String idCard = request.getParameter("idCard");     //用户身份证号码
            logger.error(idCard);
            if (StringUtil.isEmpty(idCard)){
                logger.error("注册是身份证号码为空");
            }

            String password = request.getParameter("password");     //用户密码
            logger.error(password);
            if (StringUtil.isEmpty(password)){
                logger.error("注册时密码为空");
            }

            String address = request.getParameter("address");   //用户住址

            //String alias = UUIDUtil.getUUID();  //为每个用户生成一个唯一的标识
            String alias = request.getParameter("alias");
            logger.error(alias);
            if (StringUtil.isEmpty(alias)){
                logger.error("注册时没有alias");
            }

            /*String isDangYuanStr = request.getParameter("isDangYuan");
            logger.error(isDangYuanStr);
            if (StringUtil.isEmpty(isDangYuanStr)){
                logger.error("注册时没有isDangYuan");
            }*/

            /*String tag = "";
            if ("1".equals(isDangYuanStr)){
                tag = "dangyuantag";
            }*/

            /*logger.error(tag);
            if (StringUtil.isEmpty(tag)){
                logger.error("注册时没有tag");
            }*/

            if (StringUtil.isEmpty(name)||StringUtil.isEmpty(phone)||
                    StringUtil.isEmpty(idCard)||StringUtil.isEmpty(password)||
                    StringUtil.isEmpty(alias)){
                logger.error("注册时有些字段为空");
                throw new Exception();
            }

            User user = new User();
            user.setName(name);
            user.setPhone(phone);
            user.setPassword(password);
            user.setAddress(address);
            user.setIdCard(idCard);
            user.setAlias(alias);
            /*user.setIsDangYuan(Integer.parseInt(isDangYuanStr));
            user.setTag(tag);*/

            yzzx_dao.insertData(connection,user);
            out.print("{\"status\":1}");
            logger.info("--《新用户注册》-- ："+name);
        }catch (Exception e){
            out.print("{\"status\":0}");
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    //处理用户登录操作
    public void mpLoginCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String idCard = request.getParameter("idCard");
            String password = request.getParameter("password");
            if (StringUtil.isEmpty(idCard)||StringUtil.isEmpty(password)){
                throw new Exception();
            }
            User user = new User();
            user.setIdCard(idCard);
            user.setPassword(password);
            User user1 = yzzx_dao.loginCheck(connection,user);
            if (user1!=null){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("user",user1);
                map.put("status",1);
                String result = mapper.writeValueAsString(map);
                out.print(result);
                logger.info("--《用户登录》-- ："+user1.getName());
            }else{
                out.print("{\"status\":0}");
            }
        }catch (Exception e){
            out.print("{\"status\":0}");
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    //查询社区反馈
    public void searchSqfk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String userId = request.getParameter("userId");
            if (StringUtil.isEmpty(userId)){
                out.print("{\"status\":0}");
                return;
            }
            List<Djfw_djzs_data> djfw_djzs_datas = sqfk_dao.getDjzsDatas(connection,Integer.parseInt(userId));
            List<Wyfw_tsjy> wyfw_tsjies = sqfk_dao.getTsjyDatas(connection,Integer.parseInt(userId));
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("result1",djfw_djzs_datas);
            map.put("result2",wyfw_tsjies);
            map.put("status",1);
            String result = mapper.writeValueAsString(map);
            out.print(result);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
            out.print("{\"status\":0}");
        }finally {
            pool.returnConnection(connection);
        }

    }

    public void searchWyfk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        try {
            String userId = request.getParameter("userId");
            if (StringUtil.isEmpty(userId)){
                out.print("{\"status\":0}");
                return;
            }
            List<Wyfw_tsjy> wyfw_tsjies = wyfk_dao.getDatas(connection,Integer.parseInt(userId));
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("result",wyfw_tsjies);
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
}
