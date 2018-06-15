var isBankNoCertification = false;
var isTelCertification = false;

/**
 * 验证身份证
 * @param inputId 输入框id
 * @param tipId 展示信息元素ID
 * @param imgId 图片元素ID
 * @returns {Boolean}
 */
function testID(inputId, tipId, imgId) {
	// 身份证正则表达式(15位)
	var isIDCard1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
	// 身份证正则表达式(18位)
	var isIDCard2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|x|X)$/;
	var idCard = document.getElementById(inputId).value;
	var ret1 = isIDCard1.test(idCard);
	var ret2 = isIDCard2.test(idCard);
	if (!ret1 && !ret2) {
		document.getElementById(tipId).innerHTML = "请输入正确的身份证号码";
		document.getElementById(imgId).style.display = "none";
		return false;
	} else {
		// 获取出生日期
		// UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-"
		// + UUserCard.substring(12, 14);
		// 获取年龄
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var day = myDate.getDate();
		if (idCard.length == 18) {
			var age = myDate.getFullYear() - idCard.substring(6, 10) - 1;
			if (idCard.substring(10, 12) < month
					|| idCard.substring(10, 12) == month
					&& idCard.substring(12, 14) <= day) {
				age++;
			}
			if (age < 18) {
				document.getElementById(tipId).innerHTML = "该身份证人员还未满18周岁";
				document.getElementById(imgId).style.display = "none";
				return false;
			}
		} else if (idCard.length == 15) {
			var age = myDate.getFullYear()
					- (1900 + parseInt(idCard.substring(6, 8))) - 1;
			if (idCard.substring(8, 10) < month
					|| idCard.substring(8, 10) == month
					&& idCard.substring(10, 12) <= day) {
				age++;
			}
			if (age < 18) {
				document.getElementById(tipId).innerHTML = "该身份证人员还未满18周岁";
				document.getElementById(imgId).style.display = "none";
				return false;
			}
		}
		
		var con = sendReq("/myAccount/testBindCardID", "idCard=" + idCard);
		con = JSON.parse(con);
		if (con.code != 200) {
			document.getElementById(tipId).innerHTML = con.msg;
			document.getElementById(imgId).style.display = "none";
			return false;
		} else {
			document.getElementById(tipId).innerHTML = "";
			document.getElementById(imgId).style.display = "inline-block";
			return true;
		}
		
	}
}

/**
 * 验证姓名 仅中文
 * @param inputId 输入框id
 * @param tipId 提示语标签id
 * @param imgId 提示图片id
 * @returns {Boolean}
 */
function testName(inputId, tipId, imgId) {
	/*
	 * 真实姓名的正则表达式？ 要求：真实姓名可以是汉字，也可以是字母，但是不能两者都有，也不能包含任何符号和数字 注意：
	 * 1.如果是英文名,可以允许英文名字中出现空格 2.英文名的空格可以是多个，但是不能连续出现多个 3.汉字不能出现空格
	 */
	/*
	 * var par = /^([\u4e00-\u9fa5]+|([a-zA-Z]+\s?)+)$/; var name =
	 * document.getElementById("name").value; var ret = par.test(name); if
	 * (!ret) { document.getElementById("tip_name").style.display = "block";
	 * document.getElementById("tip_name").innerHTML = "真实姓名不合法，请重新输入";
	 * document.getElementById("span_name").style.display = "none"; return
	 * false; } else { document.getElementById("tip_name").style.display =
	 * "none"; document.getElementById("span_name").style.display =
	 * "inline-block"; return true; }
	 */
	var par = /^[\u4e00-\u9fa5]+$/;
	var name = document.getElementById(inputId).value;
	var ret = par.test(name);
	if (!ret) {
		document.getElementById(tipId).innerHTML = "请输入正确的姓名";
		document.getElementById(imgId).style.display = "none";
		return false;
	} else {
		if (name.length < 2) {
			document.getElementById(tipId).innerHTML = "请输入正确的姓名";
			document.getElementById(imgId).style.display = "none";
			return false;
		}
		document.getElementById(tipId).innerHTML = "";
		document.getElementById(imgId).style.display = "inline-block";
		return true;
	}

}

