<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/4/22
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试JSR-303</title>
    <script src="../../../scripts/jquery.min.js"></script>
    <script>
        $(function () {
            // 请求验证的POJO
            var pojo = {
                id : null,
                date : '2017-08-08',
                doubleValue : 999999.09,
                intValue : 100,
                range: 1000,
                email : 'email',
                size : 'adv1212'
            }
            $.post({
                url : "./validate",
                contentType : "application/json",
                data : JSON.stringify(pojo),
                success : function (result) {
                }
            })
        });
    </script>
</head>
<body>

</body>
</html>
