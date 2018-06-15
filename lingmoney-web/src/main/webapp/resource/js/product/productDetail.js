// 金额合法标识，请求后台是需先判断此值
var moneyValid = false;

$(function() {
	$(".media-link li:first").css("cursor", "pointer")
	$(".media-link li:first").hover(function() {
		$(this).find("div").show();
	}, function() {
		$(this).find("div").hide();
	});
	
	$('.son_ul').hide(); // 初始ul隐藏
	$('.select_box span').hover(function() { // 鼠标移动函数
		$(this).parent().find('ul.son_ul').slideDown(); // 找到ul.son_ul显示
		$(this).parent().find('li').hover(function() {
			$(this).addClass('hover')
		}, function(){
			$(this).removeClass('hover')
		}); // li的hover效果
		$(this).parent().hover(function() {
		}, function() {
			$(this).parent().find("ul.son_ul").slideUp();
		});
	}, function() {
	});

	$('ul.son_ul li').click(function() {
		$(this).parents('li').find('span').html($(this).html());
		$(this).parents('li').find('ul').slideUp();
	});

	var changTab = $("#changTab").val();
	if (changTab == 1 || changTab == "null") {
		changeTab3(1);
	} else if (changTab == 2) {
		changeTab3(2);
	}
	
	// 金额输入框事件 START ======================================
	var isSpecialKeyCode = false;
	var isNumbersKeyCode = false;
	$("#buyMoney").keydown(function(event) {
		isSpecialKeyCode = (event.keyCode == 190 || event.keyCode == 110 || event.keyCode == 46
				|| event.keyCode == 8 || event.keyCode == 37 || event.keyCode == 39);
		
		isNumbersKeyCode = ((48 <= event.keyCode && event.keyCode <= 57) || (96 <= event.keyCode && event.keyCode <= 105));
		
		if (!isSpecialKeyCode && !isNumbersKeyCode) {
			event.returnValue = false;
		}
		
	});

	$("#buyMoney").keyup(function() {
		buyMoneyAvailable();
	});
	
	$("#buyMoney").change(function() {
		buyMoneyAvailable();
	});
	
	// 金额输入框事件 END ==========================================
	
	// 弹框关闭事件 START =========================================
	$(".jtClose").click(function(){
		$("#buyerInfoValidDialog").hide();
		$("#rz-box-bg").hide();
	});
	
	$(".hx_close").click(function(){
		$("#hxAccountDialog").hide();
		$("#rz-box-bg").hide();
	});
	
	$(".rz-close1").click(function(){
 		$("#rz-box-bg3").hide();
 		$("#protocol1").hide();
 	});
	
	$(".rz-close,.a_go").click(function(){
 		$("#rz-box-bg").hide();
 		$("#protocol2").hide();
 		$("#protocol3").hide();
 		$("#protocol4").hide();
 		$("#takeHeart_div_cz").hide();
 	});
	
	$(".rz-close, .reset, .ensure").click(function() {
		$("#cart").hide();
		$("#rz-box-bg").hide();
		$("#div_cz").hide();
		$("#deep").hide();
		$("#tou_xqd").hide();
		$("#zhc").hide();
		$("#butOverLimit").hide();
		$("#increasedRates").hide();
	});
	
	$(".rz-close, #ok_button").click(function(){
		$("#rz-box-bg").hide();
		$("#div_customer").hide();
		$("#div_cz").hide();
		$("#div_customer_tip").hide();
		$("#increasedRates").hide();
	});
	// 弹框关闭事件 END ==========================================

	// 产品到期时间计算
	timerss();
	
	$(".main-head li:last").css("border","none");
	$(".main3 li:last").css("margin-right","0");
	$(".licai li:last").css("margin-right","0"); 
	//债券转让协议 
	$("#a_protocal_1").click(function(){
 		$("#rz-box-bg3").show();
 		$("#protocol1").show();
 		offsetDiv("#protocol1");
 		//a_protocal_1();
 	});
	//借款协议 
	function a_protocal_1(){
	    var tId=$("#tradingId").val();
		$.ajax({
				type : "post",
				url:'/pdf/getPdfPath',
				data:'tId='+tId,
				dataType:'json',
			    success : function(data) {
				 if(data.code==200){
					 window.location.href="/pdf/PdfPath?tId="+tId;
				 }else{
					 cusAlert(data.msg);
				 }
			},
			error : function(data) {
				alert(data);
			}
		});
	}
 	
 	$(".a_go1").click(function(){
 		$("#rz-box-bg3").hide();
 		$("#protocol1").hide();
		$(".input_agree").attr("checked",true);
		$(".ok").attr("disabled", false);
		$(".ok").css({
			"cursor" : "pointer",
			"background" : "#ea5513",
			"color" : "#fff"
		});
 	});
 	
 	//风险提示 
 	$("#a_protocal_2").click(function(){
 		$("#rz-box-bg").show();
 		$("#protocol2").show();
 		offsetDiv("#protocol2");
 	});
 	
 	//免责声明 
 	$("#a_protocal_3").click(function(){
 		$("#rz-box-bg").show();
 		$("#protocol3").show();
 		offsetDiv("#protocol3");
 	});
 	
 	//获取抵押物描述信息  
 	$("#a_protocal_4").click(function(){
 		$("#rz-box-bg").show();
 		$("#protocol4").show();
 		$.ajax({
 			type : "post",
 			url:'/product/getIntroduction',
 			data:'code='+$("#code").val(),
 			dataType:'text',
			success : function(data) {
				$("#introduction4").html(data);
			},
			error : function(data) {
				alert(data);
			}
 		});
 		offsetDiv("#protocol4");
 	});
 	
 	//计算器 
 	$(".jsq_nei").click(function(){
 		offsetDivC(".calculator");
    	$(".calculator").slideDown(1000);
    	$("#rz-box-bg").show();
    	resets();
 	});
 	
	$("a.close").click(function(){
		$(".calculator").slideUp(500);
		$("#rz-box-bg").hide();
		resets();
	});
	
	$("#tkID").click(function(){
 		$("#rz-box-bg").hide();
 		$("#takeHeart_div_cz").hide();
 	});
	
	var isRedpack = 0;
	//立即购买 
	$(".detail_buy").click(function() {
		var pCode = $('#code').val();
		var buyMoney = $("#buyMoney").val();
		var yield1 = $("#yield").text();//预计年化收益率 
		var tId = $("#tradingId").val();
		var userRedPacketId = "";
		if (checkedRedPacket > 0) {
			userRedPacketId = checkedRedPacket;
		}
		
		
		//是否放弃使用可用优惠券
		var redPacketNumber = $("#redPacketNumber").text();
//		if (redPacketNumber.indexOf("可用") < 0){ // 选择了卡劵
//			isRedpack = 1;			
//		}
		
		var reg = /\d+/g;
		var num = redPacketNumber.match(reg);
		if (!moneyValid) {
			$('#sp').css("display", "block");
			$('#sp').html("<font color='red'>输入的数据不符合要求!</font>");
		} else if (null != num && redPacketNumber.indexOf("可用") > 0){
			$("#carnum").text("您有"+num+"张卡可用，确认放弃使用吗？");
			cartick();
			
			
		} else {
			$.ajax({
				url : '/purchase/buyProductVersionOne',
				type : 'post',
				data : {pCode : pCode, buyMoney : buyMoney, tId : tId, userRedPacketId : userRedPacketId},
				success : function (data) {
					if (data.code == 3009) {//未登录 
						$("#zhc").show();
						$("#rz-box-bg").show();
						offsetDiv("#zhc");
						return false;
					}
					
					if (data.code == 3012) {//未开通账户
						$("#hxAccountDialog p:first").text("请先开通华兴银行存管账户");
						$("#hxAccountDialog a:last").text("立即开通");
						$("#hxAccountDialog").show();
						$("#rz-box-bg").show();
						offsetDiv("#hxAccountDialog");
						return false;
					}
					if (data.code == 3013) {//账户未激活
						$("#hxAccountDialog p:first").text("请绑定银行卡激活E账户");
						$("#hxAccountDialog a:last").text("激活E账户");
						$("#hxAccountDialog").show();
						$("#rz-box-bg").show();
						offsetDiv("#hxAccountDialog");
						return false;
					}
					
					if (data.code == 3030) {//用户验证提示 
//						$("#buyerInfoValidDialog p:first").text("您目前还有未支付的产品，请您到【我的理财-待支付】进行处理");
//						$("#buyerInfoValidDialog a:last").attr("href","/myFinancial/finanCialManage?status=0").text("确定");
//						$("#buyerInfoValidDialog").show();
//						$("#rz-box-bg").show();
//						offsetDiv("#buyerInfoValidDialog");
						
						cusAlert("您目前还有未支付的产品，请您到【我的理财-待支付】进行处理");
						$("#ok_button").attr("href","/myFinancial/finanCialManage?status=0").text("确定");
						return false;
					}
					
					if (data.code == 277) {//用户验证提示 
//						$("#buyerInfoValidDialog p:first").text("您的当前账户余额不足，请充值！");
//						$("#buyerInfoValidDialog a:last").attr("href","/myFinancial/accountRecharge").text("充值");
//						$("#buyerInfoValidDialog").show();
//						$("#rz-box-bg").show();
//						offsetDiv("#buyerInfoValidDialog");
						
						cusAlert("您的当前账户余额不足，请充值！");
						$("#ok_button").attr("href","/myFinancial/accountRecharge").text("充值");
						return false;
					}
					
					if (data.code == 274) {//产品购买限额
//						$("#butOverLimit p:first").html(data.msg);
//						$("#butOverLimit").show();
//						$("#rz-box-bg").show();
//						offsetDiv("#butOverLimit");
						
						cusAlert(data.msg);
						return false;
					}
					
					// 剩余错误提示 
					if (data.code != 200) {
//						$("#buyerInfoValidDialog p:first").text(data.msg);
//						$("#buyerInfoValidDialog a:last").attr("href","javascript:$('#buyerInfoValidDialog').hide();$('#rz-box-bg').hide();").text("确定");
//						$("#buyerInfoValidDialog").show();
//						$("#rz-box-bg").show();
//						offsetDiv("#buyerInfoValidDialog");
						
						cusAlert(data.msg);
						return false;
					} else {
						theRestOfTime = data.obj.remainDt;
						var tid = data.obj.tId;
						
						// 支付倒计时 
						paymentCountDownInterval = setInterval('paymentCountDown()', 1000);
						
						// 确认支付表单展示数据
						$("#tradingId").val(tid);
						// 实际理财金额，扣除手续费 
						var code_back = sendReq("/product/financialMoney",
								"money=" + buyMoney + "&code=" + pCode);
						if(code_back=="code:500") {
							cusAlert("系统错误");
							return false;
						}
						
						var redPacketNumber = $("#redPacketNumber").text();
						var reg = /\d+/g;
						var num = redPacketNumber.match(reg);
						if (null != num && redPacketNumber.indexOf("返现红包") > 0){
							$("#redpacket").text(num +"元（产品成立发放至您的账户余额）");
						} else {
							$("#redpack").hide();
						}
						
						$("#licmoney").text(fmoney(code_back, 2));
						$("#zhimoney").text(fmoney(buyMoney, 2));
						$("#lastbuyMoney").val(buyMoney);
						$("#yield1").text(yield1);
						
						$("#rz-box-bg").css('z-index','1000');
						$(".rz-box-con").css('z-index','10000');
						$("#rz-box-bg").show();
						$("#tou_xqd").show();
						offsetDiv("#tou_xqd");
					}
				}
				
			});
		}
		
		
					
		
	});
	
	// 截至时间倒计时
	var now = new Date();
	var edDt = $("#edDt").val();
	var second = edDt - now.getTime();
	if (($("#rule").val() == 1 || $("#rule").val() == 2) && (second > 0)) {
		timer();
	}

	var stDt = $("#stDt").val();
	var seconds = now.getTime() - stDt;
	if (($("#rule").val() == 1 || $("#rule").val() == 2) && (seconds > 0)) {
		timers();
	}
	
	//立即支付按钮 
	$(".ok").css("cursor", "pointer");

	// 《同意阅读协议》勾选框
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
	});
	
	queryMyRedPacket(0);
	
	// 项目进度
	animate();	
});	

