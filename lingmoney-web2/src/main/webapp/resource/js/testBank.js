$(function () {
	//登录
	$('#login').click(function () {
		loginFun();
	})

	//开户
	$('#accountOpenSubmit').click(function () {
		accountOpenFun();
	})

	//绑卡
	$('#bindCardSubmit').click(function () {
		bindCardFun();
	})

	//充值
	$('#rechargeSubmit').click(function () {
		rechargeFun();
	})

	//投标
	$('#biddingSubmit').click(function () {
		biddingFun();
	})

	//提现
	$('#withdrawSubmit').click(function () {
		withdrawFun();
	})

	//修改交易密码
	$('#modiTradPawSubmit').click(function () {
		modiTradPawFun();
	})

	//验证用户登录状态
	$('#verifylogin').click(function () {
		verifyloginFun();
	})

	//退出
	$('#loginOut').click(function () {
		loginOutFun();
	})

	//查询绑定银行卡
	$('#querybindCardSubmit').click(function () {
		queryBindCardFun();
	})

	//修改绑定银行卡
	$('#modifybindCardSubmit').click(function () {
        modifyBindCardFun();
	})
})

function modifyBindCardFun(){
	$.ajax({
		type: "post",
		data: {},
		url: "/bank/changePersonalBindCard",
		dataType: "json",
		success: function (data) {
			console.log(data);
			if (data.code == 200) {
				$("#accountOpenForm").attr("action", data.obj.bankUrl);
				$("#accountOpenRequestData").val(data.obj.requestData);
				$("#accountOpenTransCode").val(data.obj.transCode);
				$("#channelFlow").val(data.obj.channelFlow);
				$("#accountOpenForm").submit();
			} else {
				$("#errorDiv").show();
				$("#sorryMsg").html(data.msg);
			}
		}
	});
}

function queryBindCardFun() {
	$.ajax({
		type: "post",
		data: {},
		url: "/bank/queryBindCardInfo",
		dataType: "json",
		success: function (data) {
			console.log(data);
			$("#errorDiv").show();
			$("#sorryMsg").html(data.msg + data.obj.cardno);
		}
	});
}

function loginOutFun() {
	$.ajax({
		type: "post",
		data: {},
		url: "/users/logout",
		dataType: "json",
		success: function (data) {
			console.log(data);
			$("#errorDiv").show();
			$("#sorryMsg").html(data.msg);
		}
	});
}

function verifyloginFun() {
	$.ajax({
		type: "post",
		data: {},
		url: "/users/queryUserStatus",
		dataType: "json",
		success: function (data) {
			console.log(data);
			$("#errorDiv").show();
			$("#sorryMsg").html(data.msg);
		}
	});
}

function modiTradPawFun() {
	$.ajax({
		type: "post",
		data: {},
		url: "/bank/modifyTradingPassword",
		dataType: "json",
		success: function (data) {
			console.log(data);
			if (data.code == 200) {
				$("#accountOpenForm").attr("action", data.obj.bankUrl);
				$("#accountOpenRequestData").val(data.obj.requestData);
				$("#accountOpenTransCode").val(data.obj.transCode);
				$("#channelFlow").val(data.obj.channelFlow);
				$("#accountOpenForm").submit();
			} else {
				$("#errorDiv").show();
				$("#sorryMsg").html(data.msg);
			}
		}
	});
}

function withdrawFun() {
	$.ajax({
		type: "post",
		data: {
			amount: $('#withdrawAmount').val()
		},
		url: "/bank/cashWithdraw",
		dataType: "json",
		success: function (data) {
			console.log(data);
			if (data.code == 200) {
				$("#accountOpenForm").attr("action", data.obj.bankUrl);
				$("#accountOpenRequestData").val(data.obj.requestData);
				$("#accountOpenTransCode").val(data.obj.transCode);
				$("#channelFlow").val(data.obj.channelFlow);
				$("#accountOpenForm").submit();
			} else {
				$("#errorDiv").show();
				$("#sorryMsg").html(data.msg);
			}
		}
	});
}

function biddingFun() {
	$.ajax({
		type: "post",
		data: {
			tId: $('#biddingTid').val()
		},
		url: "/bank/singleBidding",
		dataType: "json",
		success: function (data) {
			console.log(data);
			if (data.code == 200) {
				$("#accountOpenForm").attr("action", data.obj.bankUrl);
				$("#accountOpenRequestData").val(data.obj.requestData);
				$("#accountOpenTransCode").val(data.obj.transCode);
				$("#channelFlow").val(data.obj.channelFlow);
				$("#accountOpenForm").submit();
			} else {
				$("#errorDiv").show();
				$("#sorryMsg").html(data.msg);
			}
		}
	});
}

function rechargeFun() {
	$.ajax({
		type: "post",
		data: {
			amount: $('#rechargeAmount').val()
		},
		url: "/bank/accountRecharge",
		dataType: "json",
		success: function (data) {
			console.log(data);
			if (data.code == 200) {
				$("#accountOpenForm").attr("action", data.obj.bankUrl);
				$("#accountOpenRequestData").val(data.obj.requestData);
				$("#accountOpenTransCode").val(data.obj.transCode);
				$("#channelFlow").val(data.obj.channelFlow);
				$("#accountOpenForm").submit();
			} else {
				$("#errorDiv").show();
				$("#sorryMsg").html(data.msg);
			}
		}
	});
}

function bindCardFun() {
	$.ajax({
		type: "post",
		data: {},
		url: "/bank/tiedCard",
		dataType: "json",
		success: function (data) {
			console.log(data);
			if (data.code == 200) {
				$("#accountOpenForm").attr("action", data.obj.bankUrl);
				$("#accountOpenRequestData").val(data.obj.requestData);
				$("#accountOpenTransCode").val(data.obj.transCode);
				$("#channelFlow").val(data.obj.channelFlow);
				$("#accountOpenForm").submit();
			} else {
				$("#errorDiv").show();
				$("#sorryMsg").html(data.msg);
			}
		}
	});
}

function accountOpenFun() {
	$.ajax({
		type: "post",
		data: {
			acName: $('#accountOpenName').val(),
			idNo: $('#accountOpenIdCard').val(),
			mobile: $('#accountOpenMobile').val()
		},
		url: "/bank/accountOpen",
		dataType: "json",
		success: function (data) {
			console.log(data);
			if (data.code == 200) {
				$("#accountOpenForm").attr("action", data.obj.bankUrl);
				$("#accountOpenRequestData").val(data.obj.requestData);
				$("#accountOpenTransCode").val(data.obj.transCode);
				$("#channelFlow").val(data.obj.channelFlow);
				$("#accountOpenForm").submit();
			} else {
				$("#errorDiv").show();
				$("#sorryMsg").html(data.msg);
			}
		}
	});
}

function loginFun() {
	$.ajax({
		type: "post",
		data: {
			account: $('#account').val(),
			password: $('#password').val()
		},
		url: "/users/login",
		dataType: "json",
		success: function (data) {
			$("#errorDiv").show();
			$("#sorryMsg").html(data.msg);
		}
	});
}