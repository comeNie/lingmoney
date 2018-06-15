<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>


<script type="text/javascript" src="/resource/js/updatePsw.js"></script>
<style type="text/css">
.tip_update {
	clear: both;
	dispaly: none;
	height: 25px;
	line-height: 25px;
	color: red;
}
</style>



<script type="text/javascript">
	$(document).ready(function() {
		$("#membersUserCode").addClass("hover");
		$("#nav_member03").addClass("nav-hover");
		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*顶部*/

		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		})
		/*顶部*/
		
		

		$('#image').click(function() {
			if (oldPsws() && newPsws() && configPsws()) {
				$.ajax({
					type : "post",
					url : "/myAccount/updatePsw",
					data : $("#form").serialize(),
					success : function(data) {
						if(data!="success") {
							$("#ask p:first").text(data);
							$("#ask .button_ok").click(function() {
								askClose()
							});
						} else{
							$("#ask p:first").text("密码修改成功！");
							$("#ask .button_ok").click(function() {
								window.location.href="/login";
							});
						}
						$("#rz-box-bg").show();
						$("#ask").show();
						offsetDiv("#ask");
						
					},
					error : function(data) {
						// 跳转到500
						window.location.href="/500";
					}
				});
			}
		});

		

		$(".rz-close").click(function() {
			askClose();
		});

	    $(".modifyTradingPassWordBtn").click(function(){
	    	modifyTradingPassWord();
		})

	})
</script>

</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->

	<div class="mainbody_member">
		<div class="box clearfix">
			<!-- 我的账户菜单开始 -->
			<jsp:include page="/common/myaccount.jsp"></jsp:include>
			<!-- 我的账户菜单结束 -->

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
				</p>
			</div>
			<!-- 确认弹出框-->

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle" >修改密码</div>
					<div class="zonglan clearfix">
					   
                            <p style="color: red; margin:10px 0px;">为保证账户的安全登录，请牢记您的新密码！</p>
                      
					
						
						
                      
                        
						<div><p  style="font-size: 16px; font-weight:bold;" >修改登录密码</p></div>
						<form method="post" id="form">
							<table cellpadding="0" cellspacing="0" class="table_realname">
								<tr>
									<td height="54" width="110"><span class="color">*</span>旧密码：</td>
									<td><input type="password" onblur="oldPsws()" style="color: #000"
										name="oldPsw" id="oldPsw" required /></td>
									<td style="padding-left: 10px;"><div id="tip_oldPsw"
											class="tip_update"></div> <span style="display: none"
										id="span_oldPsw"><img
											src="/resource/images/bg_true.gif" /></span></td>
								</tr>
								<tr>
									<td height="54" width="110"><span class="color">*</span>新密码：</td>
									<td><input type="password" name="newPsw" id="newPsw" style="color: #000"
										onblur="newPsws()" required /></td>
									<td style="padding-left: 10px;"><div id="tip_newPsw"
											class="tip_update"></div> <span style="display: none"
										id="span_newPsw"><img
											src="/resource/images/bg_true.gif" /></span></td>
								</tr>
								<tr>
									<td height="54" width="110"><span class="color">*</span>确认新密码：</td>
									<td><input type="password" name="configPsw" id="configPsw" style="color: #000"
										onblur="configPsws()" required /></td>
									<td style="padding-left: 10px;"><div id="tip_configPsw"
											class="tip_update"></div> <span style="display: none"
										id="span_configPsw"> <img
											src="/resource/images/bg_true.gif" /></span></td>
								</tr>
                                <tr>
									<td colspan="2"  height="100" align="center"  style="margin-top:20px">
									   <a href="javascript:void(0)" id="image"
									class="button_infor">修改登录密码</a>
									</td>
								</tr>
							</table>

						</form>
						<!-- 修改交易密码 -->
						<div><p style="font-size: 16px; font-weight:bold; margin-top:15px; " >修改交易密码</p></div>
                        <form action="" target="_blank" method="post" id="modifyTradingPassWord">
                            <input type="hidden" id="requestData" name="RequestData" />
                            <input type="hidden" id="transCode" name="transCode" />
                        </form>
                        
                        <table>
                            <tr>   
                                <td width="50"></td>
                                <td height="100" align="center">
                                   <a href="javascript:void(0)" class="modifyTradingPassWordBtn button_infor">修改交易密码</a>
                                </td>
                            </tr>
                        </table>
					</div>

				</div>

			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>