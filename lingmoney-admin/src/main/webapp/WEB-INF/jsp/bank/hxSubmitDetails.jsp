<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>华兴银行提交收益明细</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/formatter.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/bank/hxSubmitDetail.js"></script>
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
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>借款编号：</td>
						<td><input class="easyui-textbox" name="loanNo" id="loanNo"
							style="width: 200px"
							data-options="prompt:'请输入借款编号',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#loanNo').textbox('setValue', '');
											}
										}]" /></td>
						<td>还款流水号：</td>
						<td><input class="easyui-textbox" name="channelFlow" id="channelFlow"
							style="width: 200px"
							data-options="prompt:'请输入还款流水号',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#channelFlow').textbox('setValue', '');
											}
										}]" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="还款信息列表" fit='true' id="paymentGrid"
				data-options="rownumbers:false,toolbar:toolbar,singleSelect:true,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/bank/submitPaymentGains/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100,hidden:false">id</th>
						<th data-options="field:'channelFlow',width:100">还款流水号</th>
						<th data-options="field:'oldChannelFlow',width:100">原垫付请求流水号</th>
						<th data-options="field:'paymentType',width:100,formatter:formatPaymentType">还款类别</th>
						<th data-options="field:'status',width:100,formatter:formatPaymentStatus">还款状态</th>
						<th data-options="field:'paymentDate',width:100,formatter:formatter.DateTimeFormatter">操作时间</th>
						<th data-options="field:'feeAmt',width:100">平台手续费</th>
						<th data-options="field:'bankFlow',width:100">银行交易流水</th>
						<th data-options="field:'amount',width:100">还款金额</th>
						<th data-options="field:'bwAmt',width:100">借款金额</th>
						<th data-options="field:'mortgageId',width:100">借款人抵押品编号</th>
						<th data-options="field:'bwAcname',width:100">借款人姓名</th>
						<th data-options="field:'bwAcno',width:100">借款人账户</th>
						<th data-options="field:'bwAcbankname',width:100">接口账户银行</th>
						<th data-options="field:'loanNo',width:100">借款编号</th>
						<th data-options="field:'investObjName',width:100">标的名称</th>
						<th data-options="field:'submitDetailStatus',width:100,formatter:formatSubimitType">收益明细提交状态</th>
						<th data-options="field:'submitChannelFlow',width:100">收益明细提交流水号</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<!-- 提交收益明细结果查询对话框  -->
	<div id="queryDialog" class="easyui-dialog"
		style="width: 700px; height: 500px; padding: 2px 5px" closed="true"
		resizable="true" modal="true" data-options="title:'提交收益明细结果查询'">
		<table class="view">
			<tr>
				<th>提交收益明细交易流水号：</th>
				<td name="OLDREQSEQNO"></td>
				<th>交易状态：</th>
				<td name="RETURN_STATUS"></td>
			</tr>
			<tr>
				<th>失败原因：</th>
				<td name="ERRORMSG"></td>
				<th>借款编号：</th>
				<td name="LOANNO"></td>
			</tr>
			<tr>
				<th>还款账号户名：</th>
				<td name="BWACNAME"></td>
				<th>还款账号：</th>
				<td name="BWACNO"></td>
			</tr>
			<tr>
				<th>总笔数：</th>
				<td name="TOTALNUM"></td>
				<th>还款类型：</th>
				<td name="DFFLAG"></td>
			</tr>
			<tr>
				<th colspan="4">需发收益的投资人列表</th>
			</tr>
			<tr>
				<!-- 此处为动态展示投资人列表 -->
			</tr>
			</table>
			<div style="width: 650px; height: 200px;">
				<table class="easyui-datagrid" title="投资人列表" fit='true' id="reListGrid"
					data-options="height:300,width:650,rownumbers:false,singleSelect:true,pagination: false,fitColumns:false,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'SUBSEQNO',collapsible:true">
						<thead>
							<tr>
								<th data-options="field:'SUBSEQNO',width:100,hidden:false">子流水号</th>
								<th data-options="field:'ACNO',width:100">投资人账号</th>
								<th data-options="field:'ACNAME',width:100">还款账号户名</th>
								<th data-options="field:'INCOMEDATE',width:100">该收益所属截止日期</th>
								<th data-options="field:'AMOUNT',width:100">还款总金额</th>
								<th data-options="field:'PRINCIPALAMT',width:100">本次还款本金</th>
								<th data-options="field:'INCOMEAMT',width:100">本次还款收益</th>
								<th data-options="field:'FEEAMT',width:100">本次还款费用</th>
								<th data-options="field:'STATUS',width:100">状态</th>
								<th data-options="field:'ERRMSG',width:100">错误原因</th>
								<th data-options="field:'HOSTDT',width:100">银行交易日期</th>
								<th data-options="field:'HOSTJNLNO',width:100">银行交易流水号</th>
							</tr>
						</thead>
				</table>
			</div>
			<div style="text-align: right; padding-top: 10px">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#queryDialog').dialog('close')">关闭</a>
			</div>
		
	</div>

	<!--提交收益明细end -->	
	
	
	<!-- 查询投资人列表  -->
	<div id="queryPeopleDialog" class="easyui-dialog"
		style="width: 800px; height: 400px; padding: 10px 20px" closed="true"
		resizable="true" modal="true" data-options="title:'提交收益明细投资人列表查询'">
		<table class="easyui-datagrid" title="还款信息列表" fit='true' id="peopleGrid"
				data-options="rownumbers:false,singleSelect:true,fitColumns:false,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'SUBSEQNO',collapsible:true,url:'',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'SUBSEQNO',width:100,hidden:false">子流水号</th>
						<th data-options="field:'ACNO',width:100">投资人账号</th>
						<th data-options="field:'ACNAME',width:100">投资人账号户名</th>
						<th data-options="field:'INCOMEDATE',width:100">该收益所属截止日期</th>
						<th data-options="field:'AMOUNT',width:100">还款总金额</th>
						<th data-options="field:'PRINCIPALAMT',width:100">本次还款本金</th>
						<th data-options="field:'INCOMEAMT',width:100">本次还款收益</th>
						<th data-options="field:'FEEAMT',width:100">本次还款费用</th>
					</tr>
				</thead>
			</table>
		</table>
	</div>
	
</body>
</html>
