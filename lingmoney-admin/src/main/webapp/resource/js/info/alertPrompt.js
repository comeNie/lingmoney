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
	text : '更改状态',
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

function publish() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$("#ffStatus").form('clear');
	$("#ffStatus").form('load',rows[0]);
	$("#DivStatus").dialog('open');
}

function changeStatus() {
	showLoading();
	$('#ffStatus').form('submit',{
		url: '/rest/info/alertPrompt/changeStatus',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivStatus").dialog('close');
				$("#adGrid").datagrid('reload');
			}
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
function formatStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
	} else if (value == 2) {
		return "<font color='black'>开户激活不可用</font>";
	}
}
function formatButtonType(value, row) {
	if (value == 0) {
		return "<font color='red'>关闭</font>";
	} else if (value == 1) {
		return "<font color='blue'>跳转页面</font>";
	}
}
function formatJumpType(value, row) {
	if (value == 0) {
		return "<font color='red'>activity</font>";
	} else if (value == 1) {
		return "<font color='blue'>fragment</font>";
	} else if (value == 2) {
		return "<font color='black'>webview</font>";
	}
}
function formatAlertType(value, row) {
	if (value == 0) {
		return "<font color='red'>开户</font>";
	} else if (value == 1) {
		return "<font color='blue'>激活</font>";
	} else if (value == 2) {
		return "<font color='black'>普通</font>";
	}
}


// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}
// 删除
function del(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){
			$.getJSON("/rest/info/alertPrompt/delete?r=" + Math.random() + "&id=" + rows[0].id,
			    function(info) {
			       if(info.code==200){
			    	   $.messager.alert('提示', '删除成功', 'info');
			    	   $("#adGrid").datagrid('reload');
			       }else{
			    	   $.messager.alert('错误提示', info.message, 'error');
			       }
			});
	    }else{
	    	return
	    }
	  });
	closeLoading();
}

// 保存
function saveAndUpdate(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/info/alertPrompt/saveAndUpdate',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivAdd").dialog('close');
				$("#adGrid").datagrid('reload');
			}
			else
			{
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
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/info/alertPrompt/saveAndUpdate',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			}
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		name:$("#name").val(),
		status:$("#status").combogrid('getValue')
	});
}
