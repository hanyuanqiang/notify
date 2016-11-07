<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/17
  Time: 12:34
  To change this template use File | Settings | File Templates.
 --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<script>
    //使用Ajax异步加载指定id的信息
    function showDetail(id) {
        var xmlHttp;
        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        } else {
            xmlHttp = new ActiveXObject("Microsoft.XMLHttp");
        }
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.status == 200 && xmlHttp.readyState == 4) {
                var result = eval("(" + xmlHttp.responseText + ")");
                $("#title").html(result.title);
                $("#name").html(result.name);
                $("#content").html(result.content);
                $("#type").html(result.type);
                $("#publishDate").html(result.publishDate);
            }
        };
        /* 把用户名和密码通过post方式传到后台 */
        xmlHttp.open("post", "/notify_Web/yzfw/xfzj", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("action=showOne&id=" + id);
    }

    function onClickPublishButton(){
        $('#publish').attr('hidden',false);
        $('#showPage').attr('hidden','hidden');
    }

    function clickReturnButton(){
        $('#publish').attr('hidden','hidden');
        $('#showPage').attr('hidden',false);
    }

    function submitCheck(){
        var editorTitle = $("#editorTitle").val();
        var content = UE.getEditor('editor').getContent();
        if(editorTitle==''){
            alert("请输入标题");
            return false;
        }else if (content==''){
            alert("请输入内容");
            return false;
        }else{
            return true;
        }
    }

</script >


<div hidden="hidden" id="publish" style="border-style: solid;border-width: 2px;border-color: #1b6d85">
    <div style="margin-left: 20px">
        <h3 align="center">发布幸福之家信息</h3>
        <form action="/notify_Web/yzfw/xfzj?action=publish&mark=<%=request.getParameter("mark")%>" method="post" onsubmit="return submitCheck()">
            标题：<input type="text" name="title" id="editorTitle" size="40"><br>
            正文：
            <script type="text/plain" id="editor" name="content"></script>
            <script type="text/javascript">
                //实例化编辑器
                var ue = UE.getEditor('editor', {
                    initialFrameWidth: 770,
                    initialFrameHeight: 350
                });
            </script>
            类型：
            <select class="form-control" name="type" style="width: 200px">
                <option>关爱成长</option>
                <option>健康父母</option>
                <option>和睦夫妻</option>
                <option>感恩家庭</option>
                <option>其他</option>
            </select>
            <input type="text" hidden="hidden" value="${currentUser.name}" name="name">
            <div style="margin-top: 10px;">
                <div align="right" style="margin-right: 100px;"><input type="button" class="btn btn-success" value="返&nbsp;&nbsp;回" onclick="clickReturnButton()">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="submit" class="btn btn-success" value="发&nbsp;&nbsp;布">
                </div>
            </div>
        </form>
    </div>
</div>

<%--<div id="publishButton"><button class="btn btn-primary" onclick="onClickPublishButton()">发布邻里互动信息</button></div>--%>

<div id="showPage">

    <div style="margin-bottom: 10px">
        <form action="/notify_Web/yzfw/xfzj?mark=shequ" method="post" id="s_form">
            <c:if test="${unique=='shequ'}">
                <script>
                    $("#s_form").attr("action","/notify_Web/yzfw/xfzj?mark=shequ");
                </script>
                <%--<form action="/notify_Web/yzfw/llhd?mark=shequ" method="post">--%>
            </c:if>

            <c:if test="${unique=='wuye'}">
                <script>
                    $("#s_form").attr("action","/notify_Web/yzfw/xfzj?mark=wuye");
                </script>
                <%--<form action="/notify_Web/yzfw/llhd?mark=wuye" method="post">--%>
            </c:if>

            <table>
                <tr>
                    <td><label style="margin-bottom: 0px">分类获取：</label></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>
                        <select class="form-control" name="contentType" style="width: 150px">
                            <option value="">所有数据...</option>
                            <option value="关爱成长" ${contentType=="关爱成长"?'selected':'' }>关爱成长</option>
                            <option value="健康父母" ${contentType=="健康父母"?'selected':'' }>健康父母</option>
                            <option value="和睦夫妻" ${contentType=="和睦夫妻"?'selected':'' }>和睦夫妻</option>
                            <option value="感恩家庭" ${contentType=="感恩家庭"?'selected':'' }>感恩家庭</option>
                            <option value="其他" ${contentType=="其他"?'selected':'' }>其他</option>
                        </select>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>
                        <input type="submit" value="分类" class="btn btn-primary">
                    </td>
                </tr>
            </table>
        </form>
    </div>




    <p align="center">历史发布列表<button style="float: right;margin-right: 30px;" class="btn btn-success btn-xs" onclick="onClickPublishButton()">发&nbsp;布</button></p>
    <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
    <div>
        <ul>
            <c:forEach items="${xfzjDatas}" var="data">
                <li><a href="#" onclick="showDetail(${data.id})" data-toggle="modal"
                       data-target="#myModal">『${data.type}』&nbsp;&nbsp;${data.title}</a>
                    <span style="margin-left: 50px;">${data.publishDate}</span>
                    <c:if test="${unique=='shequ'}">
                        <a class="btn btn-danger btn-xs" href="/notify_Web/yzfw/xfzj?action=delete&mark=shequ&id=${data.id}" onclick="return confirm('确定删除这条记录？')" style="margin-left: 50px;">
                            删除
                        </a>
                    </c:if>

                    <c:if test="${unique=='wuye'}">
                        <a class="btn btn-danger btn-xs" href="/notify_Web/yzfw/xfzj?action=delete&mark=wuye&id=${data.id}" onclick="return confirm('确定删除这条记录？')" style="margin-left: 50px;">
                            删除
                        </a>
                    </c:if>

                    <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 20px;"></div>
                </li>
            </c:forEach>
        </ul>
        <div align="center">
            <nav>
                <ul class="pagination">
                    ${pageCode}
                    <%--翻页标签--%>
                </ul>
            </nav>
        </div>
    </div>
</div>



<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 800px">
        <div class="modal-content">
            <style>
                img{
                    max-width:92%;
                }
            </style>

            <div class="over-view">
                <p class="title">
                <h3 style="text-align:center;">
                    <strong id="title">
                    </strong>
                </h3>

                <p style="text-align: center;">
                    <span>发布者：</span>
                    <span id="name"></span>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <span>类型：</span>
                    <span id="type"></span>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <span>发布日期：</span>
                    <span id="publishDate"></span>
                </p>

                <p style="border-top:1px solid #808080;"></p>
                </p>
                <p class="main"><p style="font-size: 120%;text-indent:2em;" id="content"></p>
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>

        </div>
    </div>
</div>