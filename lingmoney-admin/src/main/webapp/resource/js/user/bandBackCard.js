var toolbar = [ {
	/*text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}, '-', {*/
	text : '解除绑定',
	iconCls : 'icon-remove',
	handler : function() {
		publish();
	}
}
, '-', {
	text : '添加',
	iconCls : 'icon-remove',
	handler : function() {
		add();
	}
}
, '-', {
	text : '刷新',
	iconCls : 'icon-remove',
	handler : function() {
		flush();
	}
}];
// 添加窗口的显示
function add(){
	
	$('#ffEdit').form('clear');
	
	$("#DivEdit").dialog('open');
}


function formatStatus(value){
	//alert(value);
	if(value == 0){
		return "未解绑";
	}
	else if(value == 1){
		return "<font color='green'>已解绑</font>";
	}
	else if(value == 2){
		return "<font color='green'>解绑中</font>";
	}
	else{
		return "<font color='red'>未知</font>";
	}
}
function formatTimer(value){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
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
			$.getJSON("/rest/user/unbandbankcard/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
	if(rows[0].status==2){
		 $.messager.alert("提示", "解除绑定中", "error");
		 return
	}
	
	if(rows[0].status==1){
		 $.messager.alert("提示", "已解除绑定", "error");
		 return
	}
	
	showLoading();
	$.getJSON("/rest/user/unbandbankcard/publish?r=" + Math.random() + "&id=" + rows[0].id+"&uId="+rows[0].uId,
	    function(info) {
	       if(info.code==200){
	    	   $.messager.alert('提示', '操作成功', 'info');
	    	   $("#adGrid").datagrid('reload');
	       }else{
	    	   $.messager.alert('错误提示', info.msg, 'error');
	       }
	});
	closeLoading();
}

// 保存
function save(){
	
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/user/unbandbankcard/save',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			}else if(json.code == 201){
				$.messager.alert('错误提示', json.msg, 'error');
			}else if(json.code == 202){
				$.messager.alert('错误提示', json.msg, 'error');
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
		url: '/rest/user/unbandbankcard/update',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			}
			else{
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
		telephone:$("#telephone").val()
	});
}


//保存
function flush(){
	
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/user/unbandbankcard/publishList',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			}else if(json.code == 201){
				$.messager.alert('错误提示', json.msg, 'error');
			}else if(json.code == 202){
				$.messager.alert('错误提示', json.msg, 'error');
			}
			
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}


