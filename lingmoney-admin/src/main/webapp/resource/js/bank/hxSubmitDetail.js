var formatter = new CommonFormatter();


var toolbar = [{
	text : '提交收益明细',
	iconCls : 'icon-edit',
	handler : function() {
		request();
	}
},{
	text : '查看投资人列表',
	iconCls : 'icon-edit',
	handler : function() {
		queryPeople();
	}
},{
	text : '查询提交结果',
	iconCls : 'icon-edit',
	handler : function() {
		queryResultTotal();
	}
}
]

function request(){
	var row = $("#paymentGrid").datagrid("getSelected");
	if (row == null) {
	    $.messager.alert("提示", "请选择一条还款记录", "error");
	    return;
	}
	
	if (row.submitDetailStatus != 0) {
		$.messager.alert("提示", "该还款收益明细已在队列，请勿重复操作", "error");
	    return;
	}
	
	$("#paymentGrid").datagrid("loading", "正在提交，请稍候……");
	$.post("/rest/bank/submitPaymentGains/",{channelFlow:row.channelFlow},function(data){
		if(data.code==200){
			$("#paymentGrid").datagrid("loaded");
			$.messager.alert('提交成功', '添加提交收益明细至队列成功，请等待发送', 'info');
			$("#paymentGrid").datagrid("reload");
		}else{
			$("#paymentGrid").datagrid("loaded");
			$.messager.alert('提交失败', data.msg, 'error');
		}
	})
}

// 条件检索
function search(){
	var loanNo=$('#loanNo').textbox('getValue');
	var channelFlow=$('#channelFlow').textbox('getValue');
	$('#paymentGrid').datagrid('load',{
		page:1,
		loanNo:loanNo,
		channelFlow:channelFlow
	});
}

var toolbarPeople = [
	{
		text : '查询提交结果',
		iconCls : 'icon-search',
		handler : function() {
			queryResult();
		}
	}
]


function queryPeople(){
	$("#paymentGrid").datagrid("loading", "正在查询结果，请稍候……");
	var rows = $("#paymentGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$("#paymentGrid").datagrid("loaded");
	    $.messager.alert("提示", "请选择一条还款记录", "error");
	    return
	}
	// 弹出一个选择投资人的弹窗
	$("#paymentGrid").datagrid("loaded");
	$("#queryPeopleDialog").dialog('open');
	$("#peopleGrid").datagrid('options').url = "/rest/bank/submitPaymentGains/repayList";
	$("#peopleGrid").datagrid('load',{'page' : 1,'loanNo':rows[0].loanNo});
}

/**
 * 查询提交结果
 * 
 * @param
 */
function queryResultTotal(){
	$("#paymentGrid").datagrid("loading", "正在查询结果，请稍候……");
	
	var paymentRows = $("#paymentGrid").datagrid("getSelections");
	if (paymentRows.length == 0) {
		$("#paymentGrid").datagrid("loaded");
	    $.messager.alert("提示", "请选择一条还款记录", "error");
	    return
	}
	// 如果提交状态为未提交 则不加载
	if(paymentRows[0].submitDetailStatus==0){
		$("#paymentGrid").datagrid("loaded");
	    $.messager.alert("提示", "收益明细还未提交", "error");
	    return
	}
	
	$.post("/rest/bank/submitPaymentGains/query",{oldReqSeqNo:paymentRows[0].submitChannelFlow,loanNo:paymentRows[0].loanNo},function(data){
		if(data.code==200){
			$("#paymentGrid").datagrid("reload");
			var obj = data.obj;
			for(var o in obj){
				if(o=="RSLIST"){
					var relist=obj[o];
					if(relist==null||relist.length==0){
						continue;
					}
					var reLiJsonObj={
						"total": relist.length,
						"rows":relist
					}
					$('#reListGrid').datagrid('loadData', reLiJsonObj); // 将数据绑定到datagrid
					
					/*
					 * for(var i in relist){ var peopleObj=relist[0]; for(var po
					 * in peopleObj){
					 * 
					 * $("#reListGrid").datagrid('options') } }
					 */
					continue;
				}
				
				if(o=="RETURN_STATUS"){
					// L 交易处理中 F 失败 S完成
					var val="";
					switch(obj[o]){
						case 'L':val="交易处理中";break;
						case 'F':val="失败";break;
						case 'S':val="成功";break;
						default:val="状态未识别";break;
					}
					
					$("#queryDialog").find("td[name=" + o + "]").html(val);
					continue;
				}
				if(o=="DFFLAG"){
					switch(obj[o]){
						case '1':val="正常还款";break;
						case '2':val="垫付后，借款人还款";break;
						case '3':val="公司垫付还款";break;
						case '4':val="自动还款";break;
						default:val="状态未识别";break;
					}
					$("#queryDialog").find("td[name=" + o + "]").html(val);
					continue;
				}
				
				$("#queryDialog").find("td[name=" + o + "]").html(obj[o]);
			}
			$("#queryDialog").dialog('open');
		}else{
			$("#paymentGrid").datagrid("loaded");
			$.messager.alert('查询错误', data.msg, 'error');
		}
	})
}



