<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加产品</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/product/productImg.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>产品名称：</td>
						<td><input class="easyui-textbox" name="name" id="pName"
							style="width: 200px"
							data-options="prompt:'请输入产品名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#pName').textbox('setValue', '');
											}
										}]" /></td>
						<td>状态 ：</td>
						<td><select class="easyui-combobox" name="status" id="status"
							style="width: 200px;"
							data-options="value:'',prompt:'请选择状态',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#status').combobox('setValue', '');
											}
										}]">
								<option value="0">不可用</option>
								<option value="1">可用</option>
						</select></td>
						<td>类型 ：</td>
						<td><select class="easyui-combobox" name="type" id="type"
							style="width: 200px;"
							data-options="value:'',prompt:'请选择类型 ',panelHeight:'auto',editable:false,icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#type').combobox('setValue', '');
											}
										}]">
								<option value="0">WEB端我的理财产品图片</option>
								<option value="1">APP端产品背景图Android</option>
								<option value="2">APP端产品背景图NAV</option>
								<option value="3">APP端产品背景图IOS</option>
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/product/image/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100">id</th>
						<th data-options="field:'name',width:100">产品名称</th>
						<th data-options="field:'url',width:100">图片路径</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">状态</th>
						<th data-options="field:'type',width:100" formatter="formatType">类型</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<%--添加的弹出层--%>
	<div id="DivAdd" class="easyui-dialog"
		style="width: 400px; height: 210px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'产品图片设置',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">产品名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">上传图片：</label></th>
					<td><input class="easyui-filebox" name="imageFile"
						style="width: 200px" data-options="prompt:'请选择图片'"></input></td>
				</tr>
				<tr>
					<th><label for="status">类型：</label></th>
					<td><select class="easyui-combobox" name="type"
						style="width: 200px;"
						data-options="value:'',prompt:'请选择类型 ',panelHeight:'auto',editable:false">
							<option value="0">WEB端我的理财产品图片</option>
							<option value="1">Android产品背景图</option>
							<option value="2">Nav产品背景图</option>
							<option value="3">iOS产品背景图</option>
					</select></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="saveAndUpdate()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>