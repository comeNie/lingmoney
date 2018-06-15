$(function(){
	$.getJSON("/rest/gift/lingbaoSign/showCartCount?r=" + Math.random(),
		    function(info) {
		       if(info.code==200){
		    	   $("#cartCountNow").text(info.obj);
		       }
		});
});
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
	iconCls : 'icon-edit',
	handler : function() {
		del();
	}
}];

function formatType(value, row) {
	if (value == 0) {
		return "<font color='green'>每日签到赠送</font>";
	} else if (value == 1) {
		return "<font color='red'>连续签到赠送</font>";
	} 
}

function formatTimer(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
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
			$.getJSON("/rest/gift/lingbaoSign/delete?r=" + Math.random() + "&id=" + rows[0].id,
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
		url: '/rest/gift/lingbaoSign/saveAndUpdate',
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

// 设置购物车限制个数
function setCartCount(){
	var cartCount = $("#cartCount").val();
	$.getJSON("/rest/gift/lingbaoSign/setCartCount?r=" + Math.random() + "&cartCount="+ cartCount,
		    function(info) {
		       if(info.code==200){
		    	   $("#cartCountNow").text(cartCount);
		    	   $.messager.alert('提示', '设置成功', 'info');
		       }else{
		    	   $.messager.alert('错误提示', info.msg, 'error');
		       }
		});
}

