<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<link rel="icon" type="image/x-icon" href="/resource/images/ico.ico">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<!--移动端版本兼容 -->
<script type="text/javascript">
	var phoneWidth = parseInt(window.screen.width);
	var phoneScale = phoneWidth / 640;
	var ua = navigator.userAgent;
	if (/Android (\d+\.\d+)/.test(ua)) {
		var version = parseFloat(RegExp.$1);
		if (version > 2.3) {
			console.log('Android version = ' + version + '> 2.3');
			document
					.write('<meta name="viewport" content="width=640,initial-scale=1, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
		} else {
			console.log('Android version = ' + version + '<= 2.3');
			document
					.write('<meta name="viewport" content="width=640,initial-scale=1, target-densitydpi=device-dpi">');
		}
	} else {
		console.log('iOS');
		document
				.write('<meta name="viewport" content="width=640,initial-scale=1, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
	};
</script>
<!--移动端版本兼容 end-->

</head>
<body>

	<!-- 关键字 start -->
	<input type="hidden" id="activityKey"
		value="${activityProduct.actUrl }">
	<!-- 关键字 end -->

	<!-- css、js、活动说明、免责声明、背景遮罩层 start-->
	${projectInfo }
	<!-- css、js、活动说明、免责声明、背景遮罩层 end-->
	<input type="hidden" id="isLogin" value="${isLogin }" />
	<input type="hidden" id="referralCode" value="${referralCode }" />
</body>
</html>