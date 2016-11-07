<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/19
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function submitClick(){
        var title = $("#title").val();
        var content = UE.getEditor('editor').getContent();
        if(title==""){
            $("#error").text("请输入标题");
        }else if(content==""){
            $("#error").text("请输入发布内容")
        }else {
            $("form").submit();
        }
    }
</script>
<div>
    <h3 align="center">发布通知</h3>
    <form action="/notify_Web/wyfw/tzgg?action=publish" method="post">
        标题：<input type="text" name="title" class="form-control" id="title" style="width: 400px;"><br>
        内容：
        <script type="text/plain" id="editor" name="content"></script>
        <div style="margin-top: 20px;margin-right: 60px;" align="right">

            <span id="error" style="width:200px;text-align: right;color: red;margin-right: 30px;"></span>
            <input type="button" class="btn btn-primary" value="发布" onclick="submitClick()">
        </div>
    </form>

    <script type="text/javascript">
        //实例化编辑器
        /*var ue = UE.getEditor('editor');*/
        var ue = UE.getEditor('editor',{
            initialFrameWidth : 800,
            initialFrameHeight: 300
        });
    </script>

</div>

