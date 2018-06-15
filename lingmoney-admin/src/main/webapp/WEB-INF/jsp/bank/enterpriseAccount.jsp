<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业账户</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/formatter.js"></script>
<script type="text/javascript" src="/resource/js/bank/enterpriseAccount.js"></script>
<style type="text/css">
	.accountDesc{
		margin: 0 10px 0 0;
		color: #FF3333;
		font-size: 14px;
	}
</style>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<table>
				<tr>
					<td>公司名称</td>
					<td><input class="easyui-textbox" id="companyName"></td>
					<td>银行账号</td>
					<td><input class="easyui-textbox" id="bankNo"></td>
					<td><a class="easyui-linkbutton" href="javascript:serach();" iconcls="icon-search">查询</a></td>
				</tr>
			</table>
		</div>
		
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="enterpriseAccountListGrid"
				data-options="rownumbers:false,singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,pageSize:20,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/bank/enterpriseAccount/listEnterpriseAccount',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:150">id</th>
						<th data-options="field:'enterpriseName',width:120">企业名</th>
						<th data-options="field:'idNumber',width:100">企业注册证件号</th>
						<th data-options="field:'channelFlow',width:200">流水号</th>
						<th data-options="field:'bankNo',width:140">银行账号</th>
						<th data-options="field:'status',width:120,formatter:statusFormat">审核状态</th>
						<th data-options="field:'errorMsg',width:120">失败原因</th>
						<th data-options="field:'partyName',width:80">经办人姓名</th>
						<th data-options="field:'partyMobile',width:100">经办人手机号</th>
						<th data-options="field:'requestTime',width:120,formatter:formatTimers">申请时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<div id="addAccountDiv" class="easyui-dialog" style="width:450px;height:300px;" 
		data-options="title:'申请开户',closed:true,modal:true,
		buttons:[{text:'提交', handler:requestAccountOpen},{text:'取消', handler:cancelAccountOpen}]">
		<table>
			<tr style="height: 40px;">
				<td style="padding:0 19px;">企业名：</td>
				<td><input id="companyNameForm" name="companyName" class="easyui-textbox"></td>
			</tr>
			<tr style="height: 40px;">
				<td style="padding:0 19px;">企业注册证件号码：</td>
				<td><input id="occCodeNoForm" name="occCodeNo" class="easyui-textbox"></td>
			</tr>
		</table>
		<ul class = "accountDesc">
			<li>由于三证合一的政策，现企业均为同一社会信用代码证，其中包含组织机构代码，具体规则如下：</li>
			<li>若企业为18位的统一社会信用代码证，则组织机构代码证为统一社会信用代码证的第9位至17位，且第16位与第17位需以横线隔开。</li>
			<li>示例：统一社会信用代码证为91430111MA4L16JQ9B，组织机构代码为MA4L16JQ9，系统填写为MA4L16JQ-9。</li>
		</ul>
	</div>
	
	<div id="withdrawalsWin" class="easyui-dialog" style="width:450px;height:370px;" 
		data-options="title:'申请开户',closed:true,modal:true,
		buttons:[{text:'提交', handler:withdrawals},{text:'取消', handler:cancelWithdrawalsWin}]">
		<ul class = "accountDesc">
			<li>请确认使用的浏览器允许弹出窗口，可在浏览器设置中进行开启不拦截，如果不会，请百度。</li>
			<li>请先确定输入的金额小于等于账户可用余额，可在余额查询中进行查看</li>
			<li>请确定提现的企业账户的经办人的手机在你手中，否则无法提现。</li>
			<li>==================================</li>
			<li style="font-weight: bold ;">请企业的经办人保管好自己的手机。如发生手机丢失等情况，第一时间进行挂失。</li>
			<li style="font-weight: bold ;">请企业的经办人保管好自己的手机。如发生手机丢失等情况，第一时间进行挂失。</li>
			<li style="font-weight: bold ;">请企业的经办人保管好自己的手机。如发生手机丢失等情况，第一时间进行挂失。</li>
			<li>==================================</li>
			<li>输入提现金额，点击确认后，跳转到银行页面操作。</li>
		</ul>
		<table>
			<tr style="height: 40px;">
				<td style="padding:0 19px;">提现金额：</td>
				<td><input id="withAmount" name="withAmount" class="easyui-textbox"></td>
			</tr>
		</table>
	</div>
	
	<!-- 企业经办人信息变更记录 -->
	<div id="listTransactorDiv" class="easyui-dialog" style="width:900px; height: 350px;" 
		data-options="title:'企业经办人信息变更列表', closed:true, modal:true">
		<table class="easyui-datagrid" title="" fit='true' id="listTransactorGrid"
			data-options="rownumbers:false,singleSelect:true,toolbar:transActorToolbar,fitColumns:true,pagination:true,pageSize:20,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/bank/hxEnterpriseAgent/listTransactor',method:'post'">
			<thead>
				<tr>
					<th data-options="field:'id',width:150">id</th>
					<th data-options="field:'eacId',width:120">企业账户ID</th>
					<th data-options="field:'name',width:80">经办人姓名</th>
					<th data-options="field:'mobile',width:100">经办人手机号</th>
					<th data-options="field:'channelFlow',width:200">流水号</th>
					<th data-options="field:'status',width:120,formatter:statusFormat">审核状态</th>
					<th data-options="field:'errorMsg',width:120">失败原因</th>
					<th data-options="field:'requestTime',width:120,formatter:formatTimers">申请时间</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<!-- 银行表单提交 -->
	<form id="bankPostForm" method="post" target="_blank" style="display:none;">
		<input name="RequestData" id="requestData">
		<input name="transCode" id="transCode">
	</form>
	
	<!-- 企业绑定卡信息 -->
	<div id="enterpriseBindCardDialog" class="easyui-dialog" style="width:900px; height: 350px;" 
		data-options="title:'企业绑定卡信息', closed:true, modal:true">
		<table class="easyui-datagrid" title="" fit='true' id="enterpriseBindCardGrid"
			data-options="rownumbers:false,singleSelect:true,fitColumns:true,pagination:true,pageSize:10,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post'">
			<thead>
				<tr>
					<th data-options="field:'cardNo',width:120">卡号</th>
					<th data-options="field:'cardType',width:80,formatter:cardTypeFormatter">卡类型</th>
					<th data-options="field:'bindDate',width:140">绑卡时间</th>
					<th data-options="field:'state',width:100,formatter:cardStateFormatter">绑定卡状态</th>
					<th data-options="field:'otherBankFlag',width:100,formatter:otherBankFlagFormatter">本他行标志</th>
					<th data-options="field:'bankNumber',width:120">开户行</th>
					<th data-options="field:'bankName',width:200">开户行名称</th>
				</tr>
			</thead>
		</table>
	</div>

</body>
</html>