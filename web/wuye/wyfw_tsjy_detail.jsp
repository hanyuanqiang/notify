<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    function submitCheck(){
        var wuyeReplay = $('#wuyeReplay').val();
        if(wuyeReplay==''){
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

    <c:choose>
        <c:when test="${data.wuyeReplay==null||data.wuyeReplay==''}">
            回复：
            <form action="/notify_Web/wyfw/tsjy?action=wuye&handle=addReplay&id=${data.id}" method="post">
                    <%--<script type="text/plain" id="editor" name="shequReplay"></script>
                    <script type="text/javascript">
                        //实例化编辑器
                        var ue = UE.getEditor('editor', {
                            initialFrameWidth: 770,
                            initialFrameHeight: 200
                        });
                    </script>--%>
                <textarea class="form-control" rows="5" name="wuyeReplay" id="wuyeReplay" style="width: 600px;" id="shequReplay"></textarea>
                <br>
                <input type="button" value="回复" class="btn btn-info" onclick="submitCheck()">
            </form>
        </c:when>
        <c:otherwise>
            物业回复:<p>${data.wuyeReplay}</p>
        </c:otherwise>
    </c:choose>
</div>