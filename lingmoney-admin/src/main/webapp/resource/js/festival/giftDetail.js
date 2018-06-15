
var toolbar = [ {
	text : '发货',
	iconCls : 'icon-ok',
	handler : function() {
		delivery();
	}
},{
	text : '确认收货',
	iconCls : 'icon-ok',
	handler : function() {
		processingReceipt();
	}
}];

function formatTimer(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}

// 查询
function search(){
	$('#adGrid').datagrid('load', $.serializeObject($('#searchForm')));
}

// 发货处理弹框
function delivery(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if (rows[0].state != 2 && rows[0].state != 3) {
		$.messager.alert("提示", "请选择未发货/已发货礼品", "error");
		return
	}
	if(rows[0].sendTime){
		rows[0].sendTime = formatTimer(new Date(rows[0].sendTime));
	}
	if(rows[0].receiveTime){
		rows[0].receiveTime = formatTimer(new Date(rows[0].receiveTime));
	}
	$("#ffDelivery").form('load',rows[0]);
	$("#DivDelivery").dialog('open');
}

// 发货
function processingDelivery(){
	showLoading();
	$('#ffDelivery').form('submit',{
		url: '/rest/festival/giftDetail/processingDelivery?r=' + Math.random(),
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivDelivery").dialog('close');
				$("#adGrid").datagrid('reload');
			}else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

//确认收货
function processingReceipt(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择至少一条记录", "error");
		return
	}
	if (rows[0].state != 2) {
		$.messager.alert("提示", "请选择已发货礼品", "error");
		return
	}
    showLoading();
	$.messager.confirm('确认','确认收货？',function(r){    
	    if (r){
			$.getJSON("/rest/festival/giftDetail/processingReceipt?r=" + Math.random() + "&id=" + rows[0].id,
			    function(info) {
			       if(info.code==200){
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
