var toolbar = [{
	text : '批量修改',
	iconCls : 'icon-edit',
	handler : function() {
		bachUpdate();
	}
}/*, '-', {
	text : '修改',
	iconCls : 'icon-edit',
	handler : function() {
		edit();
		
		}*/
];
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

function formatStatus(value,row){
	if(value == 0){
		return "<font color='orange'>未生效</font>";
	}
	else if(value == 1){
		return "<font color='blue'>礼品冻结</font>";
	}else if(value == 2){
		return "<font color='green'>已发送</font>";
	}else if(value == 3){
		return "<font color='#00b7ee'>未发送</font>";
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
	$.getJSON("/rest/product/giftDetail/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
	$.getJSON("/rest/product/giftDetail/publish?r=" + Math.random() + "&id=" + rows[0].id,
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
		url: '/rest/product/giftDetail/save',
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
	$('#econtent').val($("#egDesc").val());
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$("#DivEdit").dialog('open');
}

function bachUpdate(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请至少选择一条记录", "error");
	    return
	}
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	$("#DivEdit").dialog('open');
}
function update(){
	var rows = $("#dg").datagrid("getSelections");
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/product/gift/update',
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



