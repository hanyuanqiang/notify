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
        xmlHttp.open("post", "/notify_Web/sqfw/shequ", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("action=updatePassword&newPass="+newPass);
    }

</script>

<style type="text/css">
    .headdiv {
        height: 150px;
        width: 100%;
        background-image: url('${pageContext.request.contextPath}/common/社区头部背景.png');
        background-size: 100%;
        background-repeat: no-repeat;
        background-color: #FFFFFF;
    }
</style>

<script>

    $(function () {
        var url = window.location.href;
        if(url.indexOf("sstz")!=-1){
            $("#sstz").css("background-color","#DAD5C7");
        }else if(url.indexOf("djfw")!=-1){
            $("#djfw").css("background-color","#DAD5C7");
        }else if(url.indexOf("hdzj")!=-1||url.indexOf("hdly")!=-1){
            $("#hdzjhdly").css("background-color","#DAD5C7");
        }else if(url.indexOf("bmfw")!=-1){
            $("#bmfw").css("background-color","#DAD5C7");
        }else if(url.indexOf("llhd")!=-1||url.indexOf("xfzj")!=-1){
            $("#llhdxfzj").css("background-color","#DAD5C7");
        }else if(url.indexOf("detail")!=-1){
            $("#detail").css("background-color","#DAD5C7");
        }else if(url.indexOf("jdgl")!=-1){
            $("#jdgl").css("background-color","#DAD5C7");
        }else if(url.indexOf("tsjy")!=-1){
            $("#tsjy").css("background-color","#DAD5C7");
        }else if(url.indexOf("yhgl")!=-1){
            $("#yhgl").css("background-color","#DAD5C7");
        }
    });

</script>

<div>
    <div class="headdiv">
        <p style="float: right;padding: 5px;">欢迎你，<span style="color: #aa7700">${currentUser.name}</span>&nbsp;&nbsp;&nbsp;
            <a href="#" onclick="" data-toggle="modal"
               data-target="#myModalUpdatePass">修改密码</a>&nbsp;&nbsp;&nbsp;
            <a href="/notify_Web/sqfw/shequ?action=logout">退出</a>
        </p>

    </div>
    <!--顶部导航栏-->
    <nav class="navbar navbar-default" role="navigation" style="background-color: #59e131;margin-bottom: 0px;">
        <div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="dropdown" id="sstz">
                        <a href="/notify_Web/sqfw/sstz?action=showAll" class="dropdown-toggle" data-toggle="dropdown">
                            实时通知
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/notify_Web/sqfw/sstz?action=publish">发布</a></li>
                            <li><a href="/notify_Web/sqfw/sstz?action=showAll">历史发布</a></li>
                        </ul>
                    </li>
                    <li class="dropdown" id="djfw">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            党建服务
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/notify_Web/djfw/djzs">党建助手</a></li>
                            <li><a href="/notify_Web/djfw/fcdjyg">党建E家</a></li>
                        </ul>
                    </li>
                    <li class="dropdown" id="hdzjhdly">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            社区活动
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/notify_Web/sqfw/hdzj">活动信息</a></li>
                            <li><a href="/notify_Web/sqfw/hdly">活动掠影</a></li>
                        </ul>
                    </li>
                    <li id="bmfw"><a href="/notify_Web/sqfw/bmfw">便民服务</a></li>

                    <li class="dropdown" id="llhdxfzj">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            和谐社区
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/notify_Web/yzfw/llhd?mark=shequ">邻里互动</a></li>
                            <li><a href="/notify_Web/yzfw/xfzj?mark=shequ">幸福之家</a></li>
                        </ul>
                    </li>
                    <li id="detail"><a href="/notify_Web/wyfw/detail?action=show&page=shequ">物业公司</a></li>
                    <li id="jdgl"><a href="/notify_Web/sqfw/jdgl?type=person">监督管理</a></li>
                    <li id="tsjy"><a href="/notify_Web/wyfw/tsjy?action=shequ">用户投诉</a></li>
                    <li id="yhgl"><a href="/notify_Web/sqfw/yhgl">用户管理</a></li>
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


