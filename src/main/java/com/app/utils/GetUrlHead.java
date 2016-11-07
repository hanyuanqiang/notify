package com.app.utils;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2016/7/11.
 */
//该类用于获取图片的url的头部，如：http://localhost:8080/notify_Web
public class GetUrlHead {
    private Logger logger = Logger.getLogger(this.getClass());

    public static String getUrlHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String urlHead = "";
        Enumeration<String> headers = request.getHeaders("origin");
        while (headers.hasMoreElements()) {
            urlHead+= headers.nextElement();
        }
        if (StringUtil.isEmpty(urlHead)){
            urlHead = request.getScheme()+"://"+request.getServerName();
        }
        return urlHead+=request.getContextPath();*/
        return getUrlHead_noProjectName(request,response)+request.getContextPath();
    }

    //获取url头部(不包括项目名)
    public static String getUrlHead_noProjectName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String urlHead = "";
        Enumeration<String> headers = request.getHeaders("origin");
        while (headers.hasMoreElements()) {
            urlHead+= headers.nextElement();
        }
        *//*if (StringUtil.isEmpty(urlHead)){
            urlHead = request.getScheme()+"://"+request.getServerName();
        }*//*
        String test = request.getScheme()+"://"+request.getServerName();
        return urlHead;*/
        StringBuffer sb = request.getRequestURL();
        String contextPath = request.getContextPath();
        int tag = sb.toString().indexOf(contextPath);
        String result = sb.toString().substring(0,tag);
        return result;
    }
}
