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
	text : '发布',
	iconCls : 'icon-redo',
	handler : function() {
		publish();
	}
}, '-', {
	text : '使用',
	iconCls : 'icon-cut',
	handler : function() {
		use();
	}
}, '-', {
	text : '作废',
	iconCls : 'icon-cancel',
	handler : function() {
		cancel();
	}
}];


function formatTimer(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd");
	}
	return "";
}

function formatStatus(value, row) {
	if (value == 0) {
		return "初始";
	} else if (value == 1) {
		return "<font color='blue'>已发布</font>";
	} else if (value == 2) {
		return "<font color='red'>已使用</font>";
	} else if (value == 3) {
		return "<font color='green'>已作废</font>";
	}
}

// 使用
function use() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	if(rows[0].status!=1){
		$.messager.alert("提示", "只有已发布状态可以修改为使用状态", "error");
		return
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要修改为使用状态吗？', function(r) {
		if (r) {
			$.getJSON("/rest/takeheart/category/use?r=" + Math.random() + "&id=" + rows[0].id, function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '修改成功', 'info');
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
// 作废
function cancel() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	if(rows[0].status==3){
		$.messager.alert("提示", "已作废", "error");
		return
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要修改为作废状态吗？', function(r) {
		if (r) {
			$.getJSON("/rest/takeheart/category/cancel?r=" + Math.random() + "&id=" + rows[0].id, function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '修改成功', 'info');
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

function save() {
	showLoading();
	$('#ffAdd').form('submit', {
		url : '/rest/takeheart/category/save',
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

function edit() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	if(rows[0].status!=0){
		$.messager.alert("提示", "只有初始状态可以修改", "error");
		return
	}
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load', rows[0]);
	$("#DivEdit").dialog('open');
}

function update() {
	showLoading();
	$('#ffEdit').form('submit', {
		url : '/rest/takeheart/category/update',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}


function publish() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	if (rows[0].status !=0) {
		$.messager.alert("提示", "只有初始状态可以发布", "error");
		return
	}
	$("#ffPublish").form('clear');
	$("#ffPublish").form('load', rows[0]);
	$("#DivPublish").dialog('open');
}

function pub() {
	showLoading();
	$('#ffPublish').form('submit', {
		url : '/rest/takeheart/category/pub',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				$("#DivPublish").dialog('close');
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
		name : $("#sname").val()
	});
}
