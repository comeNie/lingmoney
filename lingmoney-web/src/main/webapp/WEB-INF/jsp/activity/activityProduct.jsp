
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	
	<!-- 关键字 start -->
	<input type="hidden" id="activityKey"
		value="${actUrl }">
	<input type="hidden" id="referralCode"
		value="${CURRENT_USER.referralCode }">
	<!-- 关键字 end -->
	
	<!-- css、js、活动说明、免责声明、背景遮罩层 start-->
	${projectInfo }
	<!-- css、js、活动说明、免责声明、背景遮罩层 end-->

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>