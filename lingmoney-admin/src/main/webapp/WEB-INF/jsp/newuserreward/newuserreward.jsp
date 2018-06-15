<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新手福利管理</title>
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
	text : '发布',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}, '-', {
	text : '作废',
	iconCls : 'icon-cancel',
	handler : function() {
		del();
	}
}];
var dataGrid;
$(function() {
    dataGrid = $('#dataGrid').datagrid({
        url : '/rest/newUserReward/list',
        height : 455,
        title : '新手福利',
        border : false,
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        pageSize : 10,
        pageList : [ 10, 15, 20 ],
        columns : [ [ 
            {width : '180', title : '提示语', field : 'markedWords'},
            {width : '175', title : '产品详情中应用到的图标URL', field : 'proPic',
            	formatter : function(value, row, index) {
            		if(value == null){
            			return '';
            		}
	            	return "<img src='" + value + "' width='80px'>";
	            }
            },
            {width : '175', title : '首页中新手福利的图标URL', field : 'indexPic',
            	formatter : function(value, row, index) {
            		if(value == null){
            			return '';
            		}
	            	return "<img src='" + value + "' width='80px'>";
	            }
            }, 
            
            {width : '150', title : '创建时间', field : 'createTime'},
            {width : '80', align : 'center', title : '状态', field : 'status', 
	            formatter : function(value, row, index) {
	            	switch (value) {
					case 0:
						return '初始化';
					case 1:
						return '已发布';
					case 2:
						return '已作废';
					}
	            }}
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
	
	showLoading();
	$.messager.confirm('确认', '您确认想要作废吗？', function(r) {
		if (r) {
			$.post('/rest/newUserReward/delete',{"id":rows[0].id},function(info){
				closeLoading();
				if (info.code == 200) {
					$.messager.alert('提示', '操作成功', 'info');
					$("#dataGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
		}
	});
}

function search() {
	dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
	$("#DivAdd").dialog('setTitle','添加');
}
function edit(){
	var rows = $("#dataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return;
	}
	if(rows[0].status == 1){
		$.messager.alert("提示", "已发布，不能编辑", "error");
		return;
	}
	if(rows[0].status == 2){
		$.messager.alert("提示", "已作废，不能编辑", "error");
		return;
	}
	$("#DivEdit").dialog('setTitle','编辑消息');
	$("#DivEdit").dialog('open');
	
	$("#_proPic").filebox('setValue','');
	$("#_indexPic").filebox('setValue','');
	$("#id").val(rows[0].id);
	$("#_markedWords").textbox("setValue",rows[0].markedWords);
}

function save(){
	if($("#markedWords").val() == ''){
		$.messager.alert("提示", "请输入标题", "error");
		return;
	}
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/newUserReward/saveOrEdit',
		ajax:true,
		success:function(data){
			closeLoading();
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
}

function saveEdit(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/newUserReward/saveOrEdit',
		ajax:true,
		success:function(data){
			closeLoading();
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivEdit").dialog('close');
				dataGrid.datagrid('reload');
			}
			else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
}
function publish(){
	var rows = $("#dataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return;
	}
	if(rows[0].status == 1){
		$.messager.alert("提示", "已发布，不能重复发布", "error");
		return;
	}
	if(rows[0].status == 2){
		$.messager.alert("提示", "已作废，不能发布", "error");
		return;
	}
	showLoading();
	
	$.messager.confirm('确认', '您确认要发布吗？', function(r) {
		if(r){
			$.post('/rest/newUserReward/publish',{"id":rows[0].id},function(info){
				closeLoading();
				if(info.code == 200){
					$.messager.alert("提示", "操作成功", "info");
					dataGrid.datagrid('reload');
				}else{
					$.messager.alert("提示", info.msg, "error");
				}
			},'json');
		}
	});
	
}
function clearFile(field){
	$("#" + field).filebox('setValue','');
}

function verifyImg(filed){
	alert(field)
	var name = $("#" + field).filebox('getValue');
	alert(name)
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
						<td>提示语：</td>
						<td><input class="easyui-textbox" name="markedWords" id="title"
							style="width: 200px" data-options="prompt:'请输入提示语',
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
							    <option value="0">初始化</option>   
							    <option value="1">已发布</option>   
							    <option value="2">已作废</option> 
							</select>  
						</td>
						<td><a href="javascript:search()" class="easyui-linkbutton"
							iconcls="icon-search">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div id="tb" data-options="region:'center'"
			style="padding: 5px; height: auto">
			<table id="dataGrid" data-options="fit:true,border:false"></table>
		</div>
	</div>
	<!-- 添加弹出层 -->
	<div id="DivAdd" class="easyui-dialog"
		style="width: 620px; height: 220px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons'">
		<form id="ffAdd" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<table id="tblAdd" class="view">
				<tr>
					<td >提示语：</td>
					<td><input class="easyui-textbox" name="markedWords" id="markedWords"
						style="width: 400px"
						data-options="prompt:'请输入标题',
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#markedWords').textbox('setValue', '');
									}
								}]" /></td>
					
				</tr>
				<tr>
					<td>产品详情中应用到的图标URL：</td>
					<td>
						<input class="easyui-filebox" data-options="buttonText:'请选择图片文件'" id="proPic" name="proPic"  style="width:355px">
						<a href="javascript:clearFile('proPic')">清空</a>
					</td>
					
				</tr>
				<tr>
					<td>首页中新手福利的图标URL：</td>
					<td>
						<input  class="easyui-filebox" data-options="buttonText:'请选择图片文件'" id="indexPic" name="indexPic" style="width:355px">
						<a href="javascript:clearFile('indexPic')">清空</a>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-ok" onclick="save()">确定</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-cancel"
						onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 编辑弹出层 -->
	<div id="DivEdit" class="easyui-dialog"
		style="width: 620px; height: 220px; padding: 10px 20px" closed="true"
		resizable="true" modal="true"
		data-options="buttons: '#dlg-buttons'">
		<form id="ffEdit" method="post" novalidate="novalidate"
			enctype="multipart/form-data">
			<input type="hidden" name="id" id="id">
			<table id="tblEdit" class="view">
				<tr>
					<td >提示语：</td>
					<td><input class="easyui-textbox" name="markedWords" id="_markedWords"
						style="width: 400px"
						data-options="prompt:'请输入标题',
					            icons: [{
									iconCls:'icon-remove',
									handler: function(e){
										$('#_markedWords').textbox('setValue', '');
									}
								}]" /></td>
					
				</tr>
				<tr>
					<td>产品详情中应用到的图标URL：</td>
					<td>
						<input  class="easyui-filebox" data-options="buttonText:'请选择图片文件'" id="_proPic" name="proPic" style="width:355px">
						<a href="javascript:clearFile('_proPic')">清空</a>
					</td>
					
				</tr>
				<tr>
					<td>首页中新手福利的图标URL：</td>
					<td>
						<input  class="easyui-filebox" data-options="buttonText:'请选择图片文件'" id="_indexPic" name="indexPic" style="width:355px">
						<a href="javascript:clearFile('_indexPic')">清空</a>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" style="text-align: right; padding-top: 10px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
						iconcls="icon-ok" onclick="saveEdit()">确定</a> <a
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