// 通用格式化
var formatter = new CommonFormatter();

// 单笔发标工具栏
var toolbar = [{
	text : '修改标的',
	iconCls : 'icon-edit',
	handler : function() {
		edit();
	}
}, '-', {
	text : '查看借款人',
	iconCls : 'icon-organ',
	handler : function() {
		borrower();
	}
}, '-', {
	text : '查看投标人',
	iconCls : 'icon-organ',
	handler : function() {
		bidder();
	}
}, '-', {
	text : '发起标的',
	iconCls : 'icon-ok',
	handler : function() {
		bidding();
	}
}, '-', {
	text : '申请流标',
	iconCls : 'icon-cancel',
	handler : function() {
		askForBiddingLoss();
	}
}, '-', {
	text : '流标结果查询',
	iconCls : 'icon-search',
	handler : function() {
		queryBiddingLossResult();
	}
}, '-', {
	text : '申请放款',
	iconCls : 'icon-ok',
	handler : function() {
		askForBankLendding();
	}
}, '-', {
	text : '放款结果查询',
	iconCls : 'icon-search',
	handler : function() {
		queryBankLenddingResult();
	}
}, '-', {
	text : '通知银行标的结清',
	iconCls : 'icon-ok',
	handler : function() {
		hxRepaymentFinishNotice();
	}
}, '-', {
	text : '手动成立',
	iconCls : 'icon-ok',
	handler : manualEstablish
}];

function manualEstablish() {
	var row = $('#adGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条记录');
		return;
	}
	
	if (row.pStatus != '项目筹集期') {
		$.messager.alert('提示', '产品不在筹集期无法成立');
		return
	}
	
	$.messager.confirm('提示', '确定要手动成立吗？', function(b) {
	   if (b) {
			$.ajax({
				url : '/rest/bank/hxBidding/manualEstablish',
				type : 'post',
				data : {pCode : row.loanNo},
				success : function (data) {
					$.messager.alert('提示', data.msg);
					if (data.code == 200) {
						$('#adGrid').datagrid('reload');
					}
				}
			});
	   }
	});
	
}

function hxRepaymentFinishNotice() {
	var row = $('#adGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请选择一条记录', 'error');
		return;
	}
	
	$.messager.confirm('提示', '注意：标的结清后将不再受理还款交易！确认标的结清?', function(ok) {
		if(ok) {
			showProgress();
			$.ajax({
				url : '/rest/bank/singlePayment/hxRepaymentFinishNotice',
				type : 'post',
				data :{
					loanNo : row.loanNo
				},
				success : function(data){
					closeProgress();
					$.messager.alert('提示', data.msg, 'info');
					if (data.code == 200) {
						 $('#adGrid').datagrid('reload');
					}
				}
			});
		}
	});

}

// 格式化标的状态
function formatBidStatus(value, row){
	if(value == -1){
		return "初始";
	}else if(value == 0){
		return "<font color='#rg7732'>正常</font>";
	}else if(value == 1){
		return "<font color='#999444'>撤销</font>";
	}else if(value == 2){
		return "<font color='#512581'>流标</font>";
	}else if(value == 3){
		return "<font color='red'>银行主动流标</font>";
	}else if(value == 4){
		return "<font color='green'>标的成立</font>";
	}
}

//格式化投标状态
function formatTradingStatus(value, row){
	if(value == 0){
		return "<font color='#rg7732'>待支付</font>";
	}else if(value == 1){
		return "<font color='#999444'>持有中</font>";
	}else if(value == 2){
		return "<font color='#512581'>待回款</font>";
	}else if(value == 3){
		return "<font color='#ea6678'>已回款</font>";
	}else if(value == 4){
		return "<font color='#888777'>冻结中</font>";
	}else if(value == 5){
		return "<font color='#er6235'>支付失败</font>";
	}else if(value == 8){
		return "<font color='#er6235'>已撤标</font>";
	}else if(value == 12){
		return "<font color='#er6235'>支付中</font>";
	}else if(value == 18){
		return "<font color='#er6235'>订单失效</font>";
	}
}

