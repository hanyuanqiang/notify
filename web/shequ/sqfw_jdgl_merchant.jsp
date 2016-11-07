<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/18
  Time: 13:13
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

    img{
        max-width:95%;
    }
</style>

<div>
    <%--未审核--%>
    <div>
        <h3>未审核</h3>

        <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
        <table border="1" align="center" style="table-layout: fixed" width="100%">
            <tr style="height: 30px;">
                <td width="10%" style="text-align: center">商家店名</td>
                <td style="text-align: center">营业执照</td>
                <td width="15%" style="text-align: center">负责人信息</td>
                <td width="30%" style="text-align: center">店铺地点</td>
                <td width="13%" style="text-align: center">操作</td>
            </tr>
            <c:forEach items="${merchant_no_audit}" var="data">
                <tr style="height: 30px;">
                    <td style="text-align: center">${data.name}</td>
                    <td style="text-align: center">${data.licenseInfo}</td>
                    <td style="text-align: center">${data.principal}</td>
                    <td style="text-align: center">${data.location}</td>
                    <td style="text-align: center">
                        <a href="/notify_Web/sqfw/jdgl?type=merchant&action=pass&id=${data.id}" class="btn btn-info btn-xs">通过</a>
                        <a href="/notify_Web/sqfw/jdgl?type=merchant&action=fail&id=${data.id}" class="btn btn-danger btn-xs">不通过</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <hr>

    <%--审核已通过--%>
    <div>
        <h3>审核已通过</h3>
        <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
        <table border="1" align="center" style="table-layout: fixed" width="100%">
            <tr style="height: 30px;">
                <td width="10%" style="text-align: center">商家店名</td>
                <td style="text-align: center">营业执照</td>
                <td width="15%" style="text-align: center">负责人信息</td>
                <td width="30%" style="text-align: center">店铺地点</td>
                <td width="8%" style="text-align: center">操作</td>
            </tr>
            <c:forEach items="${merchant_audit_pass}" var="data">
                <tr style="height: 30px;">
                    <td style="text-align: center">${data.name}</td>
                    <td style="text-align: center">${data.licenseInfo}</td>
                    <td style="text-align: center">${data.principal}</td>
                    <td style="text-align: center">${data.location}</td>
                    <td style="text-align: center">
                        <a class="btn btn-danger btn-xs" href="/notify_Web/sqfw/jdgl?type=merchant&action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                            删除
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <hr>
    <%--审核未通过--%>
    <div>
        <h3>审核未通过</h3>
        <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
        <table border="1" align="center" style="table-layout: fixed" width="100%">
            <tr style="height: 30px;">
                <td width="10%" style="text-align: center">商家店名</td>
                <td style="text-align: center">营业执照</td>
                <td width="15%" style="text-align: center">负责人信息</td>
                <td width="30%" style="text-align: center">店铺地点</td>
                <td width="8%" style="text-align: center">操作</td>
            </tr>
            <c:forEach items="${merchant_audit_fail}" var="data">
                <tr style="height: 30px;">
                    <td style="text-align: center">${data.name}</td>
                    <td style="text-align: center">${data.licenseInfo}</td>
                    <td style="text-align: center">${data.principal}</td>
                    <td style="text-align: center">${data.location}</td>
                    <td style="text-align: center">
                        <a class="btn btn-danger btn-xs" href="/notify_Web/sqfw/jdgl?type=merchant&action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                            删除
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>