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
 
function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd");
	}else{
		return "";
	}
}
function formatTimers(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		walletDt:$('#walletDate').datebox('getValue')
	});
	
}
//添加
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}

//添加后保存
function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/wallet/constraint/save',
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
//修改
function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffEdit").form('clear');
	if(rows[0].walletDt){
		rows[0].walletDt = formatTimer(new Date(rows[0].walletDt));
	}
	$("#ffEdit").form('load',rows[0]);
	$("#DivEdit").dialog('open');
}
//修改后保存
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/wallet/constraint/update',
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
	    	$.getJSON("/rest/wallet/constraint/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

$(function(){
	$('#awalletDt').datebox().datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1<=date;
		}
	});
	$('#ewalletDt').datebox().datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1<=date;
		}
	});
});



