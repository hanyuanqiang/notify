<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/16
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">
    $(function () {
        var url = location.search.toString();
        if(url.indexOf("type=merchant")!=-1){
            var li=document.getElementById("li1");
            li.className="active";
        }else if(url.indexOf("type=company")!=-1){
            var li=document.getElementById("li2");
            li.className="active";
        }else {
            var li=document.getElementById("li0");
            li.className="active";
        }
    });
</script>
<style>
    table{
        max-width: 100%;
    }
</style>

<div>
    <ul class="nav nav-tabs">
        <li id="li0">
            <a  href="/notify_Web/sqfw/jdgl?type=person">个人申请</a>
        </li>
        <li id="li1">
            <a href="/notify_Web/sqfw/jdgl?type=merchant">商家申请</a>
        </li>
        <li id="li2">
            <a href="/notify_Web/sqfw/jdgl?type=company">公司申请</a>
        </li>
    </ul>

    <jsp:include page="${dataListPage}"></jsp:include>

</div>
