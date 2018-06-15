$(document).ready(function() {
	checkHxAccount();
	
	//部分赎回提示框，取消按钮和关闭按钮执行事件。遮罩层和部分赎回提示框隐藏。
	$("#cancelPortionSell, #closePortionSell").click(function(){
		$("#rz-box-bg, #portionSellDiv").hide();
	});
	
	$(".rz-close, #ok_button").click(function(){
		$("#rz-box-bg,#trade,#div_customer,#div_customer_tip").hide();
	});
	$("#latestTrade").click(function() {
		offsetDiv("#trade");
		$(".btn_trade").trigger("click");
		$(this).addClass("hover");
		$("#allTrade").removeClass("hover")
	});
	$("#allTrade").click(function() {
		offsetDiv("#trade");
		$("#allTradeFlow").trigger("click");
		$(this).addClass("hover");
		$("#latestTrade").removeClass("hover")
	});
	$("#finanCialManageTab").addClass("hover");
	$("#nav_member02").addClass("nav-hover");
	$(".main-head li:last").css("border", "none");
	$(".main3 li:last").css("margin-right", "0");
	$(".licai li:last").css("margin-right", "0");

	/* 顶部 */

	$(".media-link li:first").hover(function() {
		$(this).find("div").show();
	}, function() {
		$(this).find("div").hide();
	})
	/* 顶部 */

	/* 理财管理 */

	$(".div_manage1").click(function() {
		$(this).next(".div_manage2").toggle();
	})

	$(".manageItem_con").click(function() {
		$(this).next(".manageItem_bottom").toggle();
	})

	// 债权转让协议
	$("#assignmentOfCredit").click(function() {
		$("#rz-box-bg").show();
		$("#assignmentOfCreditDialog").show();
		offsetDiv("#assignmentOfCreditDialog");
	});
	
	$(".a_go1").click(function() {
		$("#rz-box-bg").hide();
		$("#assignmentOfCreditDialog").hide();
		$(".input_agree").attr("checked",true);
		$(".toPay").attr("disabled", false);
		$(".toPay").css({
			"cursor" : "pointer",
			"background" : "#ea5513",
			"color" : "#fff"
		});
	});
	$(".rz-close1").click(function() {
		$("#rz-box-bg").hide();
		$("#assignmentOfCreditDialog").hide();
	});
	
	$(".reset,.button_esc,.rz-close").click(function() {
		$("#rz-box-bg").hide();
		$("#ask").hide();
		$("#tui").hide();

		$("#wallet").hide();
		$("#fails").hide();
		$("#failss").hide();
		$("#wallet").hide();
		$("#AllSellDiv").hide();
		$("#walletSucc").hide();
		$("#nobindbank").hide();
	})
	$(".button_ok").click(function() {
		
		$("#nobindbank").hide();
		$("#rz-box-bg").hide();
	})
	$(".reset,.button_esc,,.rz-close").click(function() {
		$("#ask").hide();
		$("#tui").hide();
		$("#tou_xqd").hide();
		$("#wallet").hide();
		$("#fail").hide();
		$("#fails").hide();
		$("#failss").hide();
		$("#wallet").hide();
		$("#AllSellDiv").hide();
		$("#nobindbank").hide();
	})

	$(".input_agree").click(function() {
		if ($(this).attr("checked")) {
			$(".ok").attr("disabled", false);
			$(".ok").css({
				"cursor" : "pointer",
				"background" : "#ea5513",
				"color" : "#fff"
			});
		} else {
			$(".ok").attr("disabled", true);
			$(".ok").css({
				"cursor" : "default",
				"background" : "#eee",
				"color" : "#535353"
			});
		}
	})

	var status = $("#status").val();
	if (status == '3') {
		$("#quanb3").addClass("hover");
	} else if (status == '0') {
		var theRestTimeOfPayment = $("#theRestTimeOfPayment").val();
		if (theRestTimeOfPayment > 0) {
			theRestOfTime = theRestTimeOfPayment;
			paymentCountDown();
			paymentCountDownInterval = setInterval("paymentCountDown()", 1000);
		} else {
			$("#theRestTimeOfPayment").next().text("00:00:00");
			$(".input_agree").attr("disabled", true);
			$(".toPay").attr("disabled", true);
			$(".toPay").css({
				"cursor" : "default",
				"background" : "#eee",
				"color" : "#535353"
			});
			$("#assignmentOfCredit").unbind("click");
		}
		
		$("#quanb0").addClass("hover");
	} else if (status == '4') {
		$("#quanb4").addClass("hover");
	} else if (status == '2') {
		$("#quanb2").addClass("hover");
	} else if (status == '12') {
		$("#quanb12").addClass("hover");
	} else {
		$("#quanb1").addClass("hover");
	}
			
	$("#money_input").keyup(function() {
		tests();
	});
	
	var date =$("#runDate").val(); 
	if (date!=null&& date!=''){
			var left=0;
			if(date==0){
			   left=-8;
			}else if(date>0&&date<=224){
			   left=-8+date*3;
			}else if(date>224){
			   left=-8+224*3+(52/2);
			}
			$(".s_progress .ani").css("left",left);
			$(".s_progress p").html(date);
	}
	
	$(".input_agree").click(function() {
		if ($(this).attr("checked")) {
			$(".toPay").attr("disabled", false);
			$(".toPay").css({
				"cursor" : "pointer",
				"background" : "#ea5513",
				"color" : "#fff"
			});
		} else {
			$(".toPay").attr("disabled", true);
			$(".toPay").css({
				"cursor" : "default",
				"background" : "#eee",
				"color" : "#535353"
			});
		}
	})
});


