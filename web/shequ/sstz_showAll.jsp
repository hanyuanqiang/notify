<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/16
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>

    <div style="margin-bottom: 10px">
        <form action="/notify_Web/sqfw/sstz?action=showAll" method="post">
            <table>
                <tr>
                    <td><label style="margin-bottom: 0px">分类获取：</label></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>
                        <select class="form-control" name="contentType" style="width: 150px">
                            <option value="">所有数据...</option>
                            <option value="小区通知" ${contentType=="小区通知"?'selected':'' }>小区通知</option>
                            <option value="民政" ${contentType=="民政"?'selected':'' }>民政</option>
                            <option value="计生" ${contentType=="计生"?'selected':'' }>计生</option>
                            <option value="社会组织" ${contentType=="社会组织"?'selected':'' }>社会组织</option>
                            <option value="热点关注" ${contentType=="热点关注"?'selected':'' }>热点关注</option>
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


    <p align="center" style="font-size: 20px">历史发布列表</p>
    <ul>
        <c:forEach items="${datas}" var="data">
            <li style="margin-bottom: 5px"><a href="/notify_Web/sqfw/sstz?action=showOne&id=${data.id}">『${data.type}』&nbsp;&nbsp;${data.title}</a>
                <span style="margin-left: 50px;font-size: 15px">${data.publishDate}</span>
                <a class="btn btn-danger btn-xs" href="/notify_Web/sqfw/sstz?action=delete&id=${data.id}" onclick="return confirm('确定删除这条记录？')" style="margin-left: 50px;">
                    删除
                </a>
                <div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;margin: 5px 0px;"></div>
            </li>
        </c:forEach>
    </ul>
    <br>
</div>

<div align="center">
    <nav>
        <ul class="pagination">
            ${pageCode}
            <%--翻页标签--%>
        </ul>
    </nav>
</div>
