var toolbar = [ {
	text : '导入限制手机号',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
}, '-', {
	text : '更改状态',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}];


// 更改状态
function publish() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.getJSON(
			"/rest/festival/winningCount/publish?r=" + Math.random() + "&id=" + rows[0].id,
			function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '设置成功', 'info');
					$("#adGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
	closeLoading();
}

// 添加窗口的显示
function add(){
	$('#ffRegist').form('clear');
	$("#DivRegist").dialog('open');
}


//保存
function upload(){
	showLoading();
	$('#ffRegist').form('submit',{
		url: '/rest/festival/winningCount/upload',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivRegist").dialog('close');
				$("#adGrid").datagrid('reload');
			}else{
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
		telephone:$("#telephone").val(),
		status:$("#status").combogrid('getValue')
	});
}

