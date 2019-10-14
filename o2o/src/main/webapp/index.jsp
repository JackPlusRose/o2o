<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 2019/8/20
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script>
        $(function () {
            $("#button1").click(function () {
                var value = $("#id1").val();
                $.get("userController/testSelectAll",
                    {id: value},
                    function (data) {
                        var rows = data.rows;
                        var str="查询结果：";
                        var row;
                        var user='';
                        for(i=0;i<rows.length;i++) {
                            row = rows[i];
                            for(var key in row){
                                user+=key+":"+row[key]+"&nbsp;&nbsp;&nbsp;&nbsp;";
                            }
                            user=user+"<br/>";
                        }
                        str+=user;
                        $("span").html(str);
                    },
                    "json"
                )
            });
        })
    </script>
</head>
<body>
<h1>hello</h1>
<form action="userController/testUserInsert" method="post">
    <label for="name1">姓名:</label>
    <input type="text" placeholder="请输入姓名:" name="name" id="name1"/><br/>
    <label for="password1">密码:</label>
    <input type="password" placeholder="请输入密码:" name="password" id="password1"/><br/>
    <input type="submit" value="注册"/>
</form>
<label for="id1">输入id:</label>
<input type="text" name="id" id="id1" placeholder="请输入查询id"/>
<input type="button" value="查询" id="button1"/><br/>
<span ></span><br>
<form action="userController/testUpload" method="post" enctype="multipart/form-data">
    <label for="upload1">请选择文件:</label>
    <input type="file" name="upload" id="upload1"/><br/>
    <input type="submit" value="上传"/>
</form>
</body>
</html>
