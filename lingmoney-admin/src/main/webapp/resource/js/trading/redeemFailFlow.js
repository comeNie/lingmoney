var toolbar = [ {
	text : '赎回',
	iconCls : 'icon-add',
	handler : function() {
		redeem();
	}
}];
/**
 ** 乘法函数，用来得到精确的乘法结果
 ** 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
 ** 调用：accMul(arg1,arg2)
 ** 返回值：arg1乘以 arg2的精确结果
 **/
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    }
    catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    }
    catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

//trading/test

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
	if(value==18){
		return "赎回失败";
	}else{
		return "待处理";
	}
}

//查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		status:$('#status').combobox('getValue')
	});
}

function redeem(){
	var row = $("#adGrid").datagrid("getSelected");
	if (row == null) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return;
	}
	
	$.ajax({
		url : "/rest/trading/redeemFailFlow/redeem.html",
		data : {
			"id" : row.id
		},
		dataType : "json",
		type : "post",
		success : function(data) {
			$.messager.alert("提示", data.msg);
			if (data.code == 200) {
				$("#adGrid").datagrid('reload');
			} else {
				return;
			}
		}
	});
}
function sell(){
	showLoading();
	$("#esellDt").val($('#viewASellDt').datetimebox("getValue"));
	$('#ffEdit').form('submit',{
		url: '/rest/trading/trading/sell?r='+Math.random(),
		ajax:true,
		success:function(data){
			if(data.code == 200){
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', data.msg, 'error');
			}
		}
	});
	closeLoading();
}