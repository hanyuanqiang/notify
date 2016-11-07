<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/22
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

    img{
        max-width:95%;
    }

    table{
        max-width: 100%;
    }
</style>

<script>
    function checkSearchForm(){
        if($("#userName").val()||$("#bxTime").val()){
            $("form").submit();
        }else {
            alert("至少输入其中一项");
            return;
        }
    }
</script>

<div>
    <div style="margin-right: 20px;text-align: right">
        <form class="navbar-form navbar-right" role="search" method="post" action="/notify_Web/wyfw/bxsq?handle=report&tag=search">
            <div class="form-group">
                姓名：<input type="text" class="form-control" placeholder="报修者姓名" id="userName" name="userName" value="${userName}">
                报修日期：<input type="text" class="form-control" placeholder="报修日期" id="bxTime" name="bxTime" onClick="WdatePicker()" value="${bxTime}">
            </div>
            <input type="button" class="btn btn-success" value="搜索" onclick="checkSearchForm()"/>
        </form>
    </div>

    <div id="showPage">
        <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
        <table border="1" align="center" width="100%"  style="table-layout: fixed">
            <tr style="height: 30px;">
                <td width="5%" style="text-align: center">序号</td>
                <td width="8%" style="text-align: center">姓名</td>
                <td width="13%" style="text-align: center">电话</td>
                <td style="text-align: center">报修内容</td>
                <td width="15%" style="text-align: center">报修时间</td>
                <td width="8%" style="text-align: center">处理状态</td>
                <td width="10%" style="text-align: center">点评</td>
                <td width="8%" style="text-align: center"><strong>操作</strong></td>
            </tr>
            <c:forEach items="${reportDatas}" var="data">
                <tr style="height: 30px;">
                    <td style="text-align: center">${data.id}</td>
                    <td style="text-align: center">${data.userName}</td>
                    <td style="text-align: center">${data.phone}</td>
                    <td style="text-align: center">${data.content}</td>
                    <td style="text-align: center">${data.bxTime}</td>
                    <td style="text-align: center">
                        <c:if test="${data.repairsDate==''||data.repairsDate==null}">
                            <span style="color: #d21a0d">未处理</span>
                        </c:if>
                        <c:if test="${data.repairsDate!=''&&data.repairsDate!=null}">
                            <c:if test="${data.isRepairsFinish==0}">
                                <span style="color: #0c10d2">处理中</span>
                            </c:if>
                        </c:if>
                        <c:if test="${data.isRepairsFinish==1}">
                            <span style="color: #38d24a">已处理</span>
                        </c:if>
                    </td>
                    <td style="text-align: center">
                        <c:choose>
                            <c:when test="${data.star==0}">
                                <span style="color:#0a13e8;">暂未点评</span>
                            </c:when>
                            <c:when test="${data.star==1}">
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                            </c:when>
                            <c:when test="${data.star==2}">
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                            </c:when>
                            <c:when test="${data.star==3}">
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                            </c:when>
                            <c:when test="${data.star==4}">
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                            </c:when>
                            <c:when test="${data.star==5}">
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                                <img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='10px' height='10px'>
                            </c:when>
                        </c:choose>
                    </td>
                    <td style="text-align: center">
                        <a class="btn btn-danger btn-xs" href="/notify_Web/wyfw/bxsq?handle=report&action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                            删除
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div align="center">
        <nav>
            <ul class="pagination">
                ${pageCode}
                <%--翻页标签--%>
            </ul>
        </nav>
    </div>
</div>