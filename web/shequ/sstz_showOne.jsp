<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/16
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    img{
        max-width:92%;
    }
</style>
<div class="over-view" >
    <p class="title">
    <h3 style="text-align:center;">
        <strong>
            ${data.title}
        </strong>
    </h3>

    <p style="text-align: center;">
        <span>类型：</span>
        <span>${data.type}</span>
        <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <span>发布日期：</span>
        <span>${data.publishDate}</span>
    </p>

    <p style="border-top:1px solid #808080;"></p>
    </p>
    <p class="main"><p style="font-size: 120%;text-indent:2em;" id=main>${data.content}</p>
    </p>
    <p style="text-align: right;">
        <span>发布日期：</span>
        <span>${data.publishDate}</span>
    </p>
</div>
