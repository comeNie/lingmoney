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
// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#acontent").val("");
	createKindEditor("#acontent");
	$("#DivAdd").dialog('open');
}
function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}

function formatType(value,row){
	if(value == 0){
		return "展示所有产品";
	}
	else if(value == 1){
		return "<font color='green'>展示指定产品</font>";
	}
	else{
		return "<font color='red'>未知</font>";
	}
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
	$.getJSON("/rest/product/giftProduct/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

// 保存
function save(){
	
	var grid=$("#apId").combogrid("grid");//获取表格对象 
	var row = grid.datagrid('getSelected');//获取行数据 	  
	$("#apName").val(row.name);
	
	var grid1=$("#agId").combogrid("grid");//获取表格对象 
	var row1 = grid1.datagrid('getSelected');//获取行数据 	  
	$("#agName").val(row1.gName);
	
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/product/giftProduct/save',
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
	var editor = createKindEditor("#econtent");
	editor.html(rows[0].gDesc);
	createKindEditor("#acontent");
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$("#DivEdit").dialog('open');
}
function update(){
	
	var grid1=$("#egId").combogrid("grid");//获取表格对象 
	var row1 = grid1.datagrid('getSelected');//获取行数据 	  
	$("#egName").val(row1.gName);
	
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/product/giftProduct/update',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#econtent").val("");
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
		pName:$("#productName").val()
	});
}

function createKindEditor(contentId){
	var editor= KindEditor.create(contentId, {
		allowFileManager : false,
		newlineTag:"br",
        uploadJson : '/rest/info/uploadImg',
        afterChange: function () {
            this.sync();
        },
        afterBlur: function () { this.sync(); }
	});
	return editor;
}



