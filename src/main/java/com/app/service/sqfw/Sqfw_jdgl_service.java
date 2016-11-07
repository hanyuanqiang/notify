package com.app.service.sqfw;

import com.app.dao.sqfw.Sqfw_jdgl_company_dao;
import com.app.dao.sqfw.Sqfw_jdgl_merchant_dao;
import com.app.dao.sqfw.Sqfw_jdgl_person_dao;
import com.app.model.sqfw.Sqfw_jdgl_company;
import com.app.model.sqfw.Sqfw_jdgl_merchant;
import com.app.model.sqfw.Sqfw_jdgl_person;
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
@WebServlet(name = "sqfw_jdgl",urlPatterns = "/sqfw/jdgl")
public class Sqfw_jdgl_service extends HttpServlet{

    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = Logger.getLogger(this.getClass());
    private Sqfw_jdgl_person_dao person_dao = new Sqfw_jdgl_person_dao();
    private Sqfw_jdgl_company_dao company_dao = new Sqfw_jdgl_company_dao();
    private Sqfw_jdgl_merchant_dao merchant_dao = new Sqfw_jdgl_merchant_dao();
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


            String type = request.getParameter("type")+"";
            if (type.equals("person")){
                handelPerson(request,response);
            }else if (type.equals("company")){
                handelCompany(request,response);
            }else if (type.equals("merchant")){
                handelMerchant(request,response);
            }

