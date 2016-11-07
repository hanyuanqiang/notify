<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/20
  Time: 0:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    function submitClick(){
        var title = $("#title").val();
        var content = UE.getEditor('editor').getContent();
        var phone = $("#phone").val();
        if(title==""){
            alert("请输入标题");
        }else if (content==''){
            alert("请输入内容");
        }else if (phone==""){
            alert("请输入联系方式")
        }else{
            $("form").submit();
        }
    }


</script>



<div style="margin-top: 15px;">
    <form action="/notify_Web/shfw?handle=add&action=addData" method="post">
        <div>
            <p style="float:left;">名称：</p>
            <input type="text" name="title" class="form-control" style="width:500px;" id="title">
        </div>
        <br>
        <span>具体描述：</span>
        <script type="text/plain" id="editor" name="content"></script>
        <script type="text/javascript">
            //实例化编辑器
            var ue = UE.getEditor('editor', {
                initialFrameWidth: 770,
                initialFrameHeight: 350
            });
        </script>
        <div style="margin-top: 15px;">
            <span style="float: left">联系方式：</span>
            <input type="text" name="phone" class="form-control" style="width: 200px;" id="phone">
        </div>
        <div style="margin-top: 15px;margin-bottom: 60px;">
            <span style="float: left">所属类型：</span>
            <select class="form-control" name="type" style="width:200px;">
                <option>餐饮类</option>
                <option>服务类</option>
                <option>行宿类</option>
            </select>
            <input type="button" class="btn btn-primary" value="新增" style="float:right;margin-right: 120px;" onclick="submitClick()">
        </div>
    </form>
</div>