//支付倒计时
var theRestOfTime = -1;
//倒计时interval
var paymentCountDownInterval;
function paymentCountDown(){
	theRestOfTime = theRestOfTime - 1000;
    var m = 0;
    var s = 0;
    if(theRestOfTime >= 0){
      m = Math.floor(theRestOfTime / 1000 / 60 % 60);
      s = Math.floor(theRestOfTime / 1000 % 60);
      if (m < 10) {
    	  m = "0" + m;
      }
      if (s < 10) {
    	  s = "0" + s;
      }
      $("#theRestTimeOfPayment").next().text("00:" + m + ":" + s);
    } else {
    	window.clearInterval("paymentCountDownInterval");
    	theRestOfTime = -1;
    	window.location.reload();
    }
}

//随心取交易流水查询 
function btn_trade(pId,tId,limit){
	var url = "";
	if(typeof(limit) == "undefined"){
		url = "/myFinancial/takeheartTransactionFlow?pId="+pId+"&tId="+tId;
	}else{
		url = "/myFinancial/takeheartTransactionFlow?pId="+pId+"&tId="+tId+"&limit="+limit;
	}
	$.ajax({
		type : 'POST',
		url : url,
		dataType : 'json',
		success : function(data) {
			var html = "";
			if (data != null) {
				$.each(data.data, function(i, item) {
					var minute = (new Date()-item.operateTime.time)/1000/60;
					var tr_class = '';
					var typeStr = '';
					var stateStr = '';
					var moneyDouble = 0;
					var cardPan = '';
					//交易类型：0买,1卖,2付息
					if (item.type == 0) {
						tr_class = 'bg-tz'; // bt-tz投资 ,bg-cz充值 ,bg-tx提现 ,bg-hk还款
						typeStr = "申购";
						moneyDouble = item.money;
					}
					if (item.type == 1) {
						tr_class = 'bg-hk'; // bt-tz投资 ,bg-cz充值 ,bg-tx提现 ,bg-hk还款
						typeStr = "赎回";
						moneyDouble = item.money;
					}
					if (item.type == 2) {
						tr_class = 'bg-tx'; // bt-tz投资 ,bg-cz充值 ,bg-tx提现 ,bg-hk还款
						typeStr = "付息";
						moneyDouble = item.interest;
					}
					//状态：0失败,1成功,2待处理,3冻结状态,18失败 
					if(item.state == 0 || item.state == 18){
						stateStr = '失败';
					}
					if(item.state == 1){
						stateStr = '成功';
					}
					if(item.state == 2){
						if(item.type == 0){
							if(minute>30){
								stateStr = '申购失败';
							}else{
								stateStr = '待处理';
							}
						}else{
							stateStr = '待处理';
						}
						
					}
					if(item.state == 3){
						if(minute>30){
							stateStr = '失败';
						}else{
							stateStr = '处理中';
						}
					}
					if(item.state == 15){
						stateStr = '赎回中';
					}
					if(item.cardPan!=null && item.cardPan!='') {
						cardPan = item.cardPan;
						cardPan = '尾号'+cardPan.substring(cardPan.length-4,cardPan.length);
					}
					
					html += '<tr class=' + tr_class + '>';
					html += '<td width="110">' + format(item.operateTime.time,'yyyy-MM-dd HH:mm:ss')+ '</td>';
					if(item.payEndTime != null){
						html += '<td width="110">' + format(item.payEndTime.time,'yyyy-MM-dd HH:mm:ss')+ '</td>';
					}else{
						html += '<td width="110"></td>';
					}
					html += '<td width="50">' + typeStr + '</td>';
					html += '<td width="50">' + stateStr + '</td>';
					html += '<td width="70">' + moneyDouble + '</td>';
					html += '<td width="70">' + cardPan + '</td>';
					html += '</tr>';
				});
			}
			$("#takeHeartFlow").html("");
			$("#takeHeartFlow").html(html);
		},
		error : function(er) {}
	});
	offsetDiv("#trade");
	$("#rz-box-bg,#trade").show();
	$("#latestTrade").addClass("hover");
	$("#allTrade").removeClass("hover")
}

