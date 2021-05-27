<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2021-02-21
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html>
<head>
    <title>west2</title>
</head>
<body>
<h3>模拟网盘</h3>
<h2>网页登入</h2>
<form action="user/login" method="post">
    姓名：<input type="text" name="username" /><br/>
    密码：<input type="text" name="password" /><br/>
    <input type="submit" value="登入"/><br/>
</form>
<a href="user/gotoRegist">还未登入，注册</a>
<hr>
<h2>安卓登入</h2>
<form action="user/loginForApp" method="post">
    姓名：<input type="text" name="username" /><br/>
    密码：<input type="text" name="password" /><br/>
    <input type="submit" value="登入"/><br/>
</form>
<a href="user/gotoRegist">还未登入，注册</a>
<p>支持上传的文件类型：<br>
    图片：以jpg，png，gif，tif，bmp结尾的图片<br>
    文档：以html，htm，css，js，ini，txt，jsp，sql，xml，java，bat，mxp，properties，doc，docx，<br>
    vsd，mdb，pdf，xlsx，xls，pptx，ppt，wps结尾的文档<br>
    压缩包：以zip，rar，gz结尾的文件<br>
    文件：以class，jar，exe，mf，chm，torrent，wpd，dbx，pst，qdf，pwl，ram结尾的文件</p>
</body>
</html>
