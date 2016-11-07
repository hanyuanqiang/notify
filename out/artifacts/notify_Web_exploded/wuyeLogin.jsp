<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/23
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <title>物业登录</title>
    <link href="${pageContext.request.contextPath}/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/dist/css/bootstrap-theme.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap/dist/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/dist/js/bootstrap.js"></script>

</head>

<script>
    function registFrame(){
        $("#registFrame").attr('hidden',false);
        $("#loginFrame").attr('hidden','hidden');
    }
    function loginFrame(){
        $("#registFrame").attr('hidden','hidden');
        $("#loginFrame").attr('hidden',false);
    }
    function login(){
        var loginUserName = $("#loginUserName").val();
        var loginPassword = $("#loginPassword").val();
        if(loginUserName==''){
            alert("请输入用户名");
            return;
        }
        if(loginPassword==''){
            alert("请输入密码");
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
                if (result.loginError==null||result.loginError==''){
                    window.location.href = "/notify_Web/wyfw/tzgg?action=showAll";
                }else{
                    alert(result.loginError);
                }
            }
        };
        /* 把用户名和密码通过post方式传到后台 */
        xmlHttp.open("post", "/notify_Web/wyfw/wuye", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("action=login&userName="+loginUserName+"&password="+loginPassword);
    }

    function regist(){
        var registUserName = $("#registUserName").val();
        var registPassword = $("#registPassword").val();
        var rpassword = $("#rpassword").val();

        if(registUserName==''){
            alert("请输入用户名");
            return;
        }
        if(registPassword==''){
            alert("请输入密码");
            return;
        }
        if(rpassword==''){
            alert("请输入确认密码");
            return;
        }
        if(rpassword!=registPassword){
            alert("密码输入不一致");
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
                if (result.registError==null||result.registError==''){
                    window.location.href = "/notify_Web/wyfw/tzgg?action=showAll";
                }else{
                    alert(result.registError);
                }
            }
        };
        /* 把用户名和密码通过post方式传到后台 */
        xmlHttp.open("post", "/notify_Web/wyfw/wuye", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("action=regist&userName="+registUserName+"&password="+registPassword);
    }


</script>

<body style="background-color: #b2bdbd;">


<%--登录--%>
<div align="center" style="margin-top: 70px;" id="loginFrame">

    <div style="background-color: whitesmoke;width: 300px;border-radius: 15px;padding: 20px;">
        <p align="center" style="font-size: 25px;">物业登录</p>
        <p style="border-top:1px solid #808080;"></p>
        <form class="form-horizontal" action="" method="post" id="loginForm">
            <div class="form-group">
                <label for="loginUserName" class="col-sm-4 control-label">用户名</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="loginUserName" name="userName" required>
                </div>
            </div>
            <div class="form-group">
                <label for="loginPassword" class="col-sm-4 control-label">密码</label>
                <div class="col-sm-8">
                    <input type="password" class="form-control" id="loginPassword" name="password" required>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-7">
                    <input type="button" class="btn btn-info" value="登&nbsp;录" onclick="login()"/>
                </div>
                <div class="col-sm-5">
                    <button type="reset" class="btn btn-primary">重&nbsp;置</button>
                </div>
            </div>
            <%--<div style="float:right;">
                    <span style="font-size: 8px;">没有账号？</span><a href="#" style="font-size: 8px;color: deepskyblue" onclick="registFrame()">注册</a>
            </div>--%>
        </form>
    </div>
</div>






<%--注册--%>
<div align="center" style="margin-top: 70px;" hidden="hidden" id="registFrame">

    <div style="background-color: whitesmoke;width: 300px;border-radius: 15px;padding: 20px;">
        <p align="center" style="font-size: 25px;">注册物业账号</p>
        <p style="border-top:1px solid #808080;"></p>
        <form class="form-horizontal" action="" method="post" id="registForm">
            <div class="form-group">
                <label for="registUserName" class="col-sm-4 control-label">用户名</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="registUserName" name="userName" required>
                </div>
            </div>
            <div class="form-group">
                <label for="registPassword" class="col-sm-4 control-label">密码</label>
                <div class="col-sm-8">
                    <input type="password" class="form-control" id="registPassword" name="password" required>
                </div>
            </div>
            <div class="form-group">
                <label for="rpassword" class="col-sm-4 control-label">确认密码</label>
                <div class="col-sm-8">
                    <input type="password" class="form-control" id="rpassword" name="rpassword" required>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-7">
                    <input type="button" class="btn btn-info" value="注&nbsp;册" onclick="regist()"/>
                </div>
                <div class="col-sm-5">
                    <button type="reset" class="btn btn-primary" onclick="loginFrame()">返&nbsp;回</button>
                </div>
            </div>
        </form>
    </div>
</div>


</body>
</html>