//选中红包数据，id
var checkedRedPacket = -1;
// 确认使用红包
function confirmUseRedPacket() {
	if (checkedRedPacket == -1) {
		//cusAlert("请先选中一张加息券!");
		return false;
	}
	
	var name = $("#redPacketName" + checkedRedPacket).val();
	$("#redPacketNumber").html("我的卡券：<a style='display:inline;float:none;color:#eb5314;' href='javascript:void(0);' onclick='showRedPacketList();'>" + name + "></a>");;
	
	$("#rz-box-bg").hide();
	$("#increasedRates").hide();
	
	countRedPacketMoney();
}

// 计算加息金额
function countRedPacketMoney() {
	// 计算加息
	var buyMoney = $('#buyMoney').val();
	var unitTime = $("#unitTime").val();//时间单位 
	var fTime = Number($("#fTime").val()); //固定期限 
	if(unitTime==0){//天
		fTime = fTime;
	}else if(unitTime==1){//周
		fTime = 7 * fTime;
	}else if(unitTime==2){//月
		fTime = 30 * fTime;
	}else if (unitTime==3){//年
		fTime = 365 * fTime;
	}

	var type = $("#hrpType" + checkedRedPacket).val();
	if (buyMoney > 0 && checkedRedPacket != -1 && type == 1 && fTime > 0) {
		var rate = $("#redPacketRate" + checkedRedPacket).val();
		var interest = Number(buyMoney) * Number(rate) * Number(fTime) / 36500;
		$("#redPacketIncome").text(" + ￥" + interest.toFixed(2));
	
	} else {
		$("#redPacketIncome").text("");
	}
}

