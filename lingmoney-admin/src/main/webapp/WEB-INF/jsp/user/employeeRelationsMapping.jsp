<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工推荐码映射</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/user/employeeRelationsMapping.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>员工推荐码：</td>
						<td><input class="easyui-textbox" name="employeeid"
							id="employeeid" style="width: 200px"
							data-options="prompt:'请输入员工推荐码',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#employeeid').textbox('setValue', '');
											}
										}]" /></td>
						<td>员工姓名：</td>
						<td><input class="easyui-textbox" name="employeename"
							id="employeename" style="width: 200px"
							data-options="prompt:'请输入员工姓名',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#employeename').textbox('setValue', '');
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
				data-options="rownumbers:true,singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/user/employeeRelationsMapping/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100,hidden:true">ID</th>
						<th data-options="field:'type',width:100">类型</th>
						<th data-options="field:'compName',width:100">公司</th>
						<th data-options="field:'department',width:100">部门</th>
						<th data-options="field:'employeeName',width:100">员工姓名</th>
						<th data-options="field:'employeeid',width:100">员工推荐码</th>
						<th data-options="field:'newid',width:100">注册推荐码</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 430px; height: 320px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加员工推荐码映射',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">类型(0或1):</label></th>
					<td><input class="easyui-numberbox" name="type"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">公司(总部、睿博财富、睿博盈通、臣君资本):</label></th>
					<td><input class="easyui-textbox" name="compName"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">部门:</label></th>
					<td><input class="easyui-textbox" name="department"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">员工姓名:</label></th>
					<td><input class="easyui-textbox" name="employeeName"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">员工推荐码(0+员工号):</label></th>
					<td><input class="easyui-textbox" name="employeeid"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">注册推荐码:</label></th>
					<td><input class="easyui-textbox" name="newid"
						data-options="required:false" style="width: 200px"></input></td>
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
		style="width: 430px; height: 320px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'修改员工推荐码映射',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">类型(0或1):</label></th>
					<td><input class="easyui-numberbox" name="type"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">公司(总部、睿博财富、睿博盈通、臣君资本):</label></th>
					<td><input class="easyui-textbox" name="compName"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">部门:</label></th>
					<td><input class="easyui-textbox" name="department"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">员工姓名:</label></th>
					<td><input class="easyui-textbox" name="employeeName"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">员工推荐码(0+员工号):</label></th>
					<td><input class="easyui-textbox" name="employeeid"
						data-options="required:true" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">注册推荐码:</label></th>
					<td><input class="easyui-textbox" name="newid"
						data-options="required:false" style="width: 200px"></input></td>
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