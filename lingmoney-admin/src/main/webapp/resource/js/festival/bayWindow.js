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
	iconCls : 'icon-cancel',
	handler : function() {
		del();
	}
}, '-', {
	text : '更改状态',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}];


//格式化图片
function formatPicture(value,row,index){
	if('' != value && null != value){
		value = '<img style="width:46px; height:46px; margin-top:5px;" src="' + value + '">';
	  	return  value;
	}
}

// 更改状态
function publish() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	showLoading();
	$.getJSON(
			"/rest/festival/bayWindow/publish?r=" + Math.random() + "&id=" + rows[0].id,
			function(info) {
				if (info.code == 200) {
					$.messager.alert('提示', '设置成功', 'info');
					$("#adGrid").datagrid('reload');
				} else {
					$.messager.alert('错误提示', info.msg, 'error');
				}
			});
	closeLoading();
}

// 添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
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
			$.getJSON("/rest/festival/bayWindow/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
function saveAndUpdate(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/festival/bayWindow/saveAndUpdate',
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

/**
 * 格式化时间
 * 
 * @param value
 * @param row
 * @returns
 */
function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}

// 修改窗口
function edit(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffAdd").form('clear');
	if(rows[0].startTime){
		rows[0].startTime = formatTimer(new Date(rows[0].startTime));
	}
	if(rows[0].endTime){
		rows[0].endTime = formatTimer(new Date(rows[0].endTime));
	}
	$("#ffAdd").form('load',rows[0]);
	$("#DivAdd").dialog('open');
}

// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		title:$("#title").val(),
		status:$("#status").combogrid('getValue')
	});
}

