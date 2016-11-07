<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/20
  Time: 0:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">
    $(function () {
        var url = location.search.toString();
        if(url.indexOf("handle=fwl")!=-1){
            var li=document.getElementById("li1");
            li.className="active";
        }else if(url.indexOf("handle=xsl")!=-1){
            var li=document.getElementById("li2");
            li.className="active";
        }else if(url.indexOf("handle=add")!=-1) {
            var li=document.getElementById("li3");
            li.className="active";
        } else {
            var li=document.getElementById("li0")
            li.className="active";
        }
    });
</script>

<div>
    <h3 align="center">生活服务管理</h3>
    <ul class="nav nav-tabs">
        <li id="li0">
            <a href="/notify_Web/shfw?handle=cyl">餐饮类</a>
        </li>
        <li id="li1">
            <a href="/notify_Web/shfw?handle=fwl">服务类</a>
        </li>
        <li id="li2">
            <a href="/notify_Web/shfw?handle=xsl">行宿类</a>
        </li>
        <li id="li3">
            <a href="/notify_Web/shfw?handle=add&action=showAddPage">新增信息</a>
        </li>
    </ul>

    <jsp:include page="${dataListPage}"></jsp:include>

</div>