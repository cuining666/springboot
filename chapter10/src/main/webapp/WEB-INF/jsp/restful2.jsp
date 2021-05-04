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
            post();
        });

        function get() {
            $.get("./user2/1", function (user) {
                if (user == null) {
                    alert("结果为空");
                } else {
                    alert("用户信息为" + JSON.stringify(user));
                }
            });
        }
        
        function post() {
            var params = {
                'userName' : 'user_name_new2',
                'sexCode' : 1,
                'note' : 'note_new2'
            };
            // var url = "./user2/entity";
            var url = "./user2/annotation"
            $.post({
                url : url,
                contentType : "application/json",
                data : JSON.stringify(params),
                success : function (result, status, jqXHR) {
                    var success = jqXHR.getResponseHeader("success");
                    var status = jqXHR.status;
                    alert("响应码是：" + success + "；状态码是：" + status);
                    if (result == null || result.id == null) {
                        alert("插入失败");
                        return;
                    }
                    alert("插入成功");
                }
            });
        }
    </script>
</head>
<body>
    <h1>测试RESTful2下的请求</h1>
</body>
</html>
