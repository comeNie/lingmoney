var toolbar = [ {
	text : '导出到excel',
	iconCls : 'icon-add',
	handler : function() {
		var rows = $('#adGrid').datagrid('getRows')//获取当前的数据行
		if(rows.length==0){
			$.messager.alert('错误信息','没有可导出的数据','error');
			return ;
		}
		var user_finc=$("#sFincUsers").combogrid('getValue');
		if(!user_finc){
			 $.messager.alert("提示", "清闲选择融资用户", "error");
			 return;
		}
	    Export(user_finc);
	}
}


,'-',{
	text : '确认还款',
	iconCls : 'icon-ok',
	handler : function() {
		pay();
	}
}
/*,'-',{
	text : '导入确认excel',
	iconCls : 'icon-ok',
	handler : function() {
		add();
	}
}*/];
//添加窗口的显示
function add(){
	$('#ffAdd').form('clear');
	$("#DivAdd").dialog('open');
}
function Export(user_finc) {  
    var f = $('<form action="/rest/excel/exportTakeHeart" method="post" id="fm1"></form>');  
    var i = $('<input type="hidden"  name="platUserNo" />');  
    i.val(user_finc);
    i.appendTo(f);  
    f.appendTo(document.body).submit();  
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
	//alert(11);
    var rows = $('#adGrid').datagrid('getRows');//获取当前的数据行
    var finaclAll=0;
  
    for (var i = 0; i < rows.length; i++) {
    	finaclAll += rows[i]['sell_money'];
    	
    }
    var hj = (finaclAll).toFixed(2);
    //新增一行显示统计信息
    $('#adGrid').datagrid('reloadFooter',[{id: '<span class="subtotal">合计:</span>',financ_user:'<span class="subtotal">合计应付款:'+hj+'</span>' }]);
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
	//
	
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
	
    //alert(idStr);
    
    showLoading();
	$('#ffAdd').form('submit',{
		url: '/rest/sell_submit/takeHeartPay?idStr='+idStr,
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				//$("#DivAdd").dialog('close');
				$.messager.alert('信息提示', "确认成功", 'info');
			}
			else
			{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}