// 取消使用红包，取消勾选，清空红包数据
function cancelUseRedPacket() {
	checkedRedPacket = -1;
	// 全部反选
	$("[id^='redPacketDiv1']  .cb_head").css("background-image","url('/resource/images/jx_check_2.png')");
	
	// 红包金额去除
	$("#redPacketIncome").text("");
	
	if (availableRedPacketNum > 0) {
		$("#redPacketNumber").html("我的卡券：<a style='display:inline;float:none;color:#eb5314;' href='javascript:void(0);' onclick='showRedPacketList();'>" + availableRedPacketNum + "张可用></a>");;
	} else {
		$("#redPacketNumber").text("我的卡券：暂无可用");
	}
}

var redPacketConUse = []; // 可用红包
var couponConUse = []; // 可用加息卷
var redPacketContUse = []; // 不可用红包
var couponContUse = []; // 不可用加息卷
// 查询我的红包数据
function queryMyRedPacket(buyMoney) {
	var isLogin = $("#isLogin").val();
	if (isLogin != "true") {
		return false;
	}
	
	if (buyMoney == undefined || buyMoney == null || buyMoney == 0) {
		return false;
	}
	var pCode = $("#code").val();
	// 查询可用优惠券
	$.ajax({
		url : '/myFinancial/queryFinancialAvailableRedPacket',
		type : 'post',
		data : {pCode : pCode, buyMoney : buyMoney},
		success : function(data) {
			$("#redPacketParentDiv1").empty();
			$("#redPacketParentDiv2").empty();
			redPacketConUse = [];
			couponConUse = [];
			redPacketContUse = [];
			couponContUse = [];
			if(data.code==200){
				if (data.obj.availableCount > 0) {
					$("#redPacketNumber").html("我的卡券：<a style='display:inline;float:none;color:#eb5314;' href='javascript:void(0);' onclick='showRedPacketList();'>" + data.obj.availableCount + "张可用></a>");
					availableRedPacketNum = data.obj.availableCount;
					for (var i = 0; i < data.obj.availableList.length; i++){
						if (data.obj.availableList[i].hrpType == 1) {
							couponConUse.push(data.obj.availableList[i]);
						} else {
							redPacketConUse.push(data.obj.availableList[i]);
						}
					}
					packageRedPacketDiv(couponConUse, 1);
					initRedPackageInfo();
				} else {
					$("#redPacketNumber").text("我的卡券：暂无可用");
					initRedPackageInfo();
				}
				
				if (data.obj.unAvailableList.length > 0) {
					for (var i = 0; i < data.obj.unAvailableList.length; i++){
						if (data.obj.unAvailableList[i].hrpType == 1) {
							couponContUse.push(data.obj.unAvailableList[i]);
						} else {
							redPacketContUse.push(data.obj.unAvailableList[i]);
						}
					}
					packageRedPacketDiv(couponContUse, 0);
				}
				
			} else {
				$("#redPacketNumber").text("我的卡券：暂无可用");
				initRedPackageInfo();
			}
		}
	});
}

