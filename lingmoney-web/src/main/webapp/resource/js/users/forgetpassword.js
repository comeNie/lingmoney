
function showErrMsg(id,msg){
	$("#tip_"+id).css("display","block")
	$("#tip_"+id).html("<font>"+msg+"</font>");
	if($("#span_"+id).length>0){
		$("#span_"+id).css("display","none")
	}
}

function showSuccMsg(id){
	$("#tip_"+id).html("");
	$("#span_"+id).css("display","inline-block")
}


/**
 * 验证手机号
 * @returns
 */
function verifyTelephone() {
	$("#tip_telephone").html("");
	var telephone = $("#telephone").val();
	
	var par = /^[1][34578][0-9]{9}$/;
	var ret = par.test(telephone);
	if (!ret) {
		showErrMsg("telephone","手机号格式错误");
		return ret;
	} 
	$.post("/users/verifyTelephoneRequire",{telephone:telephone},function(res){
		if(res.code==200){
			showSuccMsg("telephone");
			return true;
		}else{
			showErrMsg("telephone",res.msg);
			return false;
		}
	});
	return ret;
}


/**
 * 验证短信验证码
 */
function validateCode() {
	var telephone = $("#telephone").val();
	var telVcode = $("#telVcode").val();
	if(!verifyTelephone()){
		showErrMsg("telVcode","手机号码有误");
		return false;
	}
	if (telVcode != null && telVcode != "") {
		$.post("/users/verifyMsgCode",{telephone:telephone,msgCode:telVcode},function(res){
			if(res.code==200){
				showSuccMsg("telVcode");
				return true;
			}else{
				showErrMsg("telVcode",res.msg);
				return false;
			}
		});
	} else {
		showErrMsg("telVcode","<font>请输入验证码</font>");
		return false;
	}
	return true;
}

/**
 * 验证密码
 * @returns {Boolean}
 */
function verifyLoginPsw() {
	var loginPsw = $("#password").val();
	if (loginPsw.length < 6 || loginPsw.length > 16) {
		showErrMsg("password","请输入6-16位登录密码");
		return false;
	}
	var par = /^[a-zA-Z0-9_!@#$%^&]+$/;
	var ret = par.test(loginPsw);
	if (!ret) {
		showErrMsg("password","字母、数字、下划线或特殊符号组成");
		return false;
	}else{
		showSuccMsg("password");
		return true;
	}
	return true;
}

/**
 * 验证确认密码
 * @returns {Boolean}
 */
function verifyLoginPswConfig() {
	var loginPsw =$("#password").val();
	var loginPswConfig = $("#repwd").val();
	if (loginPswConfig == "") {
		showErrMsg("repwd","请输入确认密码");
		return false;
	} else {
		if (loginPswConfig != loginPsw) {
			showErrMsg("repwd","确认密码有误");
			return false;
		}
		showSuccMsg("repwd");
		return true;
	}
	return true;
}

function verifyRegist() {
	if (!verifyTelephone()) {
		return false;
	}
	if (!verifyLoginPsw()) {
		return false;
	}
	if (!verifyLoginPswConfig()) {
		return false;
	}
	if (!validateCode()) {
		return false;
	}
	return true;
}

/**
 * 发送验证短信
 * @param val
 * @returns {Boolean}
 */
function sendSMS(val) {
	var telephone =$("#telephone").val();
	$("#tip_telVcode").html("");
	if (verifyTelephone()) {
		var code = $("#kaptcha").val();
		var kaptchaKey=$("#kaptchaImage").attr("key");
		$.post("/sms/sendModiPw",{phone:telephone,picKey:kaptchaKey,code:code},function(res){
			if(res.code==200){
				settime(val);
				return true;
			}else{
				showErrMsg("telVcode",res.msg);
				return false;
			}
		});
	}
}

/**
 * 短信倒计时
 */
var countdown = 120;
function settime(val) {
	// 提交
	if (countdown == 0) {
		val.removeAttribute("disabled");
		$("#" + val.id).html("重新获取验证码")
		countdown = 120;
		is_submit = false;
		

		$(".img_code").show();
		$(".phone_code").hide();
		// 刷新验证码
		var key = uuid(16);
		//生成验证码  
		$("#kaptchaImage").hide().attr('src','/commonset/pictureCode?picKey='+ key).fadeIn();
		$("#kaptchaImage").attr('key',key);
	} else {
		val.setAttribute("disabled", true);
		$("#" + val.id).html("重新发送(" + countdown + ")")
		val.value = "重新发送(" + countdown + ")";
		countdown--;
		if (countdown > -1) {
			setTimeout(function() {
				settime(val)
			}, 1000)
		}
	}
}

$(document).ready(function() {
	$(".main-head li:last").css("border", "none");
	$(".main3 li:last").css("margin-right", "0");
	$(".licai li:last").css("margin-right", "0");

	$('#submitForm').click(function() {
		if (verifyRegist()) {
			$.ajax({
				type : "post",
				url : "/users/retrievePassword",
				data : $("#form").serialize(),
				success : function(data) {
					if(data.code==200){
						$("#rz-box-bg").show();
						$("#ask").show();
						offsetDiv("#ask");
					}else{
						showErrMsg("telVcode",data.msg);
						return;
					}
					
				},
				error : function(data) {
					alert(data)
				}
			});
			return true;
		}
		return false;

	});

	$(".button_ok").click(function() {
		window.location.href = "/login";
	});
	$(".rz-close").click(function() {
		$("#rz-box-bg").hide();
		$("#ask").hide();
	});
	
	$('#kaptchaImage').click(function() {
		var key = uuid(16);
		//生成验证码  
		$(this).hide().attr('src','/commonset/pictureCode?picKey='+ key).fadeIn();
		$(this).attr('key',key);
		event.cancelBubble = true;
	});

})

function checkImgCode() {
	var code = $("#kaptcha").val();
	var kaptchaKey=$("#kaptchaImage").attr("key");
	if (code != "") {
		$.post("/commonset/verPicCode",{code:code,picKey:kaptchaKey},function(res){
			if(res.obj){
				$(".phone_code").show();
				$("#tip_telVcode").html("");
				$(".img_code").hide();
				return true;
			}else{
				showErrMsg("kaptcha", res.msg);
				return false;
			}
		});
		
	} else {
		showErrMsg("Code", "请输入验证码");
		return false;
	}
	return true;
}