<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/16
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    //使用Ajax异步加载指定id的信息
    function showDetail(id){
        var xmlHttp;
        if(window.XMLHttpRequest){
            xmlHttp=new XMLHttpRequest();
        }else{
            xmlHttp=new ActiveXObject("Microsoft.XMLHttp");
        }
        xmlHttp.onreadystatechange=function(){
            if(xmlHttp.status==200&&xmlHttp.readyState==4){
                var result = eval("("+xmlHttp.responseText+")");
                    $("#title").html(result.title);
                    $("#content").html(result.content);
                    $("#response").html(result.response);
                    $("#timestamp").html(result.timestamp);
                    $("#userName").html(result.userName);
            }
        };
        /* 把用户名和密码通过post方式传到后台 */
        xmlHttp.open("post","/notify_Web/djfw/djzs", true);
        xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlHttp.send("status=1&id="+id);
    }


</script>

<div style="word-break: normal;word-wrap: break-word;margin-top: 10px">

    <ul>
        <c:forEach items="${haveResponseDatas}" var="data">
            <li style="margin-bottom: 10px;"><a href="#"  onclick="showDetail(${data.id})" data-toggle="modal"
                   data-target="#myModal">${data.title}</a>
                <span style="margin-left: 50px;margin-right: 20px" >${data.timestamp}</span>
                <a class="btn btn-danger btn-xs" href="/notify_Web/djfw/djzs?status=1&action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                    删除
                </a>
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

<div style="word-break: normal;word-wrap: break-word;margin-top: 10px" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 800px">
        <div class="modal-content">

            <style>
                img{
                    max-width:92%;
                }
            </style>

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h5 class="modal-title" id="myModalLabel" align="center">问题详情</h5>
            </div>
            <div class="modal-body">
                <div><label>问题：</label><span id="title" style="font-size: 15px"></span></div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><label>问题描述:</label>
                    <span id="content" style="font-size: 15px"></span>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><label>社区回复:</label>
                    <span id="response"></span>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div>
                    <label>时间：</label><span id="timestamp"></span>
                    <label style="margin-left: 30px;">提问者：</label><span id="userName"></span>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>