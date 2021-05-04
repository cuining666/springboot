<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/4/24
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mvc" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Spring MVC国际化</title>
</head>
<body>
    <!-- 通过HTTP请求参数变化国际化 -->
    <a href="./page?language=zh_CN">简体中文</a>
    <a href="./page?language=en_US">美国英语</a>
    <h2><spring:message code="msg" /></h2>
    <!-- 当前国际化区域 -->
    Locale:${pageContext.response.locale}
</body>
</html>
