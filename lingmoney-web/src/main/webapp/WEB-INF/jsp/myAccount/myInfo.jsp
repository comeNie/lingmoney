<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1" />
<jsp:include page="/common/head.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="/resource/css/calendar.css">

<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/calendar.js"></script>
<script type="text/javascript" src="/resource/js/calendar-zh2.js"></script>
<script type="text/javascript" src="/resource/js/calendar-setup.js"></script>
<script type="text/javascript" src="/resource/js/city.min.js"></script>
<script type="text/javascript" src="/resource/js/swfobject.js"></script>
<script type="text/javascript" src="/resource/js/fullAvatarEditor.js"></script>
<script type="text/javascript" src="/resource/js/utils/numberUtils.js"></script>
<!-- <script type="text/javascript" src="/resource/js/jsAddress.js"></script> -->
<style type="text/css">
.div_swfContainer {
	display: none;
	position: absolute;
	z-index: 10000;
	top: 100px;
	left: 50%;
	width: 700px;
	margin: 30px auto;
	margin-left: -350px;
	background: #fff;
	box-shadow: 3px 2px 4px #bcbcbc;
	-webkit-box-shadow: 3px 2px 4px #bcbcbc;
	-moz-box-shadow: 3px 2px 4px #bcbcbc;
}

#changeTelDiv form {
	width: 100%;
}

#changeTelDiv form>table {
	margin: 0 auto;
	width: 660px;
}

#changeTelDiv input {
	padding-left: 10px;
}

#changeTelDiv form table p {
	text-align: left;
	margin-bottom: 0;
}
#orgiSendSMSID,#newSendSMSID{
   display:inline-block;
   margin-left:10px;
   width:100px;
   height:40px;
   line-height:40px;
   color:#fff;
   text-align:center;
   cursor:pointer;
   background:#ea5513;
   font-size:14px;
}
.disabled{
  background:#ccc;
}
.tip_span{
  display:inline-block;
  float:left;
  margin-top:12px;
  margin-left:7px;
}
</style>



<script type="text/javascript">
	var cacheVal = "";
	var cacheEmailVal = "";
	//加载对应用户信息 
	$(document).ready(function() {
		var edu = $("#edu").val();
		//选中用户对应教育程度 
		var len1 = $("#education").find("option").length;
		for (var i = 0; i < len1; i++) {
			var education = document.getElementById("education").options[i];
			if (edu == education.value) {
				education.selected = "selected";
			}
		}
		var jobs = $("#jobs").val();
		//选中用户对应职业 
		var len2 = $("#job").find("option").length;
		for (var i = 0; i < len2; i++) {
			var job = document.getElementById("job").options[i];
			if (jobs == job.value) {
				job.selected = "selected";
			}
		}

		//左侧导航栏选中样式 
		$("#membersUser").addClass("hover");
		$("#nav_member03").addClass("nav-hover");

		$(".main-head li:last").css("border", "none");
		$(".main3 li:last").css("margin-right", "0");
		$(".licai li:last").css("margin-right", "0");

		/*顶部*/
		$(".media-link li:first").hover(function() {
			$(this).find("div").show();
		}, function() {
			$(this).find("div").hide();
		});
		/*顶部*/
		
		
							
		// 光标进入带星号的input  置空
		$("#wechat").focus(function(){
			var wechat = $(this).val();
			if(wechat.indexOf("****")>0){
				cacheVal = wechat;
				$(this).val("");
			}
			
		})
		$("#email").focus(function(){
			var email = $(this).val();
			if(email.indexOf("****")>0){
				cacheEmailVal = email;
				$(this).val("");
			}
			
		})
	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		$('.son_ul').hide(); //初始ul隐藏
		$('.select_box span').click(function() { //鼠标移动函数
			$(this).parent().find('ul.son_ul').slideDown(); //找到ul.son_ul显示
			$(this).parent().find('li').hover(function() {
				$(this).addClass('hover')
			}, function() {
				$(this).removeClass('hover')
			}); //li的hover效果
			$(this).parent().hover(function() {
			}, function() {
				$(this).parent().find("ul.son_ul").slideUp();
			});
		});
		$('ul.son_ul li').click(function() {
			$(this).parents('li').find('span').html($(this).html());
			$(this).parents('li').find('ul').slideUp();
		});

	});
