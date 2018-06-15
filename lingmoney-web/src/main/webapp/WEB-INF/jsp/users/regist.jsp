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
<script type="text/javascript">
	$(document).ready(function() {
		// 如果页面同意条款已勾选  
		if($("input.pro").attr("checked")){
			$("input.submit").css("background", "#ea5513");
			$("input.submit").attr("disabled", false);
		}
		
		$("#zhuce").addClass("hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*顶部*/
		$(".leftul .weixinLi").hover(function() {
			$(".popweixin").show();
		}, function() {
			$(".popweixin").hide();
		})
		$(".leftul .qqLi").hover(function() {
			$(".popQQ").show();
		}, function() {
			$(".popQQ").hide();
		})
		/*顶部*/
		$("input.pro").click(function() {
			if ($(this).attr("checked")) {
				$("input.submit").css("background", "#ea5513");
				$("input.submit").attr("disabled", false);
			} else {
				$("input.submit").css("background", "#ccc");
				$("input.submit").attr("disabled", true);
			}
		});
		var Request = new Object();
		Request = GetRequest();
		var referralTel = Request["referralTel"];
		$("#referralTel").val(referralTel);
		$("#referralTel").css("color", "#434343");
		$(".phone_code").hide();

		$("#agree_protocal").click(function() {
			$("#rz-box-bg").show();
			$("#protocol").show();
			offsetDiv("#protocol");
		})
		$(".rz-close,.a_go").click(function() {
			$("#rz-box-bg").hide();
			$("#protocol").hide();
		})

		$("#agree_protocal2").click(function() {
			$("#rz-box-bg").show();
			$("#protocol2").show();
			offsetDiv("#protocol2");
		})
		$(".rz-close").click(function() {
			$("#rz-box-bg").hide();
			$("#protocol2").hide();
		})
		$(".a_go").click(function() {
			$("input.pro").attr("checked",true);
			$("input.submit").css("background", "#ea5513");
			$("input.submit").attr("disabled", false);
			$("#rz-box-bg").hide();
			$("#protocol2").hide();
		})
		
		$("#rz-box-bg").hide();
	
		$("#sendSMSID").removeAttr("disabled");
		//alert("-----------------");
		
		// 加载图片验证码
		var key = uuid(16);
		//生成验证码  
		$("#kaptchaImage").attr('src','/commonset/pictureCode?picKey='+ key);
		$("#kaptchaImage").attr('key',key);
		
	})
	$(function() {
		$('#kaptchaImage').click(function() {
			var key = uuid(16);
			//生成验证码  
			$(this).hide().attr('src','/commonset/pictureCode?picKey='+ key).fadeIn();
			$(this).attr('key',key);
			event.cancelBubble = true;
		});
	});

	window.onbeforeunload = function() {
		//关闭窗口时自动退出  
		if (event.clientX > 360 && event.clientY < 0 || event.altKey) {
			//alert(parent.document.location); 
		}
	};

	function changeCode() {
		var key = uuid(16);
		$(this).attr('key',key);
		$('#kaptchaImage').hide().attr('src','/commonset/pictureCode?picKey='+ key).fadeIn();
		event.cancelBubble = true;
	}
</script>

<Script language="javascript">
	function GetRequest() {
		var url = location.search; //获取url中"?"符后的字串
		var theRequest = new Object();
		if (url.indexOf("?") != -1) {
			var str = url.substr(1);
			strs = str.split("&");
			for (var i = 0; i < strs.length; i++) {
				theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
			}
		}
		return theRequest;
	}
</Script>
</head>
<body>
	<div id="rz-box-bg"></div>
	<!-- 协议弹框 -->
	<jsp:include page="/agreement/registAgreement.jsp"></jsp:include>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<div class="mainbody" style="background:url('/resource/images/bg_regist2.jpg') no-repeat center top #f1dd44">
		<div class="box" style="positon: relative; z-index: 1;height: 700px">
			<div class="logindiv registdiv" style="height: 700px">
				<form class="form_regist" id="regForm" method="post" action="/users/regist">
				<input  name="channel" type="hidden"   value="1" />
					<h2>注册领钱儿账号</h2>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="2">
								<div class="login-form-div">
									<i style="background: url('/resource/images/bg_regist_input.jpg') no-repeat left -128px"></i>
									<input type="text"   name="telephone"
										id="telephone" required placeholder="请输入您的手机号"
										onfocus="this.style.color='#434343'" />
								</div> 
								<span id="span_telephone" class="regist-img-ok">
									<img src="/resource/images/bg_true.gif" />
								</span>
								<div id="tip_telephone" class="tip_regist"></div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="login-form-div">
									<i style="background: url('/resource/images/bg_regist_input.jpg') no-repeat left -65px"></i>
									<input type="password" name="loginPsw" id="loginPsw" required placeholder="6-16位数字、字母和符号任二组合" onfocus="this.style.color='#434343'" />
								</div> 
								<span id="span_loginPsw" class="regist-img-ok">
									<img src="/resource/images/bg_true.gif" />
								</span>
								<div id="tip_loginPsw" class="tip_regist"></div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="login-form-div">
									<i style="background: url('/resource/images/bg_regist_input.jpg') no-repeat left -65px"></i>
									<input type="password"  
										name="loginPswConfig" id="loginPswConfig" required
										placeholder="请再次输入登录密码" onfocus="this.style.color='#434343'" />
								</div> 
								<span id="span_loginPswConfig" class="regist-img-ok">
									<img src="/resource/images/bg_true.gif" />
								</span>
								<div id="tip_loginPswConfig" class="tip_regist"></div>
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

						<tr class="phone_code">
							<td colspan="2">
								<div class="img_code-div">
									<div class="login-form-div" style="width: 155px; float: left;">
										<i style="background: url('/resource/images/bg_regist_input.jpg') no-repeat left -193px"></i>
										<input type="text" name="verificationCode" id="telVcode" required onfocus="this.style.color='#434343'"
											style="display: inline-block; width: 74%;" />
									</div>
									<button type="button" id="sendSMSID" onclick="sendSMS(this);" 
										class="button_yzm" style="float: left" >获取验证码</button>
								</div>
								<div id="tip_telVcode" class="tip_regist" style="clear: both"></div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="login-form-div">
									<i style="background: url('/resource/images/bg_regist_input.jpg') no-repeat left -256px">
									</i> <input type="text"  
										name="referralTel" id="referralTel" placeholder="请输入推荐码（选填）"
										onfocus="this.style.color='#434343'" />
								</div> 
								<span id="span_referralTel" class="regist-img-ok">
									<img src="/resource/images/bg_true.gif" />
								</span>
								<div id="tip_referralTel" class="tip_regist"></div>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="height: 60px;">
								<div style="padding-left: 40px; width: 280px;">
									<input type="checkbox"
										style="float: left; margin: 0; margin-top: 4px; padding: 0; display: inline; width: 15px; height: 15px;"
										class="pro" />&nbsp;我已阅读并同意领钱儿<a href="javascript:void(0)"
										id="agree_protocal" style="color: #ea5513; margin: 0 5px;">用户协议</a>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="height: 50px;" align="center"><input
								type="submit" value="同意并注册" class="submit" disabled="disabled" /></td>
						</tr>
						<tr>
							<td colspan="2" align="center" style="height: 40px;">已有账号，<a
								href="login" style="color: #ea5513; margin: 0 5px;">直接登录
									&gt;&gt;</a></td>
						</tr>
					</table>
				</form>
				<div class="registBottomTip">
					<p>注册成功后即可开通华兴银行资金存管账户,交易资金全程存管</p>
				</div>
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
	<script>
		window.onload = function() {
			setTimeout(fun, 50);
		};

		function fun() {
			/*input*/

			if ($("#telephone").val() != "") {
				$(".ssss:eq(0)").find("span").hide();
				$("#telephone").css("color", "#434343");
			}
			if ($("#loginPsw").val() != "") {
				$(".ssss:eq(1)").find("span").hide();
				$("#loginPsw").css("color", "#434343");
			}

		}
	</script>
	
	<input id="dataInput" value="${msg}" style="display:none"/>
</body>
<script type="text/javascript" src="/resource/js/users/formCheck.js"></script>
<script type="text/javascript" src="/resource/js/users/regist.js"></script>
</html>