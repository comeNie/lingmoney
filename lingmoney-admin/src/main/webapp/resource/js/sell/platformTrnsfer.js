var toolbar = [{
	text : '平台汇款',
	iconCls : 'icon-remove',
	handler : function() {
		autoPay();
	}
}];


function formatTimer(value){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}


function autoPaySubmit(){	
	var rows = $("#dg").datagrid("getSelections");	
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
  	var money = $("#money").val();
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
	$("#uId").val(idStr);
	$('#autoPayForm').form('submit',{
		url: '/rest/sell/platformTransfer/autoPay',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				
				$("#autoPay").dialog('close');
				$("#dgx").datagrid('reload');
			}
			else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
	
	
}

function autoPay(){

	$('#autoPayForm').form('clear');
	$("#autoPay").dialog('open');
	
}


function search(){
		
		var telphone = $('#telphone').val()
		$("#dg").datagrid({  
			type : 'POST',
	        url:'/rest/sell/platformTransfer/selectUserList?phone='+telphone	
		});
}








