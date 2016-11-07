<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/19
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<script>
    //使用Ajax异步加载指定id的信息
    function showDetail(id) {
        var xmlHttp;
        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        } else {
            xmlHttp = new ActiveXObject("Microsoft.XMLHttp");
        }
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.status == 200 && xmlHttp.readyState == 4) {
                var result = eval("(" + xmlHttp.responseText + ")");
                $("#title").html(result.title);
                $("#userName").html(result.userName);
                if(result.picUrl_beforeRepairs!=null){
                    $("#content").html("<img src='"+result.picUrl_beforeRepairs+"'><br>"+result.content);
                }else{
                    $("#content").html(result.content);
                }
            }
        };
        xmlHttp.open("post", "/notify_Web/wyfw/bxsq", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("handle=noYuyue&action=showDetail&id=" + id);
    }

    function yuyueTime(id){
        $("#yuyueForm").attr("action","/notify_Web/wyfw/bxsq?handle=noYuyue&action=yuyue&id="+id);
    }

    function onClickPublishButton(){
        $('#publish').attr('hidden',false);
        $('#publishButton').attr('hidden','hidden');
    }

    //预约维修时间时候要验证，避免预约到比现在更早的时间
    function checkForm(){
        var repairsDate = $('#repairsDate').val();
        if(Date.parse(new Date(repairsDate))+86400000<=Date.parse(new Date())){
            alert("你不能预约比现在更早的时间");
            return false;
        }else {
            return true;
        }
    }
</script >


<div style="margin-top: 15px;">

    <ul>
        <c:forEach items="${noYuyueDatas}" var="data">
            <li style="height: 40px;">
                <a href="#" onclick="showDetail(${data.id})" data-toggle="modal"
                   data-target="#showDetail">
                    <span style="">『${data.userName}』&nbsp;</span>
                   ${data.title}
                </a>
                <button class="btn btn-info btn-xs" style="margin-left: 50px;"  data-toggle="modal" data-target="#yuyueTimeModal" onclick="yuyueTime(${data.id})">
                    预约时间
                </button>
                <a class="btn btn-danger btn-xs" href="/notify_Web/wyfw/bxsq?handle=noYuyue&action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                    删除
                </a>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
            </li>
        </c:forEach>
    </ul>

</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="showDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 800px">
        <div class="modal-content">

            <style>
                img{
                    max-width:92%;
                }
            </style>

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h7 class="modal-title" id="myModalLabel">详情</h7>
            </div>
            <div class="modal-body"  style="word-break: normal;word-wrap: break-word;margin-top: 10px;">
                <div><span>用户名:</span>
                    <label id="userName"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>标题:</span>
                    <label id="title"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>详情信息:</span>
                    <label id="content"></label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<%--预约时间模态框--%>
<!-- 模态框（Modal） -->
<div class="modal fade" id="yuyueTimeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 400px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h7 class="modal-title" id="myModalLabel">预约维修时间</h7>
            </div>
            <form action="" method="post" id="yuyueForm" onsubmit="return checkForm()">
                <div class="modal-body">
                    输入时间：<input class="form-control" type="text" onClick="WdatePicker()" name="repairsDate" id="repairsDate">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">预约</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </form>
        </div>
    </div>
</div>
