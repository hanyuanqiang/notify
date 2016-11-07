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
                $("#repairsDate").html(result.repairsDate);
            }
        };
        xmlHttp.open("post", "/notify_Web/wyfw/bxsq", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("handle=haveYuyue&action=showDetail&id=" + id);
    }

    function repairsFinish(id){
        //$("#yuyueForm").attr("action","/notify_Web/wyfw/bxsq?handle=noYuyue&action=yuyue&id="+id);
        var xmlHttp;
        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        } else {
            xmlHttp = new ActiveXObject("Microsoft.XMLHttp");
        }
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.status == 200 && xmlHttp.readyState == 4) {
                var result = eval("(" + xmlHttp.responseText + ")");
                if(result.status==1){
                    alert("操作成功");
                    window.location.href = "/notify_Web/wyfw/bxsq?handle=haveYuyue";
                }
            }
        };
        xmlHttp.open("post", "/notify_Web/wyfw/bxsq", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("handle=haveYuyue&action=repairsFinish&id=" + id);
    }

    function onClickPublishButton(){
        $('#publish').attr('hidden',false);
        $('#publishButton').attr('hidden','hidden');
    }

</script >



<div style="margin-top: 15px;">

    <ul>
        <c:forEach items="${haveYuyueDatas}" var="data">
            <li>
                <a href="#" onclick="showDetail(${data.id})" data-toggle="modal"
                   data-target="#showDetail">
                    <span style="">『${data.userName}』&nbsp;</span>
                        ${data.title}
                </a>
                <button class="btn btn-info btn-xs" style="margin-left: 50px;"  data-toggle="modal" data-target="#yuyueTimeModal" onclick="repairsFinish(${data.id})">
                    维修完成
                </button>
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
            <div class="modal-body" style="word-break: normal;word-wrap: break-word;margin-top: 10px;">
                <div><span>用户名:</span>
                    <label id="userName"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>标题:</span>
                    <label id="title"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>详情:</span>
                    <label id="content"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>预约维修时间:</span>
                    <label id="repairsDate"></label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>