// 发起标的
function bidding(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return
	}
	if (rows[0].investObjState != -1 && rows[0].investObjState != 5) {
	    $.messager.alert("提示", "请选择初始标的", "error");
	    return
	}
	$.messager.confirm('提示', '确认发起标的?', function(ok) {
		if(ok) {
			showProgress();
			$.ajax({
				url : '/rest/bank/hxBidding/bidding',
				type : 'post',
				data :{
					biddingId : rows[0].id
				},
				success : function(data){
					closeProgress();
					if(data.code == 200){
						$.messager.alert('提示', '发标成功', 'info');
					} else{
						$.messager.alert('错误提示', data.msg, 'error');
					}
					$("#adGrid").datagrid('reload');
				}
			});
		}
	});
}

// 查看流标结果
function queryBiddingLossResult(){
	var row = $('#adGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请选择一条记录', 'error');
		return;
	}
	showProgress();
	$.ajax({
		url : '/rest/bank/queryBiddingLossResult',
		type : 'post',
		data : {
			loanNo : row.loanNo
		},
		success : function(data) {
			closeProgress();
			$.messager.alert("提示", data.msg);
			if (data.code == 6026) {
				$('#adGrid').datagrid('reload');
			}
		}
	});
}

// 查询放款结果
function queryBankLenddingResult(){
	var row = $('#adGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请选择一条记录', 'error');
		return;
	}
	showProgress();
	$.ajax({
		url : '/rest/bank/queryBankLenddingResult',
		type : 'post',
		data : {
			loanNo : row.loanNo
		},
		success : function(data) {
			closeProgress();
			$.messager.alert("提示", data.msg);
			if (data.code == 200 && data.obj == 1) {
				$('#adGrid').datagrid('reload');
			}
		}
	});
}

//请求流标
function askForBiddingLoss(){
	var row = $('#adGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请选择一条记录', 'error');
		return;
	}
	$.messager.confirm('提示', '确认申请流标?', function(ok) {
		if(ok) {
			$.messager.prompt('提示信息', '请输入流标原因：', function(cancelReason){
				if(cancelReason != '' && cancelReason.length > 128) {
					$.messager.alert('提示', '最多输入128个字符', 'info');
					return false;
				}
				
				showProgress();
				
				$.ajax({
					url : '/rest/bank/applyBiddingLoss',
					type : 'post',
					data :{
						loanNo : row.loanNo,
						cancelReason : cancelReason
					},
					success : function(data){
						closeProgress();
						if(data.code == 200){
							$.messager.alert('提示', '申请成功');
							$('#adGrid').datagrid('reload');
						} else{
							$.messager.alert('错误提示', data.msg, 'error');
						}
					}
				});
				
			});
		}
	});
}

//请求放款
function askForBankLendding() {
	var row = $('#adGrid').datagrid('getSelected');
	
	if (row == null) {
		$.messager.alert('提示', '请选择一条记录', 'error');
		return;
	}
	
	$('#bankLenddingForm').form('clear');
	$('#bankLenddingDialog').dialog('open');
	$('#lendBiddingId').val(row.id);
}

function bankLenddingSubmit() {
	$('#bankLenddingDialog').dialog('close');
	showProgress();
	$.ajax({
		url: '/rest/bank/askForBankLendding',
		type: 'post',
		data: $('#bankLenddingForm').serialize(),
		success: function(data) {
			closeProgress();
			$.messager.alert('提示', data.msg);
		}
	});
}

