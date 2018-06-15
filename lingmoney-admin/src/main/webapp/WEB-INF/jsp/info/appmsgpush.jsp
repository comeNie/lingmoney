<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>app消息推送管理</title>
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript">
var toolbar = [ {
	text : '添加',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
}, '-', {
	text : '修改',
	iconCls : 'icon-edit',
	handler : function() {
		edit();
	}
}, '-', {
	text : '推送',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}, '-', {
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}];
var dataGrid;
$(function() {
    dataGrid = $('#dataGrid').datagrid({
        url : '/rest/appmsgpush/list',
        height : 455,
        title : '推送消息',
        border : false,
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        pageSize : 10,
        pageList : [ 10, 15, 20 ],
        columns : [ [ 
            {width : '200', title : '消息标题', field : 'msgTitle'},
            {width : '200', title : '消息内容', field : 'msgContent'},
            {width : '65', title : '发送用户', field : 'userGroup', 
            	formatter : function(value, row, index) {
	            	switch (value) {
					case 0:
						return '全部用户';
					case 1:
						return '指定用户';
					}
            	}},
            {width : '200', title : '指定用户sql', field : 'userGroupSql'},
            {width : '80', align : 'center', title : '打开行为', field : 'openAction', 
	            formatter : function(value, row, index) {
	            	switch (value) {
					case 0:
						return '打开应用';
					case 1:
						return '打开指定页面';
					case 2:
						return '打开连接';
					}
	            }},
            {width : '125', title : '打开的页面', field : 'openPage'},
            {width : '125', title : '打开的链接', field : 'openUrl'},
            {width : '125', title : '操作时间', field : 'addTime',
            	formatter : function(value, row, index) {
            		if(value != null){
		            	var dd = new Date(value);
		            	return dd.getFullYear()+"-"+dd.getMonth()+"-"+dd.getDate()+" "+dd.getHours()+":"+dd.getMinutes();
            		}
	            }},
            {width : '60', align : 'center', title : '状态', field : 'status', 
	            formatter : function(value, row, index) {
	            	switch (value) {
					case 0:
						return '未发送';
					case 1:
						return '已发送';
					}
	            }},
        ] ],
        toolbar : toolbar
    });
});

function del() {
	var rows = $("#dataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return;
	}
	if(rows[0].status == 1){
		$.messager.alert("提示", "消息已推送，不能删除", "error");
		return;
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要删除吗？', function(r) {
		if (r) {
			$.getJSON("/rest/appmsgpush/delete?r=" + Math.random() + "&id="
					+ rows[0].id, function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '删除成功', 'info');
					$("#dataGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
		}
	});
	closeLoading();
}
function changeSendUsers(type) {
	if (type == 1) {
		$("#userGroupSql").textbox('enable');
	} else {
		$("#userGroupSql").textbox('disable');
	}
}
function changeOpenAction(type){
	if (type == 0) {
		$("#openPage").textbox('disable');
		$("#openUrl").textbox('disable');
	} else if(type == 1){
		$("#openPage").textbox('enable');
		$("#openUrl").textbox('disable');
	} else if(type == 2){
		$("#openPage").textbox('disable');
		$("#openUrl").textbox('enable');
	}
}
function formatUserGroup(value, row) {
	if (value == 0) {
		return "全部用户";
	} else if (value == 1) {
		return "指定用户";
	}
}
function formatOpenAction(value, row) {
	if (value == 0) {
		return "打开应用";
	} else if (value == 1) {
		return "打开指定页面";
	} else if (value == 2) {
		return "打开链接";
	}
}
function formatStatus(value, row) {
	if (value == 0) {
		return "未发送";
	} else if (value == 1) {
		return "已发送";
	}
}
function search() {
	dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$('#userGroup').click();
	$('#openAction').click();
	$("#DivAdd").dialog('open');
	$("#DivAdd").dialog('setTitle','添加消息');
}
function edit(){
	var rows = $("#dataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return;
	}
	if(rows[0].status == 1){
		$.messager.alert("提示", "消息已推送，不能编辑", "error");
		return;
	}
	$("#DivAdd").dialog('setTitle','编辑消息');
	$('#ffAdd').form('clear');
	$("#ffAdd").form('load',rows[0]);
	if(rows[0].userGroup == 1){
		$("#userGroupSql").textbox('enable');
	}
	if(rows[0].openAction == 1){
		$("#openPage").textbox('enable');
	}
	if(rows[0].openAction == 2){
		$("#openUrl").textbox('enable');
	}
	$("#DivAdd").dialog('open');
}

