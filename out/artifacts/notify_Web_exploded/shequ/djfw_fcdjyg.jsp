<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/17
  Time: 12:34
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
$("#content").html(result.content);
$("#type").html(result.type);
$("#publishDate").html(result.publishDate);
    if(result.whoLook=='0'){
        $("#wLook").html("所有用户");
    }else if (result.whoLook=='1'){
        $("#wLook").html("所有党员");
    }else if(result.whoLook=='2'){
        $("#wLook").html(result.userName);
    }
}
};
/* 把用户名和密码通过post方式传到后台 */
xmlHttp.open("post", "/notify_Web/djfw/fcdjyg", true);
xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
xmlHttp.send("action=showOne&id=" + id);
}

function onClickPublishButton(){
$('#publish').attr('hidden',false);
$('#showPage').attr('hidden','hidden');
}

function clickReturnButton(){
    $('#publish').attr('hidden','hidden');
    $('#showPage').attr('hidden',false);
}

    function submitCheck(){
        var editorTitle = $("#editorTitle").val();
        var content = UE.getEditor('editor').getContent();
        var whoLook = $("#whoLook").val();
        var dangYuanId = $("#loadSelect").val();
        if(editorTitle==''){
            alert("请输入标题");
            return false;
        }else if (content==''){
            alert("请输入内容");
            return false;
        }else if(whoLook==2) {
            if(!dangYuanId){
                alert("请选择党员");
                return false;
            }
        }else{
           return ture;
        }
    }

    function loadDangYuan(){
        var val = $("#whoLook").val();
        if(val==2){
            $.post("/notify_Web/djfw/fcdjyg",{action:"loadDangYuan"},
                function(result){
                    var result=eval('('+result+')');
                    var htmlText = '<span id="loadSelect1">选择党员：</span><select class="form-control" name="dangYuanId" style="width: 100px" id="loadSelect">';
                    $.each( result, function(i, n){
                        htmlText+='<option value="'+n.id+'">'+n.name+'</option>';
                    });
                    htmlText+='</select>';
                    /*alert(htmlText);*/
                    $("#selectGroup").append(htmlText);
                }
            );
        }else{
            $("#loadSelect").remove();
            $("#loadSelect1").remove();
        }
    }
</script >


<div hidden="hidden" id="publish" style="border-style: solid;border-width: 2px;border-color: #1b6d85">
    <div style="margin-left: 20px">
        <h3 align="center">发布信息</h3>
        <form action="/notify_Web/djfw/fcdjyg?action=addData" method="post" class="form-inline" onsubmit="return submitCheck()">
            <div class="form-group">
                标题：<input type="text" name="title" class="form-control" id="editorTitle" style="width: 400px;" >
            </div><br>
            <div class="form-group" style="margin-top: 10px">
                正文：
                <script type="text/plain" id="editor" name="content"></script>
                <script type="text/javascript">
                    //实例化编辑器
                    var ue = UE.getEditor('editor', {
                        initialFrameWidth: 770,
                        initialFrameHeight: 400
                    });
                </script>
            </div>
            <div class="form-group" style="margin-top: 20px;" id="selectGroup">
                类型：
                <select class="form-control" name="type" style="width: 150px">
                    <option>党史纵横</option>
                    <option>微图书馆</option>
                    <option>掌上课堂</option>
                    <option>党情速递</option>
                    <option>爱心历程</option>
                    <option>党员风采</option>
                    <option>缴费提醒</option>
                </select>
                选择人群：
                <select class="form-control" name="whoLook" style="width: 150px" onchange="loadDangYuan()" id="whoLook">
                    <option value="0">所有人</option>
                    <option value="1">所有党员</option>
                    <option value="2">指定党员</option>
                </select>

            </div>
            <div style="margin-top: 10px;">
                <div align="right" style="margin-right: 100px;"><input type="button" class="btn btn-success" value="返&nbsp;&nbsp;回" onclick="clickReturnButton()">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="submit" class="btn btn-success" value="发&nbsp;&nbsp;布">
                </div>
            </div>

        </form>
    </div>
</div>

<div id="showPage">

    <div style="margin-bottom: 10px">
        <form action="/notify_Web/djfw/fcdjyg" method="post">
            <table>
                <tr>
                    <td><label style="margin-bottom: 0px">分类获取：</label></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>
                        <select class="form-control" name="contentType" style="width: 150px">
                            <option value="">所有数据...</option>
                            <option value="党史纵横" ${contentType=="党史纵横"?'selected':'' }>党史纵横</option>
                            <option value="微图书馆" ${contentType=="微图书馆"?'selected':'' }>微图书馆</option>
                            <option value="掌上课堂" ${contentType=="掌上课堂"?'selected':'' }>掌上课堂</option>
                            <option value="党情速递" ${contentType=="党情速递"?'selected':'' }>党情速递</option>
                            <option value="爱心历程" ${contentType=="爱心历程"?'selected':'' }>爱心历程</option>
                            <option value="党员风采" ${contentType=="党员风采"?'selected':'' }>党员风采</option>
                            <option value="缴费提醒" ${contentType=="缴费提醒"?'selected':'' }>缴费提醒</option>
                        </select>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>
                        <input type="submit" value="分类" class="btn btn-primary">
                    </td>
                </tr>
            </table>
        </form>
    </div>


    <p align="center">历史发布列表<button style="float: right;margin-right: 30px;" class="btn btn-success btn-xs" onclick="onClickPublishButton()">发&nbsp;布</button></p>
    <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
    <div>
        <ul>
            <c:forEach items="${fcdjygDatas}" var="data">
                <li><a href="#" onclick="showDetail(${data.id})" data-toggle="modal"
                       data-target="#myModal">『${data.type}』&nbsp;&nbsp;${data.title}</a>
                    <span style="margin-left: 50px;">${data.publishDate}</span>
                    <a class="btn btn-danger btn-xs" href="/notify_Web/djfw/fcdjyg?action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')" style="margin-left: 50px;">
                        删除
                    </a>
                    <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
                </li>
            </c:forEach>
        </ul>
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



<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 800px">
        <div class="modal-content">
                <style>
                    img{
                        max-width:92%;
                    }
                </style>

                <div class="over-view">
                    <p class="title">
                    <h3 style="text-align:center;">
                        <strong id="title">
                        </strong>
                    </h3>

                    <p style="text-align: center;">
                        <span>类型：</span>
                        <span id="type"></span>
                        <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <span>发布日期：</span>
                        <span id="publishDate"></span>
                        <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <span>对谁可见：</span>
                        <span id="wLook"></span>
                    </p>

                    <p style="border-top:1px solid #808080;"></p>
                    </p>
                    <p class="main"><p style="font-size: 120%;text-indent:2em;" id="content"></p>
                    </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>

        </div>
    </div>
</div>