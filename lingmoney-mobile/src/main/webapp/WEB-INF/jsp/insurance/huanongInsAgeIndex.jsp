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
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
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
	<input id="pId" type="hidden" value="">
	<div id="personal_agreement">
		<jsp:include page="huanongInsAgePersonal.jsp"></jsp:include>
	</div>
	<div id="company_agreement">
		<jsp:include page="huanongInsAgeCompany.jsp"></jsp:include>
	</div>
</body>
<script type="text/javascript">
getPrdModel();
	function GetQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		console.log(r[2]);
		if (r != null) {
			return unescape(r[2]);
		}
		return null;
	}

	function getPrdModel() {
		var pCode = "";
		$.ajax({
			url : '/insurance/queryProBorType',
			type : 'get',
			data : {
				pId : GetQueryString("pId")
			},
			dataType : 'json',
			success : function(data) {
				console.log(data);
				if (data.code == 200) {
					console.log(data.obj);
					if (data.obj == 1010) {
						showPersionalAgreement();
					} else {
						showCompanyAgreement();
					}
				} else {
					showCompanyAgreement();
				}
			},
			error : function(d) {
				showCompanyAgreement();
			}
		});
	}

	function showPersionalAgreement() {
		console.log("显示个人协议");
		$("#personal_agreement").show();
		$("#company_agreement").hide();
	}

	function showCompanyAgreement() {
		console.log("显示公司协议");
		$("#personal_agreement").hide();
		$("#company_agreement").show();
	}
</script>
</html>
