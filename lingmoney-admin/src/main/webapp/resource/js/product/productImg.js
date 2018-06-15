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
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}, '-', {
	text : '更改状态',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}];
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}
//删除
function del(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/product/image/delete?r=" + Math.random() + "&id=" + rows[0].id,
    		    function(info) {
    		       if(info.code==200){
    		    	   $.messager.alert('提示', '删除成功', 'info');
    		    	   $("#adGrid").datagrid('reload');
    		       }else{
    		    	   $.messager.alert('错误提示', info.msg, 'error');
    		       }
    		});    
	    }else{
	    	return
	    }   
	});
	closeLoading();
}

//发布
function publish(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/product/image/publish?r=" + Math.random() + "&id=" + rows[0].id,
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

//保存
function saveAndUpdate(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/product/image/saveAndUpdate',
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
function formatStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
	}
}
function formatType(value, row) {
	if (!value || value == 0) {
		return "<font color='black'>WEB端我的理财产品图片</font>";
	} else if (value == 1) {
		return "<font color='blue'>Android产品背景图</font>";
	}else if (value == 2) {
		return "<font color='green'>Nav产品背景图</font>";
	}else if (value == 3) {
		return "<font color='red'>iOS产品背景图</font>";
	}
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

//查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		name:$("#pName").val(),
		status:$("#status").combogrid('getValue'),
		type:$("#type").combogrid('getValue')
	});
}

function getChecked(){
    var nodes = $('#menuTree').tree('getChecked',['checked','indeterminate']);
    var s = '';
    for(var i=0; i<nodes.length; i++){
        if (s != '') s += ',';
        s += nodes[i].id;
    }
    return s;
}

/*//初始化dialog
function initDialogSetting(title){
	$('#DivSet').dialog({
	    title: title,
	    iconCls:'icon-setting',
	    modal: true,
	    toolbar:[{
			text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#menuTree').tree('reload');}
		},'-',{
			text:'展开',
			iconCls:'icon-expand',
			handler:function(){$('#menuTree').tree('expandAll');}
		},'-',{
			text:'折叠',
			iconCls:'icon-collapse',
			handler:function(){$('#menuTree').tree('collapseAll');}
		}],
		buttons:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){doSetting();}
		}]
	});
	$('#DivSet').dialog('open');
}*/
