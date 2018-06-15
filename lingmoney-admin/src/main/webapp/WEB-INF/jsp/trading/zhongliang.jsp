<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>赎回失败流水</title>
<jsp:include page="/common/head.jsp"></jsp:include>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/trading/zhongliang.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>状态：</td>
						<td><select class="easyui-combobox" name="status" id="status"
							style="width: 200px;" data-options="editable:false">
								<option value="-1">全部</option>
								<option value="1">购买成功</option>
								<option value="2">赎回中</option>
								<option value="3">赎回成功</option>
						</select></td>
						<td>用户：</td>
						<td><select class="easyui-combogrid" name="uId" id="uId"
							style="width: 400px;"
							data-options="
						            panelWidth: 400,
						            idField: 'uId',
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/rest/trading/zhongliang/listUsers',
						            method: 'get',
						            columns: [[
						                {field:'uId',title:'用户id',width:1},
						                {field:'name',title:'用户姓名',width:1},
						                {field:'department',title:'所在部门',width:1}
						            ]],
						            fitColumns: true
						        ">
						</select></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="rownumbers:true,singleSelect:true,fitColumns:true,pagination:true,iconCls:'icon-view',
		            loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/trading/zhongliang/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:150">交易id</th>
						<th data-options="field:'pName',width:150">产品名称</th>
						<th data-options="field:'pCode',width:150">产品code</th>
						<th data-options="field:'buyDt',width:200" formatter="formatTimer">购买时间</th>
						<th data-options="field:'financialMoney',width:150">理财金额(元)</th>
						<th data-options="field:'status',width:200" formatter="formatStatus">状态</th>
						<th data-options="field:'userName',width:150">用户姓名</th>
						<th data-options="field:'department',width:150">所在部门</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 编辑的弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 350px; height: 150px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'卖出产品',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<input type="hidden" name="id" /> <input type="hidden" name="sellDt"
				id="esellDt" />

			<table id="tblEdit" class="view">
				<tr>
					<th><label>卖出日期：</label></th>
					<td><input class="easyui-datetimebox" id="viewASellDt"
						required style="width: 200px"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="sell()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>