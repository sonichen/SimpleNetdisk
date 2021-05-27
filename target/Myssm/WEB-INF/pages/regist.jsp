<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2021-02-24
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>提示：<br>用户注册要求：
    用户名：必须是6-10位字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）不能以数字开头<br>
    密码：必须是6-20位的字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）</p>
<h2>网页注册</h2>
<form action="regist" method="post">
    姓名：<input type="text" name="username" /><br/>
    密码：<input type="text" name="password" /><br/>
    <input type="submit" value="注册"/><br/>
</form>
<hr><hr>
<h2>安卓注册</h2>
<form action="registForApp" method="post">
    姓名：<input type="text" name="username" /><br/>
    密码：<input type="text" name="password" /><br/>
    <input type="submit" value="注册"/><br/>
</form>
</body>
</html>
