<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/19
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    img{
        max-width:95%;
    }
</style>
<div style="margin-bottom: 50px;">
    <%--<h3 align="center" style="">公司介绍</h3>--%>
    <div style="margin: 10px 0px;">
       <div style="margin: 10px 0px">
           <label style="padding: 5px 10px;color:white;font-size: 18px;background-color: #4184F3;border-radius: 14px;margin-left: 20px;margin-bottom: 0px;">公司介绍</label>
           <div style="border-top:1px solid #4184F3;height: 1px;overflow:hidden;margin: 0px 20px;"></div>
           <div style="margin: 0px 20px;border: 1px solid #4184F3;">
               <div style="margin: 20px;" id="tagDiv">
                   <img src="${data.picUrl}" id="img"><br>
                   <p>${data.message}</p>
               </div>
           </div>
       </div>



        <div style="margin: 30px 0px">
            <label style="padding: 5px 10px;color:white;font-size: 18px;background-color: #4184F3;border-radius: 14px;margin-left: 20px;margin-bottom: 0px;">人员配置</label>
            <div style="border-top:1px solid #4184F3;height: 1px;overflow:hidden;margin: 0px 20px;"></div>
            <div style="margin: 0px 20px;border: 1px solid #4184F3;">
                <div style="margin: 20px;">
                    <p>${data.person}</p>
                </div>
            </div>
        </div>



        <div style="margin: 30px 0px">
            <label style="padding: 5px 10px;color:white;font-size: 18px;background-color: #4184F3;border-radius: 14px;margin-left: 20px;margin-bottom: 0px;">硬件设备</label>
            <div style="border-top:1px solid #4184F3;height: 1px;overflow:hidden;margin: 0px 20px;"></div>
            <div style="margin: 0px 20px;border: 1px solid #4184F3;">
                <div style="margin: 20px;">
                    <p>${data.hardset}</p>
                </div>
            </div>
        </div>



        <div style="margin: 30px 0px">
            <label style="padding: 5px 10px;color:white;font-size: 18px;background-color: #4184F3;border-radius: 14px;margin-left: 20px;margin-bottom: 0px;">其他信息</label>
            <div style="border-top:1px solid #4184F3;height: 1px;overflow:hidden;margin: 0px 20px;"></div>
            <div style="margin: 0px 20px;border: 1px solid #4184F3;">
                <div style="margin: 20px;">
                    <p>${data.others}</p>
                </div>
            </div>
        </div>
    </div>

    <c:choose>
        <c:when test="${page==null||page==''}">
            <div style="margin-bottom: 30px;margin-top: 10px;margin-right: 20px;">
                <a href="/notify_Web/wyfw/detail?action=updatePage" class="btn btn-info  btn-xs" style="float: right">修改信息</a>
            </div>
        </c:when>
        <c:otherwise>
            <h3 align="center" style="margin-top: 30px">用户评价</h3>
            <hr style="margin: 10px 20px;border-color: black">
            <div style="margin: 20px;">
                <% int i=1;
                    pageContext.setAttribute("tag","starId"+i);
                %>
                <c:forEach items="${comments}" var="comment">
                    <div style="padding-left: 10px;margin-top: 20px;">
                        <label>${comment.userName}</label><span style="margin-left: 30px;">${comment.repairsDate}</span>
                        <p style="border-top:1px solid #808080;width: 200px;"></p>
                        <span>评论内容：</span><label>${comment.comment}</label><br>
                        <span>满意度：</span><span id=${tag}></span>
                        <script>
                            var star = ${comment.star};
                            var starHtmlCode = "";
                            for(i=0;i<star;i++){
                                starHtmlCode+="<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1468922812110&di=ab17e24e808e27700eb4766669bebf3c&imgtype=jpg&src=http%3A%2F%2Fimages.gofreedownload.net%2Fthumps_middle%2F3d-yellow-star-26725.jpg' width='20px' height='20px'>"
                            }
                            $("#${tag}").html(starHtmlCode);
                        </script>
                        <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden"></div>
                    </div>
                    <%
                        pageContext.setAttribute("tag","starId"+(++i));
                    %>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

</div>
<style>
    img{
        max-width:98%;
    }
</style>