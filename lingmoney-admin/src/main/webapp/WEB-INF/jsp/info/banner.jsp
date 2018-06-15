<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页小banner设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="/resource/js/info/banner.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>名称：</td>
						<td><input class="easyui-textbox" name="name" id="bannername"
							style="width: 200px"
							data-options="prompt:'请输入名称',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#bannername').textbox('setValue', '');
											}
										}]" /></td>
						<td>状态 ：</td>
						<td><select class="easyui-combogrid" id="status"
							name="status" style="width: 200px"
							data-options="
							            panelWidth: 200,
							            idField: 'id',
							            editable:false,
							            prompt:'请选择状态',
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/useStatus.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'名称',width:50}
							            ]],
							            fitColumns: true,
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#status').combogrid('setValue', '');
											}
										}]
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/base/banner/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'name',width:150">名称</th>
						<th data-options="field:'url',width:150">跳转链接</th>
						<th data-options="field:'path',width:300">图片路径</th>
						<th data-options="field:'level',width:50">优先级</th>
						<th data-options="field:'status',width:50"
							formatter="formatStatus">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 420px; height: 250px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'上传图片',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="url">跳转链接：</label></th>
					<td><input class="easyui-textbox" name="url" id="url"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">选择图片：</label></th>
					<td><input class="easyui-filebox" name="imageFile"
						id="imageFile" style="width: 200px"
						data-options="prompt:'请选择图片',required:true"></input></td>
				</tr>
				<tr>
					<th><label for="url">优先级(1最高，2……)：</label></th>
					<td><input class="easyui-textbox" name="level"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 编辑的弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 420px; height: 250px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改应用信息',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name" id="bname"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="url">跳转链接：</label></th>
					<td><input class="easyui-textbox" name="url" id="burl"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">选择图片：</label></th>
					<td><input class="easyui-filebox" name="imageFile"
						style="width: 200px" data-options="prompt:'默认不修改'"></input></td>
				</tr>
				<tr>
					<th><label for="url">优先级(1最高，2……)：</label></th>
					<td><input class="easyui-textbox" name="level"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a> <a
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