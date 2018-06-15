<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8">
<title>银行卡前六位</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/info/prefixBank.js"></script>
<script type="text/javascript">
	$(function() {
	});
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>银行名称：</td>
						<td><input class="easyui-textbox" name="name" id="name" /></td>

						<td>银行卡号：</td>
						<td><input class="easyui-textbox" name="prefixNumber"
							id="prefixNumber" /></td>

						<td>银行简称：</td>
						<td><input class="easyui-textbox" name="bankShort"
							id="bankShort" /></td>
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/prefixBank/query',method:'get'">
				<thead>
					<tr>
						<!-- <th data-options="field:'id',width:50">id</th> -->
						<th data-options="field:'name',width:100">银行名称</th>
						<th data-options="field:'size',width:100">银行卡卡号位数</th>
						<th data-options="field:'prefixNumber',width:100">卡号前六位</th>
						<th data-options="field:'status',width:300,
											formatter:function(value){
												if(value==0)
													return '不可用';
												if(value==1)
													return '可用';
												else
													return '未知';
											}">状态</th>
						<th data-options="field:'bankShort',width:30">银行简称</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 500px; height: 450px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加银行卡前六位',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="get" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">银行名称:</label></th>
					<td><input class="easyui-text easyui-validatebox" name="name"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="size">银行卡卡号位数:</label></th>
					<td><input class="easyui-text easyui-validatebox" name="size"
						data-options="required:true,validType:'length[0,11]'"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="prefixNumber">卡号前六位:</label></th>
					<td><input class="easyui-text easyui-validatebox"
						name="prefixNumber"
						data-options="required:true,validType:'length[3,6]'"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="status">状态:</label></th>
					<td>
						<!-- <input class="easyui-text" name="status"	style="width: 200px"> -->
						<select name="status"
						style="border: 1px solid balck; height: 24px; width: 200px;">
							<option value="0">不可用</option>
							<option value="1">可用</option>
					</select>
					</td>
				</tr>
				<tr>
					<th><label for="bankShort">银行简称代码:</label></th>
					<td><input class="easyui-text easyui-validatebox"
						name="bankShort"
						data-options="required:true,validType:'length[1,10]'"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<td><span class="bankCodeSpan"></span></td>
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
	<!-- 编辑的弹出层 -->

	<div id="DivEdit" class="easyui-dialog"
		style="width: 500px; height: 450px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改银行卡前六位',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="get" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">银行名称:</label></th>
					<td><input class="easyui-text easyui-validatebox" name="name"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="size">银行卡卡号位数:</label></th>
					<td><input class="easyui-text easyui-validatebox" name="size"
						data-options="required:true,validType:'length[0,11]'"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="prefixNumber">卡号前六位:</label></th>
					<td><input class="easyui-text easyui-validatebox"
						name="prefixNumber"
						data-options="required:true,validType:'length[3,6]'"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="status">状态:</label></th>
					<td>
						<!-- <input class="easyui-text" name="status"	style="width: 200px"> -->
						<select name="status"
						style="border: 1px solid balck; height: 24px; width: 200px;">
							<option value="0">不可用</option>
							<option value="1">可用</option>
					</select>
					</td>
				</tr>
				<tr>
					<th><label for="bankShort">银行简称代码:</label></th>
					<td><input class="easyui-text easyui-validatebox"
						name="bankShort"
						data-options="required:true,validType:'length[1,10]'"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<td><span class="bankCodeSpanEdit"></span></td>
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