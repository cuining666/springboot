<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/5/16
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试关闭请求</title>
    <script src="../../scripts/jquery.min.js"></script>
    <script>
        $(function () {
            $("#submit").click(function () {
                $.post({
                    url : "/manage/shutdown",
                    success : function (result) {
                        if (result != null || result.message != null) {
                            alert(result.message);
                            return;
                        }
                        alert("关闭应用失败");
                    }
                });
            });
        });
    </script>
</head>
<body>
    <input id="submit" type="button" value="关闭应用" />
</body>
</html>
