<%--
  Created by IntelliJ IDEA.
  User: ioi
  Date: 2021/4/25
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>RESTful</title>
    <script src="../../scripts/jquery.min.js"></script>
    <script>
        $(function () {
            deleteUser();
        });

        function get() {
            $.get("./user/1", function (user) {
                if (user == null) {
                    alert("结果为空");
                } else {
                    alert("用户信息为" + JSON.stringify(user));
                }
            });
        }
        
        function post() {
            var params = {
                'userName' : 'user_name_new',
                'sexCode' : 1,
                'note' : 'note_new'
            }
            $.post({
                url : "./user",
                contentType : "application/json",
                data : JSON.stringify(params),
                success : function (result) {
                    if (result == null || result.id == null) {
                        alert("插入失败");
                        return;
                    }
                    alert("插入成功");
                }
            });
        }

        function put() {
            var params = {
                'userName' : 'user_name_new1',
                'sexCode' : 2,
                'note' : 'note_new1'
            }

            $.ajax({
                url: "./user/24",
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify(params),
                success: function (result) {
                    if (result == null || result.id == null) {
                        alert("修改失败");
                        return;
                    }
                    alert("修改成功");
                }
            });
        }

        function patch() {
            $.ajax({
                url: "./user/23/user_name_update",
                type: "PATCH",
                success: function (result) {
                    if (result == null) {
                        alert("更新失败");
                        return;
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
        
        function deleteUser() {
            $.ajax({
                url: "./user/22",
                type: "DELETE",
                success: function (result) {
                    if (result == null) {
                        alert("删除失败");
                        return;
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
    </script>
</head>
<body>
    <h1>测试RESTful下的请求</h1>
</body>
</html>