//我的卡劵切换
function couponActive1() {	
	$('#coupon-active1').addClass('active')
	$('#coupon-active2').removeClass('active')
	$("#redPacketParentDiv1").empty();
	packageRedPacketDiv(couponConUse, 1);
	packageRedPacketDiv(couponContUse, 0);
}

function couponActive2() {
	$('#coupon-active2').addClass('active')
	$('#coupon-active1').removeClass('active')
	$("#redPacketParentDiv2").empty();
	packageRedPacketDiv(redPacketConUse, 1);
	packageRedPacketDiv(redPacketContUse, 0);	
}


// 初始化加息券展示信息
function initRedPackageInfo() {
	checkedRedPacket = -1;
	$("#redPacketIncome").text("");
} 

var availableRedPacketNum = 0;
/**
 * 拼装红包div
 * @param obj 循环对象
 * @param type 是否可用 0 否  1 是
 */
function packageRedPacketDiv(obj, type) {
	$.each(obj, function (i, item) {
		var id = "redPacketDiv" + type + i ;
		var div = $("#redPacketDemoDiv").clone().removeAttr("style").attr("id" , id);
		
		// 添加id
		div.append("<input type='hidden' name='redPacketId' value='" + item.id + "'>");
		div.append("<input type='hidden' id='hrpType" + item.id + "' value='" + item.hrpType + "'>");
		if(item.hrpType == 1){
			div.append("<input type='hidden' id='redPacketName" + item.id + "' value='" + item.amount + "%" + item.hrName + "'>");
		}else{
			div.append("<input type='hidden' id='redPacketName" + item.id + "' value='" + item.amount + "元" + item.hrName + "'>");
		}
		div.append("<input type='hidden' id='redPacketRate" + item.id + "' value='" + item.amount + "'>");
		
		if(item.hrpType == 1){
			div.find(".cb_head").html("<span>" + item.amount + "<i>%</i></span>").append("&nbsp;" + item.hrName);
		}else{
			div.find(".cb_head").html("<span>" + item.amount + "<i>元</i></span>").append("&nbsp;" + item.hrName);
		}
		div.find("label").text("有效期至：" + item.overtimeDate);
		
		if (checkedRedPacket != -1 && item.id == checkedRedPacket) {
			div.find(".cb_head").css("background-image","url('/resource/images/jx_check_1.png')");
		}
		
		// 获取备注信息
		if (item.activityRemark != null) {
			var remarkHtml = "";
			$.each(item.activityRemark, function (index, remark) {
				for (rIndex in remark) {
					remarkHtml += "<p>" + rIndex + "：<span>" + remark[rIndex] + "</span></p>";
				}
			});
			div.find(".cb_middle").html(remarkHtml);
		}
		
		// 过期时间展示
		if (0 < item.dayOfOvertime && item.dayOfOvertime <= 3) {
			div.find("span:last").text(item.dayOfOvertime + "天后过期");
		}
		
		// 不可用，边框置灰，不可选
		if (type == 0) {
			div.css("background-image", "url('/resource/images/cardTopbg_gq.jpg')");
			div.find(".cb_head").css("background-image","none");
		}
		
		if(item.hrpType == 1){
			$("#redPacketParentDiv1").append(div);
		} else {
			$("#redPacketParentDiv2").append(div);
		}
		
		// 可用才绑定点击事件
		if (type == 1) {
			div.click(function() {
				// 全部反选
				$("[id^='redPacketDiv1']  .cb_head").css("background-image","url('/resource/images/jx_check_2.png')");
				
				// 选中
				$(this).children(".cb_head").css("background-image","url('/resource/images/jx_check_1.png')");
				checkedRedPacket = $(this).children("[name='redPacketId']").val();
				
			});
		}
	});
}

