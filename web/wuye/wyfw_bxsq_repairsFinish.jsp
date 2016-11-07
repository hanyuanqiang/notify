<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/19
  Time: 11:01
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
                //把标题写入指定节点元素内
                $("#title").html(result.title);
                $("#userName").html(result.userName);
                //下面语句判断用户是否上传了图片
                if(result.picUrl_beforeRepairs!=null){
                    $("#content").html("<img src='"+result.picUrl_beforeRepairs+"'><br>"+result.content);
                }else{
                    $("#content").html(result.content);
                }
                //写入预约时间
                $("#repairsDate").html(result.repairsDate);
                //下面判断语句用于判断用户是否已经评论
                if(result.comment==null&&result.picUrl_afterRepairs==null){
                    $("#comment").html("用户还没有评论");
                }else if(result.picUrl_afterRepairs!=null){
                    $("#comment").html("<img src='"+result.picUrl_afterRepairs+"'><br>"+result.comment);
                }else{
                    $("#comment").html(result.comment);
                }
                //
                var star = result.star;

                var starHtmlCode = "";
                if(star==0){
                    starHtmlCode+="暂无";
                }else {
                    while(star>0){
                        starHtmlCode+="<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='20px' height='20px'>"
                        star--;
                    }
                }
                $("#star").html(starHtmlCode);
            }
        };
        xmlHttp.open("post", "/notify_Web/wyfw/bxsq", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("handle=repairsFinish&action=showDetail&id=" + id);
    }


    function onClickPublishButton(){
        $('#publish').attr('hidden',false);
        $('#publishButton').attr('hidden','hidden');
    }

</script >

<div style="margin-top: 15px;">
    <ul>
        <c:forEach items="${repairsFinishDatas}" var="data">
            <li>
                <a href="#" onclick="showDetail(${data.id})" data-toggle="modal"
                   data-target="#showDetail">
                    <span style="">『${data.userName}』&nbsp;</span>
                        ${data.title}
                </a>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
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


<!-- 模态框（Modal） -->
<div class="modal fade" id="showDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 800px">
        <div class="modal-content">

            <style>
                img{
                    max-width:92%;
                }
            </style>

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h7 class="modal-title" id="myModalLabel">详情</h7>
            </div>
            <div class="modal-body" style="word-break: normal;word-wrap: break-word;margin-top: 10px;">
                <div><span>用户名:</span>
                    <label id="userName"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>标题:</span>
                    <label id="title"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>详情:</span>
                    <label id="content"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>维修时间:</span>
                    <label id="repairsDate"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>用户评论:</span>
                    <label id="comment"></label>
                </div>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
                <div><span>满意度:</span>
                    <label id="star"></label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>