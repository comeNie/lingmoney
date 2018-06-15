var orderStatusJson;//订单状态json
var exchangeTypeJson;//兑换类型json
var gifttypeJson;//礼品类型json
var expressCompanyJson;//快递公司json
var receiveWayJson;// 礼品领取方式json
$(function() {
	$.ajax({
		type : "GET",
		url : "/resource/json/order_status.json",
		async : false,
		dataType : "json",
		success : function(data) {
			orderStatusJson = data;
		}
	});
	$("#status").combogrid("grid").datagrid("loadData", orderStatusJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/exchange_type.json",
		async : false,
		dataType : "json",
		success : function(data) {
			exchangeTypeJson = data;
		}
	});
	$("#type").combogrid("grid").datagrid("loadData", exchangeTypeJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/giftType.json",
		async : false,
		dataType : "json",
		success : function(data) {
			gifttypeJson = data;
		}
	});
	$("#gifttype").combogrid("grid").datagrid("loadData", gifttypeJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/express_company.json",
		async : false,
		dataType : "json",
		success : function(data) {
			expressCompanyJson = data;
		}
	});
	$("#expressCompanys").combogrid("grid").datagrid("loadData", expressCompanyJson);
	
	$.ajax({
		type : "GET",
		url : "/resource/json/receiveWay.json",
		async : false,
		dataType : "json",
		success : function(data) {
			receiveWayJson = data;
		}
	});
	$("#receiveWay").combogrid("grid").datagrid("loadData", receiveWayJson);
});

var toolbar = [ /*{
	text : '处理订单',
	iconCls : 'icon-edit',
	handler : function() {
		order();
	}
},*/{
	text : '发货',
	iconCls : 'icon-ok',
	handler : function() {
		delivery();
	}
}];
//状态 0兑换成功  1已发货 2已退回 3等待领取 4已收货/已领取
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
		return "<font color='black'>领宝兑换</font>";
	} else if (value == 1) {
		return "<font color='blue'>抽奖</font>";
	} else if (value == 2) {
		return "<font color='red'>限时抢</font>";
	}
}
function formatGifttype(value, row) {
	if (value == 0) {
		return "<font color='black'>实物礼品</font>";
	} else if (value == 1) {
		return "<font color='blue'>虚拟礼品</font>";
	} else if (value == 2) {
		return "<font color='red'>领宝</font>";
	}
}
function formatReceiveWay(value, row) {
	if (value == 0) {
		return "<font color='black'>寄送</font>";
	} else if (value == 1) {
		return "<font color='blue'>自取</font>";
	} else if (value == 2) {
		return "<font color='red'>无需领取</font>";
	}
}
function formatAddress(value, row) {
	if(typeof(value)=="undefined"||value==null){
		return "无收货地址";
	}
	return "【<a href='javascript:void(0);' onclick='showAddress("+value+");'>查看</a>】";
}
function showAddress(id){
	$.get('/rest/gift/lingbaoExchangeInfo/getAddress',{'id':id},function(data){
		$("#name").html(data.obj.name);
		$("#phone").html(data.obj.telephone);
		$("#address").html(data.obj.address);
	});
	$('#addressWin').window('open');
}
function formatTimer(value, row) {
	if (value) {
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}
	return "";
}

// 查询
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		rows:20,
		uname:$("#uname").val(),
		uPhoneNumber:$("#uPhoneNumber").val(),
		giftName:$("#giftName").val(),
		expressNumber:$("#expressNumbers").val(),
		status:$("#status").combogrid('getValue'),
		type:$("#type").combogrid('getValue'),
		serialNumber:$("#serialNumbers").val(),
		activityId:$("#activityId").combogrid('getValue'),
		expressCompany:$("#expressCompanys").combogrid('getText'),
		gifttype:$("#gifttype").combogrid('getValue'),
		receiveWay:$("#receiveWay").combogrid('getValue')
	});
}

// 发货处理弹框
function delivery(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if(rows[0].status == 2){
		// 如果状态为2(已收货)
		$.messager.alert("提示", "该礼品已收货", "error");
		return
	}
	if(rows[0].receiveWay==0){
		// 寄送，有快递公司、快递单号
		$('#DivDelivery').css("height","150px");
		$('#expressCompanyTr').show();
		$('#expressNumberTr').show();
		$('.window-shadow').css({"background":"none","box-shadow":"none"});
		$('#expressCompany, #expressNumber').textbox({ 
			required:true
		});
	}else if(rows[0].receiveWay==1||rows[0].receiveWay==2){
		// 自取或无需领取，无快递公司、快递单号
		$('#DivDelivery').css("height","85px");
		$('#expressCompanyTr').hide();
		$('#expressNumberTr').hide();
		$('.window-shadow').css({"background":"none","box-shadow":"none"});
		$('#expressCompany, #expressNumber').textbox({ 
			required:false
		});
	}
	if(rows[0].sendTime){
		rows[0].sendTime = formatTimer(new Date(rows[0].sendTime));
	}
	$("#ffDelivery").form('load',rows[0]);
	$("#DivDelivery").dialog('open');
}

// 发货
function processingDelivery(){
	$("#deliveryStatus").val(1);
	showLoading();
	$('#ffDelivery').form('submit',{
		url: '/rest/gift/lingbaoExchangeInfo/processingOrder?r=' + Math.random(),
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

function giftNameFormatter(value, row, index) {
	return "<span title='"+ value +"'>" + value + "</span>";
}
