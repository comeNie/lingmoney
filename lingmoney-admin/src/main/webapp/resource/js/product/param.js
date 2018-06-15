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

var toolbarHoliday = [ {
	text : '添加',
	iconCls : 'icon-add',
	handler : function() {
		addHoliday();
	}
}, '-', {
	text : '修改',
	iconCls : 'icon-edit',
	handler : function() {
		editHoliday();
	}
}, '-', {
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		delHoliday();
	}
}];


//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#atype").val("product_group");
	$("#DivAdd").dialog('open');
}

function addHoliday(){
	$('#ffAddHoliday').form('clear');
	$("#hatype").val("Holiday");
	$("#DivAddHoliday").dialog('open');
}
function delHoliday(){
	var rows = $("#adHolidayGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){
			$.getJSON("/rest/product/param/delete?r=" + Math.random() + "&id=" + rows[0].id,
			    function(info) {
			       if(info.code==200){
			    	   $.messager.alert('提示', '删除成功', 'info');
			    	   $("#adHolidayGrid").datagrid('reload');
			       }else{
			    	   $.messager.alert('错误提示', info.msg, 'error');
			       }
			});
	    }else{
	    	return;
	    }
	});
	closeLoading();
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
			$.getJSON("/rest/product/param/delete?r=" + Math.random() + "&id=" + rows[0].id,
			    function(info) {
			       if(info.code==200){
			    	   $.messager.alert('提示', '删除成功', 'info');
			    	   $("#adGrid").datagrid('reload');
			       }else{
			    	   $.messager.alert('错误提示', info.msg, 'error');
			       }
			});
	    }else{
	    	return;
	    }
	});
	closeLoading();
}
//保存
function saveHoliday(){
	showLoading();
	$('#ffAddHoliday').form('submit',{
		url: '/rest/product/param/save',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivAddHoliday").dialog('close');
				$("#adHolidayGrid").datagrid('reload');
			}
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
//保存
function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/product/param/save',
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

function editHoliday(){
	var rows = $("#adHolidayGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#DivEditHoliday").form('clear');
	$("#ffEditHoliday").form('load',rows[0]);
	$("#DivEditHoliday").dialog('open');
}
function updateHoliday(){
	showLoading();
	$('#ffEditHoliday').form('submit',{
		url: '/rest/product/param/update',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivEditHoliday").dialog('close');
				$("#adHolidayGrid").datagrid('reload');
			}
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/product/param/update',
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

