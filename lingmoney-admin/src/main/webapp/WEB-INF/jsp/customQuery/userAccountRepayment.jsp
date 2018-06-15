<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>撤/流标回款记录</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/customQuery/userAccountRepayment.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true" >
		
		<div class="easyui-panel" data-options="region:'north',title:'查询窗口',iconCls:'icon-book'" style="padding: 5px; height: 80px">
			<form>
				<table>
					<tr>
						<td>用户姓名：</td>
						<td><input class="easyui-textbox" id="name" /></td>
						<td>用户手机号：</td>
						<td><input class="easyui-textbox" id="tel" /></td>
						<td>日期：</td>
						<td><input class="easyui-datebox" id="initTime" /></td>
						<td>状态：</td>
						<td>
							<select class="easyui-combobox" id="status">
								<option value="" selected="selected">全部</option>
								<option value="0">待回款</option>
								<option value="1">回款成功</option>
							</select>
						</td>
						<td><a href="javascript:search()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" style="padding: 5px;">
			<table class="easyui-datagrid" title="查询结果" id="userAccountRepaymentDatagrid" 
				data-options="singleSelect:true,toolbar:toolbar,fit:true,fitColumns:true,pagination:true,pageSize:30,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',url:'/rest/bank/queryUsersAccountRepaymentWithSelfCondition',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:40">id</th>
						<th data-options="field:'tid',width:100">交易ID</th>
						<th data-options="field:'loanNo',width:100">借款编号</th>
						<th data-options="field:'amount',width:100">金额</th>
						<th data-options="field:'state',width:100, formatter:formatState">状态</th>
						<th data-options="field:'type',width:100,  formatter:formatType">类型</th>
						<th data-options="field:'acNo',width:100">E账号</th>
						<th data-options="field:'acName',width:100">用户姓名</th>
						<th data-options="field:'mobile',width:100">手机号</th>
						<th data-options="field:'initTime',width:100">入库时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
	</div>
	
</body>
</html>