function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/appmsgpush/saveOrEdit',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivAdd").dialog('close');
				dataGrid.datagrid('reload');
			}
			else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
function publish(){
	var rows = $("#dataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return;
	}
	if(rows[0].status == 1){
		$.messager.alert("提示", "消息已推送，不能重复推送", "error");
		return;
	}
	showLoading();
	$.get('/rest/appmsgpush/publish?id='+rows[0].id,{},function(info){
		if(info.code == 200){
			$.messager.alert("提示", "推送成功", "info");
			dataGrid.datagrid('reload');
		}else{
			$.messager.alert("提示", info.msg, "error");
		}
	},'json');
	closeLoading();
}
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 700px;" fit="true">
		<div class="easyui-panel"
			data-options="region:'north',iconCls:'icon-book'"
			style="padding: 5px; height: 50px">
			<form id="searchForm">
				<table>
					<tr>
						<td>消息标题：</td>
						<td><input class="easyui-textbox" name="msgTitle" id="title"
							style="width: 200px" data-options="prompt:'请输入标题',
							            icons: [{
											iconCls:'icon-remove',
											handler: function(e){
												$('#title').textbox('setValue', '');
											}
										}]"/></td>
						<td>状态 ：</td>
						<td>
							<select id="status" class="easyui-combobox" name="status" style="width:70px;" 
								data-options="editable:false,
									panelHeight:'auto'"> 
							    <option value="">全部</option>   
							    <option value="0">未发送</option>   
							    <option value="1">已发送</option>   
							</select>  
						</td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							id="btnAddOK" iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table id="dataGrid" data-options="fit:true,border:false"></table>
		</div>
	</div>
	<!-- 弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 620px; height: 470px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id">
			<table id="tblAdd" class="view">
				<tr>
					<td>消息标题：</td>
					<td><input class="easyui-textbox" name="msgTitle" id="msgTitle"
						style="width: 400px"
						data-options="prompt:'请输入标题',
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#msgTitle').textbox('setValue', '');
									}
								}]" /></td>
					
				</tr>
				<tr>
					<td>消息内容：</td>
					<td><input class="easyui-textbox" name="msgContent" id="msgContent"
						style="width: 400px;"
						data-options="prompt:'请输入内容',
									height:88,
									multiline:true,
						            icons: [{
										iconCls:'icon-remove',
										handler: function(e){
											$('#msgContent').textbox('setValue', '');
										}
									}]" /></td>
					
				</tr>
				<tr>
					<td>发送用户：</td>
					<td><input id="userGroup" type="radio" name="userGroup" value="0" checked onclick="changeSendUsers(0)"/>全部用户
						<input type="radio" name="userGroup" value="1" onclick="changeSendUsers(1)"/>指定用户
					</td>
				</tr>
				<tr>
					<td>用户查询sql：</td>							
					<td><input class="easyui-textbox" name="userGroupSql" id="userGroupSql"
						style="width: 400px"
						data-options="prompt:'请输入sql',
									height:88,
									disabled:true,
									multiline:true,
						            icons: [{
										iconCls:'icon-remove',
										handler: function(e){
											$('#userGroupSql').textbox('setValue', '');
										}
									}]" /></td>
				</tr>
				<tr>
					<td>打开行为：</td>
					<td><input id="openAction" type="radio" name="openAction" value="0" checked onclick="changeOpenAction(0)"/>打开应用
						<input type="radio" name="openAction" value="1" onclick="changeOpenAction(1)"/>打开指定页面
						<input type="radio" name="openAction" value="2" onclick="changeOpenAction(2)"/>打开链接
					</td>
				</tr>
				<tr>
					<td>打开页面：</td>
					<td><input class="easyui-textbox" name="openPage" id="openPage"
						style="width: 400px"
						data-options="prompt:'请指定页面',disabled:true,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#openPage').textbox('setValue', '');
									}
								}]" /></td>
					
				</tr>
				<tr>
					<td>打开链接：</td>
					<td><input class="easyui-textbox" name="openUrl" id="openUrl"
						style="width: 400px"
						data-options="prompt:'请指定链接',disabled:true,
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#openUrl').textbox('setValue', '');
									}
								}]" /></td>
					
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

</body>
</html>