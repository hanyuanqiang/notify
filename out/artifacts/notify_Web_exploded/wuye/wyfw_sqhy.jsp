<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/16
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
    <%--新增一条社区黄页信息--%>
        <h3 align="center">社区黄页管理</h3>
        <p style="border-top:1px solid #808080;"></p>
    <div>
        <form class="form-inline" action="/notify_Web/wyfw/sqhy?action=add" method="post">
            <div class="form-group">
                <label for="example2">名称：</label>
                <input type="text" class="form-control" id="example2" style="width: 150px;" name="phoneName" id="phoneName" required>
            </div>
            <div class="form-group">
                <label for="example1">服务电话：</label>
                <input type="text" class="form-control" id="example1" style="width: 150px;" name="phoneNumber" id="phoneNumber" required>
            </div>
            <div class="form-group">
                <label for="example3">分类：</label>
                <select class="form-control" name="type" id="example3" required>
                    <option>公共热线</option>
                    <option>客服热线</option>
                    <option>文化类</option>
                    <option>生活类</option>
                </select>
            </div>
            <input type="submit" class="btn btn-info" value="发布">
        </form>

    </div>
    <c:forEach items="${maps}" var="map">
        <p>${map.key}</p>
        <c:forEach items="${map.value}" var="data">
            <div>
                <p>${data.phoneName}<span style="margin-left: 20px;">服务电话:${data.phoneNumber}</span>
                    <a href="/notify_Web/wyfw/sqhy?action=delete&id=${data.id}" class="btn btn-danger btn-xs" style="margin-left: 20px;" onclick="return confirm('确定删除这条记录？')">删除</a>
                </p>
                <p></p>
            </div>
        </c:forEach>
    </c:forEach>
</div>



