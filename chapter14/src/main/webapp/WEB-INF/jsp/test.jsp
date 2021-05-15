<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/5/14
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>购买产品测试</title>
    <script src="../../scripts/jquery.min.js"></script>
    <script>
        $(function () {
            var params = {
                userId : 1,
                productId : 1,
                quantity : 3
            };
            $.post("/purchase", params, function (result) {
                alert(result.message);
            });
        });
    </script>
</head>
<body>
    <h1>抢购商品</h1>
</body>
</html>
