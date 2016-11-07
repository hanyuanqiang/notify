<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/15
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    function updatePassword() {
        var oldPass = $("#oldPass").val();
        var newPass = $("#newPass").val();
        var newPass1 = $("#newPass1").val();
        if(oldPass==''){
            alert("请输入原密码");
            return;
        }
        if(oldPass!='${currentUser.password}'){
            alert("原密码不正确");
            return;
        }
        if(newPass==''){
            alert("请输入新密码");
            return;
        }
        if(newPass1==''){
            alert("请确认新密码");
            return;
        }
        if(newPass!=newPass1){
            alert("新密码输入不一致");
            return;
        }
        var xmlHttp;
        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        } else {
            xmlHttp = new ActiveXObject("Microsoft.XMLHttp");
        }
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.status == 200 && xmlHttp.readyState == 4) {
                var result = eval("(" + xmlHttp.responseText + ")");
                if (result.result){
                    alert("密码修改成功");
                }else{
                    alert(result.updateError);
                }
            }
        };
        /* 把用户名和密码通过post方式传到后台 */
        xmlHttp.open("post", "/notify_Web/wyfw/wuye", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("action=updatePassword&newPass="+newPass);
    }

</script>

<style type="text/css">
    .headdiv {
        height: 150px;
        width: 100%;
        background-image: url('${pageContext.request.contextPath}/common/钥匙3.jpg');
        background-size: 100%;
        background-repeat: no-repeat;
        background-color: #E8E8E6;
    }
</style>

<script>

    $(function () {
        var url = window.location.href;
        if(url.indexOf("tzgg")!=-1){
            $("#tzgg").css("background-color","#DAD5C7");
        }else if(url.indexOf("bxsq")!=-1){
            $("#bxsq").css("background-color","#DAD5C7");
        }else if(url.indexOf("detail")!=-1){
            $("#detail").css("background-color","#DAD5C7");
        }else if(url.indexOf("shfw")!=-1){
            $("#shfw").css("background-color","#DAD5C7");
        }else if(url.indexOf("llhd")!=-1||url.indexOf("xfzj")!=-1){
            $("#llhdxfzj").css("background-color","#DAD5C7");
        }else if(url.indexOf("tsjy")!=-1){
            $("#tsjy").css("background-color","#DAD5C7");
        }else if(url.indexOf("sqhy")!=-1){
            $("#sqhy").css("background-color","#DAD5C7");
        }else if(url.indexOf("jdgl")!=-1){
            $("#zzrz").css("background-color","#DAD5C7");
        }
    });

</script>

<div>
    <div class="headdiv">
        <p style="float: right;padding: 15px;">欢迎你，<span style="color: #aa7700">${currentUser.name}</span>&nbsp;&nbsp;&nbsp;
            <a href="#" onclick="" data-toggle="modal"
               data-target="#myModalUpdatePass">修改密码</a>&nbsp;&nbsp;&nbsp;
            <a href="/notify_Web/wyfw/wuye?action=logout">退出</a>
        </p>
    </div>
    <!--顶部导航栏-->
    <nav class="navbar navbar-default" role="navigation" style="margin-bottom: 0px;">
        <div>
            <%--<div style="background-color: #38d24a;height: 100px;"></div>--%>
            <div>
                <ul class="nav navbar-nav">
                    <li class="dropdown" id="tzgg">
                        <a href="" class="dropdown-toggle" data-toggle="dropdown">
                            通知公告
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/notify_Web/wyfw/tzgg?action=publish">发布</a></li>
                            <li><a href="/notify_Web/wyfw/tzgg?action=showAll">历史公告</a></li>
                        </ul>
                    </li>
                    <li id="bxsq"><a href="/notify_Web/wyfw/bxsq?handle=noYuyue">报修申请</a></li>
                    <li id="detail"><a href="/notify_Web/wyfw/detail?action=show">公司信息</a></li>
                    <li id="shfw"><a href="/notify_Web/shfw?handle=cyl">生活服务</a></li>

                    <li class="dropdown" id="llhdxfzj">
                        <a href="" class="dropdown-toggle" data-toggle="dropdown">
                            和谐社区
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/notify_Web/yzfw/llhd?mark=wuye">邻里互动</a></li>
                            <li><a href="/notify_Web/yzfw/xfzj?mark=wuye">幸福之家</a></li>
                        </ul>
                    </li>

                    <li id=""><a href="#">缴费管理</a></li>
                    <li id="tsjy"><a href="/notify_Web/wyfw/tsjy?action=wuye">用户投诉</a></li>
                    <li id="sqhy"><a href="/notify_Web/wyfw/sqhy">社区黄页</a></li>

                    <li class="dropdown" id="zzrz">
                        <a href="" class="dropdown-toggle" data-toggle="dropdown">
                            资质认证
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/notify_Web/sqfw/jdgl?handle=person">个人认证</a></li>
                            <li><a href="/notify_Web/sqfw/jdgl?handle=merchant">商家认证</a></li>
                            <li><a href="/notify_Web/sqfw/jdgl?handle=company">公司认证</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div style="border-top:1px solid #808080;"></div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModalUpdatePass" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 300px">
        <div class="modal-content">
            <div class="over-view" style="margin: 0px 30px">
                <p class="title">
                <h3 style="text-align:center;">
                    <strong>修改密码
                    </strong>
                </h3>

                <p style="border-top:1px solid #808080;"></p>
                <form class="form-horizontal" action="" method="post" id="updatePasswordForm">
                    <div class="form-group">
                        <label for="oldPass" class="col-sm-4 control-label">原密码</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="oldPass" name="oldPass" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newPass" class="col-sm-4 control-label">新密码</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="newPass" name="newPass" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newPass1" class="col-sm-4 control-label">确认密码</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="newPass1" name="newPass1" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="form-group">
                            <div class="col-sm-7">
                                <input type="button" class="btn btn-info" value="修改密码" onclick="updatePassword()"/>
                            </div>
                            <div class="col-sm-5">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

