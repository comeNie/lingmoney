<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="/resource/js/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="/resource/js/jquery-2.1.1.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/resource/js/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="/resource/js/ajaxExt.js"></script>
<style type="text/css">
	.input-group{
		margin-bottom: 5px;
	}
</style>
</head>
<body style="margin: 10px;">
	<h3>华兴银行接口测试-手机端</h3>
	<div class="panel-group" id="accordion">
		<!-- 账户开立 -->
		<jsp:include page="mobile/accountOpen.jsp"></jsp:include>
		<!-- 绑卡 -->
		<jsp:include page="mobile/binkCard.jsp"></jsp:include>
		<!-- 充值 -->
		<jsp:include page="mobile/accountRecharge.jsp"></jsp:include>
		<!-- 单笔投标 -->
		<jsp:include page="mobile/singleBidding.jsp"></jsp:include>
		<!-- 提现 -->
		<jsp:include page="mobile/cashWithdraw.jsp"></jsp:include>
		<!-- 自动投标授权 -->
		<jsp:include page="mobile/autoTenderAuthorize.jsp"></jsp:include>
		<!-- 债权转让 -->
		<jsp:include page="mobile/transferDebt.jsp"></jsp:include>
	</div>
</body>
</html>