            String handle = request.getParameter("handle")+"";
            if (handle.equals("person")){
                wuyePerson(request,response);
            }else if (handle.equals("company")){
                wuyeCompany(request,response);
            }else if (handle.equals("merchant")){
                wuyeMerchant(request,response);
            }


        }else if("mp".equals(from)){
            //处理app端相关请求

            try{

                String action = request.getParameter("action");
                List datas = null;
                if (action.equals("person")){
                    //监督管理
                    datas = person_dao.getDatas(connection,1);
                }else if (action.equals("company")){
                    datas = company_dao.getDatas(connection,1);
                }else if (action.equals("merchant")){
                    datas = merchant_dao.getDatas(connection,1);
                }else {
                    throw new Exception();
                }
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
        }else{
            //处理未知请求
            response.sendRedirect("/unknown.jsp");
        }

    }


    public void wuyePerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("add")){
                String idCardInfoTemp = request.getParameter("idCardInfo");
                String idCardInfo = PicUtil.replaceImgSrc(request,response,idCardInfoTemp);
                String workContent = request.getParameter("workContent");
                String phone = request.getParameter("phone");
                String name = request.getParameter("name");
                Sqfw_jdgl_person person = new Sqfw_jdgl_person();
                person.setIdCardInfo(idCardInfo);
                person.setPhone(phone);
                person.setWorkContent(workContent);
                person.setName(name);
                person_dao.insertData(connection,person);
            }
            request.setAttribute("person_records",person_dao.getDatas(connection,-1));
            request.setAttribute("includePage","/wuye/zzrz_person.jsp");
            request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    public void wuyeCompany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("add")){
                String legalPerson = request.getParameter("legalPerson");
                String licenseInfoTemp = request.getParameter("licenseInfo");
                String licenseInfo = PicUtil.replaceImgSrc(request,response,licenseInfoTemp);
                String phone = request.getParameter("phone");
                String location = request.getParameter("location");
                Sqfw_jdgl_company company = new Sqfw_jdgl_company();
                company.setLegalPerson(legalPerson);
                company.setLicenseInfo(licenseInfo);
                company.setPhone(phone);
                company.setLocation(location);
                company_dao.insertData(connection,company);
            }
            request.setAttribute("company_records",company_dao.getDatas(connection,-1));
            request.setAttribute("includePage","/wuye/zzrz_company.jsp");
            request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    public void wuyeMerchant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("add")){
                String name = request.getParameter("name");
                String licenseInfoTemp = request.getParameter("licenseInfo");
                String licenseInfo = PicUtil.replaceImgSrc(request,response,licenseInfoTemp);
                String principal = request.getParameter("principal");
                String location = request.getParameter("location");
                Sqfw_jdgl_merchant merchant = new Sqfw_jdgl_merchant();
                merchant.setName(name);
                merchant.setLicenseInfo(licenseInfo);
                merchant.setLocation(location);
                merchant.setPrincipal(principal);
                merchant_dao.insertData(connection,merchant);
            }
            request.setAttribute("merchant_records",merchant_dao.getDatas(connection,-1));
            request.setAttribute("includePage","/wuye/zzrz_merchant.jsp");
            request.getRequestDispatcher("/wuyeMainPage.jsp").forward(request,response);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }












    public void handelPerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String action = request.getParameter("action")+"";

            if (action.equals("pass")){
                //把某条信息设置为审核通过
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Sqfw_jdgl_person person = new Sqfw_jdgl_person();
                person.setStatus(1);
                person.setId(Integer.parseInt(idStr));
                person_dao.updateData(connection,person);
            }else if (action.equals("fail")){
                //把某条信息设置为审核未通过
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Sqfw_jdgl_person person = new Sqfw_jdgl_person();
                person.setStatus(2);
                person.setId(Integer.parseInt(idStr));
                person_dao.updateData(connection,person);
            }else if ("delete".equals(action)){
                String id = request.getParameter("id");
                person_dao.deleteDataById(connection,Integer.parseInt(id));
            }

            request.setAttribute("person_no_audit",person_dao.getDatas(connection,0));
            request.setAttribute("person_audit_pass",person_dao.getDatas(connection,1));
            request.setAttribute("person_audit_fail",person_dao.getDatas(connection,2));
            request.setAttribute("dataListPage","/shequ/sqfw_jdgl_person.jsp");
            request.setAttribute("includePage","/shequ/sqfw_jdgl.jsp");
            request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    public void handelCompany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("pass")){
                //把某条信息设置为审核通过
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Sqfw_jdgl_company company = new Sqfw_jdgl_company();
                company.setStatus(1);
                company.setId(Integer.parseInt(idStr));
                company_dao.updateData(connection,company);
            }else if (action.equals("fail")){
                //把某条信息设置为审核未通过
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Sqfw_jdgl_company company = new Sqfw_jdgl_company();
                company.setStatus(2);
                company.setId(Integer.parseInt(idStr));
                company_dao.updateData(connection,company);
            }else if ("delete".equals(action)){
                String id = request.getParameter("id");
                company_dao.deleteDataById(connection,Integer.parseInt(id));
            }
            request.setAttribute("company_no_audit",company_dao.getDatas(connection,0));
            request.setAttribute("company_audit_pass",company_dao.getDatas(connection,1));
            request.setAttribute("company_audit_fail",company_dao.getDatas(connection,2));
            request.setAttribute("dataListPage","/shequ/sqfw_jdgl_company.jsp");
            request.setAttribute("includePage","/shequ/sqfw_jdgl.jsp");
            request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }

    public void handelMerchant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String action = request.getParameter("action")+"";
            if (action.equals("pass")){
                //把某条信息设置为审核通过
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Sqfw_jdgl_merchant merchant = new Sqfw_jdgl_merchant();
                merchant.setStatus(1);
                merchant.setId(Integer.parseInt(idStr));
                merchant_dao.updateData(connection,merchant);
            }else if (action.equals("fail")){
                //把某条信息设置为审核未通过
                String idStr = request.getParameter("id");
                if (StringUtil.isEmpty(idStr)){
                    throw new Exception();
                }
                Sqfw_jdgl_merchant merchant = new Sqfw_jdgl_merchant();
                merchant.setStatus(2);
                merchant.setId(Integer.parseInt(idStr));
                merchant_dao.updateData(connection,merchant);
            }else if ("delete".equals(action)){
                String id = request.getParameter("id");
                merchant_dao.deleteDataById(connection,Integer.parseInt(id));
            }
            request.setAttribute("merchant_no_audit",merchant_dao.getDatas(connection,0));
            request.setAttribute("merchant_audit_pass",merchant_dao.getDatas(connection,1));
            request.setAttribute("merchant_audit_fail",merchant_dao.getDatas(connection,2));
            request.setAttribute("dataListPage","/shequ/sqfw_jdgl_merchant.jsp");
            request.setAttribute("includePage","/shequ/sqfw_jdgl.jsp");
            request.getRequestDispatcher("/shequMainPage.jsp").forward(request,response);
        }catch (Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            pool.returnConnection(connection);
        }
    }
}
