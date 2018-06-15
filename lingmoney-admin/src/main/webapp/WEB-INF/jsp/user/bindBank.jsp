<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查询用户是否绑卡</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/user/bindBank.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>注册手机号：</td>
						<td><input class="easyui-textbox" name="userTel" id="userTel"
							style="width: 200px"
							data-options="prompt:'请输入注册手机号',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#userTel').textbox('setValue', '');
											}
										}]" /></td>
						<td>姓名：</td>
						<td><input class="easyui-textbox" name="userName"
							id="userName" style="width: 200px"
							data-options="prompt:'请输入姓名',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#userName').textbox('setValue', '');
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
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="rownumbers:true,singleSelect:true,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100,hidden:true">ID</th>
						<th data-options="field:'userTel',width:100">注册手机号</th>
						<th data-options="field:'userName',width:100">用户姓名</th>
						<th data-options="field:'uId',width:100">用户id</th>
						<th data-options="field:'name',width:100">银行名称</th>
						<th data-options="field:'number',width:200">银行卡号</th>
						<th data-options="field:'tel',width:100">绑卡手机号</th>
						<th data-options="field:'status',width:100"
							formatter="formatBindStatus">绑卡状态</th>
						<th data-options="field:'isdefault',width:100"
							formatter="formatIsDefault">默认银行卡</th>
						<th data-options="field:'bindtime',width:200"
							formatter="formatTimer">绑卡时间</th>
						<th data-options="field:'platformType',width:100"
							formatter="formatPlatForm">绑卡平台</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

</body>
</html>