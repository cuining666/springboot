<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/4/24
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息</title>
</head>
<body>
    <table>
        <tr>
            <td>编号：</td>
            <td>${user.id}</td>
        </tr>
        <tr>
            <td>用户名称：</td>
            <td>${user.userName}</td>
        </tr>
        <tr>
            <td>备注：</td>
            <td>${user.note}</td>
        </tr>
    </table>
</body>
</html>
