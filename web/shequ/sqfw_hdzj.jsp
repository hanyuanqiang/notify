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
            }
        };
        /* 把用户名和密码通过post方式传到后台 */
        xmlHttp.open("post", "/notify_Web/sqfw/hdzj", true);
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
        if(editorTitle==''){
            alert("请输入标题");
            return;
        }else if (content==''){
            alert("请输入内容");
            return;
        }else{
            $('form').submit();
        }
    }
</script >


<div hidden="hidden" id="publish" style="border-style: solid;border-width: 2px;border-color: #1b6d85">
    <div style="margin-left: 20px">
        <h2 align="center">发布活动</h2>
        <form action="/notify_Web/sqfw/hdzj?action=publish" method="post">
            标题：<input type="text" name="title" id="editorTitle" size="40" ><br>
            正文：
            <script type="text/plain" id="editor" name="content"></script>
            <script type="text/javascript">
                //实例化编辑器
                var ue = UE.getEditor('editor', {
                    initialFrameWidth: 770,
                    initialFrameHeight: 330
                });
            </script>
            <div style="margin-top: 10px;">
                <div align="right" style="margin-right: 100px;"><input type="button" class="btn btn-success" value="返&nbsp;&nbsp;回" onclick="clickReturnButton()">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" class="btn btn-success" value="发&nbsp;&nbsp;布" onclick="submitCheck()">
                </div>
            </div>
        </form>
    </div>
</div>

<div  id="showPage">
    <p align="center">活动列表<button style="float: right;margin-right: 30px;" class="btn btn-success btn-xs" onclick="onClickPublishButton()">发&nbsp;布</button></p>
    <p style="border-top:1px solid #808080;"></p>
    <div>
        <ul>
            <c:forEach items="${hdzjDatas}" var="data">
                <li><a href="#" onclick="showDetail(${data.id})" data-toggle="modal"
                       data-target="#myModal01">${data.title}</a>
                    <span style="margin-left: 50px;">${data.publishDate}</span>
                    <a class="btn btn-info btn-xs" style="margin-left: 50px;" href="/notify_Web/sqfw/hdzj?action=showParticipants&infoId=${data.id}">
                        报名情况
                    </a>
                    <a class="btn btn-danger btn-xs" href="/notify_Web/sqfw/hdzj?action=deleteHuoDong&id=${data.id}" onclick="return confirm('确定删除这条记录？')" style="margin-left: 10px;">
                        删除活动
                    </a>
                    <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div align="center">
        <nav>
            <ul class="pagination">
                ${pageCode}
            </ul>
        </nav>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal01" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <span>发布日期：</span>
                    <span id="publishDate"></span>
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