function showRedPacketList() {
	$("#rz-box-bg").show();
	$('#increasedRates').show();
	offsetDiv("#increasedRates");
}

// div居中控制
function offsetDiv(s) {
	height = $(s).height() + 30;
	width = $(s).width();
	$(s).css("margin-top", -height / 2);
	$(s).css("margin-left", -width / 2);
}

// 计算预期收益
function calculateIncome(money) {
	var unitTime = $("#unitTime").val();//时间单位 
	var fTime = Number($("#fTime").val()); //固定期限 
	var fYield = $("#fYield").val();//固定收益 
	if(unitTime==0){//天
		fTime = fTime;
	}else if(unitTime==1){//周
		fTime = 7 * fTime;
	}else if(unitTime==2){//月
		fTime = 30 * fTime;
	}else if (unitTime==3){//年
		fTime = 365 * fTime;
	}
	var income = sendReq("/product/income", "money="
			+ money + "&ftime=" + fTime + "&fYield=" + fYield);
	$("#expectIncome").text("￥" + income);
}

// 预期收益置零
function initializeExpectIncome() {
	$("#expectIncome").text("0.00");
}

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

// 滑动门
function changeTab3(index) {
	$("#changTab").val(index);
	for (var i = 1; i <= 3; i++) {
		if (document.getElementById("f_" + i) != null) {
			document.getElementById("f_" + i).className = "normal";
			document.getElementById("f_" + index).className = "selected";
			document.getElementById("f" + i).style.display = "none";
		}
	}
	document.getElementById("f" + index).style.display = "block";
}

