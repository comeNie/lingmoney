<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>手机版本号设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/info/clientVersion.js"></script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="settingForm" method="post" novalidate="novalidate"
				enctype="multipart/form-data">
				<table>
					<tr>
						<td>当前版本更新图片:<a id="imageUrl" target="_blank"></a></td>
						<td><input class="easyui-filebox" name="imageUpload"
							id="imageUpload" style="width: 220px"
							data-options="prompt:'上传新的版本更新图片'"></input></td>
						<td><a href="javascript:imageUpload()"
							class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok">上传</a></td>
						<td><a href="javascript:imageUrlAllow(0)" class="easyui-linkbutton"
							id="addbankMaintain" iconcls="icon-edit">禁用版本更新图片</a></td>
						<td><a href="javascript:imageUrlAllow(1)" class="easyui-linkbutton"
							id="addbankMaintain" iconcls="icon-edit">开通版本更新图片</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table class="easyui-datagrid" title="查询结果" fit='true' id="adGrid"
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/clientVersion/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'type',width:100" formatter="formatType">手机类型</th>
						<th data-options="field:'versionNumber',width:100">版本号</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">状态</th>
						<th data-options="field:'createTime',width:100"
							formatter="formatTimer">创建时间</th>
						<th data-options="field:'title',width:100">标题</th>
						<th data-options="field:'versionName',width:100">版本名</th>
						<th data-options="field:'downloadAddress',width:100">下载地址</th>
						<th data-options="field:'alertType',width:100"
							formatter="formatAlert">弹窗类型</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 400px; height: 500px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加手机版本号',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="name">手机类型:</label></th>
					<td><select class="easyui-combogrid" name="type" id="type"
						style="width: 200px;"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/resource/json/client_version.json',
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
					<th><label for="name">版本号:</label></th>
					<td><input class="easyui-textbox" name="versionNumber"
						id="versionNumber" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">状态:</label></th>
					<td><select class="easyui-combogrid" name="status" id="status"
						style="width: 200px;"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/resource/json/version_status.json',
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
					<th><label for="name">标题:</label></th>
					<td><input class="easyui-textbox" name="title" id="title"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">版本名:</label></th>
					<td><input class="easyui-textbox" name="versionName"
						id="versionName" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">下载地址:</label></th>
					<td><input class="easyui-textbox" name="downloadAddress"
						id="downloadAddress" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">弹窗类型:</label></th>
					<td><select class="easyui-combogrid" name="alertType"
						id="alertType" style="width: 200px;"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/resource/json/alert_type.json',
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
					<th><label for="name">升级说明:</label></th>
					<td><input class="easyui-textbox" name="introduction"
						id="introduction" style="width: 200px"
						data-options="multiline:true,height:140"></input></td>
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
		style="width: 400px; height: 500px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'编辑手机版本号',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<input type="hidden" name="id" />
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">手机类型:</label></th>
					<td><select class="easyui-combogrid" name="type" id="type"
						style="width: 200px;"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/resource/json/client_version.json',
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
					<th><label for="name">版本号:</label></th>
					<td><input class="easyui-textbox" name="versionNumber"
						id="versionNumber" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">状态:</label></th>
					<td><select class="easyui-combogrid" name="status" id="status"
						style="width: 200px;"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/resource/json/version_status.json',
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
					<th><label for="name">标题:</label></th>
					<td><input class="easyui-textbox" name="title" id="title"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">版本名:</label></th>
					<td><input class="easyui-textbox" name="versionName"
						id="versionName" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">下载地址:</label></th>
					<td><input class="easyui-textbox" name="downloadAddress"
						id="downloadAddress" style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">弹窗类型:</label></th>
					<td><select class="easyui-combogrid" name="alertType"
						id="alertType" style="width: 200px;"
						data-options="
						            panelWidth: 200,
						            idField: 'id',
						            editable:false,
						            loadMsg:'正在加载,请稍后...',
						            textField: 'name',
						            url: '/resource/json/alert_type.json',
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
					<th><label for="name">升级说明:</label></th>
					<td><input class="easyui-textbox" name="introduction"
						id="introduction" style="width: 200px"
						data-options="multiline:true,height:140"></input></td>
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

</body>
</html>