/**
 * 查询提交结果
 * 
 * @param
 */
function queryResult(){
	$("#peopleGrid").datagrid("loading", "正在查询结果，请稍候……");
	var rows = $("#peopleGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$("#peopleGrid").datagrid("loaded");
	    $.messager.alert("提示", "请选择一条投资人记录", "error");
	    return
	}
	
	var paymentRows = $("#paymentGrid").datagrid("getSelections");
	if (paymentRows.length == 0) {
		$("#peopleGrid").datagrid("loaded");
	    $.messager.alert("提示", "请选择一条还款记录", "error");
	    return
	}
	// 如果提交状态为未提交 则不加载
	if(paymentRows[0].submitDetailStatus==0){
		$("#peopleGrid").datagrid("loaded");
	    $.messager.alert("提示", "此投资人对应的收益明细还未提交", "error");
	    return
	}
	
	$.post("/rest/bank/submitPaymentGains/query",{oldReqSeqNo:paymentRows[0].submitChannelFlow,loanNo:paymentRows[0].loanNo,subSeqNo:rows[0].SUBSEQNO},function(data){
		if(data.code==200){
			$("#peopleGrid").datagrid("loaded");
			var obj = data.obj;
			for(var o in obj){
				if(o=="RSLIST"){
					var relist=obj[o];
					var reLiJsonObj={
						"total": relist.length,
						"rows":relist
					}
					$('#reListGrid').datagrid('loadData', reLiJsonObj); // 将数据绑定到datagrid
					
					/*
					 * for(var i in relist){ var peopleObj=relist[0]; for(var po
					 * in peopleObj){
					 * 
					 * $("#reListGrid").datagrid('options') } }
					 */
					continue;
				}
				
				$("#queryDialog").find("td[name=" + o + "]").html(obj[o]);
			}
			$("#queryDialog").dialog('open');
		}else{
			$("#peopleGrid").datagrid("loaded");
			$.messager.alert('查询错误', data.msg, 'error');
		}
	})
}


function formatSubimitType(value, row, index){
	// 0-未提交 1-已提交 2-提交中
	var text = "";
	switch(value){
	case 0:text = "未提交";break;
	case 1:text = "已提交";break;
	case 2:text = "提交中";break;
	case 3:text = "提交成功";break;
	}
	return text;
}

// 格式化还款类别
function formatPaymentType(value,row,index){
	// 格式化提交状态 还款类别 1-个人普通还款 2-垫付后，借款人还款 3-公司垫付还款 4-自动还款
	var text = "";
	switch(value){
	case 1:text = "个人普通还款";break;
	case 2:text = "垫付后，借款人还款";break;
	case 3:text = "公司垫付还款";break;
	case 4:text = "自动还款";break;
	}
	return text;
	
}

function formatPaymentStatus(value,row,index){
	// 还款状态 1-还款成功 2- 还款中 3-还款失败
	var text = "";
	switch(value){
	case 1:text = "还款成功";break;
	case 2:text = "还款中";break;
	case 3:text = "还款失败";break;
	case 4:text = "请求超时";break;
	}
	return text;
}