// 购买金额是否合法
function buyMoneyAvailable() {
	$('#sp').html("");
	var minMoney = $('#minMoney').val(); //起投金额 
	var toumoney = $('#toumoney').val(); //剩余可购金额 
	var buyMoney = $('#buyMoney').val(); //购买金额 
	var increaMoney = $('#increaMoney').val();
	var buyLimit = $("#buyLimit").val(); //购买限额 
	var regex = /^([1-9]\d*|0)(\.\d{1,2})?$/;
	var med = null;
	var p_rule = $('#rule').val();//投资规则，0:金额限制，1:时间限制，2:金额时间限制，3:无限制
	
	if (regex.test(buyMoney)) {
		if (!isNaN(toumoney) && (parseFloat(toumoney) - parseFloat(buyMoney) < 0)) {
			$('#sp').css("display", "block");
			$('#sp').html("<font color='red'>输入金额不能大于产品剩余可购金额!</font>");
			initializeExpectIncome();
			moneyValid = false;
			return false;
		}
		
		if (!isNaN(buyMoney) && buyLimit > 0 && (parseFloat(buyMoney) > parseFloat(buyLimit))) {
			$('#sp').css("display", "block");
			$('#sp').html("<font color='red'>该产品每人限购"+ buyLimit +"元</font>");
			initializeExpectIncome();
			moneyValid = false;
			return false;
		}
		
		if (parseFloat(buyMoney) < parseFloat(minMoney)) {
			$('#sp').css("display", "block");
			$('#sp').html("<font color='red'>起投金额不能小于" + minMoney + "元!</font>");
			initializeExpectIncome();
			moneyValid = false;
			return false;
		}
		
		if (increaMoney != 0) {
			if ((parseFloat(buyMoney) % increaMoney) != 0) {
				$('#sp').css("display", "block");
				$('#sp').html("<font color='red'>输入的数据不符合要求!</font>");
				initializeExpectIncome();
				moneyValid = false;
				return false;
			}
		}
		
		calculateIncome(buyMoney);
		
		queryMyRedPacket(buyMoney);
		
		countRedPacketMoney();
		
		$('#sp').css("display", "none");
		moneyValid = true;
		return true;	
	} else {
		
		$('#sp').css("display", "block");
		$('#sp').html("<font color='red'>输入的数据不符合要求!</font>");
		initializeExpectIncome();
		moneyValid = false;
		return false;
	}
}

// 立即支付银行存管/京东  
function subbuy() {
	var tid = $('#tradingId').val();
	var pType = $('#pType').val();
	$("tId").val(tid);
	if (theRestOfTime > 0) {
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
			           	$("#tou_xqd").hide();
			           	$("#div_cz").show();
			           	offsetDiv("#div_cz");
			           	
		           		$("#jdTradingId").val(tid);
		           	    $("#jdActiveForm").attr("action","/product/jdBuyProductHtml");
	 				    $("#jdActiveForm").submit();
		            },
		            error: function(data) {
		                alert("error:"+data.responseText);
		            }
		        });
		 } else {
			 $.ajax({
	             type: "POST",
	             url: "/bank/singleBiddingVersionOne",
	             data: $('#sbidForm').serialize(),
	             success: function (d) {
	            	if (d.code != 200) {
	            		cusAlert(d.msg);
	 					return false;
	 				}
	 				// 组织表单，提交数据
	 				$("#requestData").val(d.obj.requestData);
	 				$("#transCode").val(d.obj.transCode);
	 				$("#hxBankForm").attr("action",d.obj.bankUrl);
	 				$("#hxBankForm").submit();
	 				// 页面弹框
	 				$("#tou_xqd").hide();
					$("#div_customer").show();
					offsetDiv("#div_customer");
					$("#div_customer h2").text("请在新页面完成支付");
					$("#div_customer p:eq(0)").html('完成支付前请<span style="margin: 0 5px; color: #ea5513">不要关闭此窗口</span>，完成支付后根据您的情况点击下面按钮<br><a class="rect shape="rect" coords="0,75,265,151" href="/hxbankOpertion" style="padding-top:15px;display:block">了解更多，请查看银行存管操作指南>></a>');
					$("#div_customer p:eq(1) a:eq(0)").html('已完成支付').attr('target','_self').attr('href','/bank/singleBiddingResult?number='+d.obj.channelFlow).click(function(){$('.rz-close').trigger('click')});
					$("#div_customer p:eq(1) a:eq(1)").html('支付遇到问题').attr('target','_self').attr('href','/helpproblem.html').click(function(){$('.rz-close').trigger('click')});
					isClick = false;
	             },
	             error: function(data) {
	                 alert("error:"+data.responseText);
	             }
	         });
		 }
		 
	}
}

function exist(id){
	var s=document.getElementById(id);
    if(s){return true}
    else{return false}
}

// 截至时间倒计时
function timer() {
	var d1 = document.getElementById("edDt").value;
	var d2 = new Date();//结束时间
	d2.setTime(d1);
	var d3 = new Date();//当前时间
	var ts = d2.getTime() - d3.getTime();//计算剩余的毫秒数  
	if(document.getElementById("timer")){
		if(ts<=1000 && ts>0){
			window.clearInterval(timerInterval);
			location.reload();
		} else if(ts<0) {
			window.clearInterval(timerInterval);
			if(exist("timer")){
				document.getElementById("timer").innerHTML = "00天00时00分00秒";												
			}
			return false;
		}
	}
	var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
	var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
	var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
	var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
	dd = checkTime(dd);
	hh = checkTime(hh);
	mm = checkTime(mm);
	ss = checkTime(ss);
	if(exist("timer")){
		document.getElementById("timer").innerHTML = dd + "天" + hh + "时" + mm + "分" + ss + "秒";
	}
}
var timerInterval = setInterval("timer()", 1000);

