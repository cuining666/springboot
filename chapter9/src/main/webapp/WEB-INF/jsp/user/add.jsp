<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/4/21
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>新增用户</title>
    <script src="../../../scripts/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#submit").click(function () {
                var userName = $("#userName").val();
                var note = $("#note").val();
                if ($.trim(userName) == '') {
                    alert("用户名不能为空!");
                    return;
                }
                var params = {
                    userName : userName,
                    note : note
                }

                $.post({
                    url: "./insert",
                    // 此处需告知传递参数类型为Json，不能缺少
                    contentType: "application/json",
                    // 将JSON转化成字符串传递
                    data: JSON.stringify(params),
                    // 成功后的方法
                    success: function (result) {
                        if (result == null || result.id == null) {
                            alert("新增用户失败！");
                            return;
                        }
                        alert("新增用户成功！");
                    }

                });
            });
        });
    </script>
</head>
<body>
    <div style="margin: 20px 0;"></div>
    <form id="insertForm">
        <table>
            <tr>
                <td>用户名称:</td>
                <td><input id="userName" name="userName"></td>
            </tr>
            <tr>
                <td>备注:</td>
                <td><input id="note" name="note"></td>
            </tr>
            <tr>
                <td></td>
                <td align="right"><input id="submit" type="button" value="提交" /></td>
            </tr>
        </table>
    </form>
</body>
</html>
