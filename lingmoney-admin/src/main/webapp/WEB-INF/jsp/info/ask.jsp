<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>你问我答设置</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/DatePattern.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/ajax.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/info/ask.js"></script>
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
						<td><input class="easyui-textbox" name="title" id="stitle"
							style="width: 200px"
							data-options="prompt:'请输入标题',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#stitle').textbox('setValue', '');
											}
										}]" /></td>
						<td>状态 ：</td>
						<td><select class="easyui-combogrid" id="state"
							name="state" style="width: 200px"
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
												$('#state').combogrid('setValue', '');
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
				data-options="singleSelect:true,toolbar:toolbar,fitColumns:true,pagination:true,iconCls:'icon-view',loadMsg:'请稍后，正在加载数据...',idField:'id',collapsible:true,url:'/rest/info/ask/list',method:'post'">
				<thead>
					<tr>
						<th data-options="field:'id',width:50">id</th>
						<th data-options="field:'title',width:100">标题</th>
						<th data-options="field:'time',width:100" formatter="formatTimer">提问时间</th>
						<th data-options="field:'status',width:100"
							formatter="formatStatus">回答状态</th>
						<th data-options="field:'hot',width:100" formatter="formatHot">是否热门</th>
						<th data-options="field:'state',width:100" formatter="formatState">问题状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<form id="ffAdd" method="post" novalidate="novalidate">
		<input type="hidden" name="content" id="acontent" /> <input
			type="hidden" name="time" id="atime" /> <input type="hidden"
			name="anwserMan" id="aanwserMan" /> <input type="hidden"
			name="keyword" id="akeyword" /> <input type="hidden" name="status"
			id="astatus" /> <input type="hidden" name="hot" id="ahot" /> <input
			type="hidden" name="type" id="atype" /> <input type="hidden"
			name="askId" id="aaskId" />
	</form>
	<form id="ffEdits" method="post" novalidate="novalidate">
		<input type="hidden" name="id" id="esid" /> <input type="hidden"
			name="content" id="escontent" /> <input type="hidden" name="time"
			id="estime" /> <input type="hidden" name="anwserMan"
			id="esanwserMan" /> <input type="hidden" name="keyword"
			id="eskeyword" /> <input type="hidden" name="status" id="esstatus" />
		<input type="hidden" name="hot" id="eshot" /> <input type="hidden"
			name="type" id="estype" /> <input type="hidden" name="askId"
			id="esaskId" />
	</form>
	<div id="DivEdit" class="easyui-dialog"
		style="width: 860px; height: 610px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="title:'客服人员回答问题',iconCls: 'icon-edit',buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate">
			<input type="hidden" name="id" id="eid" /> <input type="hidden"
				name="status" id="estatus" /> <input type="hidden" name="anwserMan"
				id="eanwserMan" />
			<table id="tblEdit" class="view">
				<tr>
					<th><label for="name">标题:</label></th>
					<td colspan="4"><input class="easyui-textbox"
						readonly="readonly" name="title" id="etitle" style="width: 200px"></input>
					</td>
				</tr>
				<tr>
					<th><label for="content">内容:</label></th>
					<td colspan="4"><input class="easyui-textbox" name="content"
						readonly="readonly" id="econtent" data-options="multiline:true"
						style="width: 725px; height: 100px"></input></td>
				</tr>
				<tr>
					<th><label for="keyword">关键字:</label></th>
					<td colspan="4"><input class="easyui-textbox" name="keyword"
						id="ekeyword" style="width: 200px"></input>(英文逗号隔开)</td>
				<tr>
					<th><label for="anwser">回答：</label></th>
					<td colspan="4"><textarea name="anwser" id="anwser"
							style="width: 750px; height: 280px; visibility: hidden;"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						id="btnAddOK" iconcls="icon-ok" onclick="toAnwser()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:KindEditor.remove('#anwser'),$('#DivEdit').dialog('close');">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>