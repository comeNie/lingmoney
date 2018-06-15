var toolbar = [ {
	text : '查看消息',
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
	text : '修改阅读状态',
	iconCls : 'icon-ok',
	handler : function() {
		reader();
	}
}];
// 添加窗口的显示
function add(){
	
	$('#ffAdd').form('clear');
	
	$("#DivAdd").dialog('open');
}


function formatStatus(value){
	if(value == 0){
		return "未读";
	}
	else if(value == 1){
		return "<font color='green'>已读</font>";
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
			$.getJSON("/rest/user/usermessage/delete?r=" + Math.random() + "&id=" + rows[0].id,
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


function reader(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	showLoading();
	$.getJSON("/rest/user/usermessage/reader?r=" + Math.random() + "&id=" + rows[0].id,
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
	
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/info/cartooncategory/save',
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
	KindEditor.remove('#acontent');
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffEdit").form('clear');
	if(rows[0].time){
		rows[0].time = formatTimer(new Date(rows[0].time));
	}
	if(rows[0].status==0){
		rows[0].status='未读';
	}else if(rows[0].status==1){
		rows[0].status='已读';
	}else{
		rows[0].status='未知';
	}
	var editor = createKindEditor("#acontent");
	$.getJSON("/rest/user/usermessage/listContent?r=" + Math.random() + "&id=" + rows[0].id,
		    function(info) {
		       if(info.code==200){
		    	   editor.html(info.msg)
		       }else{
		    	   $.messager.alert('错误提示', info.msg, 'error');
		       }
		});
	$("#ffEdit").form('load',rows[0]);
	$("#DivEdit").dialog('open');
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


// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		nameCom:$("#nameCom").val()
	});
}

