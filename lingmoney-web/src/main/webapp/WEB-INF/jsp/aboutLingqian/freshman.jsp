<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div
		style="width: 100%; min-width:1024px;overflow: hidden; height: 446px; border-top: 1px solid #ddd; background: url('/resource/images/banner_freshman.jpg') no-repeat center top #d46bca"></div>
	<div class="mainbody mainbody_freshman">
		<div class="freshman_div1"></div>
		<div class="freshman_div2"></div>
		<div class="freshman_div3">
			<div class="box">
				<a href="safe.html">>更多详情</a>
			</div>
		</div>
		<div class="freshman_div4">
		
		</div>
		 <!-- <div class="freshman_div5">
			<div class="box">
				<div class="link">
					<a href="#" class="link1"></a><a href="#" class="link2"></a>
				</div>
				
			</div>
		</div> -->
		<div style="padding:50px 0">
			<a href="regist.html" class="a-get">立即注册</a>
		</div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>