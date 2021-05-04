<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello Spring Boot</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h1>操作MongoDB文档</h1>
</body>
<script>
    $(function () {
        for (var i = 1; i <= 10; i++) {
            var user = {
                'id': i,
                'userName': 'user_name' + i,
                'note': 'note' + i,
                'roles': [{
                    'id': i,
                    'roleName': 'role_name' + i,
                    'note': 'note' + i
                }, {
                    'id': i + 1,
                    'roleName': 'role_name' + (i + 1),
                    'note': 'note' + (i + 1)
                }]
            };
            post(user);
        }

        function post(user) {
            var url = './save';
            $.post({
                url: url,
                contentType: "application/json",
                data: JSON.stringify(user),
                success: function (result) {
                    if (result == null || result.id == null) {
                        alert("插入失败");
                        return;
                    }
                }
            });
        }
    });
</script>
</html>
