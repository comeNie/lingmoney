
var msg=$("#dataInput").val();
if(msg != null && msg != ""){
	sAlert(msg);
}

function showErrMsg(id,msg){
	$("#tip_"+id).css("display","block")
	$("#tip_"+id).html(msg);
	if($("#span_"+id).length>0){
		$("#span_"+id).css("display","none")
	}
}

function showSuccMsg(id){
	$("#tip_"+id).html("");
	$("#span_"+id).css("display","inline-block")
}

var isSendMsg = false;
var msgVerifyCount=0;

var formCheck = new FormCheck({
    selector : '#regForm',    // form表单的选择器
    isAjax : false,  // 表单提交方式 如果是true 就要自己组织ajax提交 如果是false 就只是判断能不能提交
    debug:true,
    // 验证前初始化的钩子
    startCheck : function(post){
        console.log('startCheck', post);
    },
    // 验证完成的钩子
    endCheck : function (res){
        console.log('endCheck', res);
    }
});

var fVerifyType = formCheck.getVerifyType();


/// 密码验证
formCheck.addVerify({
    id : '密码',
    selector : '#loginPsw',
    DAlias : 'loginPsw',
    verify : [{
        type : fVerifyType.require()
    },{
        // 长度验证
        type : fVerifyType.lenLimit(6, 16)
    },{
        // 密码正则验证
        type : fVerifyType.regex(/^[a-zA-Z0-9_!@#$%^&]+$/),
        success : function(d){
            showSuccMsg(d.DAlias);
        }
    },{
        // 验证密码
        type : fVerifyType.post("/users/verifyPwd",function(){return {pwd:$("#loginPsw").val()}
	     }), 
        success : function (d, res){
            // 判断下是否是json对象
            if(typeof res !== 'object'){
                res = JSON.parse(res);
            }

            if(!res){
                showErrMsg(d.DAlias, '网络错误');
                console.log(res);
            }else if(res.code === 200){
                showSuccMsg(d.DAlias);
                return true;  
            }else{
                showErrMsg(d.DAlias,res.msg);
                return false; 
            }
        }
    }
    
    
    ],
    error : function (d, code, msg){
        showErrMsg(d.DAlias, msg);
    }
});


/// 确认密码
formCheck.addVerify({
    id : '确认密码',
    selector : '#loginPswConfig',
    DAlias : 'loginPswConfig',
    verify : [{
        type : fVerifyType.require()
    },{
        type : fVerifyType.compareWith(function(){return $("#loginPsw").val()}),
        success : function (d){
            showSuccMsg(d.DAlias);
            return true;  
        }
    }],
    error : function (d, code, msg){
        showErrMsg(d.DAlias, msg);
    }
});


/// 手机号
formCheck.addVerify({
    id : '手机号',
    selector : '#telephone',
    DAlias : 'telephone',
    verify : [{
        type : fVerifyType.require(),
    }, {
        type : fVerifyType.regex(/^[1][34578][0-9]{9}$/),
    }, {
        /// 验证手机唯一性
        type : fVerifyType.post("/users/verifyTelephone",function(){return {telephone:$('#telephone').val()}
	     }), 
        success : function (d, res){
            // 判断下是否是json对象
            if(typeof res !== 'object'){
                res = JSON.parse(res);
            }

            if(!res){
                showErrMsg(d.DAlias, '网络错误');
                console.log(res);
            }else if(res.code === 200){
            	console.log("isSendMsg: "+window.isSendMsg)
            	if(isSendMsg){
            		sendSMSPost();
            		window.isSendMsg = false;
            	}
                showSuccMsg(d.DAlias);
                return true;  
            }else{
            	window.isSendMsg = false;
                showErrMsg(d.DAlias,res.msg);
                return false; 
            }
        }
    }],

    error : function (d, code, msg){
    	window.isSendMsg = false;
        showErrMsg(d.DAlias, msg);
    }

});

/// 推荐码
formCheck.addVerify({
    id : "推荐码",
    selector : "#referralTel",
    DAlias : 'referralTel',
    verify : [{
        type : fVerifyType.noRequire(),
        success : function (d){
            showSuccMsg(d.DAlias);
        }
    },
    
    {
        /// 长度验证
        type : fVerifyType.lenLimit(6, 8),
    },{
        type : fVerifyType.post("/users/verifyReferralTel",function(){return {referralTel:$("#referralTel").val()}
	     }),
        success : function (d, res){
            if(typeof res !== 'object'){
                res = JSON.parse(res);
            }

            if(!res){
                showErrMsg(d.DAlias, '网络错误');
            }else if(res.code === 200){
                showSuccMsg(d.DAlias);
                return true;  
            }else{
                showErrMsg(d.DAlias,res.msg);
                return false; 
            }
        }
    }],
    error : function (d, code, msg){
        showErrMsg(d.DAlias, msg);
    }
});


/// 验证短信验证码
formCheck.addVerify({
    id : '短信验证码',
    selector : "#telVcode",
    blur : true,
    DAlias : 'telVcode',
    
    verify : [{
        type : fVerifyType.require()
    },{
        type : fVerifyType.post("/users/verifyMsgCode",function(){return {telephone:$("#telephone").val(),msgCode:$("#telVcode").val()}
	     }),
        success : function (d, res){
            if(typeof res !== 'object'){
                res = JSON.parse(res);
            }
            if(!res){
                showErrMsg(d.DAlias, '网络错误');
                console.log(res);
            }else if(res.code === 200){
                $("#tip_telVcode").html("");
               // $("#tip_telVcode").css("display","none");
                return true;  
            }else{
                showErrMsg(d.DAlias,res.msg);
                return false; 
            }
        }
    }],
    error : function (d, code, msg){
        showErrMsg(d.DAlias, msg);
    }
});


/**
 * 发送验证短信
 * @param val
 * @returns {Boolean}
 */
function sendSMS() {
	msgVerifyCount = 0;
	window.isSendMsg = true;
	formCheck.verify("手机号");
}

function sendSMSPost(){
	console.log("sendSMSPost-isSendMsg : " + window.isSendMsg);
	var telephone =$("#telephone").val();
	var code = $("#kaptcha").val();
	var kaptchaKey=$("#kaptchaImage").attr("key");
	$("#tip_telVcode").html("");
	$.post("/sms/sendreg",{phone:telephone,picKey:kaptchaKey,code:code},function(res){
		if(res.code == 200){
			settime($("#sendSMSID"));
			return true;
		}else if(res.code == 3007){
			showErrMsg("telVcode",res.msg);
			$(".img_code").show();
			$(".phone_code").hide();
			// 刷新验证码
			var key = uuid(16);
			//生成验证码  
			$("#kaptchaImage").hide().attr('src','/commonset/pictureCode?picKey='+ key).fadeIn();
			$("#kaptchaImage").attr('key',key);
			
		}
		
		else{
			showErrMsg("telVcode",res.msg);
			return false;
		}
	});
}

/**
 * 短信倒计时
 */
var countdown = 60;
function settime(val) {
	
	// 提交
	if (countdown == 0) {
		val.removeAttr("disabled");
		val.html("重新获取验证码")
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
		val.attr("disabled", true);
		val.html("重新发送(" + countdown + ")");
		val.val("重新发送(" + countdown + ")");
		countdown--;
		if (countdown > -1) {
			setTimeout(function() {
				settime(val)
			}, 1000)
		}
	}
}

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


//自定义alert
function sAlert(msg) {
	var html = '<div id="sShadow" style="position: fixed;z-index: 9999;left: 0;top: 0;width: 100%;height: 100%;background:#000;opacity:0.6;filter:alpha(opacity=60);"></div>'
	html += '<div style="position: fixed;z-index: 9999;left: 50%;top: 50%;padding-bottom: 30px;width: 700px;background: #fff;" id="sAlert">';
	html += '<div style="height: 52px;line-height:52px;padding-left:22px;border: 0;border-bottom: 1px solid #ea5413;border-left: 8px solid #ea5413;"><h2>提示</h2></div>';
	html += '<p style="padding-top: 30px; font-size: 16px; color: #c54107; text-align:center;padding-left:14px;margin-top:15px;">'+msg+'</p>';
	html += '<p style="padding-top: 10px;text-align:center;padding-left:14px;margin-top:15px;">';
	html += '<a href="javascript:void(0)" onclick="sAlertClose()"  style="text-align:center;padding: 0;display: inline-block;margin-right: 20px;width: 203px;height: 48px;line-height: 48px;font-size: 20px;color: #fff;-webkit-border-radius: 3px;-ms-border-radius: 3px;-o-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;background: #ea5413;">确定</a></p></div>';
	$("body").append(html);
	offsetDiv("#sAlert");
	$('#sAlert').show();
}

function sAlertClose() {
	$('#sShadow').remove();
	$('#sAlert').remove();
	//window.location.reload();
}
