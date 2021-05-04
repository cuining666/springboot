<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/4/24
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Session</title>
</head>
<body>
    <%
        session.setAttribute("id", 1L);
        response.sendRedirect("./test");
    %>
</body>
</html>
