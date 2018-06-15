<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>callback</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<title>Insert title here</title>
<style type="text/css">
  .btn{
    display: inline-block;
	margin-left: 20px;
	width: 150px;
	height: 40px;
	line-height: 40px;
	text-align: center;
	color: #362e2b;
	background: #eee;
	font-size: 16px;
	border: 1px solid #d2d3d3;
	border-radius: 6px;
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
	-o-border-radius: 6px;
	-ms-border-radius: 6px;
	width: 150px;
	cursor:pointer;
  }
</style>
</head>
<body style="background:#f7f7f7">
	<c:if test="${status==1}">
		<div
			style="padding: 150px 0 16px 0; border-bottom: 0px solid #e5e5e5; text-align: center">
			<img src="/resource/images/img_returnSuccess.gif">
			<hr/>
			<input type="button" name="close" value="关闭" onclick="window.close();" class="btn"/>
		</div>
	</c:if>

	<c:if test="${status==0}">
		<div
			style="padding: 150px 0 16px 0; border-bottom: 0px solid #e5e5e5; text-align: center">
			<img src="/resource/images/img_returnFail.gif">
			<hr style="margin:0 auto 20px auto;width:50%;"/>
			<input type="button" name="close" value="关闭" onclick="window.close();" class="btn"/>
		</div>
	</c:if>
	
</body>
</html>