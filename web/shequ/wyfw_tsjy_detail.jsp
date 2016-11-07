<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/18
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    function submitCheck(){
        var shequReplay = $('#shequReplay').val();
        if(shequReplay==''){
            alert("请输入评论内容");
            return;
        }
        $('form').submit();
    }
</script>


<div>
    <p><span>投诉人：</span>${data.userName}</p>
    <p><span>投诉内容：</span>${data.complainContent}</p>
    <p><span>投诉时间：</span>${data.complainDate}</p>

    <p>
        <span>
    <c:choose>
        <c:when test="${data.wuyeReplay==null||data.wuyeReplay==''}">
        物业回复：<span style="color: red">物业还未回复</span>
        </c:when>
        <c:otherwise>
            物业回复： ${data.wuyeReplay}
        </c:otherwise>
    </c:choose>

        </span>
    </p>


    <c:choose>
        <c:when test="${data.shequReplay==null||data.shequReplay==''}">
            回复：
            <form action="/notify_Web/wyfw/tsjy?action=shequ&handle=addReplay&id=${data.id}" method="post">
                <%--<script type="text/plain" id="editor" name="shequReplay"></script>
                <script type="text/javascript">
                    //实例化编辑器
                    var ue = UE.getEditor('editor', {
                        initialFrameWidth: 770,
                        initialFrameHeight: 200
                    });
                </script>--%>
                <textarea class="form-control" rows="5" name="shequReplay" id="shequReplay" style="width: 600px;" id="shequReplay"></textarea>
                    <br>
                <input type="button" value="回复" class="btn btn-info" onclick="submitCheck()">
            </form>
        </c:when>
        <c:otherwise>
           <strong> 社区回复：</strong><span>${data.shequReplay}</span>
        </c:otherwise>
    </c:choose>
</div>