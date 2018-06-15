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
	    	$.getJSON("/rest/product/category/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

//保存
function save(){
	showLoading();
	$("#agName").val($('#agId').combogrid('getText'));
	$("#acode").val('code');
	$('#ffAdd').form('submit',{
		url: '/rest/product/category/save',
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
function formatStatus(value){
    if(value==0)
        return '未生效';
    else if(value==1)
        return '<font color="green">已生效</font>';
}
function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if (rows[0].status==1) {
		$.messager.alert("提示", "已生效记录不能修改", "error");
		return
	}
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	if(rows[0].type==0){
		$("#efixTypeTr").show();
	}
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	$("#egName").val($('#egId').combogrid('getText'));
	$("#ecode").val('code');
	$('#ffEdit').form('submit',{
		url: '/rest/product/category/update',
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
		name:$("#categoryName").val()
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
/**
 * 显示和隐藏固定子类型
 * @param trId
 * @param id
 * @param newValue
 * @param oldValue
 * @param isEdit
 */
function showAndHideFixType(trId,id,newValue,oldValue,isEdit){
	if(newValue && newValue == 0){
		$("#"+trId).show();
		if(isEdit){
			var rows = $("#adGrid").datagrid("getSelections");
			if(rows[0].fixType && rows[0].fixType!=0){
				$('#'+id).combobox('setValue', rows[0].fixType);
				return;
			}
		}
		$('#'+id).combobox('setValue', '1');
	}else{
		$("#"+trId).hide();
		$('#'+id).combobox('disableValidation');
		return ;
	}
	
}
