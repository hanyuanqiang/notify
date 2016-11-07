<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/4
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>

    th {
        font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
        color: #4f6b72;
        border-right: 1px solid #C1DAD7;
        border-bottom: 1px solid #C1DAD7;
        border-top: 1px solid #C1DAD7;
        letter-spacing: 2px;
        text-transform: uppercase;
        text-align: left;
        padding: 6px 6px 6px 12px;
        background: #CAE8EA  no-repeat;
    }

    th.nobg {
        border-top: 0;
        border-left: 0;
        border-right: 1px solid #C1DAD7;
        background: none;
    }

    td {
        border-right: 1px solid #C1DAD7;
        border-bottom: 1px solid #C1DAD7;
        background: #fff;
        font-size:11px;
        padding: 6px 6px 6px 12px;
        color: #4f6b72;
    }


    td.alt {
        background: #F5FAFA;
        color: #797268;
    }

    th.spec {
        border-left: 1px solid #C1DAD7;
        border-top: 0;
        background: #fff no-repeat;
        font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
    }

    th.specalt {
        border-left: 1px solid #C1DAD7;
        border-top: 0;
        background: #f5fafa no-repeat;
        font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
        color: #797268;
    }

    table{
        max-width: 100%;
    }
</style>
<div style="margin-right: 20px;text-align: right">
    <form class="navbar-form navbar-right" role="search" method="post" action="/notify_Web/sqfw/yhgl?action=search">
        <div class="form-group">
            身份证号码：<input type="text" class="form-control" placeholder="身份证号码" id="idCard" name="idCard" value="${idCard}">
            姓名：<input type="text" class="form-control" placeholder="姓名" id="name" name="name" value="${name}" style="width: 100px">
            是否党员：
            <select class="form-control" name="isDangYuan" style="width: 120px">
                <option value="-1">所有用户</option>
                <option value="1" ${isDangYuan=="1"?'selected':'' }>党员</option>
                <option value="0" ${isDangYuan=="0"?'selected':'' }>非党员</option>
            </select>
        </div>
        <input type="submit" class="btn btn-success" value="搜索"/>
    </form>
</div>
<p>&nbsp;</p>
<p>&nbsp;</p>
<div style="word-break: normal;word-wrap: break-word;">

    <div>
        <h4 align="center" style="font-family: Helvetica,
        'Hiragino Sans GB', 'Microsoft Yahei',
        '微软雅黑', Arial, sans-serif;">小区用户列表</h4>
    </div>

    <table align="center" width="100%;" border="1" style="table-layout: fixed">
        <tr>
            <td width="7%" style="text-align: center"><strong>姓名</strong></td>
            <td width="17%" style="text-align: center"><strong>身份证号码</strong></td>
            <td width="12%" style="text-align: center"><strong>联系方式</strong></td>
            <td style="text-align: center"><strong>地址</strong></td>
            <td width="10%" style="text-align: center"><strong>用户密码</strong></td>
            <td width="6%" style="text-align: center"><strong>是否党员</strong></td>
            <td width="16%" style="text-align: center"><strong>操作</strong></td>
        </tr>
        <c:forEach items="${users}" var="data">
            <tr style="height: 45px;">
                <td style="text-align: center">${data.name}</td>
                <td style="text-align: center">${data.idCard}</td>
                <td style="text-align: center">${data.phone}</td>
                <td style="text-align: center">${data.address}</td>
                <td style="text-align: center">${data.password}</td>

                <c:choose>
                    <c:when test="${data.isDangYuan=='1'}">
                        <td style="text-align: center"><span>是</span></td>
                    </c:when>
                    <c:otherwise>
                        <td style="text-align: center"><span>否</span></td>
                    </c:otherwise>
                </c:choose>


                <c:choose>
                    <c:when test="${data.isDangYuan=='1'}">
                        <td style="text-align: center">
                            <a class="btn btn-danger btn-xs" href="/notify_Web/sqfw/yhgl?action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                                删除
                            </a>
                            <a class="btn btn-primary btn-xs" href="/notify_Web/sqfw/yhgl?action=recoverRole&id=${data.id}" onclick="return confirm('你确定要撤销该用户的党员身份？')">
                                党员撤销
                            </a>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td style="text-align: center">
                            <a class="btn btn-danger btn-xs" href="/notify_Web/sqfw/yhgl?action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                                删除
                            </a>
                            <a class="btn btn-info btn-xs" href="/notify_Web/sqfw/yhgl?action=changeRole&id=${data.id}" onclick="return confirm('你确定把该用户转为党员？')">
                                转为党员
                            </a>
                        </td>
                    </c:otherwise>
                </c:choose>



            </tr>
        </c:forEach>

    </table>
    <div align="center">
        <nav>
            <ul class="pagination">
                ${pageCode}
                <%--翻页标签--%>
            </ul>
        </nav>
    </div>

</div>