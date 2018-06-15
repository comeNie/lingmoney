var toolbar = [{
	text : '申请开户',
	iconCls : 'icon-add',
    handler : addAccount
}, '-', {
	text : '开户结果查询',
	iconCls : 'icon-search',
	handler : queryResult
}, '-', {
	text : '经办人变更',
	iconCls : 'icon-edit',
	handler : changeTransactor
}, '-', {
	text : '查看经办人变更记录',
	iconCls : 'icon-search',
	handler : listTransactor
}, '-', {
	text : '绑定卡信息查询',
	iconCls : 'icon-search',
	handler : queryBindCard
}, '-', {
	text : '绑定卡解绑',
	iconCls : 'icon-edit',
	handler : unBindCard
}, '-', {
	text : '余额查询',
	iconCls : 'icon-search',
	handler : queryBalance
}, '-', {
	text : '提现',
	iconCls : 'icon-edit',
	handler : withdrawalsWindow
}];

var transActorToolbar = [{
	text : '企业经办人信息变更结果查询',
	iconCls : 'icon-search',
	handler : queryChangeTransactorResult
}];

function withdrawalsWindow(){
	var row = $('#enterpriseAccountListGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条数据');
		return false;
	}
	
	if (!row.bankNo) {
		$.messager.alert('提示', '该账号还未开户');
		return false;
	}
	$('#withAmount').textbox('setValue', '');
	$('#withdrawalsWin').dialog('open');
}

//提现
function withdrawals() {
	var row = $('#enterpriseAccountListGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条数据');
		return false;
	}
	
	if (!row.bankNo) {
		$.messager.alert('提示', '该账号还未开户');
		return false;
	}
	
	var amount = $('#withAmount').val();
	if(amount != null && amount > 0){
		$.ajax({
			url : '/rest/bank/enterpriseAccount/withdrawals',
			type : 'post',
			data : {companyNameId : row.id, amount: amount},
			success : function (data) {
				if (data) {
					submitBankPostForm(data);
					$('#withdrawalsWin').dialog('close');
					$.messager.alert("提示", "请前往银行页面操作");
					$('#enterpriseAccountListGrid').datagrid('reload');
				} else {
					$.messager.alert("提示", "系统错误，请刷新后重试");
				}
			}
		});
	}else{
		$.messager.alert("提示", "请输入提现金额");
	}
}

function queryBalance() {
	var row = $('#enterpriseAccountListGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条数据');
		return false;
	}
	
	if (!row.bankNo) {
		$.messager.alert('提示', '该账号还未开户');
		return false;
	}
	
	$.ajax({
		url : '/rest/bank/enterpriseAccount/queryCompanyBalance',
		type : 'post',
		data : {companyNameId : row.id},
		success : function (data) {
			if (data.code == 200) {
				$.messager.alert('提示', data.obj.ACNAME + "的可用余额为:" + data.obj.AVAILABLEBAL + "，冻结金额为：" + data.obj.FROZBL + ",账户余额为:" + data.obj.ACCTBAL);
			} else {
				$.messager.alert('提示', '查询无结果');
			}
		}
	});
}

function unBindCard() {
	var row = $('#enterpriseAccountListGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条数据');
		return false;
	}
	
	if (!row.bankNo) {
		$.messager.alert('提示', '该账号还未开户');
		return false;
	}
	
	$.messager.confirm('提示', '确定要解绑该银行卡？', function(b) {
		if(b) {
			$.ajax({
				url : '/rest/bank/enterpriseBindCard/unBindCard',
				type : 'post',
				data : {bankNo : row.bankNo},
				success : function (data) {
					if (data) {
						submitBankPostForm(data);
						$.messager.alert("提示", "请前往银行页面操作");
					} else {
						$.messager.alert("提示", "系统错误，请刷新后重试");
					}
				}
			});
		}
	});
}

function closeTransactorDiv() {
	$('#listTransactorDiv').dialog('close');
}

function listTransactor() {
	var row = $('#enterpriseAccountListGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条数据');
		return false;
	}
	
	$('#listTransactorDiv').dialog('open');
	$('#listTransactorGrid').datagrid('load', {
		eacId : row.id
	});
}

function queryChangeTransactorResult() {
	var row = $('#listTransactorGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条数据');
		return false;
	}
	
	$.ajax({
		url : '/rest/bank/hxEnterpriseAgent/queryResult',
		type : 'post',
		data : {id : row.id},
		success : function(data) {
			$.messager.alert('提示', data.msg);
			if(data.code == 200) {
				$('#listTransactorGrid').datagrid('load', {
					eacId : row.eacId
				});
				$('#enterpriseAccountListGrid').datagrid('reload');
			}
		}
	});
}

