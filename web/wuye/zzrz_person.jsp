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
        var idCardInfo = UE.getEditor('editor').getContent();;
        var workContent = $("#workContent").val();
        var phone = $("#phone").val();
        var name = $("#name").val();
        if(idCardInfo==''){
            alert("请输入身份证信息");
        }else if (workContent==''){
            alert("请输入工作内容");
        }else if (phone==''){
            alert("请输入个人联系方式");
        }else if (name==''){
            alert("请输入姓名");
        }else {
            $("form").submit();
        }
    }
</script>
<div>
    <%--未审核--%>
    <div id="showPage">
        <h3 align="center">历史申请记录【个人】
            <button style="float: right;margin-right: 30px;" class="btn btn-success btn-xs" onclick="newApply()">新建申请</button>
        </h3>
        <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
        <table border="1" align="center" width="100%"  style="table-layout: fixed">
            <tr style="height: 30px;">
                <td width="8%" style="text-align: center">姓名</td>
                <td style="text-align: center">个人身份证信息</td>
                <td width="15%" style="text-align: center">个人联系方式</td>
                <td width="25%" style="text-align: center">个人具体工作</td>
                <td width="10%" style="text-align: center">状态</td>
            </tr>
            <c:forEach items="${person_records}" var="data">
                <tr style="height: 30px;">
                    <td style="text-align: center">${data.name}</td>
                    <td style="text-align: center">${data.idCardInfo}</td>
                    <td style="text-align: center">${data.phone}</td>
                    <td style="text-align: center">${data.workContent}</td>
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
            <h3 align="center">新建个人申请</h3>
            <form action="/notify_Web/sqfw/jdgl?handle=person&action=add" method="post">

                <div style="margin-top: 15px;">
                    姓名：<input type="text" name="name" id="name" size="40"><br>
                </div>
                <div style="margin-top: 15px;">
                    个人身份证信息：
                    <script type="text/plain" id="editor" name="idCardInfo"></script>
                    <script type="text/javascript">
                        //实例化编辑器
                        var ue = UE.getEditor('editor', {
                            initialFrameWidth: 770,
                            initialFrameHeight: 350
                        });
                    </script>
                </div>
                <div style="margin-top: 15px;">
                    个人具体工作:
                    <textarea class="form-control" rows="5" name="workContent" id="workContent" style="width: 600px;"></textarea>
                </div>
                <div style="margin-top: 15px;">
                    个人联系方式：<input type="text" name="phone" id="phone" size="40"><br>
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