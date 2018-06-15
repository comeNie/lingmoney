<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>零钱宝全部赎回</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript">
	function walletRedeem(){
		$.ajax({
			url : "/rest/walletRedeem/walletRedeem/walletRedeem",
			dataType : "json text",
			type : "post",
			success : function(data) {
				window.location.reload();
			}
		});
	}
</script>
</head>
<body>
<button onclick="walletRedeem()">全部赎回</button>
</body>
</html>