<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/20
  Time: 0:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                $("#type").html(result.type);
                $("#phone").html(result.phone);
                if(result.picUrl!=null){
                    $("#content").html("<img src='"+result.picUrl+"'><br>"+result.content);
                }else{
                    $("#content").html(result.content);
                }
            }
        };
        /* 把用户名和密码通过post方式传到后台 */
        xmlHttp.open("post", "/notify_Web/shfw", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("handle=xsl&action=showDetail&id=" + id);
    }

    function onClickPublishButton(){
        $('#publish').attr('hidden',false);
        $('#publishButton').attr('hidden','hidden');
    }

</script >


<div>
    <ul>
        <c:forEach items="${xslDatas}" var="data">
            <li style="margin-top: 15px;">
                <span>『${data.title}』</span>
                &nbsp;&nbsp;&nbsp;
                <span>联系方式：${data.phone}</span>
                <a href="#"  onclick="showDetail(${data.id})" data-toggle="modal"
                   data-target="#myModal" class="btn btn-info btn-xs" style="margin-left: 30px;">详情
                </a>
                <a class="btn btn-danger btn-xs" href="/notify_Web/shfw?handle=xsl&action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                    删除
                </a>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 3px 20px;"></div>
            </li>
        </c:forEach>
    </ul>
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

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h7 class="modal-title" id="myModalLabel">详情</h7>
            </div>
            <div class="modal-body">
                <div><span>标题:</span>
                    <label id="title"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 3px 0px;"></div>
                <div><span>所属类别:</span>
                    <label id="type"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 3px 0px;"></div>
                <div><span>联系方式:</span>
                    <label id="phone"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 3px 0px;"></div>
                <div><span>详细介绍:</span>
                    <label style="font-size: 120%;" id="content"></label>
                    <%--<label id="content"></label>--%>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>