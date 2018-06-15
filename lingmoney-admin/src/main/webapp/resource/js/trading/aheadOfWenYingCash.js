function subQuery(){
			var username = $('#username').textbox('getValue');
			var tel = $('#tel').textbox('getValue');
			var productName = $('#productName').textbox('getValue');
			var buyDt = $('#buyDt').datebox('getValue');
			$('#adGrid').datagrid('reload', {'username': username,'tel': tel,'productName': productName, 'buyDt': buyDt});
		}
		
		function refresh(){
			$('#username').textbox('setValue','');
			$('#tel').textbox('setValue','');
			$('#productName').textbox('setValue','');
			$('#buyDt').datebox('setValue','');
			$('#adGrid').datagrid('reload',{'t':new Date()});
		}
		
		function duifu(){
			var rows = $("#adGrid").datagrid("getSelected");
			if (rows==null) {
			    $.messager.alert("提示", "请选择一条记录", "error");
			    return false;
			}
			$.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {  
	            if (data) {  
	            	var tId = rows.tId;
	    			var uId = rows.uId;
	    			var minSellDt = rows.minSellDt;
	    			
	    			$.ajax({
	    				url: "/rest/trade/trading/duifuWenYingBao",
	    				type : "post",
	    				data: {
	    					"tId":tId,
	    					"uId":uId,
	    					"minSellDt":minSellDt,
	    				},
	    				dataType : "json",
	    				success: function(data){
	    					console.info(data);
	    					if(data.code==200) {
	    						$.messager.alert("操作提示", data.msg);
	    						$("#adGrid").datagrid('reload');
	    					}else {
	    						$.messager.alert("操作提示", data.msg+"  错误码："+data.data);
	    					}
	    				}
	    			});   
	            }
	        });  
		}