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
}, '-', {
	text : '显示状态',
	iconCls : 'icon-ok',
	handler : function() {
		changeStatus();
	}
}];

function formatStatus(value,row){
	if(value == 0){
		return "<font color='green'>显示</font>";
	}else if(value == 1){
		return "不显示";
	}
}

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
			$.getJSON("/rest/info/rechargeLimit/delete?r=" + Math.random() + "&id=" + rows[0].id, function(info) {
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
function changeStatus(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/info/rechargeLimit/changeStatus?r=" + Math.random() + "&id=" + rows[0].id+ "&status=" + rows[0].status,
	    function(info) {
	       if(info.code==200){
	    	   $.messager.alert('提示', '修改成功', 'info');
	    	   $("#adGrid").datagrid('reload');
	       }else{
	    	   $.messager.alert('错误提示', info.msg, 'error');
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
	$("#bankLogo").attr("src",rows[0].bankLogo);
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load', rows[0]);
	$("#DivEdit").dialog('open');
}
function save() {
	showLoading();
	$('#ffAdd').form('submit', {
		url : '/rest/info/rechargeLimit/save',
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
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/info/rechargeLimit/update',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			}else{
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
		bankName : $("#bankName").val()
	});
}
