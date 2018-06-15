var toolbar = [{
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
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}];



// 删除
function del() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	
	showLoading();
	$.messager.confirm('确认', '您确认想要删除吗？', function(r) {
		if (r) {
			$.getJSON("/rest/takeheart/fixRate/delete?r=" + Math.random() + "&id=" + rows[0].id, function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '删除成功', 'info');
					$("#adGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
		} else {
			return
		}
	});
	closeLoading();
}

function add() {
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}
function edit() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$("#ffAdd").form('clear');
	$("#ffAdd").form('load', rows[0]);
	$("#DivAdd").dialog('open');
}
function saveAndUpdate() {
	$("#cName").val($("#cId").combogrid("getText"));
	showLoading();
	$('#ffAdd').form('submit', {
		url : '/rest/takeheart/fixRate/saveAndUpdate',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

// 查询
function search() {
	$('#adGrid').datagrid('load', {
		page : 1,
		cId : $("#scId").combogrid("getValue")
	});
}
