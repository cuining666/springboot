<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/5/14
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增产品</title>
    <script src="../../scripts/jquery.min.js"></script>
    <script>
        $(function () {
            $("#submit").click(function () {
                var productName = $("#productName").val();
                var stock = $("#stock").val();
                var price = $("#price").val();
                var params = {
                    productName : productName,
                    stock : stock,
                    price : price
                };
                $.post("/add", params, function (result) {
                    alert(result.message);
                })
            });
        });
    </script>
</head>
<body>
    <h1>新增产品</h1>
    <form id="addForm" method="post">
        <table>
            <tr>
                <td>产品名称：</td>
                <td><input id="productName" name="productName" type="text"></td>
            </tr>
            <tr>
                <td>产品库存：</td>
                <td><input id="stock" name="stock" type="text"></td>
            </tr>
            <tr>
                <td>产品价格：</td>
                <td><input id="price" name="price" type="text"></td>
            </tr>
            <tr>
                <td></td>
                <td><input id="submit" type="button" value="提交"></td>
            </tr>
        </table>
    </form>
</body>
</html>
