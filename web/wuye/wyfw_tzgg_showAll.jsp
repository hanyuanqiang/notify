<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/19
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <p align="center" style="font-size: 20px">历史发布列表</p>
    <ul>
        <c:forEach items="${datas}" var="data">
            <li style="margin-bottom: 5px"><a href="/notify_Web/wyfw/tzgg?action=showOne&id=${data.id}">${data.title}</a>
                <span style="margin-left: 50px;">${data.publishDate}</span>
                <a class="btn btn-danger btn-xs" href="/notify_Web/wyfw/tzgg?action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')" style="margin-left: 50px;">
                    删除
                </a>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
            </li>
        </c:forEach>
    </ul>
    <br>
</div>

<div align="center">
    <nav>
        <ul class="pagination">
            ${pageCode}
            <%--翻页标签--%>
        </ul>
    </nav>
</div>