function testBankCard(BankNo) {
	$("#bankNumberFormat").hide();
	var bankno = BankNo.value;
	var num = /^\d*$/; // 全数字
	if (bankno.length < 16 || bankno.length > 19) {
		document.getElementById("number-tip").innerHTML = "银行卡号长度必须在16到19之间";
		document.getElementById("span_number").style.display = "none";
		isBankNoCertification = false;
	} else if (!num.exec(bankno)) {
		document.getElementById("number-tip").innerHTML = "银行卡号必须为全数字";
		document.getElementById("span_number").style.display = "none";
		isBankNoCertification = false;
	} else if ($("#bankShort").val() == "") {
		document.getElementById("number-tip").innerHTML = "请选择银行";
		document.getElementById("span_number").style.display = "none";
		isBankNoCertification = false;
	} else {
		var con = sendReq("/myAccount/isCardNumberBeBinded", "number=" + bankno);
		con = JSON.parse(con);
		if(con.code!=200) {
			document.getElementById("number-tip").innerHTML = con.msg;
			document.getElementById("span_number").style.display = "none";
			isBankNoCertification = false;
		} else {
			document.getElementById("number-tip").innerHTML = "";
			document.getElementById("span_number").style.display = "inline-block";
			isBankNoCertification = true;
		}
	}

}

/**
 * 京东绑卡调用JS验证手机号格式
 * @param ts input 控件
 * @returns 返回验证结果
 */
function testTel2(ts) {
	var tel = ts.value;
	var myreg = /^1[3|4|5|7|8]\d{9}$/;
	if (myreg.test(tel)) {
		document.getElementById("tel-tip").innerHTML = "";
		document.getElementById("span_tel").style.display = "inline-block";
		isTelCertification = true;
	} else {
		document.getElementById("tel-tip").innerHTML = "请输入正确的手机号码";
		document.getElementById("span_tel").style.display = "none";
		isTelCertification = false;
	}
}

/**
 * 华兴开通E账户调用JS验证手机号格式
 * @param ts input 控件
 * @returns 返回验证结果
 */
function testTel(ts, tipId, imgId) {
	var tel = document.getElementById(ts).value;
	var myreg = /^1[3|4|5|7|8]\d{9}$/;
	if (myreg.test(tel)) {
		document.getElementById(tipId).innerHTML = "";
		document.getElementById(imgId).style.display = "inline-block";
		isTelCertification = true;
		return true;
	} else {
		document.getElementById(tipId).innerHTML = "请输入正确的手机号";
		document.getElementById(imgId).style.display = "none";
		isTelCertification = false;
	}
}

var realNameinterval;
$(document)
		.ready(
				function() {
					$("#membersUserRealname").addClass("hover");
					$("#nav_member03").addClass("nav-hover");
					$(".main-head li:last").css("border", "none");
					$(".main3 li:last").css("margin-right", "0");
					$(".licai li:last").css("margin-right", "0");
					// 清空添加银行弹出框
					clearDialog();

					/* 顶部 */

					$(".media-link li:first").hover(function() {
						$(this).find("div").show();
					}, function() {
						$(this).find("div").hide();
					});
					/* 顶部 */

					$(".tip-bank").click(function() {
						var jdBindCardAvailable = $("#jdBindCardAvailable").val();
						if (jdBindCardAvailable == "N") {
							sAlert("京东绑卡功能已停用,如有需要请与客服联系。");
							return false;
						}
						
						offsetDiv("#newBnak");
						$("#newBnak").show();
						$("#rz-box-bg").show();
					});
					// 弹框关闭 ,清空输入框
					$(".rz-close").click(function() {
						$("#newBnak").hide();
						$("#rz-box-bg").hide();
						clearDialog();
						clearInterval(realNameinterval);
					});

					// 获取验证码先判断手机号，60秒获取一次
					$("#yzmCode").click(function(e) {
						clearInterval(realNameinterval);
						var tel = $("#tel").val();
						var name = $("#name").val();
						var number = $("#number").val();
						var bankShort = $("#bankShort").val();
						var idCard = $("#idCard").val();
						var telreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
						if (!testID('idCard','idCard-tip','span_idCard') || !testName('name','name-tip','span_name')
								|| !isBankNoCertification || !isTelCertification) {
							$("#msgcode_tip").html(
									"个人信息填写有误，请先校验!");
							return false;
						} else {
							$("#msgcode_tip").html("");
							var i = 59;
							$("#yzmCode").hide();
							$("#yzmCodeShadow").css("display",
									"inline-block").html(
									"重新获取验证码(60)");
							realNameinterval = setInterval(
									function() {
										$("#yzmCodeShadow").css("display","inline-block").html(
														"重新获取验证码("+ (i--) + ")");
										if (i == -1) {
											clearInterval(realNameinterval);
											$("#yzmCode").show();
											$("#yzmCodeShadow").hide();
										}
									}, 1000);
									$.ajax({
										url : "/users/jdBindCardGetSecurityCode",
										type : "post",
										data : {
											"tel" : tel,
											"name" : name,
											"number" : number,
											"bankShort" : bankShort,
											"idCard" : idCard,
										},
										success : function(data) {
											if (data.code == 200) {
												$("#msgcode_tip").html("<span style='color:green'>"
																		+ data.msg+ "</span>");
											} else {
												$("#msgcode_tip").html(data.msg);
												clearInterval(realNameinterval);
												$("#yzmCode").show();
												$("#yzmCodeShadow").hide();
											}
										}
									});
						}
					});

					// 没收到短息，提示
					$("#nomessage").mouseover(function() {
						$(".none-reason").show();
					}).mouseout(function() {
						$(".none-reason").hide();
					});

				});

