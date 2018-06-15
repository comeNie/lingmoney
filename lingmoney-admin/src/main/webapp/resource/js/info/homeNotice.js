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
	iconCls : 'icon-remove',
	handler : function() {
		publish();
	}
}];

function reload(){
	showLoading();
	$.getJSON("/rest/info/homeNotice/reload?r=" + Math.random(),
	    function(info) {
	       if(info.code==200){
	    	   $("#adGrid").datagrid('reload');
	       }else{
	    	   $.messager.alert('错误提示', info.message, 'error');
	       }
	});
	closeLoading();
}
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
	$.messager.confirm('确认', '您确认想要删除该项吗？', function(r) {
		if (r) {
			$.getJSON("/rest/info/homeNotice/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
	$.getJSON("/rest/info/homeNotice/publish?r=" + Math.random() + "&id=" + rows[0].id,
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
		url: '/rest/info/homeNotice/save',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				KindEditor.remove('#acontent');
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
	KindEditor.remove('#econtent');
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',rows[0]);
	var editor = createKindEditor("#econtent");
	$.getJSON("/rest/info/homeNotice/listContent?r=" + Math.random() + "&id=" + rows[0].id,
		    function(info) {
		       if(info.code==200){
		    	   editor.html(info.msg)
		       }else{
		    	   $.messager.alert('错误提示', info.msg, 'error');
		       }
		});
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/info/homeNotice/update',
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
