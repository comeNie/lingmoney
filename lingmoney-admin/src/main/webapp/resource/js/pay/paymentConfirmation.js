var toolbar = [
	{
	text : '确认兑付',
	iconCls : 'icon-ok',
	handler : function() {
		pay();
	}
}
];
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}

function search(){
	$('#adGrid').datagrid('load',{
		page:1
	});
	
}
//统计
function compute(data) {//计算函数
	 var rows = $('#adGrid').datagrid('getRows')//获取当前的数据行
	    var allAmount=0
	    for (var i = 0; i < rows.length; i++) {
	    	allAmount += rows[i]['userAmount'];
	    }
	   
	    //新增一行显示统计信息
	    $('#adGrid').datagrid('reloadFooter',[{id: '<span class="subtotal">合计:</span>',userAmount:'<span class="subtotal">合计应付款:'+allAmount.toFixed(2)+'</span>' }]);
}

function formatTimer(value){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}

function formatBizType(value){
	if(value=="1"){
		return "稳赢宝";
	}else if(value =="2"){
		return "随心取-全部-赎回";
	}else if(value =="3"){
		return "随心取-部分-赎回";
	}else{
		return "未知";
	}
}


function pay(){
	
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	var idStr = "";  
    for(var i=0;i<rows.length;i++){  
    	idStr += rows[i].id;  
        if(i < rows.length-1){   
        	idStr += ',';  
        }else{  
            break;  
        }  
    }  	
	
    showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/paymentConf/paymentSubmission?idStr='+idStr,
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$.messager.alert('信息提示', "确认成功", 'info');
				search();
			}else{
				$.messager.alert('错误提示', json.msg, 'error');
				search();
			}
		}
	});
	closeLoading();
}