//理财状态点击事件
function copeStatus(val) {
	if (val != null) {
		$("#status").val(val);
	}
	submit();
}

function submit() {
	$("#queryForm").submit();
}

// 随心取赎回
function shuhui(tid,model,money) {
	var isBindBank = $("#isBindBank").val();
	if(isBindBank < 1){//没绑卡
		offsetDiv("#nobindbank");
		$("#rz-box-bg").show();
		$("#nobindbank").show();
		return false;
	}
	if (model==1){
		var noCache = Date();
		//使用Agax 获取该产品的模式
		$("#productmodel").val(1);
		/* $("#redeemType").val(1); */
		$("#tid").val(tid);
		
		offsetDiv("#wallet");
		$("#AllMoney").html(fmoney(money,2)+"元");
		$("#AllMoneyT").val(fmoney(money,2));
		$("#checkMoney").val(money);
					
		$("#rz-box-bg").show();
		$("#wallet").show();
		$("#money_input").val("");
		
		var redeemType = $("#redeemType").val();
		if (redeemType==''||redeemType==null) {
			$("#redeemType").val(1);
		}
	} else{
	     $("#tid").val(tid);
		 $("#productmodel").val(0);
		 $("#ask").show();
		 offsetDiv("#ask");
		 $("#rz-box-bg").show();
	}
}

//确认赎回 
function AllSellDiv() {
	var redeemType = $("#redeemType").val();
	if (redeemType == 1) {// 部分赎回
		//先判断输入金额是否有误
		if(!tests()) {
			return false;
		}
		// 弹确认框
		$("#sellMoneySure").text(fmoney($("#money_input").val(),2)+"元");
		offsetDiv("#portionSellDiv");
		$("#wallet").hide();
		$("#rz-box-bg").show();
		$("#portionSellDiv").show();
	}
	if (redeemType == 2) {// 全部赎回
		$("#takeValueID").text(
				(parseFloat($("#takeRate").val()) * 100).toFixed(2) + "%");
		offsetDiv("#AllSellDiv");
		$("#rz-box-bg").hide();
		$("#wallet").hide();
		$("#rz-box-bg").show();
		$("#AllSellDiv").show();
	}
}

