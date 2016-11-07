package com.app.filter;

import com.app.utils.StringUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/24.
 */
//@WebFilter("/*")
public class loginFilter implements Filter{

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();

        Object object= session.getAttribute("currentUser");
        String path = request.getServletPath();
        //System.out.println(path);
        if(object==null&&path.indexOf("/wuyeLogin.jsp")<0
                && StringUtil.isEmpty(request.getParameter("from"))
                &&path.indexOf("/bootstrap")<0
                &&path.indexOf("/ueditor")<0
                &&path.indexOf("/allImages")<0
                &&path.indexOf("/wyfw/wuye")<0
                &&path.indexOf("/sqfw/shequ")<0
                && !path.contains("from=mp")
                &&path.indexOf("/welcome.jsp")<0
                &&path.indexOf("/wel")<0
                &&path.indexOf("/滚动图")<0
                &&path.indexOf("/common")<0
                &&path.indexOf("/shequLogin.jsp")<0){
            response.sendRedirect("/notify_Web/welcome.jsp");
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
    public void destroy() {

    }
}
