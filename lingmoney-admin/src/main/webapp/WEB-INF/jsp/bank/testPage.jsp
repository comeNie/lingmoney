<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/formatter.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/bank/hxDailyRec.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<style type="text/css">
.address {
	Font-size: 16px;
	Font-family: 微软雅黑;
}
</style>
</head>
<body>
	<div style="border:1px solid black;">
		测试 单笔奖励分红
		<form method="post" action="/rest/bank/requestSingleRewardOrCutAMelon">
			华兴账户id<input name="hxAccountId">
			还款金额<input name="amount">
			备注<input name="remark">
			<input type="submit">
		</form>
	</div>
	
	<div style="border:2px solid red;">
		测试自动还款
		<form method="post" action="/rest/bank/requestAutoSingleRepayment">
			标的id<input name="biddingId">
			手续费<input name="feeAmt">
			还款金额<input name="amount">
			备注<input name="remark">
			<input type="submit">
		</form>
	</div>
	
	<div style="border:2px solid lightblue;">
		查询账户余额
		<form method="post" action="/rest/bank/queryUserBalance">
			E账号<input name="acNo">
			姓名<input name="acName">
			<input type="submit">
		</form>
	</div>
	
	<div style="border:2px solid lightgreen;">
		查询账户状态
		<form method="get" action="/rest/bank/queryUserAccount">
			E账号<input name="acNo">
			类型<select name="type">
				<option value="1">账户状态 </option>			
				<option value="2">联网核查结果 </option>			
				<option value="3">实名认证结果 </option>			
			</select>
			<input type="submit">
		</form>
	</div>
	
	<div style="border:2px solid yellow; margin-top:10px;">
		企业开户
		<form method="get" id="requestForm">
			企业名<input id="companyName" name="companyName">
			企业号<input id="occCodeNo" name="occCodeNo">
			<a href="javascript:void(0);" onclick="submitForm()">提交</a>
		</form>
		
		<form action="http://183.63.131.106:40013/extService/ghbExtService.do" method="post" target="_blank">
			<input name="RequestData" id="requestData">
			<input name="transCode" id="transCode">
			<input type="submit">
		</form>
	</div>
	
	<div style="border:2px solid yellow; margin-top:10px;">
		查询企业开户结果
		<form action="/rest/bank/enterpriseAccount/queryAccountOpenResult" method="get">
			流水号<input name="oldReqSeqNo">
			<input type="submit">
		</form>
	</div>
	
	<div style="border:2px solid green; margin-top:10px;">
		债权转让
		<form id="transferDebtForm" action="/rest/bank/transferDebt" method="get">
			金额<input name="amount">
			备注<input name="remark">
			产品CODE<input name="pCode">
			转入人UID<input name="uId">
			<a href="javascript:void(0);" onclick="transferDebtFormSubmit()">提交</a>
		</form>
		
		<form action="http://183.63.131.106:40013/extService/ghbExtService.do" method="post" target="_blank">
			<input name="RequestData" id="transferDebtReqData">
			<input name="transCode" id="transferDebtReqCode">
			<input type="submit">
		</form>
	</div>
	<div style="border:2px solid green; margin-top:10px;">
		债权转让结果查询
		<form action="/rest/bank/transferDebtAssignmentSearch" method="get">
			原请求流水号<input name="oldreqseqno">
			<input type="submit">
		</form>
	</div>
	
	
	<div style="border:2px solid yellow; margin-top:10px;">
		企业代理人信息变更
		<form method="get" id="requestForm">
			银行存管账号<input id="acNo" name="acNo">
			<a href="javascript:void(0);" onclick="agentInfoUpdate()">提交</a>
		</form>
		
		<form action="http://183.63.131.106:40013/extService/ghbExtService.do" method="post" target="_blank">
			<input name="RequestData" id="requestAgentData">
			<input name="transCode" id="AgenttransCode">
			<input type="submit">
		</form>
	</div>
	
	<div style="border:2px solid yellow; margin-top:10px;">
		企业代理人信息变更结果查询
		<form action="/rest/bank/enterpriseAccount/queryAccountOpenResult" method="get">
			流水号<input name="oldReqSeqNo">
			<input type="submit">
		</form>
	</div>
	
	<div style="border:2px solid yellow; margin-top:10px;">
		企业解绑银行卡
		<form method="get" id="requestForm">
			银行存管账号<input id="unBindCardacNo" name="acNo">
			<a href="javascript:void(0);" onclick="enterpriseUnBindCard()">提交</a>
		</form>
		
		<form action="http://183.63.131.106:40013/extService/ghbExtService.do" method="post" target="_blank">
			<input name="RequestData" id="enterUnBindCardData">
			<input name="transCode" id="enterUnBindCardCode">
			<input type="submit">
		</form>
	</div>
	
	<div style="border:2px solid yellow; margin-top:10px;">
		查询企业开户结果
		<form action="/rest/bank/enterpriseBindCard/queryInfo" method="get">
			银行存管账号<input name="acNo">
			当前页<input name="currentPage">
			每页条数<input name="pageNumber">
			<input type="submit">
		</form>
	</div>
	
	<script type="text/javascript">
		function submitForm() {
			$.ajax({
				url:'/rest/bank/enterpriseAccount/requestAccountOpen',
				data:{companyName : $('#companyName').val(), occCodeNo : $('#occCodeNo').val()},
				success: function(data) {
					$("#channelFlow").val(data.channelFlow);
					$("#requestData").val(data.requestData);
					$("#transCode").val(data.transCode);
				}
			});
		}
		
		function agentInfoUpdate() {
			$.ajax({
				url:'/rest/bank/hxEnterpriseAgent/updateInfo',
				data:{acNo : $('#acNo').val()},
				success: function(data) {
					$("#requestAgentData").val(data.requestData);
					$("#AgenttransCode").val(data.transCode);
				}
			});
		}
		
		function enterpriseUnBindCard() {
			$.ajax({
				url:'/rest/bank/enterpriseBindCard/unBindCard',
				data:{acNo : $('#unBindCardacNo').val()},
				success: function(data) {
					$("#enterUnBindCardData").val(data.requestData);
					$("#enterUnBindCardCode").val(data.transCode);
				}
			});
		}
		
		function transferDebtFormSubmit() {
			$.ajax({
				url:'/rest/bank/transferDebt',
				data:$('#transferDebtForm').serialize(),
				success: function(data) {
					$("#transferDebtReqData").val(data.obj.requestData);
					$("#transferDebtReqCode").val(data.obj.transCode);
				}
			});
		}
	</script>
</body>
</html>
