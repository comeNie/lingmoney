<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>漫画分类</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/info/financialManagement.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>

</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',title:'查询窗口',iconCls:'icon-book'"
			style="padding: 5px; height: 80px">
			<form id="searchFrm">
				<table>
					<tr>
						<td>标题：</td>
						<td><input class="easyui-textbox" name="title" id="titleId"
							data-options="prompt:'请输入标题',icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#titleId').textbox('setValue', '');
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/financial/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'title',width:200">标题</th>
						<th data-options="field:'indexPic',width:200">图片路径</th>
						<th data-options="field:'status',width:50"
							formatter="formatStatus">状态</th>
						<th data-options="field:'pubDate',width:150"
							formatter="formatTimer">发布时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 添加的弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 860px; height: 500px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'添加漫画',iconCls: 'icon-add',buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table id="tblAdd" class="view">
				<tr>
					<th><label for="title">标题:</label></th>
					<td><input class="easyui-textbox" name="title" id="atitle"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">图片路径:</label></th>
					<td><input class="easyui-filebox" name="indexImg"
						id="aindexImg" style="width: 200px"
						data-options="prompt:'请选择图片',required:true"></input></td>
				</tr>
				<!-- <tr>
					<th><label for="name">用户id:</label></th>
					<td><input class="easyui-textbox" name="uId" id="auId"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">用户名称:</label></th>
					<td><input class="easyui-textbox" name="uName" id="auName"
						style="width: 200px"></input></td>
				</tr> -->
				<tr>
					<th><label for="name">内容:</label></th>
					<td><textarea name="content" id="acontent"
							style="width: 700px; height: 350px; visibility: hidden;"></textarea></td>
				</tr>


				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-clear" onclick="resets()">重置</a> <a
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
		style="width: 860px; height: 500px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'编辑漫画',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" />
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="title">标题:</label></th>
					<td><input class="easyui-textbox" name="title" id="etitle"
						style="width: 200px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label for="name">图片路径:</label></th>
					<td><img id="indexPic" style="width: 200px; height: 150px" />
						<p>
							<input class="easyui-filebox" name="indexImg" value="indexPic"
								style="width: 200px" data-options="prompt:'请选择图片'"></input>
						</p></td>
				</tr>
				<!-- <tr>
					<th><label for="name">用户id:</label></th>
					<td><input class="easyui-textbox" name="uId" id="euId"
						style="width: 200px"></input></td>
				</tr>
				<tr>
					<th><label for="name">用户名称:</label></th>
					<td><input class="easyui-textbox" name="uName" id="auName"
						style="width: 200px"></input></td>
				</tr> -->
				<tr>
					<th><label for="name">内容:</label></th>
					<td><textarea name="content" id="econtent"
							style="width: 700px; height: 200px; visibility: hidden"></textarea></td>
				</tr>


				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="update()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-clear" onclick="reset()">重置</a> <a
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