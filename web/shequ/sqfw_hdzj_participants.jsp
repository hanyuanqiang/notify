<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/17
  Time: 22:36
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

    h3 {
        margin-left: 10px;
        width:100%;
        display:block;
        line-height:1.5em;
        overflow:visible;
        font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
        font-size:22px;
        color: #797268;
        text-shadow:#f3f3f3 1px 1px 0px, #b2b2b2 1px 2px 0
    }

    table{
        max-width: 100%;
    }
</style>
<div>
    <div>
        <button class="btn btn-info btn-xs" type="button" onclick="javascript:history.back()" style="float: right">返回</button>
        <h3 align="center">报名详情</h3>
    </div>

    <p style="border-top:1px solid #808080;"></p>
    <table align="center">
        <tr>
            <td width="200px" style="text-align: center"><strong>姓名</strong></td>
            <td width="200px" style="text-align: center"><strong>身份证号码</strong></td>
            <td width="200px" style="text-align: center"><strong>联系方式</strong></td>
            <td width="200px" style="text-align: center"><strong>操作</strong></td>
        </tr>
        <c:forEach items="${participantsDatas}" var="data">
            <tr style="height: 45px;">
                <td style="text-align: center">${data.name}</td>
                <td style="text-align: center">${data.idCard}</td>
                <td style="text-align: center">${data.phone}</td>
                <td style="text-align: center">
                    <a class="btn btn-danger btn-xs" href="/notify_Web/sqfw/hdzj?action=deleteParticipant&infoId=${data.infoId}&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                        删除
                    </a>
                </td>
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
