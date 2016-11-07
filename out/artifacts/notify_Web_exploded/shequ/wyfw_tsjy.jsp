<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/18
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div>
    <%--未回复--%>
    <p>&#9650&nbsp;未回复的投诉建议</p>
    <div>
        <c:forEach items="${noShequReplay}" var="data">
            <!--打印第一条信息-->
            <div class="list-group" style="width: 600px;margin-left: 20px;"  >
                <a class="list-group-item active">
                    <h4 class="list-group-item-heading" style="height: 5px;">
                        [投诉人]${data.userName}
                    </h4>
                </a>
                <a href="/notify_Web/wyfw/tsjy?action=shequ&handle=showDetail&id=${data.id}" class="list-group-item">
                    <h4 class="list-group-item-heading" style="height: 5px;">
                        <%--${data.complainContent}--%>
                        <c:choose>
                            <c:when test="${fn:length(c) > 12}">
                                <c:out value="${fn:substring(data.complainContent, 0, 12)}..." />
                            </c:when>
                            <c:otherwise>
                                <c:out value="${data.complainContent}" />
                            </c:otherwise>
                        </c:choose>
                    </h4>
                </a>
                <div style="text-align: right">
                    <a class="btn btn-danger btn-xs" href="/notify_Web/wyfw/tsjy?action=shequ&handle=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                        删除
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>



    <%--历史投诉信息--%>
    <p>&#10148已回复</p>
    <div>
        <c:forEach items="${haveShequReplay}" var="data">
            <!--打印第一条信息-->
            <div class="list-group" style="width: 600px;margin-left: 20px;">
                <a class="list-group-item active">
                    <h4 class="list-group-item-heading" class="list-group-item-heading" style="height: 5px;">
                        [投诉人]${data.userName}
                    </h4>
                </a>
                <a href="/notify_Web/wyfw/tsjy?action=shequ&handle=showDetail&id=${data.id}" class="list-group-item">
                    <h4 class="list-group-item-heading" class="list-group-item-heading" style="height: 5px;">
                        <c:choose>
                            <c:when test="${fn:length(data.complainContent) > 12}">
                                <c:out value="${fn:substring(data.complainContent, 0, 12)}..." />
                            </c:when>
                            <c:otherwise>
                                <c:out value="${data.complainContent}" />
                            </c:otherwise>
                        </c:choose>
                    </h4>
                </a>
                <div style="text-align: right">
                    <a class="btn btn-danger btn-xs" href="/notify_Web/wyfw/tsjy?action=shequ&handle=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                        删除
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>