function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}

// 距离开始时间 倒计时
function timers() {
	var d1 = document.getElementById("stDt").value;
	var d2 = new Date();//开始时间
	d2.setTime(d1);
	var d3 = new Date();//当前时间
	var ts = d2.getTime() - d3.getTime();//计算剩余的毫秒数  
	if(document.getElementById("timers")){
		if(ts>0 && ts<=1000){
			window.clearInterval(timersInterval);
			location.reload();
		} else if(ts<0) {
			window.clearInterval(timersInterval);
			if(exist("timers")){
				document.getElementById("timers").innerHTML = "00天00时00分00秒";												
			}
			return false;
		}
	}
	var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
	var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
	var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
	var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
	dd = checkTime(dd);
	hh = checkTime(hh);
	mm = checkTime(mm);
	ss = checkTime(ss);
	if(exist("timers")){
		document.getElementById("timers").innerHTML = dd + "天" + hh + "时" + mm + "分" + ss + "秒";
	}
}
var timersInterval = setInterval("timers()", 1000);

function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}

// 到期时间
function timerss() {
	if(document.getElementById("timerss")!=undefined){
	var unitTime =  $("#unitTime").val();
	var fTime = $("#fTime").val() ;
	if(fTime==0){
		return;
	}else{
		if(unitTime==0){//天
			fTime = fTime;
		}else if(unitTime==1){//周
			fTime = 7*fTime;
		}else if(unitTime==2){//月
			fTime = 30*fTime;
		}else if(unitTime==3){//年
			fTime = 365*fTime;
		}
	}
	var d1 = document.getElementById("edDt").value;//结束时间 
	var d2 = new Date();//当前时间
	d2.setTime(d1);
	var ts = fTime*24*60*60*1000;
	var d3 = d2.getTime()+ts;//到期时间 = 结束时间+固定期限 
	var d4 = new Date(d3);
	var format = function(time, format){
	    var t = new Date(time);
	    var tf = function(i){return (i < 10 ? '0' : '') + i};
	    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
	        switch(a){
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
	}
	document.getElementById("timerss").innerHTML = format(d3, 'yyyy-MM-dd')+"（预计）";
	}
}


//进度条 
function animate() {
	$(".charts").each(function(i, item) {
		/* var a=parseInt($(item).attr("w")); */
		var priorMoney = $('#priorMoney').val();
		var reachMoney = $('#reachMoney').val();
		var percent =(reachMoney / priorMoney * 100);
		var perc=parseInt(percent, 0);
		$('#perSp').html(perc + "%");
		var a = perc;
		$(item).attr("w")
		$(item).animate({
			width : a + "%"
		}, 500);
	});
}

// 支付倒计时 默认15分钟 
var theRestOfTime = 15 * 60 * 1000;
// 倒计时interval
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
      $("#paymentCountDownDiv a:first").text("00:" + m + ":" + s);
    } else {
    	window.clearInterval("paymentCountDownInterval");
    	theRestOfTime = -1;
    	window.location.reload();
    }
}

// 取消支付
function cancelPayment() {
	$("#bombBox").show();
	offsetDiv("#bombBox");
	$('#rz-box-bg').css("z-index",9999);
	$("#bombBox h2").text("提示");
	$("#bombBoxContent").text("您确定取消支付吗？");
	$(".rz-box-con").css("z-index",9999);
	$("#bombBox a:first").click(function(){
		$('#rz-box-bg').css("z-index",9999);
		$(".rz-box-con").css("z-index",9999);
		var tid = $("#tradingId").val();
		$.ajax({
			url : "/purchase/cancelPayVersionOne",
			type : "post",
			data : {tId : tid},
			success: function (data) {
				cusAlert(data.msg);
				$("#bombBox").hide();
			}
		});
		$("#bombBox a:first").unbind("click");
		$("#bombBox a:last").unbind("click");

	});
	
	$("#bombBox a:last").click(function(){
		$("#bombBox").hide();
		$(".rz-box-con").css("z-index",1001);
		$('#rz-box-bg').css("z-index",1000);
		$("#bombBox a:first").unbind("click");
		$("#bombBox a:last").unbind("click");

	});
}


