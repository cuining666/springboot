<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/5/22
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>创建订单</title>
</head>
<body>
    <form action="/pay/buy" method="post">
        <table>
            <tr>
                <td>商品ID：</td>
                <td><input id="itemId" name="itemId" type="text"></td>
            </tr>
            <tr>
                <td>购买件数：</td>
                <td><input id="quantity" name="quantity" type="text"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="提交订单"></td>
            </tr>
        </table>
    </form>
</body>
</html>
