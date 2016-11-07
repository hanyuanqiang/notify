<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/15
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h3 align="center">发布通知</h3><br>
<div>
    <form action="/notify_Web/sqfw/sstz?action=publish" method="post" class="form-inline">
        <div class="form-group">
            <label for="title">标题:</label>
            <input type="text" name="title" class="form-control" style="width: 400px;" id="title">
        </div>
        <br>
        <div style="margin-top: 20px;">
            <label for="content" style="float: left">内容:</label>
            <script type="text/plain" id="editor" name="content" style="margin-left:35px;" id="content"></script>
        </div>
        <div style="margin-top: 20px;">
            <span>类型:</span>
            <select class="form-control" name="type" style="width: 160px">
                <option>小区通知</option>
                <option>民政</option>
                <option>计生</option>
                <option>社会组织</option>
                <option>热点关注</option>
                <option>其他</option>
            </select>
            <input type="button" class="btn btn-primary" style="margin-left: 535px;width: 100px;" onclick="submitClick()" value="发布">
            <br><br>
            <span id="error" style="width:200px;text-align: right;color: red"></span>
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

</div>

