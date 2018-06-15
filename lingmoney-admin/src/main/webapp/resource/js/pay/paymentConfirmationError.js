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
		page:1,
		platUserNo:$('#sFincUsers').combogrid('getValue')
	});
	
}
function searchUsers(){
	$('#sFincUsers').combogrid('grid').datagrid('load',{
		page:1,
		name:$('#searchName').val()
	});
}
//统计
function compute(data) {//计算函数
    var rows = $('#adGrid').datagrid('getRows');//获取当前的数据行
    var finaclAll=0;
  
    for (var i = 0; i < rows.length; i++) {
    	finaclAll += rows[i]['userAmount'];
    }
    var hj = (finaclAll).toFixed(2);
    //新增一行显示统计信息
    $('#total_amount').text(hj);
//    $('#adGrid').datagrid('reloadFooter',[{id: '<span>合计:</span>',financ_user:'<span>' + hj +'</span>' }]);
}

function save(){
	showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/excel/impExcel',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivAdd").dialog('close');
				$.messager.alert('信息提示', "上传成功", 'info');
			}
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

function formatTimer(value){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
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
			}else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