//确认赎回 
function queding() {
	try {
		$("#AllSellDiv").hide();
		$("#portionSellDiv").hide();
		var model = $("#productmodel").val();
		var tid = $("#tid").val();
		var money_input= $("#money_input").val();//赎回份额
		var redeemType = $("#redeemType").val();
		if (model==1){
			if (redeemType==1) {
				if(!tests()) {
					return false;
				}
		    } else {
				money_input=0;
			}
			$("#rz-box-bg").hide();	
			$("#wallet").hide();
		}else{
			money_input='';
			redeemType='';
		}
		
	  	var url="/trading/sellProduct?tid=" + tid +"&moneyInput=" + money_input + "&redeemType=" + redeemType;
		
		offsetDiv("#opration");	
		$("#opration").show();
		
		$.ajax({ 
            type: "post", 
            url: url, 
            dataType: "text json", 
            success: function (data) {
		         $("#opration").hide(); 
		         if (data != null) {
					 if (data.flag == 0) {
						if (model==1) {
							offsetDiv("#walletSucc");
							$("#rz-box-bg").show();
							$("#walletSucc").show();
						} else {
							$('#buyDate').text(data.buyDate);
							$('#financialMoney').text(fmoney(data.buyMoney,2));
							$('#sellDate').text(data.sellDate);
							$('#sellMoney').text(fmoney(data.sellMoney,2));
							$('#sp').text(fmoney(data.feesRate,2));
							$('#pname').text(data.pName);
							$('#financialTime').text(data.dayCount);
							var inc = data.sellMoney - data.buyMoney;
							$('#income').text(fmoney(inc, 2));
							if (data.lCount != null && data.lCount > 0) {
								var l_text = "获得领宝： <span class='color'>" + data.lCount + "</span>个";
								$('#lingbaoNum').html(l_text);
							}
							var zhu_text = "<font  class='color'>注：此产品赎回当天不计收益</font>";
							$('#zhuy').html(zhu_text);
							$('#yield').text(data.pRate);
							offsetDiv("#tou_xqd");
							$("#rz-box-bg").show();
							$("#tou_xqd").show();
							return true;
						}
					} else {//赎回失败
						$("#opration").hide();
						$("#rz-box-bg").show();
						$("#error_content").html(data.msg);
						$("#fail").show();
						offsetDiv("#fail");
						return false;
					}
				} else {
					$("#opration").hide();
					$("#rz-box-bg").show();
					$("#error_content").html("您赎回此产品失败。网络连接问题,请重新操作。");
					$("#fail").show();
					offsetDiv("#fail");
					return false;
				}
			}, 
	        error: function (XMLHttpRequest, textStatus, errorThrown) { 
	            $("#opration").hide();
				$("#rz-box-bg").show();
			
				$("#error_content").html("您赎 回此产品失败。网络连接问题,请重新操作。");
				$("#fail").show();
				offsetDiv("#fail");
				return false;
	        } 
	    });
	} catch (ex) {
		$("#opration").hide();
		$("#rz-box-bg").show();
		$("#error_content").html("您赎回此产品失败。网络连接问题,请重新操作。");
		$("#fail").show();
		offsetDiv("#fail");
		return false;
	}
}

var format = function(time, format) {
	var t = new Date(time);
	var tf = function(i) {
		return (i < 10 ? '0' : '') + i
	};
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
		switch (a) {
		case 'yyyy':
			return tf(t.getFullYear());
			break;
		case 'MM':
			return tf(t.getMonth() + 1);
			break;
		case 'mm':
			return tf(t.getMinutes());
			break;
		case 'dd':
			return tf(t.getDate());
			break;
		case 'HH':
			return tf(t.getHours());
			break;
		case 'ss':
			return tf(t.getSeconds());
			break;
		}
	})
};