function hidebox() {
	$("#rz-box-aaa").hide();
	$("#rz-box").hide();
}
setTimeout("hidebox()", 3000);

function chechSumCount() {
	var con = sendReq("/users/checkAuthentication.html");
	if (con == "SUCC") {
		return true;
	} else {
		sAlert("您今天的三次身份验证已经用完!");
		return false;
	}
}

function sAlert(str) {
	var msgw, msgh, bordercolor;
	msgw = 600;// 提示窗口的宽度
	msgh = 100;// 提示窗口的高度
	titleheight = 25 // 提示窗口标题高度
	bordercolor = "#ea5413";// 提示窗口的边框颜色
	titlecolor = "#ea5413";// 提示窗口的标题颜色

	var sWidth, sHeight;
	sWidth = screen.width;
	sHeight = screen.height;

	var bgObj = document.createElement("div");
	bgObj.setAttribute('id', 'bgDiv');
	bgObj.style.position = "absolute";
	bgObj.style.top = "0";
	bgObj.style.background = "#cccccc";
	bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
	bgObj.style.opacity = "0.6";
	bgObj.style.left = "0";
	bgObj.style.width = sWidth + "px";
	bgObj.style.height = sHeight + "px";
	bgObj.style.zIndex = "10000";
	document.body.appendChild(bgObj);

	var msgObj = document.createElement("div")
	msgObj.setAttribute("id", "msgDiv");
	msgObj.setAttribute("align", "center");
	msgObj.style.background = "white";
	msgObj.style.border = "1px solid " + bordercolor;
	msgObj.style.position = "absolute";
	msgObj.style.left = "50%";
	msgObj.style.top = "50%";
	msgObj.style.font = "12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
	msgObj.style.marginLeft = "-300px";
	msgObj.style.marginTop = -75 + document.documentElement.scrollTop + "px";
	msgObj.style.width = msgw + "px";
	msgObj.style.height = msgh + "px";
	msgObj.style.textAlign = "center";
	msgObj.style.lineHeight = "25px";
	msgObj.style.zIndex = "10001";
	var title = document.createElement("h4");
	title.setAttribute("id", "msgTitle");
	title.setAttribute("align", "right");
	title.style.margin = "0";
	title.style.padding = "3px";
	title.style.background = bordercolor;
	title.style.filter = "progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
	title.style.opacity = "0.75";
	title.style.border = "1px solid " + bordercolor;
	title.style.height = "18px";
	title.style.font = "14px Verdana, Geneva, Arial, Helvetica, sans-serif";
	title.style.color = "white";
	title.style.cursor = "pointer";
	title.innerHTML = "关闭";
	title.onclick = function() {
		document.body.removeChild(bgObj);
		document.getElementById("msgDiv").removeChild(title);
		document.body.removeChild(msgObj);
	}
	document.body.appendChild(msgObj);
	document.getElementById("msgDiv").appendChild(title);
	var txt = document.createElement("p");
	txt.style.margin = "1em 0"
	txt.setAttribute("id", "msgTxt");
	txt.innerHTML = str;
	document.getElementById("msgDiv").appendChild(txt);
}
$(document).ready(function() {
	$(".div-bank").on("mouseover", function() {
		$(this).css("border", "1px solid #5b9fe2");
		$(this).find("a.remove").show();
	});
	$(".div-bank").on("mouseout", function() {
		$(this).css("border", "1px solid #f1f1f1");
		$(this).find("a.remove").hide();
	})

	$(".tip-bank").hover(function() {
		$(this).find("p").css("color", "#E95412");
		$(this).find("span").css({
			"color" : "#E95412",
			"border-color" : "#E95412"
		});
	}, function() {
		$(this).find("p").css("color", "#5b9fe2");
		$(this).find("span").css({
			"color" : "#5b9fe2",
			"border-color" : "#5b9fe2"
		});
	})

	$(".seleBank").click(function() {
		$("#BankListDiv ").show();
	});

	$(".seleBank2").click(function() {
		$("#BankListDiv2").show();
	})
	$("#alert .rz-close, #alert .ok, #div_customer_tip .rz-button").click(function() {
		$("#alert").hide();
		$("#rz-box-bg").hide();
		$("#div_customer_tip").hide();
		$("#div_customer").hide();
	});
	$(document).click(
			function(e) {
				e.target = e.target || e.srcElement;
				if ($(e.target).prop("class") == "seleBank"
						|| $(e.target).prop("class") == "seleBank2") {
					return;
				}
				$("#BankListDiv,#BankListDiv2").hide();

			})
});




