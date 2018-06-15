<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>随心取固定收益率设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/takeheart/fixRate.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>随心取分类名称：</td>
						<td><select class="easyui-combogrid" id="scId" name="cId"
							style="width: 200px"
							data-options="
							            panelWidth: 200,
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/takeheart/category/list.html',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'名称',width:50}
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/takeheart/fixRate/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'rDay',width:100">汇率的时间(天)</th>
						<th data-options="field:'rate',width:100">时间对应的汇率</th>
						<th data-options="field:'cId',width:100">随心取分类ID</th>
						<th data-options="field:'cName',width:100">随心取分类名称</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加和修改的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 360px; height: 225px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'随心取固定收益率设置',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate">
			<input type="hidden" name="id" />
			<input type="hidden" name="cName" id="cName" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">随心取分类名称:</label></th>
					<td><select class="easyui-combogrid" id="cId" name="cId"
						style="width: 200px"
						data-options="
							            panelWidth: 200,
							            idField: 'id',
							            editable:false,
							            required:true,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/rest/takeheart/category/list.html?status=0',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'名称',width:50}
							            ]],
							            fitColumns: true
							        ">
					</select></td>
				</tr>
				<tr>
					<th><label for="name">汇率的时间(天):</label></th>
					<td><input class="easyui-numberbox" name="rDay"
						data-options="min:0,prompt:'请填写整数',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">时间对应的汇率:</label></th>
					<td><input class="easyui-numberbox" name="rate"
						data-options="max:1,min:0, precision:3,prompt:'保留3位小数',required:true"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="saveAndUpdate()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close');">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 添加和修改的弹出层 -->

</body>
</html>