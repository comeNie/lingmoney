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
	text : '批量启用',
	iconCls : 'icon-ok',
	handler : function() {
		updateStatus(1);
	}
}, '-', {
	text : '批量禁用',
	iconCls : 'icon-remove',
	handler : function() {
		updateStatus(0);
	}
}];

//添加窗口的显示
function add(){
	$("#accId").combogrid('readonly', false);
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}

function formatStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
	}
}

// 保存和修改
function saveOrUpdate(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/uiStyle/saveOrUpdate',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$.messager.alert('提示', '操作成功');
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			}else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	
	$("#ffAdd").form('clear');
	$("#ffAdd").form('load',rows[0]);
	
	$("#DivAdd").dialog('open');
}

function updateStatus(status) {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	
	$.messager.confirm('提示', '确认要更新？', function (b) {
		if (b) {
			var ids = '';
			for (var i = 0 ; i < rows.length; i++) {
				ids += rows[i].id + ",";
			}
			
			ids = ids.substr(0, ids.lastIndexOf(','));
			
			$.ajax({
				url : '/rest/uiStyle/batchUpdateStatus',
				type : 'post',
				data : {ids : ids, status : status},
				success : function (data) {
					$.messager.alert('提示', data.msg);
					if (data.code == 200) {
						$("#adGrid").datagrid("reload");
					}
				}
			});
		}
	});
}

// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page : 1,
		row : 30,
		batchNo : $("#batchNo").textbox("getValue"),
		desc : $("#desc").textbox("getValue"),
		status : $("#status").combobox("getValue")
	});
}

