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
	text : '发送信息',
	iconCls : 'icon-remove',
	handler : function() {
		send();
	}
}];
function change(){
	var userSend = $("#userSend").combogrid("getValue");
   if(userSend==0){// 部分用户
		$("#the").show();
	}else{
		$("#the").hide();
	}
}
/*
 * $(document).ready(function(){ })
 */

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
			$.getJSON("/rest/user/adminmessage/delete?r=" + Math.random() + "&id=" + rows[0].id,
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

function sendMessage(){
	var rows = $("#dg").datagrid("getSelections");
	var userSend = $("#userSend").combogrid("getValue");
	var theme = $("#theme").val();
	if (theme == '') {
	    $.messager.alert("提示", "请输入主题", "error");
	    return
	}
	var content = $("#econtent").val();
	if (content == '') {
	    $.messager.alert("提示", "请输入内容", "error");
	    return
	}
	if(userSend==0){// 部分用户
		if (rows.length == 0) {
			$.messager.alert("提示", "请选择一条记录", "error");
		    return
		}
		var idStr = "";  
	    for(var i=0;i<rows.length;i++){  
	    	idStr += rows[i].uId;  
	        if(i < rows.length-1){   
	        	idStr += ',';  
	        }else{  
	            break;  
	        }  
	    }  
		
		showLoading();
		$("#uId").val(idStr);
		$("#count").val(rows.length);
		$("#sendname").val("系统管理员");
		$('#sendForm').form('submit',{
			url: '/rest/user/adminmessage/insertAdminMessage',
			ajax:true,
			success:function(data){
				var json = JSON.parse(data);
				if(json.code == 200){
					KindEditor.remove('#econtent');
					$("#toSend").dialog('close');
					$("#adGrid").datagrid('reload');
					$.messager.alert('提示', '发送成功', 'info');
				}
				else{
					$.messager.alert('错误提示', json.msg, 'error');
				}
			}
		});
		closeLoading();
	}else{// 全部用户
		$("#the").hide();
		$("#sendname").val("系统管理员");
		$('#sendForm').form('submit',{
			url: '/rest/user/adminmessage/insertMessageToAll',
			ajax:true,
			success:function(data){
				var json = JSON.parse(data);
				if(json.code == 200){
					KindEditor.remove('#econtent');
					$("#toSend").dialog('close');
					$("#adGrid").datagrid('reload');
					$.messager.alert('提示', '发送成功', 'info');
				}
				else{
					$.messager.alert('错误提示', json.msg, 'error');
				}
			}
		});
		closeLoading();
	}
}


function send(){
	$('#userSend').combogrid({
		onLoadSuccess : function() {
			$('#userSend').combogrid('grid').datagrid('selectRecord', 0);
		}
	});
	KindEditor.remove('#econtent');
	$('#sendForm').form('clear');
	/*
	 * $("#dg").datagrid({ url: '/rest/user/adminmessage/selectAdminMessage' });
	 */
	$("#toSend").dialog('open');
	createKindEditor("#econtent");
}
function search(){
		var balence=$('#balance').val();
		var finaclMoney=$('#finaclMoney').val();
		var telephone=$('#telephone').val();
		if (balence =="" && finaclMoney=="" && telephone=="") {
		    $.messager.alert("提示", "请选择查询条件", "error");
		    return
		}
		$("#dg").datagrid({  
			type : 'POST',
			url: '/rest/user/adminmessage/selectAdminMessage?balence=' + balence+'&finaclMoney='+finaclMoney+'&telephone='+telephone
		});
}

// 保存
function save(){
	
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/user/adminmessage/save',
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
	if(rows[0].sendtime){
		rows[0].sendtime = formatTimer(new Date(rows[0].sendtime));
	}
	
	var editor = createKindEditor("#acontent");
	$.getJSON("/rest/user/adminmessage/listContent?r=" + Math.random() + "&id=" + rows[0].id,
		    function(info) {
		       if(info.code==200){
		    	   editor.html(info.obj)
		    		$("#DivAdd").dialog('close');
					$("#adGrid").datagrid('reload');
		       }else{
		    	   $.messager.alert('错误提示', info.msg, 'error');
		       }
		});
	$("#ffEdit").form('load',rows[0]);
	$("#DivEdit").dialog('open');
}
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/user/adminmessage/update',
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

