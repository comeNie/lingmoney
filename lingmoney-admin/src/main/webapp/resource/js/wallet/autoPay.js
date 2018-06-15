var toolbar = [ {
	text : '取消自动支付',
	iconCls : 'icon-cancel',
	handler : function() {
		cancel();
	}
}];

function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
} 
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		telephone:$('#phone').val()
	});
	
}


function cancel(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if(rows[0].walletAutoPay==0){
		$.messager.alert("提示", "该用户暂未开通自动支付功能，不允许修改", "error");
	    return
	}
	showLoading();
	$.messager.confirm('确认','您确认想要取消自动支付功能吗？',function(r){    
	    if (r){    
	    	$.getJSON("/rest/wallet/autoPay/cancel?r=" + Math.random() + "&id=" + rows[0].id,
    		    function(info) {
    		       if(info.code==200){
    		    	   $.messager.alert('提示', '取消成功', 'info');
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
