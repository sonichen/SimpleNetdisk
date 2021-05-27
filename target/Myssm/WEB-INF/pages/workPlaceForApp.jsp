<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" import="cn.itcast.domain.FileCustom" %>
<%@ page import="java.util.List" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<h2 >安卓接口，用户：${username}</h2>
<div>
    <form action="/user/logoutForApp" method="post">
        <input type="submit" value="点击退出">
    </form>
</div>
<h4>上传文件</h4>
<form action="/file/uploadForAppV2" method="post" enctype="multipart/form-data">
    选择文件:<input type="file" name="file" width="120px">
    <input type="hidden" value=${username} name="username">
    <input type="text" name="choosePath" value="path">
    <input type="text" name="url" value="url">
    <input type="submit" value="上传">
</form>
<hr/>
<h4>网页下载文件</h4>
<form action="/file/download" method="get">
    <input type="text" name="fileName" value="文件名">
    <input type="hidden" value=${username} name="username">
    <input type="text" name="choosePath" value="路径">
    <input type="submit" value="下载">
</form>
<h3>安卓下载文件</h3>
<form action="/file/downloadOnAppV2" method="get">
    <input type="hidden" value=${username} name="username">
    <input type="text" name="downPath" value="文件路径">
    <input type="text" name="fileName" value="文件名 ">
    <input type="submit" value="下载">
</form>
<h4>分页查看分类文件夹内容</h4>
<form action="/file/listFilesByPages" method="post">
    <input type="text" name="start" value="起始页">
    <input type="hidden" value=${username} name="userName">
    <input type="text" name="end" value="尾页">
    <input type="text" name="choosePath" value="路径">
    <input type="submit" value="查询">
</form>
<h4>查询文件夹下文件数量</h4>
<form action="/file/getTotal" method="post">
    <input type="hidden" value=${username} name="userName">
    <input type="text" name="choosePath" value="路径">
    <input type="submit" value="查询">
</form>

<h4>查看该分类文件夹下所有内容</h4>
<form action="/file/getAllFilesInDir" method="post">
    <input type="hidden" value=${username} name="username">
    <input type="text" value="路径" name="path">
    <input type="submit" value="查询">
</form>

<h4>删除文件或文件夹</h4>
<form action="/file/deleteFiles" method="post">
    <input type="hidden" value=${username} name="username">
    <input type="text" name="choosePath" value="路径">
    <input type="text" name="fileName">
    <input type="submit" value="删除">
</form>
<h4>新建文件</h4>
<form action="/file/addDirectory" method="post">
    <input type="text" name="currentPath" value="路径">
    <input type="hidden" value=${username} name="userName">
    <input type="text" name="directoryName" value="文件夹名称">
    <input type="submit" value="新建文件夹">
</form>


</body>
</html>