</script>
<script type="text/javascript">
	$(function() {
		$(".a_editorphoto").click(function() {
			$("#rz-box-bg").show();
			$(".div_swfContainer").show();
		})

		$(".rz-close").click(function() {
			$("#rz-box-bg").hide();
			$(".div_swfContainer").hide();
			$("#changeTelDiv").hide();
			$("#changeTelDiv2").hide();
			$("#changeTelDiv3").hide();
			initTips();
		});
		
		$(".rz-button.reset").click(function(){
			$("#rz-box-bg").hide();
			$("#changeTelDiv").hide();
			$("#changeTelDiv2").hide();
			$("#changeTelDiv3").hide();
			initTips();
		});
	})
</script>

</head>
<body style="position: relative; z-index: 1;">

	<!-- 确认弹出框-->
	<div id="rz-box-bg"></div>
	<div class="rz-box-con " id="ask">
		<div class="rz-title">
			<h2>修改资料</h2>
			<a href="javascript:void(0)" class="rz-close"><img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<p class="color" style="padding-top: 20px; font-size: 20px;">资料修改成功！</p>
		<p style="padding-top: 20px;">
			<a href="javascript:void(0)" class="rz-button button_ok" onclick="javascript:window.location.reload();">确定</a>
		</p>
	</div>
	<!-- 确认弹出框-->
	<div id="rz-box-bg"></div>
	<div class="div_swfContainer">
		<div class="rz-title" style="margin-bottom: 20px; border-left: 0">
			<h2>头像设置</h2>
			<a href="javascript:void(0)" title="关闭" class="rz-close"><img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<div style="padding: 10px 0 20px 40px;">
			<p id="swfContainer">
				本组件需要安装Flash Player后才可使用，请从<a
					href="http://www.adobe.com/go/getflashplayer">这里</a>下载安装。
			</p>
		</div>
	</div>
	<script type="text/javascript">
		swfobject.addDomLoadEvent(function() {
			var swf = new fullAvatarEditor("swfContainer", {
				id : 'swf',
				upload_url : '/myAccount/modifyAvatar',
				src_upload : 2
			},
			function(msg) {
				switch (msg.code) {
				case 3:
					if (msg.type == 0) {
						alert("摄像头已准备就绪且用户已允许使用。");
					} else if (msg.type == 1) {
						alert("摄像头已准备就绪但用户未允许使用！");
					} else {
						alert("摄像头被占用！");
					}
					break;
				case 5:
					if (msg.type == 1) {

						$("#img").attr("src",
								msg.content.obj.join("\n"));
						$("#rz-box-bg").hide();
						$(".div_swfContainer").hide();
					}
					break;
				}
			});
		});
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
		document.write(unescape("%3Cscript src='" + _bdhmProtocol
						+ "hm.baidu.com/h.js%3F5f036dd99455cb8adc9de73e2f052f72' type='text/javascript'%3E%3C/script%3E"));
	</script>

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

			<div class="mRight">
				<div class="mRight01">
					<div class="mtitle">我的资料</div>
					<div class="zonglan clearfix">
						<p style="color: #ea5513; margin: 20px 0;">★温馨提示：亲爱的用户，请认真填写以下信息，确保信息的准确、真实，以便领钱儿为您提供更好的服务。</p>
						<form id="formid" class="infor" method="post">
							<table>
								<tr>
									<td width="92">会员等级：</td>
									<td>
										<p style="width: 53px; height: 20px;">
											<span style="display: inline-block; float: left; margin-right: 5px;">${CURRENT_USER.degreeName}</span>
											<img src="${CURRENT_USER.degreePicture} "
												style="display: inline-block; float: left; padding-top: 5px;" />
										</p>
									</td>
								</tr>
								<tr>
									<td>当前头像：</td>
									<td>
										<p class="mu_hphoto">
											<c:choose>
												<c:when test="${CURRENT_USER.picture==null||CURRENT_USER.picture=='' }">
													<img id="img" src="/resource/images/img_memberTouxiang.gif"
														width="105" height="105" />
												</c:when>
												<c:otherwise>
													<img id="img" src="${CURRENT_USER.picture}"
														width="105" height="105" />
												</c:otherwise>
											</c:choose>
										</p> 
										<input type="hidden" id="picture" name="picture" /> 
										<a href="javascript:void(0)" class="a_editorphoto">修改头像</a>
									</td>
								</tr>
								<tr>
									<td>昵称：</td>
									<td colspan="3">
										<input type="text" id="nickName" name="nickName" onblur="checkNickName()"
											value="${CURRENT_USER.nickName }" />
										<span id="span_nickName" style="display: none; color: red; margin-left: 10px;"></span>
									</td>
								</tr>
								<tr>
									<td>手机：</td>
										<td colspan="3">${CURRENT_USER.telephone }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="javascript:void(0)" style="color:#ea560e;" onclick="changeTel()" style="cursor:pointer">更换</a>
									</td>
								</tr>
								<tr>
									<td>性别：</td>
										<c:set var="sex" value="${CURRENT_USER.sex }"></c:set>
									<td colspan="3">
										<input type="radio" name="sex" value="男士" class="sex"
										<c:if test="${sex=='男士' }">checked="checked"</c:if> />男士
										<input type="radio" name="sex" value="女士" class="sex"
										<c:if test="${sex=='女士' }">checked="checked"</c:if> />女士
									</td>
								</tr>

								<!-- <tr>
									<td>常住地址：</td>
									<td colspan="3" style="postion: relative; z-index: 1;">
										<select class="select-box" id="province" name="province"></select> 
										<select class="select-box" id="city" name="city"></select> 
										<select class="select-box" id="town" name="town"></select>
									</td>
								</tr> -->
								<%-- <tr>
									<td>详细地址：</td>
									<td colspan="3"><input type="text"
										value="${CURRENT_USER.address }" name="address"
										id="address" style="width: 476px;" /></td>
								</tr> --%>
								<tr>
									<td>教育程度：</td>
									<td colspan="3">
										<select class="select-box" name="education" id="education">
											<option value="小学">小学</option>
											<option value="初中">初中</option>
											<option value="高中">高中</option>
											<option value="大学专科">大学专科</option>
											<option value="大学本科">大学本科</option>
											<option value="硕士">硕士</option>
											<option value="博士">博士</option>
											<option value="博士后">博士后</option>
											<option value="其他">其他</option>
										</select>
									</td>
								</tr>
								
								<tr>
									<td>从事职业：</td>
									<td colspan="3">
										<select class="select-box" name="job" id="job">
											<option value="学生">学生</option>
											<option value="政府机关/干部">政府机关/干部</option>
											<option value="邮电通信">邮电通信</option>
											<option value="计算机">计算机</option>
											<option value="网络">网络</option>
											<option value="商业贸易">商业贸易</option>
											<option value="银行/金融/证券/保险/理财">银行/金融/证券/保险/理财</option>
											<option value="税务">税务</option>
											<option value="咨询">咨询</option>
											<option value="社会服务">社会服务</option>
											<option value="旅游/饭店">旅游/饭店</option>
											<option value="健康/医疗服务">健康/医疗服务</option>
											<option value="房地产">房地产</option>
											<option value="交通运输">交通运输</option>
											<option value="法律/司法">法律/司法</option>
											<option value="文化/娱乐/体育">文化/娱乐/体育</option>
											<option value="媒介/广告">媒介/广告</option>
											<option value="科研/教育">科研/教育</option>
											<option value="农业/渔业/林业/畜牧业">农业/渔业/林业/畜牧业</option>
											<option value="矿业/制造业">矿业/制造业</option>
											<option value="自由职业">自由职业</option>
											<option value="其他">其他</option>
										</select>
									</td>
								</tr>
								
								<tr>
									<td>微信号：</td>
									<td colspan="3">
										<input type="text" value="${CURRENT_USER.wechat }" name="wechat"
											id="wechat" style="width: 264px;" onblur="wechats()" />
										<span id="span_wechat"
											style="display: none; color: red; margin-left: 10px;">以字母开头，6-20位字母、数字或下划线</span>
									</td>
								</tr>

								<tr>
									<td>邮箱：</td>
									<td colspan="3">
										<span>${CURRENT_USER.email }</span>
										<a style="color: #ff6600; margin: 0 15px;" href="javascript:showWin();">更换</a>
									</td>
								</tr>

								<tr>
									<td colspan="4"  style="height: 80px">
										<input type="hidden" id="edu" value="${CURRENT_USER.education }"> 
										<input type="hidden" id="jobs" value="${CURRENT_USER.job }"> 
										<%-- <input type="hidden" id="pro" value="${CURRENT_USER.province }"> 
										<input type="hidden" id="ci" value="${CURRENT_USER.city }"> 
										<input type="hidden" id="to" value="${CURRENT_USER.town }"> --%>
										<a id="a_submit" href="javaScript:void(0)" class="button_infor" style="margin-left:190px;">修改</a>
									</td>
								</tr>
							</table>
						</form>
						<script type="text/javascript">
							
							function wechats() {
								
								var par = /^[a-zA-Z][a-zA-Z\d_]{5,20}$/;
								var wechat = document.getElementById("wechat").value;
								if (wechat == " " ||wechat == "" || wechat == null) {
									// 如果之前是有值的，那么恢复原值
									if(cacheVal!=""){
										document.getElementById("wechat").value = cacheVal;
									}
									$("#span_wechat").hide();
									return true;
								} else {
									if(wechat.indexOf("****")>0){
										return true;
									}
									var ret = par.test(wechat);
									if (!ret) {
										$("#span_wechat").show();
										return ret;
									}
									$("#span_wechat").hide();
									return ret;
								}
							}
							function checkNickName(){
								var par = /^[\u4e00-\u9fa5a-zA-Z0-9_]{1,8}$/;
								var nickName = document.getElementById("nickName").value;
								if (nickName == "" || nickName == null) {
									$("#span_nickName").show();
									$("#span_nickName").html("昵称不能为空");
									return false;
								} else {
									var ret = par.test(nickName);
									if (!ret) {
										$("#span_nickName").show();
										$("#span_nickName").html("1-8位中文、字母、数字或下划线");
										return ret;
									}
									$("#span_nickName").hide();
									return ret;
								}
							}

							//ajax提交表单  
							$("#a_submit").click(function() {
								if (checkNickName()&&wechats()) {
									var img = $("#img").attr("src");
									$("#picture").val(img);
									$.ajax({
										type : "post",
										url : "/myAccount/updateUsers",
										data : $("#formid").serialize(),
										success : function(data) {
											if(data.code==3016){
												$("#span_email").html("邮箱重复");
												$("#span_email").show();
											} else if (data.code == 200){											
												$("#rz-box-bg").show();
												$("#ask").show();
												offsetDiv("#ask");												
												$("#srcManager").attr("src", $("#img").attr("src"));
											} else {
												sAlert(data.msg);
											}																		
										},
										error : function(data) {
											alert(data);
										}
									});

									$(".button_ok").click(function() {
										$("#ask").hide();
										$("#rz-box-bg").hide();
									});

									$(".rz-close").click(function() {
										$("#rz-box-bg").hide();
										$("#ask").hide();
									});

								}

							});
							
							
						   
							//初始化修改手机号提示信息
							function initTips() {
								
								$("#orgiTelephoneTip").hide();
								$("#orgiSpanimgCode").hide();
								$("#spanOrgiTelCode").hide();
								$("#orgiTelephone").val("");
								$("#orgiKaptcha").val("");
								$("#orgiTelCode").val("");
								
								
								$("#telephoneTip").hide();
								$("#newSpanimgCode").hide();
								$("#spanNewTelCode").hide();
								$("#telephone").val("");
								$("#newKaptcha").val("");
								$("#newTelCode").val("");
								
								$(".phone_code").hide();
								$(".img_code").show();		
								//$("#orgiSendSMSID").html("重新获取验证码").removeAttr("disabled");
								$("#newSendSMSID").html("重新获取验证码").removeAttr("disabled");
								$("#orgiSendSMSID").html("重新获取验证码").removeAttr("disabled");
							}
							
							
							//自定义alert
							function sAlert1(msg,code) {
								var html = '<div id="sShadow" style="position: fixed;z-index: 9999;left: 0;top: 0;width: 100%;height: 100%;background:#000;opacity:0.6;filter:alpha(opacity=60);"></div>'
								html += '<div style="position: fixed;z-index: 9999;left: 50%;top: 50%;padding-bottom: 30px;width: 700px;background: #fff;" id="sAlert">';
								html += '<div style="height: 52px;line-height:52px;padding-left:22px;border: 0;border-bottom: 1px solid #ea5413;border-left: 8px solid #ea5413;"><h2>提示</h2></div>';
								html += '<p style="padding-top: 30px; font-size: 16px; color: #c54107; text-align:center;padding-left:14px;margin-top:15px;">'+msg+'</p>';
								html += '<p style="padding-top: 10px;text-align:center;padding-left:14px;margin-top:15px;">';
								html += '<a href="javascript:void(0)" onclick="sAlertClose('+code+')"  style="text-align:center;padding: 0;display: inline-block;margin-right: 20px;width: 203px;height: 48px;line-height: 48px;font-size: 20px;color: #fff;-webkit-border-radius: 3px;-ms-border-radius: 3px;-o-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;background: #ea5413;">确定</a></p></div>';
								$("body").append(html);
								offsetDiv("#sAlert");
								$('#sAlert').show();
							}

							function sAlertClose(code) {
								
								$('#sShadow').remove();
								$('#sAlert').remove();
								if(code!=200){
									return;
								}
								window.location.reload();
							}
							
							function getImgCode(){
								
							}
						</script>

					</div>

				</div>

			</div>
		</div>
	</div>
	
	<div class="rz-box-con" id="changeTelDiv2" style="display:none;">
		<div class="rz-title">
			<h2>输入新绑定手机号</h2>
			<a href="javascript:void(0)" id="closeDialog" class="rz-close"
				style="padding-top: 15px;"><img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<div style="margin:0 auto;width:600px;">
			<p style="padding-top:27px;padding-bottom:10px;margin-bottom:15px;border-bottom:1px solid #e5e5e5;color:#999;font-size:14px;text-align:left"><img src="/resource/images/shield.png" style="vertical-align:middle"/>为了帮助您的账户安全,请先进行安全验证</p>
			<form action="" style="padding-top:0;width:100%" id="modifyMobileForm">
				<table style="padding:0;margin:0;width:100%">
					<tr>
						<td style="width:150px;" align="right">您的原手机号码是:</td>
						<td>
						   <input name="oldTelephone" id="orgiTelephone" type="text"  style="float:left;margin-left:10px;padding-left:10px;" />
						   <span id="orgiTelephoneTip" class="color tip_span" style="display:none;"></span>
						 </td>
					</tr>
					<tr id="orgiImgCode" class="img_code">
					    <td style="width:150px;" align="right">请输入图片验证码:</td>
						<td>
						   <input id="orgiKaptcha" type="text" value="" style="float:left;width:150px;margin-left:10px;padding-left:10px;"/>
						   <img id="orgiKaptchaImage" alt="" src="/commonset/pictureCode" style="float:left;margin-left:10px;vertical-align:middle;width:90px;height:40px" key=""/>
						   <span id="orgiSpanimgCode" class="color tip_span" style="display:none;">图形验证码有误</span>
						</td>
					</tr>
					<tr id="orgiPhoneCode" class="phone_code" style="display:none;">
						<td style="width:150px;" align="right">请输入原手机验证码:</td>
						<td>
						   <input name="oldVerificationCode" id="orgiTelCode" type="text"  style="float:left;margin-left:10px;padding-left:10px;width:150px;"/>
						   <button type="button" id="orgiSendSMSID" onclick="sendSMSOld(this);" class="button_yzm" style="float: left" >获取验证码</button>
						   <span id="spanOrgiTelCode" class="color tip_span" style="display:none;"></span>
						</td>						
					</tr>
					<tr>
						<td style="width:150px;" align="right">您的密码是:</td>
						<td>
						   <input name="password" id="password" type="password"   style="float:left;margin-left:10px;padding-left:10px;" />
						   <span id="passwordTip" class="color tip_span" style="display:none;"></span>
						 </td>
					</tr>
				
					<tr>
						<td style="width:150px;" align="right">您的新手机号码是:</td>
						<td>
						   <input name="telephone" id="telephone" type="text"  style="float:left;margin-left:10px;padding-left:10px;" />
						   <span id="telephoneTip" class="color tip_span" style="display:none;"></span>
						 </td>
					</tr>
					<tr id="newImgCode" class="img_code">
					    <td style="width:150px;" align="right">请输入验证码:</td>
						<td>
						   <input id="newKaptcha" type="text" value=""  style="float:left;width:150px;margin-left:10px;padding-left:10px;"/>
						   <img id="newKaptchaImage" alt="" src="/commonset/pictureCode" style="float:left;margin-left:10px;vertical-align:middle;width:90px;height:40px" key=""/>
						   <span id="newSpanimgCode" class="color tip_span" style="display:none;">图形验证码有误</span>
						</td>
					</tr>
					<tr id="newPhoneCode" class="phone_code" style="display:none;">
						<td style="width:150px;" align="right">请输入新手机验证码:</td>
						<td>
						   <input name="verificationCode" id="newTelCode" type="text"  style="float:left;margin-left:10px;padding-left:10px;width:150px;"/>
						   <button type="button" id="newSendSMSID" onclick="sendSMS(this);" class="button_yzm" style="float: left" >获取验证码</button>
						   <span id="spanNewTelCode" class="color tip_span" style="display:none;"></span>
						</td>						
					</tr>
				</table>
			</form>
			<p style="width:100%;height:1px;background-color:#e5e5e5;"></p>
			<div style="padding:-top:10px;">
			   <a id="a_submit" class="rz-button" onclick="formSubmit()" style="margin-left: 100px; cursor: pointer">提交</a> 
			   <a class="rz-button reset" onclick="" style="margin-left: 10px; cursor: pointer">取消</a>
			</div>
		</div>
	</div>
	
	<!-- 更换邮箱弹出窗口 -->
	<div class="rz-box-con updateEmail" id="changeTelDiv3" style="display:none; margin: -151px -350px -151px -350px;">
		<div class="rz-title">
			<h2>输入新邮箱</h2>
			<a href="javascript:void(0)" id="closeDialog" class="rz-close"
				style="padding-top: 15px;"><img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<div style="margin:0 auto;width:600px;">
			<form>
				<table>
					<tr>
						<td>新电子邮箱：</td>
						<td><input id="emailInput" type="text" onblur="emails()">
							<span id="spanSendEmail" class="color tip_span" style=" width:120px; text-align:left; float: right;"></span>
						</td>
					</tr>
					<tr>
						<td>验证码：</td>
						<td>
							<input id="emailVailCode" style="width: 57px;" type="text" onblur="vailCode()">
							<button id="vailCodeBtn" class="getcode" type="button" onclick="sendVerifyEmail(this)">获取验证码</button>
							<span id="spanEmailCode" class="color tip_span" style="width:128px; text-align:left; float: right;margin-left:6px"></span>
							
						</td>
					</tr>
				</table>
			</form>
			<!-- <div style="padding:-top:10px;">
					<button class="sbmt" type="button" onclick="saveEmail()">提交</button>
					<button class="ct" type="button" onclick="cencleEmail()">取消</button>
			</div> -->
			<div style="padding:-top:10px;">
			   <a id="a_submit" class="rz-button" onclick="saveEmail()" style="margin-left: 100px; cursor: pointer">提交</a> 
			   <a class="rz-button reset" onclick="cencleEmail()" style="margin-left: 10px; cursor: pointer">取消</a>
			</div>
		</div>
	</div>

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
<script type="text/javascript" src="/resource/js/users/formCheck.js"></script>
<script type="text/javascript" src="/resource/js/users/modifyTelephone.js"></script>
<script type="text/javascript" src="/resource/js/users/modifyEmail.js"></script>
<style>
	.updateEmail h3{
		border-left: 2px solid #000000;
		padding-left: 10px;
		color: #000000;
		margin-bottom: 20px;
	}
	.updateEmail table{
		width: 100%;
		margin-bottom: 20px;
	}
	.updateEmail table tr{
		height: 60px;
		font-size: 16px;
	}
	.updateEmail table tr input{
		width: 177px;
		height: 40px;
		padding:0 10px;
		border: 1px solid #acaeb3;
		font-size: 16px;
	}
	.updateEmail table tr button.sbmt,.updateEmail table tr button.ct{
		width: 140px;
		height: 48px;
		font-size: 14px;
		margin: 20px 17px;
	}
	.updateEmail table tr button.sbmt{
		background: #ff6600;
		color: #ffffff;
	}
	.updateEmail table tr button.ct{
		background: #d5d5d5;
		color: #323232;
	}
	.updateEmail button.getcode{
		width:114px;
		height: 42px;
		font-size: 14px;
		background: #ff6600;
		color: #ffffff;
	}
</style>
</html>