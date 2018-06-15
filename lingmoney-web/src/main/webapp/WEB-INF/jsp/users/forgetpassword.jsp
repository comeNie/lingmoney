<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<script type="text/javascript" src="/resource/js/utils/numberUtils.js"></script>
<script type="text/javascript" src="/resource/js/users/forgetpassword.js"></script>
<script type="text/javascript" >
$(document).ready(function(){
	$(".main-head li:last").css("border","none");
	$(".main3 li:last").css("margin-right","0");
	$(".licai li:last").css("margin-right","0"); 
   
	$("#sendSMSID").removeAttr("disabled");
	/*顶部*/		 
	$(".leftul .weixinLi").hover(function(){
	    $(".popweixin").show();
	},function(){
	    $(".popweixin").hide();
	}) 
	$(".leftul .qqLi").hover(function(){
	    $(".popQQ").show();
	},function(){
	    $(".popQQ").hide();
	}) 	
	$(".phone_code").hide();
	/*顶部*/
	// 加载图片验证码
	var key = uuid(16);
	//生成验证码  
	$("#kaptchaImage").attr('src','/commonset/pictureCode?picKey='+ key);
	$("#kaptchaImage").attr('key',key);
	
})  
</script>
    
</head>
<body>
  
<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<!-- 确认弹出框-->
	<div id="rz-box-bg"></div>
	<div class="rz-box-con " id="ask">
		<div class="rz-title">
			<h2>修改密码</h2>
			<a href="javascript:void(0)" class="rz-close"><img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<p class="color" style="padding-top: 20px; font-size: 20px;">密码修改成功！</p>
		<p style="padding-top: 20px;">
			<a href="javascript:void(0)" class="rz-button button_ok">确定</a>
			<!-- <a href="javascript:void(0)" class="rz-button button_esc">取消</a> -->
		</p>
	</div>
	<!-- 确认弹出框-->
  <div class="mainbody">
  <div style="height:630px;background:url('/resource/images/bg_backCode2.jpg') no-repeat center top #e0e6e5">
  	
 
    <div class="box" style="positon:relative;z-index:1;background:url('/resource/images/bg_backCode.png') no-repeat 100px 40px;height:630px;">
	  <div class="logindiv registdiv" style="height:630px">
	    <form id="form" method="post">
		<h2 style="padding:50px 0 5px 38px;font-size:25px;text-align:left">找回密码</h2>
		<p style="padding:0;padding:0 0 20px 38px;text-align:left;">温馨提示：请输入手机号，找回密码！</p>
		  <table width="100%" cellpadding="0" cellspacing="0">
		    <tr>
			  <td colspan="2">
			    <div class="login-form-div">
			        <i style="background:url('/resource/images/bg_regist_input.jpg') no-repeat left -128px"></i>
			    	<input type="text" name="telephone" id="telephone" autocomplete="off"
								class="input-for"  placeholder="请输入您的手机号"
								onblur="verifyTelephone()" onfocus="this.style.color='#434343'" />
			    </div><span id="span_telephone"
								class="regist-img-ok" style="display: none"><img
									src="/resource/images/bg_true.gif" /></span>
				<div id="tip_telephone" class="tip_regist"></div>
			  </td>
			</tr>
			
			<tr class="img_code">
				<!-- <td   style="height:62px;"> -->
				<td colspan="2">
					<div class="img_code-div">
						<input name="kaptcha" type="text" id="kaptcha" maxlength="5"  onblur="checkImgCode()"
							class="chknumber_input" 
							style="display: inline-block; float: left; margin-right: 10px; width: 109px;" />
						<img id="kaptchaImage" style="display: inline-block; float: left; margin-bottom: -3px; width:132px;height:35px" key="" />
						<div id="tip_kaptcha" class="tip_regist" style="padding-left: 0;"></div>
					</div>
				</td>
			</tr>
			
			
			<tr  class="phone_code">
			  <td colspan="2">
			   <div class="img_code-div">
			     <div class="login-form-div" style="width:155px;float:left">
			        <i style="background:url('/resource/images/bg_regist_input.jpg') no-repeat left -193px"></i>
			    	<input type="text" name="verificationCode" id="telVcode" class="input-for"  placeholder="手机验证码"
								onblur="validateCode()" onfocus="this.style.color='#434343'"
										style="display: inline-block; width: 110px;" />
			    </div>
			    <span id="span_telVcode"
								style="display: none; float: left; padding-top: 5px;"
								class="regist-img-ok"><img
									src="/resource/images/bg_true.gif" /></span>
			    <button type="button" id="sendSMSID"  onclick="sendSMS(this);" class="button_yzm">获取验证码</button>
			    </div>
				<div id="tip_telVcode" class="tip_regist" style="clear:both"></div>
			  </td>
			</tr>
			<td colspan="2">
			    <div class="login-form-div">
			        <i style="background:url('/resource/images/bg_regist_input.jpg') no-repeat left -65px"></i>
			    	<input type="password"  name="password" id="password" autocomplete="off"
								class="input-for" onblur="verifyLoginPsw()" 
								placeholder="请输入6-16位新密码" onfocus="this.style.color='#434343'" />
			    </div>
			    <span id="span_password" style="display: none"
								class="regist-img-ok"><img
									src="/resource/images/bg_true.gif" /></span>
				<div id="tip_password" class="tip_regist"></div>
			  </td>
			<tr>
			  <td colspan="2">
                 <div class="login-form-div">
			        <i style="background:url('/resource/images/bg_regist_input.jpg') no-repeat left -65px"></i>
			    	 <input type="password" class="input-for" name="repwd"  autocomplete="off"
								id="repwd" onblur="verifyLoginPswConfig()"  placeholder="再次输入新密码"  onfocus="this.style.color='#434343'"  />
			    </div>
			    <span id="span_repwd" style="display: none" class="regist-img-ok">
									<img src="/resource/images/bg_true.gif" />
							</span>
				<div id="tip_repwd" class="tip_regist"></div>
			  </td>
			</tr>
			
			<tr>
			  <td colspan="2" align="center" style="padding-top:20px">
			  	<input type="button" value="提交" id="submitForm" class="input-sub" style="cursor: pointer; background: #ea5513" />
			  </td>
			</tr>
			<tr>
			  <td colspan="2" align="right" style="padding-right:30px;height:30px;">返回<a href="/index.html" style="color:#ea5513;">首页</a></td>
			</tr>
		  </table>
		</form>
	  </div>	
	</div>
    
    </div>
  </div>
	
	
<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	<script>
		window.onload = function() {
			setTimeout(fun, 100);
		};

		function fun() {
			   $("#telephone").val(""); 
				$("#telVcode").val(""); 
				$("#password").val(""); 
				$("#repwd").val("");  
		
			if($("#telVcode").val()!=""){
				$(".ssss:eq(1)").find("span").hide();
				$("#telVcode").css("color","#434343");
			}
			if($("#password").val()!=""){
				$(".ssss:eq(2)").find("span").hide();
				$("#password").css("color","#434343");
			}
		}
	</script> 
 </body>
 </html>