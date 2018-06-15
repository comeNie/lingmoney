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
	text : '刷新',
	iconCls : 'icon-reload',
	handler : function() {
		reload();
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
	showLoading();
	$.getJSON("/rest/base/banner/publish?r=" + Math.random() + "&id=" + rows[0].id,
			function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '修改成功', 'info');
					$("#adGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
	closeLoading();
}
function formatStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
	}
}
function reload(){
	showLoading();
	$.getJSON("/rest/base/banner/reload?r=" + Math.random(),
	    function(info) {
	       if(info.code==200){
	    	   $("#adGrid").datagrid('reload');
	       }else{
	    	   $.messager.alert('错误提示', info.message, 'error');
	       }
	});
	closeLoading();
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
			$.getJSON("/rest/base/banner/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/base/banner/upload',
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
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/base/banner/update',
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
		name:$("#bannername").val(),
		status:$("#status").combogrid('getValue')
	});
}

function getChecked(){
    var nodes = $('#menuTree').tree('getChecked',['checked','indeterminate']);
    var s = '';
    for(var i=0; i<nodes.length; i++){
        if (s != '') s += ',';
        s += nodes[i].id;
    }
    alert(s);
}


function doSetting(){
	
}