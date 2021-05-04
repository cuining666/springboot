<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/4/24
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
    <form method="post" action="./part" enctype="multipart/form-data">
        <input type="file" name="file" value="请选择上传的文件" />
        <input type="submit" value="提交">
    </form>
</body>
</html>
