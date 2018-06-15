var toolbar = [ {
	text : '购买',
	iconCls : 'icon-add',
	handler : function() {
		add();
	}
}, '-', {
	text : '赎回',
	iconCls : 'icon-edit',
	handler : function() {
		edit();
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

function formatInterest(value,row){
	if(value){
		return accMul(Number(value),Number(100))+"%";
	}
	return "";
}
function formatCurrentInterest(value,row){
	if(row.interestRate){
		return accMul(Number(row.interestRate),Number(100))+"%";
	}else{
		return accMul(Number(value),Number(100))+"%";
	}
}
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}

//查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		status:$('#sstatus').combobox('getValue')
	});
}
//保存
function save(){
	showLoading();
	$("#abuyDt").val($('#viewAbuyDt').datetimebox("getValue"));
	$('#ffAdd').form('submit',{
		url: '/rest/trading/trading/buy?r='+Math.random(),
		ajax:true,
		success:function(data){
			var json = $.parseJSON(data);
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
function sell(){
	showLoading();
	$("#esellDt").val($('#viewASellDt').datetimebox("getValue"));
	$('#ffEdit').form('submit',{
		url: '/rest/trading/trading/sell?r='+Math.random(),
		ajax:true,
		success:function(data){
			var json = $.parseJSON(data);
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