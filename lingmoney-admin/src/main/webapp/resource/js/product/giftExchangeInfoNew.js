var giftStatusJson;// 礼品状态json
var giftTypeJson;// 礼品类型json
var expressCompanyJson;// 快递公司json
$(function() {
	$.ajax({
		type : "GET",
		url : "/resource/json/order_status.json",
		async : false,
		dataType : "json",
		success : function(data) {
			giftStatusJson = data;
		}
	});
	$("#status").combogrid("grid").datagrid("loadData", giftStatusJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/gift_type_new.json",
		async : false,
		dataType : "json",
		success : function(data) {
			giftTypeJson = data;
		}
	});
	$("#type").combogrid("grid").datagrid("loadData", giftTypeJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/order_status.json",
		async : false,
		dataType : "json",
		success : function(data) {
			giftStatusJson = data;
		}
	});
	$("#status").combogrid("grid").datagrid("loadData", giftStatusJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/express_company.json",
		async : false,
		dataType : "json",
		success : function(data) {
			expressCompanyJson = data;
		}
	});
	$("#expressCompany").combogrid("grid").datagrid("loadData", expressCompanyJson);
	
});

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
		receipt();
	}
},{
	text : '导出excel',
	iconCls : 'icon-ok',
	handler : function() {
		exportExcel();
	}
}];

// 导出到excel
function exportExcel(){
	var rows = $("#adGrid").datagrid("getRows");
	if(rows.length==0){
		$.messager.alert('错误信息','没有可导出的数据','error');
		return ;
	}
	var bodyData = JSON.stringify(rows);  // 转成json字符串
	console.log(bodyData);
	exportData(bodyData);
}
function exportData(bodyData) {  
    var f = $('<form action="/rest/product/giftExchangeInfo/exportExcel" method="post" id="fm1"></form>');  
    var i = $('<input type="hidden"  name="json" />');  
    i.val(bodyData);
    i.appendTo(f);  
    f.appendTo(document.body).submit();  
}  

// 状态 0兑换成功 1已发货 2已退回 3等待领取 4已收货/已领取
function formatStatus(value, row) {
	if (value == 0) {
		return "<font color='black'>兑换成功</font>";
	} else if (value == 1) {
		return "<font color='blue'>已发货</font>";
	} else if (value == 2) {
		return "<font color='red'>已收货</font>";
	}
}
function formatType(value, row) {
	if (value == 0) {
		return "<font color='red'>话费充值卡</font>";
	} else if (value == 1) {
		return "<font color='blue'>超级大礼包</font>";
	}
}
function formatTimer(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}

function formatTimers(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd");
	}
	return "";
}

function formatRechargeCode(value, row){
	if (value){
		return "******";
	}
}

// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		referralId:$("#referralId").val(),
		telephone:$("#telephone").val(),
		name:$("#name").val(),
		referralCode:$("#referralCode").val(),
		status:$("#status").combogrid('getValue'),
		type:$("#type").combogrid('getValue'),
		activityName:$("#activityName").val(),
		expressNumber:$("#expressNumber").val(),
		expressCompany:$("#expressCompany").combogrid('getText')
	});
}

// 发货处理弹框
function delivery(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
//	if(rows[0].type == 0){
//		// 只有超值大礼包才能发货
//		$.messager.alert("提示", "只有超值大礼包才能发货", "error");
//		return
//	}
	if(rows[0].status == 2){
		// 如果状态为2(已收货)
		$.messager.alert("提示", "该礼品已收货", "error");
		return
	}
	if(rows[0].exchangeTime){
		rows[0].exchangeTime = formatTimer(new Date(rows[0].exchangeTime));
	}
	if(rows[0].sendTime){
		rows[0].sendTime = formatTimer(new Date(rows[0].sendTime));
	}
	if(rows[0].receiveTime){
		rows[0].receiveTime = formatTimer(new Date(rows[0].receiveTime));
	}
	if(rows[0].type==0){// 话费
		$('#DivDelivery').css("height","290px");
		$(".recharge").show();
		$("#label").text("充值卡类型：");
		$('#giftName').combobox({
			editable:false,
		    valueField:'value',
		    textField:'label',
		    data:[{
		    	label : '移动话费充值卡',
		    	value : '移动话费充值卡'
		    },{
		    	label : '联通话费充值卡',
		    	value : '联通话费充值卡'
		    },{
		    	label : '电信话费充值卡',
		    	value : '电信话费充值卡'
		    }
		          ],
		    icons:[{
				iconCls:'icon-remove',
				handler: function(e){
					$('#giftName').textbox('setValue', '');
				}
			}]
		});
	}else{// 超值大礼包
		$('#DivDelivery').css("height","190px");
		$(".recharge").hide();
		$("#label").text("超值大礼包：");
		$('#giftName').combobox({
		    editable:false,
		    valueField:'value',
		    textField:'label',
		    data:[{
		    	label : '京东E卡1000元',
		    	value : '京东E卡1000元'
		    },{
		    	label : '沃尔玛购物卡1000元',
		    	value : '沃尔玛购物卡1000元'
		    },{
		    	label : '华润万家购物卡1000元',
		    	value : '华润万家购物卡1000元'
		    },{
		    	label : '中石油加油卡1000元',
		    	value : '中石油加油卡1000元'
		    }
		          ],
		    icons:[{
				iconCls:'icon-remove',
				handler: function(e){
					$('#giftName').textbox('setValue', '');
				}
			}]
		});
	}
	$("#ffDelivery").form('load',rows[0]);
	$("#DivDelivery").dialog('open');
}

// 发货
function processingDelivery(){
	$("#deliveryStatus").val(1);
	var rows = $("#adGrid").datagrid("getSelections");
	showLoading();
	$('#ffDelivery').form('submit',{
		url: '/rest/product/giftExchangeInfo/processingDeliveryNew?r=' + Math.random(),
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


// 确认收货
function receipt(){
	// 返回被选中的行 然后集成的其实是 对象数组
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择至少一条记录", "error");
		return
	}
	 
    var ids = "";  
    for(var i=0;i<rows.length;i++){  
    	if(rows[i].status == 0){
    		$.messager.alert("提示", "该礼品未发货", "error");
    		return
    	}
    	if(rows[i].status == 2){
    		$.messager.alert("提示", "该礼品已收货", "error");
    		return
    	}
    	ids += rows[i].id;  
        if(i < rows.length-1){  
        	ids += ',';  
        }else{  
            break;  
        }  
    }
    showLoading();
	$.messager.confirm('确认','确认收货？',function(r){    
	    if (r){
			$.getJSON("/rest/product/giftExchangeInfo/comfirmReceiptNew?r=" + Math.random() + "&ids=" + ids,
			    function(info) {
			       if(info.code==200){
			    	   $.messager.alert('提示', '操作成功', 'info');
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

