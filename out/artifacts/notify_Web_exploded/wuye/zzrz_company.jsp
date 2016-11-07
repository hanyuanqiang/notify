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
        max-width:100%;
    }

    table{
        max-width: 100%;
    }
</style>

<script>
    function newApply(){
        $("#showPage").attr("hidden","hidden");
        $("#addApply").attr("hidden",false);
    }

    function clickReturnButton(){
        $('#addApply').attr('hidden','hidden');
        $('#showPage').attr('hidden',false);
    }

    function submitCheck(){
        var licenseInfo = UE.getEditor('editor').getContent();;
        var legalPerson = $("#legalPerson").val();
        var phone = $("#phone").val();
        var location = $("#location").val();
        if(licenseInfo==''){
            alert("公司营业执照不能为空");
        }else if (legalPerson==''){
            alert("公司法人");
        }else if (phone==''){
            alert("公司联系方式");
        }else if (location==''){
            alert("公司地址");
        }else {
            $("form").submit();
        }
    }
</script>
<div>
    <%--未审核--%>
    <div id="showPage">
        <h3 align="center">历史申请记录【公司】
            <button style="float: right;margin-right: 30px;" class="btn btn-success btn-xs" onclick="newApply()">新建申请</button>
        </h3>
        <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
        <table border="1" align="center" width="100%"  style="table-layout: fixed">
            <tr style="height: 30px;">
                <td width="8%" style="text-align: center">公司法人</td>
                <td style="text-align: center">公司营业执照</td>
                <td width="12%" style="text-align: center">公司联系方式</td>
                <td width="30%" style="text-align: center">公司地址</td>
                <td width="10%" style="text-align: center">状态</td>
            </tr>
            <c:forEach items="${company_records}" var="data">
                <tr style="height: 30px;">
                    <td style="text-align: center">${data.legalPerson}</td>
                    <td style="text-align: center">${data.licenseInfo}</td>
                    <td style="text-align: center">${data.phone}</td>
                    <td style="text-align: center">${data.location}</td>
                    <td style="text-align: center">
                        <c:choose>
                            <c:when test="${data.status==0}">
                                <span style="color:#0a13e8;">等待审核</span>
                            </c:when>
                            <c:when test="${data.status==1}">
                                <span style="color: #38d24a">审核通过</span>
                            </c:when>
                            <c:when test="${data.status==2}">
                                <span style="color: #ff0000">审核未通过</span>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>




    <div hidden="hidden" id="addApply" style="border-style: solid;border-width: 2px;border-color: #1b6d85">
        <div style="margin-left: 20px">
            <h3 align="center">新建公司申请</h3>
            <form action="/notify_Web/sqfw/jdgl?handle=company&action=add" method="post">

                <div style="margin-top: 15px;">
                    公司法人:
                    <input type="text" name="legalPerson" id="legalPerson" size="40">
                </div>

                <div style="margin-top: 15px;">
                    公司营业执照：
                    <script type="text/plain" id="editor" name="licenseInfo"></script>
                    <script type="text/javascript">
                        //实例化编辑器
                        var ue = UE.getEditor('editor', {
                            initialFrameWidth: 770,
                            initialFrameHeight: 350
                        });
                    </script>
                </div>
                <div style="margin-top: 15px;">
                    公司联系方式：<input type="text" name="phone" id="phone" size="40">
                </div>
                <div style="margin-top: 15px;">
                    公司地址:
                    <input type="text" name="location" id="location" size="40">
                </div>
                <div style="margin-top: 10px;">
                    <div align="right" style="margin-right: 100px;"><input type="button" class="btn btn-success" value="返&nbsp;&nbsp;回" onclick="clickReturnButton()">
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="button" class="btn btn-success" value="申&nbsp;&nbsp;请" onclick="submitCheck()">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>