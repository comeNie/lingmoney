$(document).ready(function(){
	$('#uId').combogrid({
		onLoadSuccess : function() {
			$('#uId').combogrid('grid').datagrid('selectRecord', -1);
		}
	});
});

/*var toolbar = [ {
	text : '查看',
	iconCls : 'icon-add',
	handler : function() {
		view();
	}
}];*/

function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}

function formatDate(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd");
	}
	return "";
}

function formatStatus(value,row){
	if(value==1){
		return "购买成功";
	}else if(value==2){
		return "赎回中";
	}else if(value==3){
		return "赎回成功";
	}
}

//查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		status:$('#status').combobox('getValue'),
		uId:$('#uId').combogrid('getValue')
	});
}

function view(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	$.ajax({
		url : "/rest/trading/redeemFailFlow/redem.html",
		data : {
			"uid" : rows[0].uId,
			"tId" : rows[0].tId,
			"moneyInput" : rows[0].buyMoney,
			"redeemType" : rows[0].redeemType
		},
		dataType : "json",
		type : "post",
		success : function(data) {
			alert(data.code+"..."+data.msg);
			if (data.code == 200) {
				alert("成功");
				$("#adGrid").datagrid('reload');
			} else {
				alert(data.msg);
				return;
			}
		}
	});
}
