// 修改用户手机号


//------------------------------- 验证表单 start---------------------------------

var formCheck = new FormCheck({
    selector : '#modifyMobileForm',    // form表单的选择器
    isAjax : true,  // 表单提交方式 如果是true 就要自己组织ajax提交 如果是false 就只是判断能不能提交
    action:"/users/modifyTelephone",
    method:"post",
    debug:false,
    // 验证前初始化的钩子
    startCheck : function(post){
    	// post
    },
    // 验证完成的钩子
    endCheck : function (res){
    	if(res===null){
    		return;
    	}
    	if(typeof res.code === 'undefined'){
    		//  返回结果不是json对象 或者没有code属性
    		return;
    	}
    	
    	if(res.code==200) {
			$("#rz-box-bg").hide();
			$("#changeTelDiv2").hide();
			$("#newSendSMSID").removeAttr("disabled");
		} 
		sAlert1(res.msg,res.code);
		
    }
});

var fVerifyType = formCheck.getVerifyType();

//原手机号验证
formCheck.addVerify({
    id : '原手机号',     
    selector : '#orgiTelephone', 
    blur : true,           
    
    DAlias : 'orgiTelephoneTip', 
    verify:[{
        type : fVerifyType.require(),
    }, {
        type : fVerifyType.regex(/^[1][34578][0-9]{9}$/),
    }],
    error : function (d, code, msg){
        $("#orgiTelephoneTip").html(msg);
        $("#orgiTelephoneTip").show();
    },
    success :function (d, res){
   	 	$("#orgiTelephoneTip").html("");
        $("#orgiTelephoneTip").hide();
   }
});

//原手机号验证码验证
formCheck.addVerify({
    id : '原手机号验证码',     
    selector : '#orgiTelCode', 
    blur : true,           
    
    DAlias : 'spanOrgiTelCode', 
    verify:[{
        type : fVerifyType.require(),
    }, {
        type : fVerifyType.regex(/^\d{6}$/),
    }],
    error : function (d, code, msg){
        $("#spanOrgiTelCode").html(msg);
        $("#spanOrgiTelCode").show()
    },
    success :function (d, res){
   	 	$("#spanOrgiTelCode").html("");
        $("#spanOrgiTelCode").hide();
   }
});

// 密码验证
formCheck.addVerify({
    id : '密码',     
    selector : '#password', 
    blur : true,           
    verify:[{
        type : fVerifyType.require(),
    }, {
        type : fVerifyType.lenLimit(6, 16),
    }],
    error : function (d, code, msg){
        $("#passwordTip").html(msg);
        $("#passwordTip").show();
    },
    success :function (d, res){
   	 	$("#passwordTip").html("");
        $("#passwordTip").hide();
   }
});

//新手机号验证
formCheck.addVerify({
    id : '新手机号',     
    selector : '#telephone', 
    blur : true,           
    
    DAlias : 'telephoneTip', 
    verify:[{
        type : fVerifyType.require(),
    }, {
        type : fVerifyType.regex(/^[1][34578][0-9]{9}$/),
    }],
    error : function (d, code, msg){
        $("#telephoneTip").html(msg);
        $("#telephoneTip").show();
    },
    success :function (d, res){
   	 	$("#telephoneTip").html("");
        $("#telephoneTip").hide();
   }
});

//新手机号验证码验证
formCheck.addVerify({
    id : '新手机号验证码',     
    selector : '#newTelCode', 
    blur : true,           
    
    DAlias : 'spanNewTelCode', 
    verify:[{
        type : fVerifyType.require(),
    }, {
        type : fVerifyType.regex(/^\d{6}$/),
    }],
    error : function (d, code, msg){
        $("#spanNewTelCode").html(msg);
        $("#spanNewTelCode").show();
    },
    success :function (d, res){
    	 $("#spanNewTelCode").html("");
         $("#spanNewTelCode").hide();
    }
});

//原图片验证码验证
formCheck.addVerify({
    id : '原手机图片验证码',     
    selector : '#orgiKaptcha', 
    blur : true,           
    skipSubmit:true,
    DAlias : 'orgiSpanimgCode', 
    verify:[{
        type : fVerifyType.require(),
    }, {
        type : fVerifyType.regex(/^[a-zA-Z0-9]{4}$/),
    },{
    	type : fVerifyType.post("/commonset/verPicCode", function(){return {code:$("#orgiKaptcha").val(),picKey:$("#orgiKaptchaImage").attr("key")}
	     }),
       success : function(d, res){
           if(typeof res !== 'object'){
               res = JSON.parse(res);
           }
           if(!res){
               showErrMsg(d.DAlias, '网络错误');
           }else if(res.obj){
        	   $("#orgiPhoneCode").show();
				$("#orgiImgCode").hide();
				$("#orgiSpanimgCode").html("")
				$("#orgiSpanimgCode").hide();
               return true;  
           }else{
        	   $("#orgiSpanimgCode").html(res.msg)
        	   $("#orgiSpanimgCode").show();
               return false; 
           }
       }
    }],
    error : function (d, code, msg){
    	$("#orgiSpanimgCode").html(msg)
  	    $("#orgiSpanimgCode").show();
    },
    success :function (d, res){
   	 	$("#orgiSpanimgCode").html("");
        $("#orgiSpanimgCode").hide();
   }
});

