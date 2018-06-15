<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>

<style type="text/css">
.login-form-div {
	position: relative;
	z-index: 1;
	outline: none;
}

.login-form-div span {
	position: absolute;
	z-index: 2;
	left: 40px;
	top: 10px;
}
</style>
<script type="text/javascript" src="/resource/js/users/login.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#denglu").addClass("hover");
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

		/*客服*/

		$(".kefuLeft a").toggle(function() {
			$(".kefuRight").show();

		}, function() {
			$(".kefuRight").hide();
		})

		$(".rz-close,.button_ok,.button_esc,.button_escc").click(function() {
			$("#fails").hide();
			$("#rz-box-bg").hide();
		})
	})
</script>




</head>
<body>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<div class="mainbody "
		style="background: url('/resource/images/bg_login.jpg') no-repeat center top #fdeeb6;">
		<div class="box"
			style="positon: relative; z-index: 1; height: 600px; min-heihgt: 600px;">
			<div class="logindiv" style="height: 600px; min-height: 600px">

				<form id="loginForm" onsubmit="return login()">
					<h2>登录领钱儿</h2>
					<div id="p_login_t" style="height: 25px"></div>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="2">
								<div class="login-form-div" id="div_account" tabindex="0"
									hidefocus="true">
									<i style="background: url('/resource/images/bg_login_input.jpg') no-repeat left top;"></i>
									<input type="text" name="account" id="account" required
										placeholder="手机号码/用户名" onfocus="this.style.color='#434343'" />
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="height: 90px;">
								<div class="login-form-div" id="div_loginPsw" tabindex="1"
									hidefocus="true">
									<i style="background: url('/resource/images/bg_login_input.jpg') no-repeat left -58px;"></i>
									<input type="password" name="password" id="password" required
										placeholder="登录密码" onfocus="this.style.color='#434343'" />
								</div>
								<div
									style="display: block; margin-top: 10px; margin-right: 38px; text-align: right;">
									<a href="/users/forgetpassword">忘记密码？</a>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="padding-top: 10px; height: 30px;">
								<div style="margin-left: 38px; padding-left: 20px; background: url('/resource/images/bao.png') no-repeat 0 center">领钱儿保障您的账户资金流转安全</div>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="image" id="submit_btn" src="/resource/images/button_login.png" style="width: 270px; padding-left: 0; background: none; border: none" />
							</td>
						</tr>
						<tr>
							<td colspan="2" style="padding-left: 38px; height: 30px;">还没有领钱儿账号？
								<a href="/users/regist" style="color: #ea5513;">免费注册</a>
							</td>
						</tr>

					</table>
				</form>

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

			if ($("#account").val() != "") {
				$(".ssss:eq(0)").find("span").hide();
				$("#account").css("color", "#434343");
			}
			if ($("#loginPsw").val() != "") {
				$(".ssss:eq(1)").find("span").hide();
				$("#loginPsw").css("color", "#434343");
			}
		}
	</script>

</body>
</html>