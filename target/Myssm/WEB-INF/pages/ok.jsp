<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2021-02-24
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" import="cn.itcast.domain.FileCustom" %>
<%@ page import="java.util.List" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>登入成功</h2>
<h1>${username},恭喜您成功进入百度云盘</h1>
<hr>
<a href="workplaceForApp">测试</a>
<hr>
<form action="/user/logoutForApp">
    <input type="submit" value="退出">
</form>
</body>
</html>
