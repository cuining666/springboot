<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/5/4
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <h1>登录</h1>
    <form action="/login" method="post">
        <div>
            用户名：<input id="username" name="username" type="text" />
        </div>
        <div>
            密码：<input id="password" name="password" type="password" />
        </div>
        <div>记住我：<input id="remember_me" name="remember-me" type="checkbox" /></div>
        <div>
            <input type="submit" value="立即登录" />
        </div>
        <input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
</body>
</html>
