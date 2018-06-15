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
},'-', {
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}];
// 添加窗口的显示
function add(){
	
	$('#ffAdd').form('clear');
	
	$("#DivAdd").dialog('open');
}
function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}

function formatStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
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
			$.getJSON("/rest/info/content/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

function publish(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/info/content/publish?r=" + Math.random() + "&id=" + rows[0].id,
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

// 保存
function save(){
	//$("#apubDate").val(new Date());
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/info/content/save',
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
	$("#contentPic").attr("src",rows[0].contentPic);
	$("#themePic").attr("src",rows[0].themePic);
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/info/content/update',
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
	$("#cId").val($("#cartoonName").combogrid('getValue'));
	$('#adGrid').datagrid('load',{
		page:1,
		cId:$("#cId").val(),
		status:$("#status").combogrid('getValue')
	});
}

