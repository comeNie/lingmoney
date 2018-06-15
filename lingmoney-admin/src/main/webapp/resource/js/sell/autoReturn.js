var toolbar = [ {
	text : '开通自动还款',
	iconCls : 'icon-ok',
	handler : function() {
		publish();
	}
}];
function formatTimer(value,row){
	if(value){
		return new Date(value).format("yyyy-MM-dd");
	}else{
		return "";
	}
}

// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		pactName:$("#pactName").val()		
	});
}


function publish(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if(rows[0].status==1){
		 $.messager.alert("提示", "已经开通", "error");
		 return
	}
	if(rows[0].bought==0){
		 $.messager.alert("提示", "该产品未购买，不可开通 ", "error");
		 return
	}
	showLoading();
	$.getJSON("/rest/sell/sellAutoReturn/testPcode?pCode=" + rows[0].code,
		    function(info) {
			 // alert(info.code);
	        if(info.code==200){
	        	 $.messager.confirm('Confirm','正在前往第三方平台开通自动还款服务',function(r){   
        		       if (r){   
        		    	   $("#adGrid").datagrid('reload');
        		    	   //window.location.href="/rest/autoReturnCallbackController/callbackUrl.html?code="+ rows[0].code;  
        		       }   
        		 });  
	    	    $.getJSON("/rest/sell/sellAutoReturn/save?status=0&financingId=" + rows[0].platformUserNo+ "&pCode=" + rows[0].code,
    			    function(info) {
    			       if(info.code==200){
    			    	   $("#adGrid").datagrid('reload');
    			    	   window.open("/rest/sell/sellAutoReturn/testAuto?nameNO="+rows[0].platformUserNo+ "&pCode="+ rows[0].code);
    			       }else{
    			    	   $.messager.alert('错误提示', info.msg, 'error');
    			       }
	    			});
	        }else if(info.code==201){
	        	$.messager.confirm('Confirm','正在前往第三方平台开通自动还款服务',function(r){   
     		       if (r){  
     		    	  $("#adGrid").datagrid('reload');
     		    	  //window.location.href="/rest/autoReturnCallbackController/callbackUrl.html?code="+ rows[0].code; 
     		       }   
	        	});
		    	window.open("/rest/sell/sellAutoReturn/testAuto?nameNO="+rows[0].platformUserNo+ "&pCode="+ rows[0].code);
	        }else{
	        	$.messager.alert('错误提示', info.msg, 'error');
		    	   return;
	        }
	    	   
	       });
	
	closeLoading();
}


