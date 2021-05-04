<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/4/25
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取请求头参数</title>
    <script src="../../scripts/jquery.min.js"></script>
    <script>
        $.post({
            url : "./user",
            // 设置请求头参数
            headers : {id : '1'},
            success : function (user) {
                if (user == null || user.id == null) {
                    alert("获取失败");
                    return;
                }
                alert("id=" + user.id + "user_name=" + user.userName + "note=" + user.note);
            }
        });
    </script>
</head>
<body>

</body>
</html>