function changeTransactor() {
	var row = $('#enterpriseAccountListGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条数据');
		return false;
	}
	
	if (!row.bankNo) {
		$.messager.alert('提示', '该账号还未开户');
		return false;
	}
	
	$.ajax({
		url : '/rest/bank/hxEnterpriseAgent/updateInfo',
		type : 'post',
		data : {bankNo : row.bankNo},
		success : function (data) {
			if (data) {
				submitBankPostForm(data);
				$.messager.alert('提示', '请前往银行操作页面，完成后点击《企业经办人信息变更结果查询》可以查询开户结果');
				
			} else {
				$.messager.alert('提示', '系统错误，请刷新后重试');
			}
		}
	});
}

function queryBindCard() {
	var row = $('#enterpriseAccountListGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条数据');
		return false;
	}
	
	if (row.bankNo == null || row.bankNo == '') {
		$.messager.alert('提示', '该账号还未开户成功');
		return false;
	}
	
	$('#enterpriseBindCardDialog').dialog('open');
	var options = $('#enterpriseBindCardGrid').datagrid('options');
    options.url = '/rest/bank/enterpriseBindCard/listEnterpriseBindCard';
    options.queryParams = { acNo: row.bankNo, page: 1, rows : 10 };
    $('#enterpriseBindCardGrid').datagrid(options);
	
}

function queryResult() {
	var row = $('#enterpriseAccountListGrid').datagrid('getSelected');
	if (row == null) {
		$.messager.alert('提示', '请先选择一条数据');
		return false;
	}
	
	$.ajax({
		url : '/rest/bank/enterpriseAccount/queryAccountOpenResult',
		type : 'post',
		data : {id : row.id},
		success : function (data) {
			$.messager.alert('提示', data.msg);
			if (data.code == 200) {
				$('#enterpriseAccountListGrid').datagrid('reload');
			}
		}
	});
	
}

function addAccount() {
	$('#companyNameForm').textbox('setValue', '');
	$('#occCodeNoForm').textbox('setValue', '');
	$('#addAccountDiv').dialog('open');
}

function serach() {
	$('#enterpriseAccountListGrid').datagrid('load', {
		companyName : $('#companyName').textbox('getValue'),
		bankNo : $('#bankNo').textbox('getValue')
	});
}

function requestAccountOpen() {
	var companyName = $('#companyNameForm').textbox('getValue');
	var occCodeNo = $('#occCodeNoForm').textbox('getValue');
	
	if (companyName == '' || occCodeNo == '') {
		$.messager.alert('提示', '请填写完整信息');
		return false;
	} 
	
	$.ajax({
		url:'/rest/bank/enterpriseAccount/requestAccountOpen',
		data:{companyName : companyName, occCodeNo : occCodeNo},
		type:'post',
		success: function(data) {
			if (data) {
				submitBankPostForm(data);
				$('#addAccountDiv').dialog('close');
				$.messager.alert('提示', '请前往银行操作页面，完成后点击《开户结果查询》可以查询开户结果');
				$('#enterpriseAccountListGrid').datagrid('reload');
			} else {
				$.messager.alert('提示', '申请失败，请验证信息无误后重试');
			}
		}
	});
}

function submitBankPostForm(data) {
	$("#bankPostForm").attr("action", data.bankUrl)
	$("#requestData").val(data.requestData);
	$("#transCode").val(data.transCode);
	$("#bankPostForm").submit();
}

function cancelAccountOpen() {
	$("#accountOpenForm").form('clear');
	$('#addAccountDiv').dialog('close');
}

function cancelWithdrawalsWin() {
	$('#withdrawalsWin').dialog('close');
}

function statusFormat(value) {
	if (value == 0) {
		return "<font color='gray'>审核中</font>";
	} else if (value == 1) {
		return "<font color='green'>审核通过</font>";
	} else if (value == 2) {
		return "<font color='red'>审核失败</font>";
	}
}

function formatTimers(value){
	if(value){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}

function cardTypeFormatter(value) {
	if (value == "0") {
		return "借记卡";
	}
}

function cardStateFormatter(value) {
	if (value == 0) {
		return "绑定";;
	} else if (value == 2) {
		return "预绑定";
	} else {
		return "未知";
	}
}

function otherBankFlagFormatter(value) {
	if (value == 0) {
		return "本行";;
	} else {
		return "他行";
	}
}

