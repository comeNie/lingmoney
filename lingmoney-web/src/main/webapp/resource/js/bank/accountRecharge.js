$(function() {
	// 是否点击标识，防止连点
	var isClick = false;
	
	$("#accountRechargeTab").addClass("hover");
	$("#nav_member02").addClass("nav-hover");
	
	$(".rz-close, .rz-button").click(function(){
		$("#rz-box-bg").hide();
		$("#div_customer").hide();
		$("#div_customer_tip").hide();
	});
	
	$("#money").keyup(function() {
		checkMoney();
	});
	
	$("#money").change(function() {
		checkMoney();
	});
	
	$('.rechargeinput').keyup(function(){
		$('#money').val($(this).val())
	})
	$('.rechargeBtn-btn').click(function(){
		$(".rechargeBtn").click()
	})
	// 金额是否合法
	function checkMoney() {
		$('#sp').html("");
		var money = $('#money').val(); //起投金额 
		var regex = /^([1-9]\d*|0)(\.\d{1,2})?$/;
		if (regex.test(money)) {
//			if(Number(money) < 1){// TODO 100
//				$('#sp').css("display", "inline-block");
//				$('#sp').html("<font color='red'>最低充值金额为1元!</font>");
//				return false;
//			}
//			$('#sp').css("display", "none");
			return true;
		} else {
			$('#sp').css("display", "inline-block");
			$('#sp').html("<font color='red'>输入的金额不符合要求!</font>");
			return false;
		}
	}
	
	// 点击充值按钮
	$(".rechargeBtn").click(function(){
		if(isClick == true){
			return false;
		}
		
		if (checkMoney()) {
			// 输入金额
			var money = $("#money").val();
			isClick = true;
			$.ajax({
				url : '/bank/accountRecharge',
				type : 'post',
				data : {amount : Number(money).toFixed(2)},
				success : function (d) {
					if(d.code==3009){
						$('#div_customer_tip').find('.rz-button').prop('href','/users/login');
						cusAlert(d.msg);
						isClick = false;
						return false;
					}
					if(d.code==3012||d.code==207){
						$('#div_customer_tip').find('.rz-button').prop('href','/myAccount/bindBankCard');
						cusAlert(d.msg);
						isClick = false;
						return false;
					}
					if (d.code != 200) {
						cusAlert(d.msg);
						isClick = false;
						return false;
					}
					// 组织表单，提交数据
					$('#accountRecharge').attr('action',d.obj.bankUrl);
					$("#requestData").val(d.obj.requestData);
					$("#transCode").val(d.obj.transCode);
					$("#accountRecharge").submit();
					// 页面弹框
					$("#div_customer").show();
					$("#rz-box-bg").show();
					offsetDiv("#div_customer");
					$("#div_customer h2").text("请在新页面完成充值");
					$("#div_customer p:eq(0)").html('完成充值前请<span style="margin: 0 5px; color: #ea5513">不要关闭此窗口</span>，完成充值后根据您的情况点击下面按钮<br><a shape="rect" coords="0,75,265,151" href="/hxbankOpertion" style="padding-top:15px;display:block">了解更多，请查看银行存管操作指南>></a>');
					$("#div_customer p:eq(1) a:eq(0)").html('已完成充值').attr('target','_self').attr('href','/bank/queryAccountRecharge?number='+d.obj.channelFlow).click(function(){$('.rz-close').trigger('click')});
					$("#div_customer p:eq(1) a:eq(1)").html('充值遇到问题').attr('target','_self').attr('href','/helpCenterGuide').click(function(){$('.rz-close').trigger('click')});
					isClick = false;
				},
				error : function(d){
					isClick = false;
				}
			});
		}
	});
	
	var accountOpen = $("#accountOpen").val();
	var bindBank = $("#bindBank").val();
	
	if (accountOpen == 0 || accountOpen == 1) {
		showHXDialog(0);
	} else if (bindBank == 0 || bindBank == 1) {
		showHXDialog(1);
	}
	
});

//自定义alert
function cusAlert(msg) {
	$("#rz-box-bg").show();
	$("#div_customer_tip").show();
	offsetDiv("#div_customer_tip");
	$("#div_customer_tip h2").text("提示");
	$("#div_customer_tip p:eq(0)").html(msg);
}
