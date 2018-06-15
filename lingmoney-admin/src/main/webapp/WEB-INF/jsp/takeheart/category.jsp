<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>随心取分类设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/takeheart/category.js"></script>
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
						<td><input class="easyui-textbox" name="name" id="sname" /></td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/takeheart/category/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'name',width:100">名称</th>
						<th data-options="field:'upperLimit',width:100">上限(元)</th>
						<th data-options="field:'lowerLimit',width:100">下限(元)</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">状态</th>
						<th data-options="field:'releaseDate',width:100"
							formatter="formatTimer">发布日期</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 350px; height: 210px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加随心取分类',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">名称:</label></th>
					<td><input class="easyui-textbox" name="name"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">上限(元):</label></th>
					<td><input class="easyui-numberbox" name="upperLimit"
						data-options="min:0,prompt:'请填写整数',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">下限(元):</label></th>
					<td><input class="easyui-numberbox" name="lowerLimit"
						data-options="min:0,prompt:'请填写整数',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close');">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 添加的弹出层 -->
	<!-- 编辑的弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 350px; height: 210px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改随心取分类',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<input type="hidden" name="id" />
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">名称:</label></th>
					<td><input class="easyui-textbox" name="name"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">上限(元):</label></th>
					<td><input class="easyui-numberbox" name="upperLimit"
						data-options="prompt:'请填写整数',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">下限(元):</label></th>
					<td><input class="easyui-numberbox" name="lowerLimit"
						data-options="prompt:'请填写整数',required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivEdit').dialog('close');">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 编辑的弹出层 -->
	<!-- 发布的弹出层 -->
	<div id="DivPublish" class="easyui-dialog"
		style="width: 350px; height: 180px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'发布随心取分类',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffPublish" method="post" novalidate="novalidate">
			<input type="hidden" name="id" />
			<table id="tblPublish" class="view">
				<tr>
					<th><label for="name">名称:</label></th>
					<td><input class="easyui-textbox" name="name"
						data-options="readonly:true,required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">发布日期:</label></th>
					<td><input class="easyui-datebox" name="releaseDate"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="pub()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivPublish').dialog('close');">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 发布的弹出层 -->
</body>
</html>