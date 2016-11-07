<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/19
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

</script>
<div>
    <h3 align="center">修改公司信息</h3>
    <div style="margin-left: 100px;margin-bottom: 50px;">
        <form action="/notify_Web/wyfw/detail?action=update" method="post" enctype="multipart/form-data">
            <div style="margin-bottom: 20px;">
                <p>上传图片：<input type="file" name="file"></p>
                <div>
                    <p>公司简介：</p>
                    <textarea class="form-control" rows="5" name="message" id="message" style="width: 600px;">${data.message}</textarea>
                </div>
            </div>
            <div style="margin-bottom: 20px;">
                <p>人员配置：</p>
                <textarea class="form-control" rows="5" name="person" id="person" style="width: 600px;">${data.person}</textarea>
            </div>
            <div style="margin-bottom: 20px;">
                <p>硬件设施：</p>
                <textarea class="form-control" rows="5" name="hardset" id="hardset" style="width: 600px;">${data.person}</textarea>
            </div>
            <div style="margin-bottom: 20px;">
                <p>其他：</p>
                <textarea class="form-control" rows="5" name="others" id="others" style="width: 600px;">${data.others}</textarea>
            </div>
            <div style="float: right;margin-top: 5px;margin-right: 200px;">
                <input type="submit" value="修改" class="btn btn-info  btn-xs">
            </div>
        </form>
    </div>
</div>


