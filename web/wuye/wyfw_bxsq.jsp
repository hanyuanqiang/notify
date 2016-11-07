<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/19
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">
    $(function () {
        var url = location.search.toString();
        if(url.indexOf("handle=haveYuyue")!=-1){
            var li=document.getElementById("li1");
            li.className="active";
        }else if(url.indexOf("handle=repairsFinish")!=-1){
            var li=document.getElementById("li2");
            li.className="active";
        }else if(url.indexOf("handle=report")!=-1){
            var li=document.getElementById("li3");
            li.className="active";
        }else {
            var li=document.getElementById("li0");
            li.className="active";
        }
    });
</script>

<div>
    <h3 align="center">报修申请管理</h3>
    <ul class="nav nav-tabs">
        <li id="li0">
            <a href="/notify_Web/wyfw/bxsq?handle=noYuyue">未预约</a>
        </li>
        <li id="li1">
            <a href="/notify_Web/wyfw/bxsq?handle=haveYuyue">已预约</a>
        </li>
        <li id="li2">
            <a href="/notify_Web/wyfw/bxsq?handle=repairsFinish">维修记录</a>
        </li>
        <li id="li3">
            <a href="/notify_Web/wyfw/bxsq?handle=report">报表形式查看</a>
        </li>
    </ul>

    <jsp:include page="${dataListPage}"></jsp:include>

</div>