// 借款人工具栏
var toolbars = [{
	text : '添加',
	iconCls : 'icon-add',
	handler : function() {
		adds();
	}
}, '-', {
	text : '修改',
	iconCls : 'icon-edit',
	handler : function() {
		edits();
	}
}, '-', {
	text : '删除',
	iconCls : 'icon-remove',
	handler : function() {
		del();
	}
}, '-', {
	text : '查看还款记录',
	iconCls : 'icon-organ',
	handler : function() {showPaymentLi();}
}, '-', {
	text : '还款',
	iconCls : 'icon-organ',
	handler : function() {paymentWin();}
}, '-', {
	text : '申请自动还款',
	iconCls : 'icon-organ',
	handler : function() {applyAutoRepayment();}
}, '-', {
	text : '查询自动还款授权结果',
	iconCls : 'icon-organ',
	handler : function() {queryApplyAutoRepaymentResult();}
}, '-', {
	text : '撤销自动还款授权',
	iconCls : 'icon-organ',
	handler : function() {repealApplyAutoRepayment();}
}];

// 删除借款人
function del(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var rowss = $("#settingGrid").datagrid("getSelections");
	if (rowss.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var rowsss = $("#settingGrid").datagrid("getRows");
	if (rowsss.length == 1) {
		$.messager.alert("提示", "请至少保留一个借款人", "error");
		return
	}
	showLoading();
	$.messager.confirm('确认', '您确认想要删除该项吗？', function(r) {
		if (r) {
			$.getJSON("/rest/bank/hxBorrowerInfo/delete?r=" + Math.random() + "&id=" + rowss[0].id,
			    function(info) {
			       if(info.code==200){
			    	   $.messager.alert('提示', '删除成功', 'info');
			    	   $("#settingGrid").datagrid('reload');
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

// 确认取消自动还款授权
function confirmRepeal() {
	var otpSeqNo = $("#otpSeqNo").val();
	var loanNo = $("#repealLoanNo").textbox("getText");
	var otpNo = $("#otpNo").textbox("getText");
	var remark = $("#repealRemark").textbox("getText");
	
	if (otpSeqNo == '' || otpNo == '') {
		return false;
	}
	
	$("#repealApplyAutoRepaymentDialog").dialog("close");
	
	showProgress();
	$.ajax({
		url : "/rest/bank/repealAutoRepaymentAuthorization",
		type : "post",
		data : {"otpSeqNo" : otpSeqNo, "loanNo" : loanNo, "otpNo" : otpNo, "remark" : remark},
		success : function (data) {
			closeProgress();
			$.messager.alert("info", data.msg);
		}
	});
}

// 获取短信验证码
var timeCountDown = 60;
var repealMsgCodeInterval;
function getMsgCode() {
	showProgress();
	
	$("#msgCodeBtn").linkbutton("disable");
	
	$("#msgCodeBtn").linkbutton({
		text : timeCountDown + "秒后重新获取",
	});
	repealMsgCodeInterval = setInterval("repealMsgCodeCountDown()", 1000);
	
	$.ajax({
		url : "/rest/bank/getMessageCode",
		type : "post",
		data : {type : "2", accountId : $("#repealAccountId").val()},
		success : function(data) {
			closeProgress();
			if (data.code == 200) {
				$("#otpSeqNo").val(data.obj);
			}
			$.messager.alert("提示", data.msg);
		}
	});
	
}

function repealMsgCodeCountDown() {
	if (timeCountDown > 0) {
		timeCountDown -= 1;
		$("#msgCodeBtn").linkbutton({
			text : timeCountDown + "秒后重新获取",
		});
	} else {
		window.clearInterval(repealMsgCodeInterval);
		timeCountDown = 60;
		$("#msgCodeBtn").linkbutton("enable");
		$("#msgCodeBtn").linkbutton({
			text : "获取短信验证码",
		});
	}
}

// 撤销自动还款授权
function repealApplyAutoRepayment() {
	var bidding = $("#adGrid").datagrid("getSelected");
	var borrower = $("#settingGrid").datagrid("getSelected");

	if (bidding == null || borrower == null) {
		$.messager.alert('提示', '请选择一条记录', 'error');
		return;
	}
	
	$("#repealLoanNo").textbox("setText",bidding.loanNo);
	$("#repealLoanner").textbox("setText",borrower.bwAcname);
	$("#otpNo").textbox("clear");
	$("#repealRemark").textbox("clear");
	
	$("#repealAccountId").val(borrower.accId);
	
	$("#repealApplyAutoRepaymentDialog").dialog("open");
}

// 查询自动还款授权结果
function queryApplyAutoRepaymentResult() {
	var bidding = $("#adGrid").datagrid("getSelected");
	
	if (bidding == null) {
		$.messager.alert('提示', '请选择一条记录', 'error');
		return;
	}
	showProgress();
	$.ajax({
		url : '/rest/bank/queryAutoRepaymentAuthResult',
		type : 'post',
		data : {"loanNo" : bidding.loanNo},
		success : function (data) {
			closeProgress();
			$.messager.alert("提示", data.msg);
		}
	});
}

// 申请自动还款授权
function applyAutoRepayment() {
	var bidding = $("#adGrid").datagrid("getSelected");
	var borrower = $("#settingGrid").datagrid("getSelected");

	if (bidding == null || borrower == null) {
		$.messager.alert('提示', '请选择一条记录', 'error');
		return;
	}
	
	if (bidding.investObjState != 0) {
		$.messager.alert('提示', '标的状态有误');
		return;
	}

	$.messager.confirm('提示', '确认申请自动还款?', function(ok) {
		if(ok) {
			$.messager.prompt('提示信息', '请输入备注信息：', function(cancelReason){
				if(cancelReason != '' && cancelReason.length > 60) {
					$.messager.alert('提示', '最多输入60个字符', 'info');
					return false;
				}
				
				showProgress();
				
				$.ajax({
					url : '/rest/bank/askForAutoRepaymentAuthorization',
					type : 'post',
					data : {"loanNo" : bidding.loanNo, "borrowerId" : borrower.bwId, "remark" : cancelReason},
					success : function(data) {
						closeProgress();
						if (data.code == 200) {
							$("#requestData").val(data.obj.requestData);
							$("#transCode").val(data.obj.transCode);
							$("#autoRepaymentForm").attr("action",data.obj.bankUrl);
							$("#autoRepaymentForm").submit();
							$.messager.alert("提示", "申请完成后，通过《查询自动还款授权结果》查询");
						} else {
							$.messager.alert("提示", data.msg);
						}
					}
				});
			});
		}
	});
}

// 格式化状态
function formatStatus(value, row) {
	if (!value || value == 0) {
		return "<font color='red'>不可用</font>";
	} else if (value == 1) {
		return "<font color='blue'>可用</font>";
	}
}

// 修改标的
function edit(){
	var row = $("#adGrid").datagrid("getSelected");
	if (row == null) {
	    $.messager.alert("提示", "请选择一条记录", "error");
	    return;
	}
	
	if (row.investObjState == 0) {
		$.messager.alert('提示', '该标的已在运行中，无法修改');
		return;
	}
	
	if (row.investObjState == 3) {
		$.messager.alert('提示', '该标的已结清，无法修改');
		return;
	}
	
	$("#ffEdit").form('clear');
	$("#ffEdit").form('load',row);
	$("#DivEdit").dialog('open');
}

// 更新标的
function update(){
	showLoading();
	$('#ffEdit').form('submit',{
		url: '/rest/bank/hxBidding/update',
		ajax:true,
		success:function(data){
			var json = JSON.parse(data);
			if(json.code == 200){
				$("#DivEdit").dialog('close');
				$("#adGrid").datagrid('reload');
			} else{
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

// 查看借款人弹框
function borrower(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$('#DivSetting').dialog({ 
		title:'查看借款人'+'('+rows[0].investObjName+')'
	});
	
	$("#settingGrid").datagrid({ 
		url: '/rest/bank/hxBorrowerInfo/list?biddingId='+rows[0].id
	});
	$('#settingGrid').datagrid('clearSelections'); 
	$("#DivSetting").dialog('open');
}

// 添加借款人弹框
function adds() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$('#settingGrid').datagrid('clearSelections'); 
	var rows2 = $("#settingGrid").datagrid("getRows");
	if (rows2.length >= 1) {
		$.messager.alert("提示", "暂时只能添加一个借款人", "error");
		return
	}
	var now = new Date();
	$('#ffLottery').form('clear');
	$("#ffLottery").form('load', {
		biddingId: rows[0].id,
		bwAmt : rows[0].investObjAmt
    });
	$('#biddingId').textbox({ 
		editable:false 
	});
	$('#DivLottery').dialog({ 
		title:'添加借款人'+'('+rows[0].investObjName+')'
	});
	$("#DivLottery").dialog('open');
}

// 修改借款人弹框
function edits() {
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var rowss = $("#settingGrid").datagrid("getSelections");
	if (rowss.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	
	if (rows[0].investObjState == 0) {
		$.messager.alert('提示', '该标的已在运行中，无法修改');
		return;
	}
	
	if (rows[0].investObjState == 3) {
		$.messager.alert('提示', '该标的已结清，无法修改');
		return;
	}
	
	$('#ffLottery').form('clear');
	$('#biddingId').textbox({ 
		editable:false 
	});
	$('#DivLottery').dialog({ 
		title:'修改借款人'+'('+rows[0].investObjName+')',
		iconCls: 'icon-edit'
	});
	$("#ffLottery").form('load', rowss[0]);
	$("#ffLottery").form('load', {
		biddingId: rows[0].id
    });
	$("#DivLottery").dialog('open');
}

// 查看还款信息
function showPaymentLi(){
	var rows = $("#settingGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	// 调出还款列表窗口
	 $('#paymentListDialog').dialog({ 
		title:'查看还款信息'+'('+rows[0].bwAcname+')'
	}); 
	$("#paymentGrid").datagrid({ 
		url: '/rest/bank/singlePayment/list?bwId='+rows[0].id,
		onLoadSuccess:function(data){
			if(data.total!="undefined" && data.total!=0){
				//$("#paymentListDiv").show();
				$("#tipMsg").html("")
				return;
			}
			/*$("#paymentListDiv").hide();*/
			$("#tipMsg").html("暂无还款信息，<a href=javascript:paymentWin()>点此还款</a>")
			
		}
	});
	$("#paymentListDialog").dialog('open');
}

// 添加或修改借款人
function saveAndUpdateLottery() {
	showLoading();
	$('#ffLottery').form('submit', {
		url : '/rest/bank/hxBorrowerInfo/saveAndUpdate',
		ajax : true,
		success : function(data) {
			var json = JSON.parse(data);
			if (json.code == 200) {
				$("#DivLottery").dialog('close');
				$("#settingGrid").datagrid('reload');
				$("#adGrid").datagrid('reload');
			} else {
				$.messager.alert('错误提示', json.msg, 'error');
			}
		}
	});
	closeLoading();
}

//查看投标人弹框
function bidder(){
	var rows = $("#adGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	$('#DivBidder').dialog({ 
		title:'查看投标人'+'('+rows[0].investObjName+')'
	});
	$("#bidderGrid").datagrid({ 
		url: '/rest/bank/hxBidder/list?bId='+rows[0].id
	});
	$('#bidderGrid').datagrid('clearSelections'); 
	$("#DivBidder").dialog('open');
}

// 搜索产品
function searchName(){
	$('#pId').combogrid('grid').datagrid('load',{
		page:1,
		id:$('#proId').val(),
		name:$('#proName').val()
	});
}

//搜索借款人
function searchBwId(){
	$('#bwId').combogrid('grid').datagrid('load',{
		bwAcname : $('#bwAcname').val()
	});
}

// 搜索标的
function search(){
	$('#adGrid').datagrid('load',{
		page:1,
		pId:$("#pId").combogrid('getValue'),
		loanNo:$("#loanNo").val(),
		investObjState:$("#investObjState").combogrid('getValue'),
		repayDate:$('#repaymentDate').datebox('getValue'),
		proStatus:$('#pStatus').combobox('getValue')
	});
}

// 格式化还款类别
function formatPaymentType(value, row, index){
	// 1-个人普通还款 2-垫付后，借款人还款 3-公司垫付还款 4-自动还款
	var paymentType = row.paymentType;
	var text = "";
	switch(paymentType){
	case 1:text = "个人普通还款";break;
	case 2:text = "垫付后，借款人还款";break;
	case 3:text = "公司垫付还款";break;
	case 4:text = "自动还款";break;
	}
	return text;
}

// 格式化还款状态
function formatPaymentStatus(value, row, index){
	// 1-还款成功 2- 还款中 3-还款失败
	var paymentStatus = row.status;
	var text = "";
	switch(paymentStatus){
	case 1:text = "还款成功";break;
	case 2:text = "还款中";break;
	case 3:text = "还款失败";break;
	case 4:text = "操作超时";break;
	}
	return text;
}


// 还款信息末列 formatter
function formatOperation(value, row, index){
	var paymentStatus = row.status;
	var paymentType = row.paymentType;
	
	var html = '';
	if (paymentType == 3) {
		html +=  paymentStatus == 1 ? '<a href = "#" onclick="queryPaymentRes(\''+row.channelFlow+'\');return false;"  target="_blank">查看结果</a>&nbsp;&nbsp;<a href = "#" onclick="repaymentAfterAdvance(\''+row.channelFlow+'\');">还款</a>' : '';
	} else if (paymentType == 2) {
		html += (paymentStatus==1||paymentStatus==2) ? '<a href = "#" onclick="queryPaymentRes(\''+row.channelFlow+'\');return false;">查看结果</a>' : '<a href = "#" onclick="repaymentAfterAdvance(\''+row.oldChannelFlow+'\');" >重新发起还款</a>';
	} else {
		html += (paymentStatus==1||paymentStatus==2) ? '<a href = "#" onclick="queryPaymentRes(\''+row.channelFlow+'\');return false;"  target="_blank">查看结果</a>' : '<a href = "#" onclick="paymentWin();return false;" target="_blank">重新发起还款</a>';
	}
	
    return html;
}

// 垫付后借款人还款
function repaymentAfterAdvance(channelFlow) {
	$.ajax({
		url:'/rest/bank/singlePayment/repaymentAfterAdvance',
		type:'post',
		data:{channelFlow : channelFlow},
		success:function(data) {
			if (data.code == 200) {
				var obj = data.obj;
				var transcode = obj.transCode;
				var requestData = obj.requestData;
				var bankUrl = obj.bankUrl;
				$("#sigPaymentForm").attr("action",bankUrl);
				$("#sigPaymentRequestData").val(requestData);
				$("#sigPaymentTransCode").val(transcode);
				// 提交
				$("#sigPaymentForm").submit();
				$.messager.alert('提交中', "您的请求已发送至华兴银行处理，请等待", 'info');
			} else {
				$.messager.alert('提示', data.msg);
			}
		}
	})
}
 
// 调出还款窗口
function paymentWin(){
	var rows = $("#settingGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	var bidRow=$("#adGrid").datagrid("getSelections")[0];
	
	$("#bwAcName").textbox("setValue",rows[0].bwAcname);
	$("#bwAcNo").textbox("setValue", rows[0].bwAcno);
	// 计算还款总额   本金*利率*天数/365
	
	// 调用计算还款总额接口
	$.post("/rest/bank/singlePayment/queryAmount",{loanNo:bidRow.loanNo},function(data){
		if(data.code==200){
			$("#amount").html(data.obj);
			$("#loanNo_payment").textbox("setValue", bidRow.loanNo);
			$("#paymentDialog").dialog('open'); 
		}else{
			$.messager.alert('计算出错', data.msg, 'error');
			$("#paymentDialog").dialog('close'); 
		}
	});
	
	
}

// 提交还款表单
function paymentSubmit(){
	// 根据选择的还款类别  修改action地址 
	var requestType=$("#dfFlag option:selected").val();
	if(requestType==3){
		$("#paymentForm").attr("action","/rest/bank/companyAdvPayment")
	}
	showProgress();
	// 因为要在子dialog中接收返回值，换成ajax提交
	$.post($("#paymentForm").attr("action"),$("#paymentForm").serialize(),function(data){
		if(data.code==200){
			var obj = data.obj;
			
			if(requestType==3){
				// 公司垫付
				$.messager.alert('提交成功', "您的请求已成功！", 'info');
				
			}else{
				var transcode = obj.transCode;
				var requestData = obj.requestData;
				var bankUrl = obj.bankUrl;
				$("#sigPaymentForm").attr("action",bankUrl);
				$("#sigPaymentRequestData").val(requestData);
				$("#sigPaymentTransCode").val(transcode);
				// 提交
				$("#sigPaymentForm").submit();
				$.messager.alert('提交中', "您的请求已发送至华兴银行处理，请等待", 'info');
			}
		}else{
			$.messager.alert('提交错误', data.msg, 'error');
		}
		closeProgress();
	});
}


/**
 * 查询还款结果
 * @param channelFlow
 */
function queryPaymentRes(channelFlow){
	if(channelFlow==""||channelFlow==null){
		$.messager.alert('参数错误',"流水号为空", 'error');
	}

	$("#paymentGrid").datagrid("loading", "正在查询结果，请稍候……");
	$.post("/rest/bank/singlePayment/queryResult",{oldReqSeqNo:channelFlow},function(data){
		if(data.code==200){
			var obj = data.obj;
			for(var o in obj){
				var val=obj[o];
				
				if(o=="RETURN_STATUS"){
					switch(val){
					case "S":val="成功";break;
					case "F":val="失败";break;
					case "R":val="处理中（客户仍停留在页面操作）";break;
					case "N":val="未知（已提交后台，需再次发查询接口。）";break;
					default:val="未查询到结果"
					}
				}
				$("#queryPaymentResultDialog").find("td[name=" + o + "]").html(val);
			}
			$("#paymentGrid").datagrid("loaded");
			$("#queryPaymentResultDialog").dialog('open');
		}else if(data.code==6032){
			// 返回了个错误界面  此时弹出新窗口 显示其中的html内容
			var errorPageHtml = data.msg;
			$("#errorPageHtml").val(errorPageHtml);
			$("#errorSubForm").submit();
			$("#paymentGrid").datagrid("loaded");
		}else{
			$("#paymentGrid").datagrid("loaded");
			$.messager.alert('查询错误', data.msg, 'error');
		}
	})
}


// 投标人工具栏
var toolbarBidder = [{
	text : '撤标',
	iconCls : 'icon-add',
	handler : function() {
		cancelBidding();
	}
}];

// 单笔撤标
function cancelBidding(){
	var rows = $("#bidderGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择一条记录", "error");
		return
	}
	if (rows[0].status!=4) {
		$.messager.alert("提示", "只有冻结中的订单才可撤标", "error");
		return
	}
	$.messager.confirm('提示', '确认撤标?', function(ok) {
		if(ok) {
			$.messager.prompt('提示信息', '请输入撤标原因：', function(cancelReason){
				if (cancelReason){
					if(cancelReason.length > 128) {
						$.messager.alert('提示', '最多输入128个字符', 'info');
						return false;
					}
					showProgress();
					$.ajax({
						url : '/rest/bank/hxBidding/cancelBidding',
						type : 'post',
						data :{
							id : rows[0].id,
							cancelReason : cancelReason
						},
						success : function(data){
							closeProgress();
							if(data.code == 200){
								$.messager.alert('提示', '撤标成功', 'info');
								$("#bidderGrid").datagrid('reload');
							} else{
								$.messager.alert('错误提示', data.msg, 'error');
							}
						}
					});
				}
			});
		}
	});
}



