<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业充值来账记录</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/formatter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/bank/enterpriseRechargeRecord.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<table>
				<tr>
					<td>银行账号</td>
					<td><input class="easyui-textbox" id="bankNo"></td>
					<td>交易时间</td>
					<td><input class="easyui-datebox" id="transdate"></td>
					<td>交易类型</td>
					<td>
						<select style="width:60px;" id="transType" class="easyui-combobox">
							<option value="">全部</option>
							<option value="0">收入</option>
							<option value="1">支出</option>
						</select>
					</td>
					<td>
						<a class="easyui-linkbutton" href="javascript:serach();" iconcls="icon-search">查询</a>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="eAccountRechargeGrid"
				data-options="rownumbers:false,singleSelect:true,fitColumns:true,pagination:true,pageSize:20,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/bank/enterpriseBindCard/listEnterpriseRechargeRecord',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:150">id</th>
						<th data-options="field:'reqSeqNo',width:120">第三方交易流水号</th>
						<th data-options="field:'bankAccountNo',width:100">银行账号</th>
						<th data-options="field:'transType',width:200,formatter:transTypeFormat">交易类型</th>
						<th data-options="field:'oppAccountNo',width:140">对方账号</th>
						<th data-options="field:'oppAccountName',width:120">对方户名</th>
						<th data-options="field:'amount',width:120">交易金额</th>
						<th data-options="field:'transDate',width:120">交易日期</th>
						<th data-options="field:'oppAccOpenBankNo',width:120">对方开户行</th>
						<th data-options="field:'oppAccOpenBankName',width:120">对方账户开户行名称</th>
						<th data-options="field:'postScript',width:120">附言</th>
						<th data-options="field:'comments',width:120">备注</th>
						<th data-options="field:'summaryInfoMsg',width:120">摘要</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
</body>
</html>