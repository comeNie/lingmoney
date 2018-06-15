<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的领地供应商设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/gift/lingbaoSupplier.js"></script>
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
						<td><input class="easyui-textbox" style="width: 180px"
							name="name" id="name"
							data-options="prompt:'请输入名称',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#name').textbox('setValue', '');
											}
										}]" "/></td>
						<td>状态：</td>
						<td><select class="easyui-combogrid" id="status"
							name="status" style="width: 180px"
							data-options="
							            panelWidth: 180,
							            prompt:'请选择状态',
							            idField: 'id',
							            editable:false,
							            loadMsg:'正在加载,请稍后...',
							            textField: 'name',
							            url: '/resource/json/useStatus.json',
							            method: 'get',
							            columns: [[
							                {field:'id',title:'id',width:30},
							                {field:'name',title:'name',width:50}
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/gift/lingbaoSupplier/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'name',width:50">名称</th>
						<th data-options="field:'tel',width:50">联系方式</th>
						<th data-options="field:'remark',width:50">备注</th>
						<th data-options="field:'status',width:50"
							formatter="formatStatus">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加和修改的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 420px; height: 210px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'我的领地供应商设置',iconCls: 'icon-add',
			buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        saveAndUpdate();
                    }
                },{
                    text:'关闭',
                     iconCls:'icon-cancel',
                    handler:function(){
                        $('#DivAdd').dialog('close');
                    }
                }]">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">名称：</label></th>
					<td><input class="easyui-textbox" name="name"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">联系方式：</label></th>
					<td><input class="easyui-textbox" name="tel"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">备注：</label></th>
					<td><input class="easyui-textbox" name="remark"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>