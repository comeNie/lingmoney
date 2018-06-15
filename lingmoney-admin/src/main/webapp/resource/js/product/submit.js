var toolbar = [ {
	text : '修改',
	iconCls : 'icon-edit',
	handler : function() {
		edit();
	}
}];
function formatTimer(value,row){
	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
}
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}



function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$('#eopTime').datetimebox('setValue',formatTimer(new Date()));
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	var opTimeStr = $('#eopTime').datetimebox('getValue');
	$('#eop').val(new Date(opTimeStr));
	$('#ffEdit').form('submit',{
		url: '/rest/product/submit/update',
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

//查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		type:$('#type').combogrid('getValue')
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

//初始化dialog
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
}

