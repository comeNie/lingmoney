<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
	<title>消息详情</title>
	<meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
	<link rel="stylesheet" href="/resource/css/index.css">
</head>
<body>
   <div class="zonglan clearfix ncenter">
		<h1 style="padding-bottom:15px;text-align:center">${message.topic }</h1>
		<p style="margin-bottom:16px;color:#acacac;text-align:center">
			<span style="margin-right:10px;">发件人：<em style="font-style:normal;color:#248526">系统管理员</em></span>
			<br>
			<span>时间：<fmt:formatDate value="${message.time }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
		</p>
		<p>${message.content }</p>
	</div> 
</body>
</html>