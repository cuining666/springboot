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
            // var params = {
            //     userId : 1,
            //     productId : 1,
            //     quantity : 3
            // };
            // $.post("/purchase", params, function (result) {
            //     alert(result.message);
            // });
            // 并发购买
            for (i = 0; i < 500; i++) {
                var binfa_params = {
                    userId : 1,
                    productId : 5,
                    quantity : 1
                };
                // 通过POST请求后端，这里的JavaScript会采用异步请求
                $.post("/purchase", binfa_params, function (result) {});
            }
        });
    </script>
</head>
<body>
    <h1>抢购商品</h1>
</body>
</html>