//自定义alert
function cusAlert(msg) {
	$("#tou_xqd").hide();
	$("#rz-box-bg").show();
	$("#div_customer_tip").show();
	offsetDiv("#div_customer_tip");
	$("#div_customer_tip h2").text("提示");
	$("#div_customer_tip p:eq(0)").html(msg);
}

// 充值
function goToRecharge() {
	var userCertification = $("#userCertification").val();
	var bank = $("#userBank").val();
	
	if (userCertification == null || userCertification == '' || bank == null || bank == '') {
		$("#zhc").show();
		$("#rz-box-bg").show();
		offsetDiv("#zhc");
		return false;
	}
	
	if (userCertification == 0) {
		//未开通账户
		$("#hxAccountDialog p:first").text("请先开通华兴银行存管账户");
		$("#hxAccountDialog a:last").text("立即开通");
		$("#hxAccountDialog").show();
		$("#rz-box-bg").show();
		offsetDiv("#hxAccountDialog");
		return false;
	}
	
	if (bank == 0) {
		//账户未激活
		$("#hxAccountDialog p:first").text("请绑定银行卡激活E账户");
		$("#hxAccountDialog a:last").text("激活E账户");
		$("#hxAccountDialog").show();
		$("#rz-box-bg").show();
		offsetDiv("#hxAccountDialog");
		return false;
	}
	
	window.location.href = "/myFinancial/accountRecharge";
}
function cartick(type){
	var flag;
	if(type==""||type==null){
		$("#cart").show();
		$("#rz-box-bg").show();
	}else if(type==0){
		console.log(123);
		
		$("#cart").hide();
		$("#rz-box-bg").hide();
		flag= false;
	}else if(type==1){
		console.log(456);
		$("#cart").hide();
		$("#rz-box-bg").hide();
		flag= true;
		var pCode = $('#code').val();
		var buyMoney = $("#buyMoney").val();
		var yield1 = $("#yield").text();//预计年化收益率 
		$.ajax({
			url : '/product/buyProduct',
			type : 'post',
			data : {lastbuyMoney : buyMoney, prCode : pCode, userRedPacketId : checkedRedPacket},
			success : function (data) {
				if (data.code == 3009) {//未登录 
					$("#zhc").show();
					$("#rz-box-bg").show();
					offsetDiv("#zhc");
					return false;
				}
				
				if (data.code == 3012) {//未开通账户
					$("#hxAccountDialog p:first").text("请先开通华兴银行存管账户");
					$("#hxAccountDialog a:last").text("立即开通");
					$("#hxAccountDialog").show();
					$("#rz-box-bg").show();
					offsetDiv("#hxAccountDialog");
					return false;
				}
				if (data.code == 3013) {//账户未激活
					$("#hxAccountDialog p:first").text("请绑定银行卡激活E账户");
					$("#hxAccountDialog a:last").text("激活E账户");
					$("#hxAccountDialog").show();
					$("#rz-box-bg").show();
					offsetDiv("#hxAccountDialog");
					return false;
				}
				
				if (data.code == 3030) {//用户验证提示 
					cusAlert("您目前还有未支付的产品，请您到【我的理财-待支付】进行处理");
					$("#ok_button").attr("href","/myFinancial/finanCialManage?status=0").text("确定");
					return false;
				}
				
				if (data.code == 277) {//用户验证提示 
					cusAlert("您的当前账户余额不足，请充值！");
					$("#ok_button").attr("href","/myFinancial/accountRecharge").text("充值");
					return false;
				}
				
				if (data.code == 274) {//产品购买限额
					cusAlert(data.msg);
					return false;
				}
				
				// 剩余错误提示 
				if (data.code != 200) {
					cusAlert(data.msg);
					return false;
				} else {
					theRestOfTime = data.obj.remainDt;
					var tid = data.obj.tId;
					
					// 支付倒计时 
					paymentCountDownInterval = setInterval('paymentCountDown()', 1000);
					
					// 确认支付表单展示数据
					$("#tradingId").val(tid);
					// 实际理财金额，扣除手续费 
					var code_back = sendReq("/product/financialMoney",
							"money=" + buyMoney + "&code=" + pCode);
					if(code_back=="code:500") {
						cusAlert("系统错误");
						return false;
					}
					
					$("#licmoney").text(fmoney(code_back, 2));
					$("#zhimoney").text(fmoney(buyMoney, 2));
					$("#lastbuyMoney").val(buyMoney);
					$("#yield1").text(yield1);
					
					$("#rz-box-bg").show();
					$("#tou_xqd").show();
					offsetDiv("#tou_xqd");
				}
			}
			
		});
	}
	return flag;
}




