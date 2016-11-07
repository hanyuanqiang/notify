<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/16
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    function touchModel(id){

        $('#textarea').attr("action","/notify_Web/djfw/djzs?status=0&type=response&id="+id);
    }
    function submitButton(){
        var content = $("#response").val();
        if(content==''){
            alert("请输入内容");
            return;
        }
        $("form").submit();
    }
</script>
<div style="word-break: normal;word-wrap: break-word;margin-top: 10px;">
    <ul style="list-style: none">
        <c:forEach items="${noResponseDatas }" var="data">
            <li>


                <div class="panel panel-default" style="margin-right: 50px">
                    <div class="panel-heading">
                        <div>
                            <span>『${data.userName}』&nbsp;&nbsp;&nbsp;提问于&nbsp;&nbsp;${data.timestamp}</span>
                            <button class="btn btn-info btn-xs" style="margin-left: 30px;"  data-toggle="modal" data-target="#myModal" onclick="touchModel(${data.id})">
                                回答
                            </button>&nbsp;&nbsp;
                            <a class="btn btn-danger btn-xs" href="/notify_Web/djfw/djzs?status=0&type=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')">
                                删除
                            </a>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div>
                            <p><span style="font-family: 宋体;font-size: 15px">问题：</span>
                                <span style="font-family: Microsoft YaHei;">${data.title}</span>
                            </p>
                            <p><span style="font-family: 宋体;font-size: 15px">问题描述：</span>
                                <span style="font-family: Microsoft YaHei;">${data.content}</span>
                            </p>
                        </div>

                    </div>
                </div>

            </li>
            <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
        </c:forEach>
    </ul>
</div>
<div align="center">
    <nav>
        <ul class="pagination">
            ${pageCode}
            <%--翻页标签--%>
        </ul>
    </nav>
</div>

<%--发布党建阳光信息--%>
<div>

</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content">

            <style>
                img{
                    max-width:92%;
                }
            </style>

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    回答
                </h4>
            </div>
            <form action="" method="post" id="textarea">
                <div class="modal-body">
						<textarea class="form-control" id="textarea_input" rows="17" placeholder="在这里编写回答内容..."
                                  name="response" id="response"></textarea>

                            <%--<script type="text/plain" id="editor" name="response"></script>
                            <script type="text/javascript">
                                //实例化编辑器
                                var ue = UE.getEditor('editor',{
                                    initialFrameWidth : 770,
                                    initialFrameHeight: 300
                                });
                            </script>--%>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="button" value="回答" class="btn btn-primary" onclick="submitButton()">
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>