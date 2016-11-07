<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/24
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>欢迎</title>
    <link href="${pageContext.request.contextPath}/wel/css/reset.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/wel/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/wel/js/jquery_slide.js"></script>
    <script src="${pageContext.request.contextPath}/wel/js/jquery.min.js"></script>

    <style>


        .wuyebtn {
            -moz-box-shadow:inset 0px 0px 30px 0px #f2fadc;
            -webkit-box-shadow:inset 0px 0px 30px 0px #f2fadc;
            box-shadow:inset 0px 0px 30px 0px #f2fadc;
            background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #81915e), color-stop(1, #25331b));
            background:-moz-linear-gradient(top, #81915e 5%, #25331b 100%);
            background:-webkit-linear-gradient(top, #81915e 5%, #25331b 100%);
            background:-o-linear-gradient(top, #81915e 5%, #25331b 100%);
            background:-ms-linear-gradient(top, #81915e 5%, #25331b 100%);
            background:linear-gradient(to bottom, #81915e 5%, #25331b 100%);
            filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#81915e', endColorstr='#25331b',GradientType=0);
            background-color:#81915e;
            -moz-border-radius:8px;
            -webkit-border-radius:8px;
            border-radius:8px;
            display:inline-block;
            cursor:pointer;
            color:#e4f2d9;
            font-family:Arial;
            font-size:28px;
            font-weight:bold;
            padding:11px 23px;
            text-decoration:none;
            text-shadow:0px 1px 0px #ced9bf;
        }
        .wuyebtn:hover {
            background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #25331b), color-stop(1, #81915e));
            background:-moz-linear-gradient(top, #25331b 5%, #81915e 100%);
            background:-webkit-linear-gradient(top, #25331b 5%, #81915e 100%);
            background:-o-linear-gradient(top, #25331b 5%, #81915e 100%);
            background:-ms-linear-gradient(top, #25331b 5%, #81915e 100%);
            background:linear-gradient(to bottom, #25331b 5%, #81915e 100%);
            filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#25331b', endColorstr='#81915e',GradientType=0);
            background-color:#25331b;
        }
        .wuyebtn:active {
            position:relative;
            top:1px;
        }

        .shequbtn {
            -moz-box-shadow:inset 0px 0px 30px 0px #f2fadc;
            -webkit-box-shadow:inset 0px 0px 30px 0px #f2fadc;
            box-shadow:inset 0px 0px 30px 0px #f2fadc;
            background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #81915e), color-stop(1, #25331b));
            background:-moz-linear-gradient(top, #81915e 5%, #25331b 100%);
            background:-webkit-linear-gradient(top, #81915e 5%, #25331b 100%);
            background:-o-linear-gradient(top, #81915e 5%, #25331b 100%);
            background:-ms-linear-gradient(top, #81915e 5%, #25331b 100%);
            background:linear-gradient(to bottom, #81915e 5%, #25331b 100%);
            filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#81915e', endColorstr='#25331b',GradientType=0);
            background-color:#81915e;
            -moz-border-radius:8px;
            -webkit-border-radius:8px;
            border-radius:8px;
            display:inline-block;
            cursor:pointer;
            color:#e4f2d9;
            font-family:Arial;
            font-size:28px;
            font-weight:bold;
            padding:11px 23px;
            text-decoration:none;
            text-shadow:0px 1px 0px #ced9bf;
        }
        .shequbtn:hover {
            background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #25331b), color-stop(1, #81915e));
            background:-moz-linear-gradient(top, #25331b 5%, #81915e 100%);
            background:-webkit-linear-gradient(top, #25331b 5%, #81915e 100%);
            background:-o-linear-gradient(top, #25331b 5%, #81915e 100%);
            background:-ms-linear-gradient(top, #25331b 5%, #81915e 100%);
            background:linear-gradient(to bottom, #25331b 5%, #81915e 100%);
            filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#25331b', endColorstr='#81915e',GradientType=0);
            background-color:#25331b;
        }
        .shequbtn:active {
            position:relative;
            top:1px;
        }

    </style>

</head>

<body style="background-color: rgb(108, 19, 27);background-image: url('${pageContext.request.contextPath}/common/欢迎.jpg')">

<div style="height:40px;"></div>


<div class="container_image">
    <a href="javascript:void(0)" tip="0" class="i_btn prev_L"></a>
    <a href="javascript:void(0)" tip="1" class="i_btn next_R"></a>
    <ul class="slide_img">
        <li class="on">
            <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/滚动图/图1.jpg" /></a>
        </li>
        <li>
            <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/滚动图/图2.jpg" /></a>
        </li>
        <li>
            <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/滚动图/图3.jpg" /></a>
        </li>
        <li>
            <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/滚动图/图4.jpg" /></a>
        </li>
        <li>
            <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/滚动图/图5.jpg" /></a>
        </li>
        <li>
            <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/滚动图/图6.jpg" /></a>
        </li>
    </ul>
</div>

<script type="text/javascript">
    $(function(){

        var newopt={
            /*w2:"180",//小图宽度
             h2:"490"//小图高度 */
        };

        i_slide($(".container_image"),newopt);

    });
</script>

<div style="text-align:center;font:normal 14px/24px 'MicroSoft YaHei';">

    <a href="/notify_Web/shequLogin.jsp" class="shequbtn">社区登录</a>
    <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
    <a href="/notify_Web/wuyeLogin.jsp" class="wuyebtn" style="margin-left: 160px;">物业登录</a>

</div>
</body>
</html>