// 格式化金额
function fmoney(s, n) {
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	for (i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("") + "." + r;
}

// 赎回金额
$("#money_input").keyup(function() {
	tests();
});

function tests() {
	 //校验充值金额
	$("#sp").html("");
	var regex = /^([1-9][\d]{0,99}|0)(\.[\d]{1,2})?$/;
	var money_input = $("#money_input").val();
	var flag = false;
	if (regex.test(money_input)) {
          if(parseFloat(money_input) >0){
        	  if (parseFloat(money_input) >parseFloat($("#checkMoney").val())) {
        		 
  				$("#tianshi").css("display", "inline-block");
  				$("#tianshi").html(
  						"<font color='red'>赎回金额不能大于可提金额</font>");
  				flag = false;
  			}else if ((parseFloat($("#checkMoney").val())-parseFloat(money_input)) < 1000) {
    				$("#tianshi").css("display", "inline-block");
    				$("#tianshi").html(
    						"<font color='red'>部分赎回，余额不能低于最低档（1000元）</font>");
    				flag = false;
    		}else{
        			$("#tianshi").css("display", "none");
        			flag = true;
        	}
          }else{
			$("#tianshi").css("display", "inline-block");
			$("#tianshi").html("<font color='red'>赎回金额必须大于零</font>");
			flag = false;
          }
	} else {			
		$("#tianshi").css("display", "inline-block");
		$("#tianshi").html("<font color='red'>输入格式不正确！</font>");
		flag = false;
	}
	return flag; 
}

/**
 * 部分赎回1，全部赎回2
 */
function changeTab4(index) {
	for (var i = 1; i <= 2; i++) {
		if(index == 1) {
			$("#redeemType").val(1);
		} if(index == 2) {
			$("#redeemType").val(2);
		}
		
		document.getElementById ("fun_"+i).className ="normal";
		document.getElementById ("fun_"+index).className ="selected";
		document.getElementById ("dd"+i).style.display  ="none";
	}
	document.getElementById ("dd"+index).style.display  ="block";
}
var isClick = false;
// 去支付
function gotoPay(tid, pType) {
	var checked = $(".input_agree").is(':checked');
	
	if (checked && theRestOfTime > 0) {
		if(isClick == true){
			return false;
		}
		$("#paymentTid").val(tid);
		isClick = true;
		
		if (pType == 0) {
			 $.ajax({
		            type: "POST",
		            url: "/product/jdBuyProduct",
		            data: {tId : tid},
		            success: function (d) {
		           	if (d.code != 200) {
		           		cusAlert(d.msg);
							return false;
						}
		           		$("#jdTradingId").val(tid);
		           	    $("#jdActiveForm").attr("action","/product/jdBuyProductHtml");
	 				    $("#jdActiveForm").submit();
			           	$("#tou_xqd").hide();
			    		$("#div_cz").show();
			    		offsetDiv("#div_cz");
		            },
		            error: function(data) {
		                alert("error:"+data.responseText);
		            }
		        });
		 } else {
			 $.ajax({
		            type: "POST",
		            url: "/purchase/buyProductVersionOne",
		            data: $('#paymentForm').serialize(),
		            success: function (d) {
			           	if (d.code != 200) {
			           		cusAlert(d.msg);
			           		isClick = false;
							return false;
						}
						// 组织表单，提交数据
						$("#requestData").val(d.obj.requestData);
						$("#transCode").val(d.obj.transCode);
						$("#hxBankForm").attr("action",d.obj.bankUrl);
						$("#hxBankForm").submit();
						// 页面弹框
						$("#rz-box-bg").show();
						$("#div_customer").show();
						offsetDiv("#div_customer");
						$("#div_customer h2").text("请在新页面完成支付");
						$("#div_customer p:eq(0)").html('完成支付前请<span style="margin: 0 5px; color: #ea5513">不要关闭此窗口</span>，完成支付后根据您的情况点击下面按钮');
						$("#div_customer p:eq(1) a:eq(0)").html('已完成支付').attr('target','_self').attr('href','/bank/singleBiddingResult?number='+d.obj.channelFlow).click(function(){$('.rz-close').trigger('click')});
						$("#div_customer p:eq(1) a:eq(1)").html('支付遇到问题').attr('target','_self').attr('href','/helpCenterGuide').click(function(){$('.rz-close').trigger('click')});
						isClick = false;
		            },
		            error: function(data) {
		                alert("error:"+data.responseText);
		                isClick = false;
		            }
		        });
		 }
	}
}
//自定义alert
function cusAlert(msg) {
	$("#rz-box-bg").show();
	$("#div_customer_tip").show();
	offsetDiv("#div_customer_tip");
	$("#div_customer_tip h2").text("提示");
	$("#div_customer_tip p:eq(0)").html(msg);
}
// 取消支付
function cancelPayment(tid) {
	$("#rz-box-bg").show();
	$("#bombBox").show();
	offsetDiv("#bombBox");
	$("#bombBox h2").text("提示");
	$("#bombBoxContent").text("您确定取消支付吗？");
	
	$("#bombBox a:first").click(function(){
		$.ajax({
			url : "/purchase/cancelPayVersionOne",
			type : "post",
			data : {tId : tid},
			success: function (data) {
				cusAlert(data.msg);
				$("#bombBox").hide();
				if (data.code == 200) {
					setTimeout("window.location.reload(true);", 1000);
				}
			}
		});
		
		$("#bombBox a:first").unbind("click");
		$("#bombBox a:last").unbind("click");

	});
	
	$("#bombBox a:last").click(function(){
		$("#rz-box-bg").hide();
		$("#bombBox").hide();
		$("#bombBox a:first").unbind("click");
		$("#bombBox a:last").unbind("click");

	});
	
}