// 添加新银行卡
function bindNewBankCard() {
	if (!isBankNoCertification || !isTelCertification) {
		return false;
	} else {
		$.ajax({
			url : "/users/jdBindCardCertification",
			type : "post",
			data : {
				"idCard" : $("#idCard").val(),
				"name" : $("#name").val(),
				"number" : $("#number").val(),
				"tel" : $("#tel").val(),
				"bankShort" : $("#bankShort").val()
			},
			success : function(data) {
				if (data.code == 200) {
					window.location.reload();// 刷新当前页面.
				} else {
					$("#msgcode_tip").text(data.msg);
				}
				
			}
		});
	}
}

// 删除银行卡
function delBankCard(id) {
	var val = $("#bankCardNumber").val();
	if (val == 1) {
		selfAlert("至少保留一张银行卡", null);
		return false;
	}

	selfConfirm("确认删除该绑定银行卡?", null, "confirmDelBank", id);
}

// 设置默认银行卡
function setBankCardDefault(id) {
	selfConfirm("确认设置该银行卡为默认银行卡?", null, "confirmDefaultBank", id);
}

// 取消添加
function cancelBindNewBankCard() {
	window.location.reload();
}

// 打开银行列表选择框
function chooseBank() {
	$("#BankListDiv").empty().append(content);
	$("#BankListDiv").show(300);
}

// 选择银行事件
function bankPick(ts) {
	if ($("#messageType").val() == "3") {
		$("#msgcode_tip").html("");
	}
	var url = $(ts).find("img").attr("src");
	var bankShort = $(ts).find("input[data-info='bankShort']").val();
	$("#bankShort").val(bankShort);
	$("#bankImg").attr("src", url).show();
	$("#BankListDiv").hide(300);
	$("#BankListDiv2").hide(300);
	$("#number").focus().blur();
}

// 未绑定银行卡时，提交绑定事件
function submitBindBank(id) {
	// 先判断验证码
	if ($("#msgcode").val().replace(/\s+/g, "") == "") {
		$("#msgcode_tip").html("请输入验证码");
		return false;
	} else if ($("#msgcode").val().replace(/\s+/g, "").length < 6) {
		$("#msgcode_tip").html("验证码输入有误");
		return false;
	} else {
		$("#msgcode_tip").html("");
	}
	
	$.ajax({
		url : "/users/jdBindCardCertification",
		type : "post",
		data : $("#" + id).serialize(),
		success : function(data) {
			if (data.code == 200) {
				$("#newBnak").hide();
				selfAlert("添加成功", null);
				
				if (id == "addNewBankForm") {
					clearDialog();
				}
				
			} else {
				$("#messageType").val(data.type);
				$("#msgcode_tip").text(data.msg);
				clearInterval(realNameinterval);
				$("#yzmCode").show();
				$("#yzmCodeShadow").hide();
				return false;
			}
		}
	});
	
}

// 银行卡输入事件监听
function bankNumberFocus(ts) {
	$(ts).css("color", "#434343");
	var val = $(ts).val().split("");
	var str = "";
	for (var i = 0; i < val.length; i++) {
		if (i % 4 == 0 && i != 0) {
			str += " ";
		}
		str += val[i];
	}
	$("#bankNumberFormat").html(str).show();
}

