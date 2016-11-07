<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/15
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if(session.getAttribute("currentUser")==null){
        response.sendRedirect("/notify_Web/shequLogin.jsp");
        return;
    }
%>

<html>
<head>
    <title>社区管理</title>
    <link href="${pageContext.request.contextPath}/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/dist/css/bootstrap-theme.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap/dist/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/dist/js/bootstrap.js"></script>
    <link href="${pageContext.request.contextPath}/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/third-party/jquery-1.10.2.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script>

</head>
<body style="background-image: url('${pageContext.request.contextPath}/common/社区背景平铺.png');background-attachment: fixed;
        ">

<div class=".container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <jsp:include page="/common/shequHead.jsp"></jsp:include>
        </div>
        <div class="col-md-2"></div>
    </div>

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div style="background-color:#F5F5F5;padding: 15px;">
                <jsp:include page="${includePage}"></jsp:include>
            </div>
         </div>
        <div class="col-md-2"></div>
    </div>

    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div style="background-color:#F5F5F5;">
                <jsp:include page="/common/shequFoot.jsp"></jsp:include>
            </div>
        </div>
        <div class="col-md-2"></div>
    </div>

</div>

</body>
</html>
