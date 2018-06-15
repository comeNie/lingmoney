<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://javacrazyer.iteye.com/tags/pager" prefix="w"%>
<%@ include file="/common/top.jsp"%>
<%@ include file="/common/huaxingDialog.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<link rel="stylesheet" href="/resource/css/bank/accountWithdraw.css" />
<script type="text/javascript"
	src="/resource/js/bank/accountWithdraw.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#membersUserActiveli_1").find("dl").show();
		$("#mygift").addClass("hover");
		$("#membersUserActive_1").addClass("hover");
		$("#nav_member03").addClass("nav-hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");
		/*顶部*/
		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		})
		/*顶部*/
	});
</script>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->

	<div id="rz-box-bg"></div>

	<input type="hidden" id="accountOpen" value="${CURRENT_USER.certification }">
	<input type="hidden" id="bindBank" value="${CURRENT_USER.bank }">

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 我的账户菜单开始 -->
			<jsp:include page="/common/myaccount.jsp"></jsp:include>
			<!-- 我的账户菜单结束 -->

			<!-- 自定义弹框 start -->
			<div class="rz-box-con" id="div_customer">
				<div class="rz-title">
					<h2></h2>
					<a href="javascript:void(0)" class="rz-close"> <img
						src="/resource/images/img_rz-close.png" /></a>
				</div>
				<p style="padding-top: 30px; font-size: 16px; text-align: center">

				</p>
				<p style="padding-top: 20px;">
					<a href="javascript:void(0)" class="rz-button "></a> <a
						href="javascript:void(0)" class="rz-button reset"></a>
				</p>
			</div>
			<!-- 自定义弹框 end -->

			<!-- 自定义弹框提示信息 start -->
			<div class="rz-box-con" id="div_customer_tip" style="width: 400px;">
				<div class="rz-title">
					<h2></h2>
					<a href="javascript:void(0)" class="rz-close"> <img
						src="/resource/images/img_rz-close.png" /></a>
				</div>
				<p
					style="padding-top: 30px; font-size: 20px; text-align: center; color: #ea5513">
				</p>
				<p style="padding-top: 20px;">
					<a href="javascript:void(0)" class="rz-button">确定</a>
				</p>
			</div>
			<!-- 自定义弹框提示信息 end -->

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">提现</div>
					<form action="" target="_blank" method="post" id="accountWithdraw">
						<input type="hidden" id="requestData" name="RequestData" /> <input
							type="hidden" id="transCode" name="transCode" />
						<table class="rechargeTab">
							<tr>
								<th class="td_1">可用余额：</th>
								<td class="td_2"><span> <fmt:formatNumber
											pattern="#,##0.00#" value="${balance==null?0:balance}" />
								</span>元</td>
							</tr>
							<tr>
								<th class="td_1">提现金额：</th>
								<td class="td_2"><input type="text" min="0" step="0.01"
									placeholder="请输入提现金额" id="money"><span id="sp"
									style="display: none"></span></td>
							</tr>
							<tr>
								<td></td>
								<td><a href="javascript:void(0)" class="rechargeBtn">提现</a></td>
							</tr>
						</table>
					</form>
					<input type="hidden" id="balance" value="${balance}">
					<div class="TopupWay">
						<div style="width: 100%;">
							<div class="tw_tip_3"><a href="/hxbankOpertion#pc6" target="_blank">图文指引</a></div>
							<div class="tw_tip_1"></div>
							<div class="tw_tip_2">提现操作</div>
						</div>
						<div class="sweetTip">
							<p>温馨提示：</p>
							<p>
								1、首次提现，需先进行华兴银行实名认证。   <a href="/hxbankOpertion#pc5">实名认证图文指引</a><br>
								2、提现全程免费，提现至银行卡，根据提现选择的汇款方式以及金额大小，具体到账时间有所不同。<br> 
								①实时：交易金额小于（含）5万，实时转出（含工作日及节假日）。<br>②加急：交易金额无限制，工作日08:30-16:59内发起提现，实时转出（具体到账时间，视乎收款银行系统处理效率）；非工作日08:30-16:59以外时间，下一个工作日08:30后转出。<br>
								③普通：交易金额小于（含）5万元，2小时以内转出（含工作日及节假日，具体到账时间，视乎收款银行系统处理效率)。<br>
								3、若提现过程中遇到任何问题，请及时拨打热线400-0051-655。
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>