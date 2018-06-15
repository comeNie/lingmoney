<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<style type="text/css">
.resultsBox{
	padding: 110px 0;		
	width: 580px;
	margin: 0px auto;
}
.resultsBox .resultsCont{
	padding-left: 198px;	
	padding-top: 20px;
}
.resultsBox .resultsCont.bg_1{
	background: url("/resource/images/resultsIcon_1.png") 94px 5px no-repeat;
}
.resultsBox .resultsCont.bg_2{
	background: url("/resource/images/resultsIcon_2.png") 94px 5px no-repeat;
}
.resultsBox .resultsCont.bg_3{
	background: url("/resource/images/resultsIcon_3.png") 94px 5px no-repeat;
}
.resultsBox .resultsCont h3{
	font-size: 28px;
	margin-bottom: 25px;
	font-weight: normal;
}
.resultsBox .resultsCont h3.color_1{
	color: #ea5514;
}
.resultsBox .resultsCont h3.color_2{
	color: #000000;
}
.resultsBox .resultsCont h3.color_3{
	color: #d60409;
}
.resultsBox .resultsCont p{
	font-size: 18px;
	color: #282828;
	margin-bottom: 25px;
}
.resultsBox .resultsCont p span{
	color: #ea5514;
}
.resultsBox .resultsCont p em{
	font-style: normal;
	color: #0483ef;
}
.resultsBtnbox{
	margin: 60px 0;
}
.resultsBtnbox a{
	display: block;
	width: 226px;
	height: 46px;
	border: 2px solid #ff5a00;
	text-align: center;
	line-height: 46px;
	color: #ff5a00;
	font-size: 22px;
	border-radius:3px;
	-moz-border-radius:3px;
	-ms-border-radius:3px;
	-webkit-border-radius:3px;
}
.resultsBtnbox a.checkRecord{
	float: left;
}
.resultsBtnbox a.continue{
	float: right;
}
.resultsBtnbox a.yi{
	float:none;
	margin:0px auto;
}
</style>
<script type="text/javascript">
$(function() {
	$("#accountRechargeTab").addClass("hover");
	$("#nav_member02").addClass("nav-hover");
	
	timeCountInterval = setInterval("timeCount()", 1000);
});

//五秒倒计时
var time = 5;
var timeCountInterval;
function timeCount() {
	time -= 1;
	if (time <= 0) {
		window.clearInterval(timeCountInterval);
		var type = $("#typeId").val();
		window.location.href = $("#autoReturnUrl").val();
		return false;
	} else {
		$(".resultsTip em").text(time + "秒")
	}
}
</script>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->
	<div class="mainbody_member">
		<!-- type 1： 开户结果  2： 激活结果  3:充值结果  4：提现结果 5：投标结果-->
		<input type="hidden" id="typeId" value="${resultInfo.type }">
		<!-- 自动跳转页面 -->
		<input type="hidden" id="autoReturnUrl" value="${resultInfo.autoReturnUrl }">
		<div class="box clearfix">
			<c:choose>
				<c:when test="${resultInfo.status == 1 }"><!-- 1 -->
					<div class="resultsBox" style="padding: 25px 0px;">
						<div class="resultsCont bg_1">
							<h3 class="color_1">${resultInfo.resultMsg }</h3>
							<c:if test="${resultInfo.type == 3 }">
								<p class="resultsAmount ">充值金额：<span>${resultInfo.backup }</span>元</p>
							</c:if>
							<p class="resultsTip"><em>5秒</em>后将自动返回${resultInfo.autoReturnName }</p>
						</div>
						<div class="resultsBtnbox clearfix">
							<c:choose>
								<c:when test="${resultInfo.buttonTwoUrl == null }">
									<a class="checkRecord yi" href="${resultInfo.buttonOneUrl }">${resultInfo.buttonOneName }</a>
								</c:when>		
								<c:otherwise>
									<a class="checkRecord" href="${resultInfo.buttonOneUrl }">${resultInfo.buttonOneName }</a>
									<a class="continue" href="${resultInfo.buttonTwoUrl }">${resultInfo.buttonTwoName }</a>
								</c:otherwise>					
							</c:choose>
						</div>
					</div>
				</c:when>
				
				<c:when test="${resultInfo.status == 2 }"><!-- 2 -->
					<div class="resultsBox">
						<div class="resultsCont bg_3">
							<h3 class="color_3">${resultInfo.resultMsg }</h3>
							<p class="resultsAmount ">失败原因：<span>${resultInfo.failReason }</span>，如有疑问请拨打：<span>400-0051-655</span></p>
							<p class="resultsTip"><em>5秒</em>后将自动返回${resultInfo.autoReturnName }</p>
						</div>
						<div class="resultsBtnbox clearfix">
							<c:choose>
								<c:when test="${resultInfo.buttonTwoUrl == null }">
									<a class="checkRecord yi" href="${resultInfo.buttonOneUrl }">${resultInfo.buttonOneName }</a>
								</c:when>		
								<c:otherwise>
									<a class="checkRecord" href="${resultInfo.buttonOneUrl }">${resultInfo.buttonOneName }</a>
									<a class="continue" href="${resultInfo.buttonTwoUrl }">${resultInfo.buttonTwoName }</a>
								</c:otherwise>					
							</c:choose>
						</div>
					</div>
				</c:when>
				
				<c:otherwise>
					<div class="resultsBox" style="padding: 97px 0px;">
						<div class="resultsCont bg_2">
							<h3 class="color_2">${resultInfo.resultMsg }</h3>
							<p class="resultsTip"><em>5秒</em>后将自动返回${resultInfo.autoReturnName }</p>
						</div>
						<c:if test="${resultInfo.type == 3 || resultInfo.type == 4}">
							<div class="resultsBtnbox clearfix">
								<a class="checkRecord" href="${resultInfo.buttonOneUrl }">${resultInfo.buttonOneName }</a>
								<a class="continue" href="${resultInfo.buttonTwoUrl }">${resultInfo.buttonTwoName }</a>
							</div>
						</c:if>
					</div>
				</c:otherwise>
			</c:choose>
			
		</div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>