//原图片验证码验证
formCheck.addVerify({
    id : '新手机图片验证码',     
    selector : '#newKaptcha', 
    blur : true,           
    skipSubmit:true,
    DAlias : 'orgiSpanimgCode', 
    verify:[{
        type : fVerifyType.require(),
    }, {
        type : fVerifyType.regex(/^[a-zA-Z0-9]{4}$/),
    },{
    	type : fVerifyType.post("/commonset/verPicCode", function(){return {code:$("#newKaptcha").val(),picKey:$("#newKaptchaImage").attr("key")}
	     }),
       success : function(d, res){
           if(typeof res !== 'object'){
               res = JSON.parse(res);
           }
           if(!res){
        	   $("#newSpanimgCode").html('网络错误')
        	   $("#newSpanimgCode").show();
           }else if(res.obj){
        	   $("#newPhoneCode").show();
			   $("#newImgCode").hide();
			   $("#newSpanimgCode").html();
			   $("#newSpanimgCode").hide();
               return true;  
           }else{
        	   $("#newSpanimgCode").html(res.msg)
        	   $("#newSpanimgCode").show();
               return false; 
           }
       }
    }],
    error : function (d, code, msg){
       $("#newSpanimgCode").html(msg)
  	   $("#newSpanimgCode").show();
    },
    success :function (d, res){
   	 	$("#newSpanimgCode").html("");
        $("#newSpanimgCode").hide();
   }
});
//------------------------------- 验证表单 end---------------------------------


function formSubmit(){
	$("#modifyMobileForm").submit();
}
//图片验证码绑定点击事件
$(function() {
	$('#newKaptchaImage').click(function() {
		getImgVerifyCode(this);
	});
	
	$('#orgiKaptchaImage').click(function() {
		getImgVerifyCode(this);
	});
});


//点击页面更换按钮
function changeTel(){
	$("#rz-box-bg").show();
	$("#changeTelDiv2").show();
	offsetDiv("#changeTelDiv2");
	// 修改到出现修改框时
/*	// 加载图片验证码
	var key = uuid(16);
	//生成验证码  
	$("#newKaptchaImage").attr('src','/commonset/pictureCode?picKey='+ key);
	$("#newKaptchaImage").attr('key',key);
	
	var oldKey = uuid(16);
	//生成验证码  
	$("#orgiKaptchaImage").attr('src','/commonset/pictureCode?picKey='+ oldKey);
	$("#orgiKaptchaImage").attr('key',oldKey);*/
	
	
	// 如果在获取验证码中 则不重新获取验证码
	if($("#orgiSendSMSID").attr("disabled")!=="disabled"){
		getImgVerifyCode("#orgiKaptchaImage");
		$("#orgiSendSMSID").html("获取验证码");
	}
	if($("#newSendSMSID").attr("disabled")!=="disabled"){
		getImgVerifyCode("#newKaptchaImage");
		$("#newSendSMSID").html("获取验证码");
	}
}

// 获取验证码
function getImgVerifyCode(e){
	var key = uuid(16);
	// 生成验证码  
	$(e).hide().attr('src','/commonset/pictureCode?picKey='+ key).fadeIn();
	$(e).attr('key',key);
	// show验证码img
	var trObj=$(e).parent().parent();
	$(trObj).show();
	$(trObj).find("span").html("").show();
	// hide 
	$(trObj).next().find("span").html("");
	$(trObj).next().hide();
}


//发送短信 -原手机号
function sendSMSOld(val) {
	formCheck.verify("原手机号");
	var oldKaptchaKey = $("#orgiKaptchaImage").attr('key');
	var oldCode = $("#orgiKaptcha").val();
	var telephone =  $("#orgiTelephone").val();
	$.post("/sms/sendModiPhoneVerifyOld",{phone:telephone,picKey:oldKaptchaKey,picCode:oldCode},function(res){
		if(res.code==200){
			$("#spanOrgiTelCode").hide();
			settime(val);
			return true;
		}else if(res.code==3007){
			getImgVerifyCode("#orgiKaptchaImage");
		}
		else{
			$("#spanOrgiTelCode").html(res.msg);
			$("#spanOrgiTelCode").show();
			return false;
		}
	});
}

//发送短信 -新手机号
function sendSMS(val) {
	formCheck.verify("新手机号");
	var kaptchaKey = $("#newKaptchaImage").attr('key');
	var code = $("#newKaptcha").val();
	var telephone =  $("#telephone").val();
	$.post("/sms/sendModiPhone",{phone:telephone,picKey:kaptchaKey,picCode:code},function(res){
		if(res.code==200){
			$("#spanNewTelCode").hide();
			settime(val);
			return true;
		}else if(res.code==3007){
			getImgVerifyCode("#newKaptchaImage");
		}else{
			$("#spanNewTelCode").html(res.msg);
			$("#spanNewTelCode").show();
			return false;
		}
	});
}



//获取验证码倒计时 
var countdown = 120;
var countdownOld=120;

// val 为button对象
// - orgiSendSMSID   - newSendSMSID
function settime(val) {
	
	var id=val.id;
	var item=id.substr(0,3);
	var count;
	if(item==="new"){
		count = countdown;
	}else{
		count = countdownOld;
	}
	// 提交
	if (count == 0) {
		val.removeAttribute("disabled");
		$("#" + id).html("获取验证码")
		count = 120;
		if(item === "new"){
			countdown = count;
		}else{
			countdownOld = count;
		}
		// 获取图片验证码对象 ！！ TAT
		var obj = $("#" + id).parent().parent().prev().find("img");
		getImgVerifyCode(obj);
		
	} else {
		val.setAttribute("disabled", true);
		$("#" + id).html("重新发送(" + count + ")")
		val.value = "重新发送(" + count + ")";
		count--;
		if(item === "new"){
			countdown = count;
		}else{
			countdownOld = count;
		}
		if (count > -1) {
			setTimeout(function() {
				settime(val)
			}, 1000)

		}
	}
}

