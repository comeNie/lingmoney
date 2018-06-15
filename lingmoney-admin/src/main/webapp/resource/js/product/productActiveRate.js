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
	text : '发布',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
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

function formatStatus(value,row){
	if(value == 0){
		return "未发布";
	}
	else if(value == 1){
		return "<font color='green'>已发布</font>";
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
	$.getJSON("/rest/product/productActiveRate/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
	if(rows[0].status==1){
		 $.messager.alert("提示", "已经发布", "error");
		 return
	}
	showLoading();
	$.getJSON("/rest/product/productActiveRate/publish?r=" + Math.random() + "&id=" + rows[0].id,
	    function(info) {
	       if(info.code==200){
	    	   $.messager.alert('提示', '发布成功', 'info');
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
	$('#ffAdd').form('submit',{
		url: '/rest/product/productActiveRate/save',
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
	alert(1);
	
	alert(2);
	$.getJSON("/rest/product/productActiveRate/update?r=" + Math.random() + "&id=" + rows[0].id,
		    function(info) {
		       if(info.code==200){
		    	   editor.html(info.msg)
		       }else{
		    	   $.messager.alert('错误提示', info.msg, 'error');
		       }
		});
	$("#DivEdit").dialog('open');
}


function edit(){
	
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if(rows[0].startDate){
	rows[0].startDate = formatTimer(new Date(rows[0].startDate));
   }
    if(rows[0].endDate){
	rows[0].endDate = formatTimer(new Date(rows[0].endDate));
   }
   if(rows[0].createTime){
	rows[0].createTime = formatTimer(new Date(rows[0].createTime));
   }
   $("#ffEdit").form('load',rows[0]);
   $("#DivEdit").dialog('open');
}

function update(){
	var rows = $("#adGrid").datagrid("getSelections");
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/product/productActiveRate/update',
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
		gName:$("#giftName").val()
	});
}


function sendMessage(){
	var rows = $("#adGrid").datagrid("getSelections");
	var idStr = ""; 
	for(var i=0; i<rows.length; i++){ 
		if(i<rows.length-1) {
			idStr+=rows[i].id+','; //如果选中，将value添加到变量s中 
			
		} else{
			idStr+=rows[i].id;
			// break; 
	   }
	}
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/product/giftDetail/updateStatus?str='+idStr,
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

$(function(){
	//添加
	$('#aendDate').datebox().datebox('calendar').calendar({
		validator: function(date){
			var now = new Date(formatTimer($("#astartDate").datetimebox('getValue')));
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1<=date;
		}
	});
	//修改
	if($("#estartDate").datetimebox('getValue')==$("#astartDate").datetimebox('getValue')){
		
	}
	$('#eendDate').datebox().datebox('calendar').calendar({
		validator: function(date){
			var now = new Date(formatTimer($("#estartDate").datetimebox('getValue')));
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1<=date;
		}
	});
	
});