// 银行卡事件监听
function bankNumberKeyUp(e) {
	if ($("#messageType").val() == "3") {
		$("#msgcode_tip").html("");
	}
	var val = $("#number").val();
	var bankShort = $("#bankShort").val();
	if (val.length >= 6) {
		//字母v
		if (!e)
			e = window.event;
		if ((e.keyCode || e.which) == 86) {
			var data = queryBankByTopSix(val.substr(0,6));
		}else{
			var data = queryBankByTopSix(val);
		}
		if (data != null) {
			$("#bankShort").val(data.bankShort);
			$("#bankImg").attr("src", data.bankLogo).show();
		}
	} else {
		$("#bankShort").val("");
		$("#bankImg").attr("src", "").hide();
	}
	val = val.split("");
	var str = "";
	for (var i = 0; i < val.length; i++) {
		if (i % 4 == 0 && i != 0) {
			str += " ";
		}
		str += val[i];
	}
	$("#bankNumberFormat").html(str);
}

// 清空添加银行卡弹出框
function clearDialog() {
	$("#bankShort").val("");
	$("#newBnak").find("input[name='number']").val("");
	$("#newBnak").find("input[name='tel']").val("");
	$("#newBnak").find("input[name='msgcode']").val("");
	$("#newBnak").find("p").html("");
	$("#newBnak").find("span").css("display", "none");
	$("#bankImg").attr("src", "").hide();
	
}

/**
 * 自定义弹出框 title 标题 message 内容 type 不同位置的alert确认事件
 */
function selfAlert(message, title) {
	if (title) {
		$("#alert h2").html(title);
	}
	$("#alert div:eq(1)").html(message);
	$("#alert").show();
	$("#rz-box-bg").show();
	offsetDiv("#alert");
}

// 自定义confirm取消/关闭 按钮
function confirmCancel() {
	$("#rz-box-bg").hide();
	$("#confirmDiv").hide();
}

// 自定义confirm确定按钮
function confirmOk(fn, data) {
	var func = eval(fn);
	func(data);
	$("#rz-box-bg").hide();
	$("#confirmDiv").hide();
}

/**
 * 自定义confirm事件 message 提示信息 title 标题 fn 回调方法名 data 回调方法参数 （默认一个参数）
 */
function selfConfirm(message, title, fn, data) {
	$("#confirmDiv button").unbind();
	if (title) {
		$("#confirmDiv h2").html(title);
	}
	$("#confirmDiv div:eq(1)").html(message);
	$("#confirmDiv").show();
	$("#rz-box-bg").show();
	offsetDiv("#confirmDiv");
	$("#confirmDiv button:eq(0)").click(function() {
		confirmOk(fn, data);
	});
	$("#confirmDiv button:eq(1)").click(function() {
		confirmCancel();
	});
}

// 确认删除银行卡方法
function confirmDelBank(id) {
	$.ajax({
		url : "/myAccount/deleteBankCard",
		type : "post",
		data : {
			"id" : id
		},
		success : function(data) {
			selfAlert(data.msg, null);
		}
	});
}

// 确认设置默认银行卡方法
function confirmDefaultBank(id) {
	$.ajax({
		url : "/myAccount/setBankCardDefault",
		type : "post",
		data : {
			"id" : id
		},
		success : function(data) {
			selfAlert(data.msg, null);
		}
	});
}

// 根据银行卡号前六位查询银行卡
function queryBankByTopSix(number) {
	if (number.length == 6) {
		var bank = null;
		$.ajax({
			url : "/myAccount/queryBankInfoByTopSix",
			type : "get",
			data : {
				"number" : number
			},
			async : false,
			success : function(data) {
				if (data.code == 200) {
					bank = data.obj;
				}
			}
		});
		return bank;
	}
}

function nameKeyUp() {
	if ($("#messageType").val() == "1") {
		$("#msgcode_tip").html("");
	}
}

function idcardKeyUp() {
	if ($("#messageType").val() == "2") {
		$("#msgcode_tip").html("");
	}
}

function telKeyUp() {
	if ($("#messageType").val() == "4") {
		$("#msgcode_tip").html("");
	}
}

function msgcodeKeyUp() {
	if ($("#messageType").val() == "5") {
		$("#msgcode_tip").html("");
	}
}
