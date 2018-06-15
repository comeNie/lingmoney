var toolbar = [ {
	text : '赎回',
	iconCls : 'icon-add',
	handler : function() {
		sell();
	}
}];
// 赎回
function sell(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要赎回吗？',function(r){    
	    if (r){
			$.getJSON("/rest/trade/trading/sell?r=" + Math.random() + "&tid=" + rows[0].id+ "&uid=" + rows[0].uId,
				    function(info) {
				       if(info.code==200){
				    	   $.messager.alert('提示', '赎回成功', 'info');
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
function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}
function formatAutoPay(value){
	if(value == 0){
		return "否";
	}
	else if(value == 1){
		return "<font color='green'>是</font>";
	}
	else{
		return "<font color='red'>未知</font>";
	}
}


function formatInvest(value){
	if(value == 0){
		return "非定投";
	}
	else if(value == 1){
		return "<font color='green'>周周投</font>";
	}else if(value==2){
		return "<font color='green'>半月投</font>";
	}else if(value==3){
		return "<font color='green'>月月投</font>";	
	}
	else{
		return "<font color='red'>未知</font>";
	}
}
function formatTypes(value){
	if(value == 0){
		return "固定收益类";
	}
	else if(value == 1){
		return "浮动类";
	}
	else{
		return "未知";
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
	$.messager.confirm('确认','您确认想要删除吗？',function(r){    
	    if (r){
			$.getJSON("/rest/product/recommend/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
		url: '/rest/product/recommend/update',
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
		proId:$("#proId").combogrid('getValue')
	});
}


