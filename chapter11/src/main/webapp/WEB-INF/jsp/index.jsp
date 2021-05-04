<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/5/4
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
    <h1>登录成功</h1>
    <a href="/admin">检测ROLE_ADMIN角色</a>
    <a href="/user">检测ROLE_USER角色</a>
    <form action="/logout" method="post">
        <input type="submit" value="退出登录" />
        <input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
</body>
</html>