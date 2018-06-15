var imageUrl="";
$(function(){
	$.ajax({  
	     type : "POST",
	     url : '/rest/info/clientVersion/showImageUrl',
	     async : false,
	     success : function(data) { 
	    	 if(data.code == 200){
	    		 imageUrl = data.msg;
	    		 $("#imageUrl").text(imageUrl);
		    	 $("#imageUrl").attr("href",imageUrl);
	    	 }
	     }  
	 });
});
// 上传新的版本图片
function imageUpload(){
	$('#settingForm').form('submit',{
		url: '/rest/info/clientVersion/imageUpload',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				imageUrl = json.msg;
	    		$("#imageUrl").text(imageUrl);
		    	$("#imageUrl").attr("href",imageUrl);
		    	$("#imageUpload").filebox("setValue","");
		    	$.messager.alert("提示", "上传成功", "info");
			}else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
// 禁用、开通版本更新图片
function imageUrlAllow(status){
	if(imageUrl == ''){
		$.messager.alert("提示", "版本更新图片为空", "info");
		return;
	}
	var msg = status==0?"确定要禁用版本更新图片吗?":"确定要开通版本更新图片吗?";
	$.messager.confirm('确认', msg, function(r) {
		if (r) {
			$.ajax({  
			     type : "POST",
			     url : '/rest/info/clientVersion/imageUrlAllow',
			     data: {
			    	 status:status
			     }, 
			     success : function(data) { 
			    	 if(data.code == 200){
			    		 $.messager.alert("提示", data.msg, "info");
			    	 }else{
			    		 $.messager.alert("提示", data.msg, "error");
			    	 }
			     }  
			 });
		}});
}

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
	if(value == 1){
		return "不可用";
	}else if(value == 0){
		return "<font color='green'>可用</font>";
	}
}

function formatType(value,row){
	if(value == 0){
		return "安卓";
	}else if(value == 1){
		return "苹果";
	}
}
function formatAlert(value,row){
	if(value == 1){
		return "建议";
	}else if(value == 2){
		return "强制";
	}else if(value == 3){
		return "手工";
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
			$.getJSON("/rest/info/clientVersion/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/info/clientVersion/save',
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
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/info/clientVersion/update',
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

