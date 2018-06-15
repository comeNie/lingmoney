$.event.special.valuechange = {

  teardown: function (namespaces) {
    $(this).unbind('.valuechange');
  },

  handler: function (e) {
    $.event.special.valuechange.triggerChanged($(this));
  },

  add: function (obj) {
    $(this).on('keyup.valuechange cut.valuechange paste.valuechange input.valuechange', obj.selector, $.event.special.valuechange.handler)
  },

  triggerChanged: function (element) {
    var current = element[0].contentEditable === 'true' ? element.html() : element.val()
      , previous = typeof element.data('previous') === 'undefined' ? element[0].defaultValue : element.data('previous')
    if (current !== previous) {
      element.trigger('valuechange', [element.data('previous')])
      element.data('previous', current)
    }
  }
}


var purchaseInterval;
$(function() {
	
	//验证码输入框改变事件，设置确认按钮可用
	/*$(".message-code").on('input',function(){
		$("#rz-box-ok").removeClass("active");
	});*/
	$('.message-code').on('valuechange', function (e, previous) {
		$("#rz-box-ok").removeClass("active");
    })
	
	$("#alert .rz-button.ok,#alert .rz-close").click(function(){
		$("#alert").hide();
		$("#rz-box-bg").hide();
	});
	$("#yzmCode").click(function(e){
		$('.btn-zhifu').addClass('active');
		clearInterval(purchaseInterval);
		var i = 59;
		$("#yzmCode").hide();
		$("#yzmCodeShadow").css("display","inline-block").html("重新获取验证码(60)");
		purchaseInterval = setInterval(function(){
			$("#yzmCodeShadow").css("display","inline-block").html("重新获取验证码("+(i--)+")");
			if(i==-1){
				clearInterval(purchaseInterval);
				
				$("#yzmCode").show();
				$("#yzmCodeShadow").hide();
			}
		},1000);
		quickPaymentSign();
	});
	
	 $('.onlineBankSel a').on("click",function(e){
		  $(".xianDiv").slideDown(300);
		  $('#onlineBankQuota').empty();
		  $('#onlineBankQuota').append($(this).find('span').html());
		  e.stopPropagation()
	 })
	 $("body").on("click",function(e){
		 if(e.currentTarget.tagName==="A" && $(e.currentTarget).parent().attr("class")==="onlineBankSel"){
			 return
		  }
		  if($(e.currentTarget).attr("class")==="xianDiv"){
			  return
		  }
		  $(".xianDiv").slideUp();
	  })
	
	//选择绑定的快捷支付银行下拉
	$(".pop div").on("click", function() {
		$(".pop div").removeClass("select");
		$(this).addClass("select");
		$(".div-input dt").html($(this).html());
	});

	// 阻止事件冒泡
	$(".p-value-inner").on("click", function(e) {
		$(".p-value .pop").slideDown();
		e.stopPropagation();
	})

	$(document).on("click", function(e) {
		e.target = e.target || e.srcElement;
		if ($(e.target).prop("class") === "pop") {
			return;
		} else {
			$(".p-value .pop").slideUp();
		}
	});

	var links = $(".main-zhifu .bottom .tab li a");
	var divs = $(".main-zhifu .bottom .tabContent>div");
	for (var i = 0; i < links.length; i++) {
		(function(i) {
			var curA = links[i];
			curA.index = i;
			$(curA).on("click", function() {
				for (var j = 0; j < links.length; j++) {
					$(links[j]).removeClass("active");
					$(divs[j]).removeClass("active")
					if (i == 0) {
						$(".btn-zhifu").text("立即支付");
						$(".btn-zhifu").val("0");
						$(".btn-zhifu").removeClass('noClick');
					} else {
						$(".btn-zhifu").text("网银支付");
						$(".btn-zhifu").val("1");
						$(".btn-zhifu").addClass('noClick');
					}
				}
				$(this).addClass("active");
				$(divs[this.index]).addClass("active")
			});
		})(i)
	}

	//跳转到绑定银行卡页面
	$("#addCard").on("click", function() {
		window.open("/myAccount/bindBankCard.html");
	})

	$(".rz-close,.box-message-close").on("click", function() {
		$("#rz-box-bg").hide();
		$(".rz-box-con").hide();
		//clearInterval(purchaseInterval);
	})
	
	// 单击支付按钮
	$(".btn-zhifu").click(function() {
		var n = $(this).val();
		if (n == 0) {
			var quota = $('#selectBank span em').text().replace('万','0000').replace('千','000');
			var amount = $('.p-amount strong').html();
//			console.log(parseFloat(amount) + "   " +  parseFloat(quota) + "")
			if(parseFloat(amount) > parseFloat(quota)){//判断购买金额是否大于限额
				$("#rz-box-bg").show();
				$(".rz-box-con.box-limit").show();
				offsetDiv(".box-limit");
			}else{
				if(!$(this).hasClass("active")){
					$(this).addClass('active')
				}else{
					return false;
				}
				$("#yzmCode").click();
			}
		}
		
		if (n == 1) {
			onlinePayment();
		}
	})
	
	$(".rz-button.ok").on("click",function(){
		$(".box-limit").hide();
		$("#rz-box-bg").hide();
	})
	
	//重新获取验证码
	$('.re-cquisition').on('click', function(){
		quickPaymentSign();
	})
	
	// 输入验证码后确认支付
	$('#rz-box-ok').on('click', function() {
		if($('#rz-box-ok').hasClass('active')){
			return false;
		}
		$('.box-input span').html('正在提交，请耐心等待......');
		quickPaymentPay();
	});

	// 清空验证码
	$('#rz-box-clear').on('click', function() {
		$('.message-code').attr('value', '');
		$("#rz-box-ok").removeClass("active");
	});
	
	//网银支付
	$('.onlineBankSel a').on('click', function(){
		$('.onlineBankSel').children('a').attr('data-value', '');
		$('.onlineBankSel').children('a').find('img').css('opacity', 0.6);

		$(this).attr('data-value', '1');
		$(this).find('img').css('opacity', 1);
	})
});

