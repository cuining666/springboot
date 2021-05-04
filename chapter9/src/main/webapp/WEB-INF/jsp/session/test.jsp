<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/4/24
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.springboot.chapter9.pojo.User" %>
<html>
<head>
    <title>测试＠SessionAttributes</title>
</head>
<body>
    <%
        // 从session中获取数据
        User user = (User) session.getAttribute("user");
        Long id = (Long) session.getAttribute("id_new");
        // 展示数据
        out.print("<br>user_name:" + user.getUserName());
        out.print("<br>id:" + id);
    %>
</body>
</html>
