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
	    	$.getJSON("/rest/product/categoryFixRate/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
function displayPcId(value,row){
	return value;
}
//保存
function save(){

	showLoading();
	$("#apName").val($("#apcId").combogrid("getText"));	
	$("#apId").val($("#apcId").combogrid("getValue"));
	$('#ffAdd').form('submit',{
		url: '/rest/product/categoryFixRate/save',
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
	//$("#epcId").val(rows[0].pId);
	//alert($("#epcId").val()+"name");
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$("#epcId").combogrid("setValue",rows[0].pId);
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	$("#epName").val($("#epcId").combogrid("getText"));	
	$("#epId").val($("#epcId").combogrid("getValue"));		
	$('#ffEdit').form('submit',{
		url: '/rest/product/categoryFixRate/update',
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
	//alert($('#categoryId').combogrid('getValue'));
	$('#adGrid').datagrid('load',{
		page:1,
		pId:$('#spId').combogrid('getValue')
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