function onlinePayment(){
	
	var onlineBankNo = $('.onlineBankSel a[data-value=1]').find('input').val();
	var dizNumber = $('#dizNumber').val();
	var strong = $('.p-amount strong').html();
	var tId = $('#tId').val();
	var infoId = $('#infoId').val();
	var pType = $('#pType').val();
	
	$.ajax({
		type : "post",
		data : {
			"onlineBankNo" : onlineBankNo,
			"dizNumber" : dizNumber,
			"strong" : strong,
			"tId" : tId,
			"infoId" : infoId,
			"pType" : pType
		},
		url : "/purchase/onlinePayment.html",
		dataType : "json",
		success : function(d) {
			if(d.code == 200){
				window.location.href = d.msg;
			}else{
				selfAlert(d.msg);
			}
		}
	});
}

//快捷支付
function quickPaymentPay(){
	var telCode = $('.message-code').val();
	
	//console.log(telCode);
	// 验证验证码格式
	if (telCode == "" || telCode.length < 6) {
		// 短信验证码错误，请您核对或重新获取。
		$('.box-input p span').html("短信验证码格式错误，请输入6位短信验证码。")
			return false;
		}
		
		if($('#rz-box-ok').hasClass('active')){
			return false;
		}
		
		
		var userBankId = $('#selectBank input[name="userBankId"]').val();
		var dizNumber = $('#dizNumber').val();
		var strong = $('.p-amount strong').html();
		var tId = $('#tId').val();
		var infoId = $('#infoId').val();
		var pType = $('#pType').val();
		var pCode = $('#pCode').val();
		
		$('#rz-box-ok').addClass('active');
		
		$.ajax({
			type : "post",
			data : {
				"userBankId" : userBankId,
				"dizNumber" : dizNumber,
				"strong" : strong,
				"telCode" : telCode,
				"tId" : tId,
				"pCode" : pCode,
				"infoId" : infoId,
				"pType" : pType
			},
			url : "/purchase/quickPaymentPay",
			dataType : "json",
			success : function(d) {
				//$('#rz-box-ok').removeClass('active');
				if(d.code == 200){
					window.location.href = "/product/showPayResult";
				}else{
					$('.box-input span').html(d.msg);
					clearInterval(purchaseInterval);
					$("#yzmCode").show();
					$("#yzmCodeShadow").hide();
				}
			}
		});
}

//支付获取短信验证码
function quickPaymentSign(){
	var userBankId = $('#selectBank input[name="userBankId"]').val();
	var dizNumber = $('#dizNumber').val();
	var strong = $('.p-amount strong').html();
	var userBankTel = $('#selectBank input[name="userBankTel"]').val();
	var pType = $('#pType').val();
	var tId = $('#tId').val();
	var pCode = $('#pCode').val();
	
	$.ajax({
		type : "post",
		data : {
			"userBankId" : userBankId,
			"dizNumber" : dizNumber,
			"strong" : strong,
			"tId" : tId,
			"pCode" : pCode,
			"pType" : pType
		},
		url : "/purchase/quickPaymentSign",
		dataType : "json",
		success : function(d) {
			if (d.code == 200) {
				$('.btn-zhifu').removeClass('active');
				$('.message-code').val('');
				$("#rz-box-bg").show();
				$(".rz-box-con.box-message").show();
				$('.bank_tel').text("(" + userBankTel.substring(0,3) + "****" + userBankTel.substring(7,11) + ")")
				offsetDiv(".rz-box-con.box-message");
				
				$('.box-input span').html('短信验证码已发送，请注意查收');
			} else {
				$(".btn-zhifu").removeClass('active');
				selfAlert(d.msg);
			}
		}
	});
}

/**
 *自定义弹出框
 *title 标题
 *message 内容 
 *type 不同位置的alert确认事件
 */
function selfAlert(message,title){
	if(title){
		$("#alert h2").html(title);
	}
	$("#alert div:eq(1)").html(message);
	$("#alert").show();
	$("#rz-box-bg").show();
	offsetDiv("#alert");
}

