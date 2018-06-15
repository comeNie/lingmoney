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
<link rel="stylesheet" href="/resource/css/bank/bindBankCard.css" />
<script type="text/javascript" src="/resource/js/utils/checkUtils.js"></script>
<script type="text/javascript" src="/resource/js/certification.js"></script>
<script type="text/javascript" src="/resource/js/bank/tiedCard.js"></script>
<script type="text/javascript"
	src="/resource/js/clipboard/clipboard.min.js"></script>
	<style type="text/css">
		.wsts h3{
			font-style: normal;
	font-weight: normal;
	line-height: 14px;
	font-size: 14px;
	padding-left: 10px;
	border-left: 2px solid #ea5513;
	color: #999999;
	margin-bottom: 20px;
	margin-top: 35px;
			}
		.wsts p{
			font-size: 14px;
			color: #717171;
			background: none;
			padding: 0;
			line-height: 20px;
			margin-bottom: 10px;
		}
		.wsts p span{
			color: red;
		}
		.wsts p a{
			color: blue;
			text-decoration: underline;
		}
	</style>
</head>
<body>
	<input id="jdBindCardAvailable" value="${jdBindCardAvailable }"
		type="hidden">
	<!-- 自定义ALERT弹框 -->
	<div id="rz-box-bg"></div>
	<div class="rz-box-con" id="alert" style="width: 500px; height: 200px">
		<div class="rz-title">
			<h2>提示</h2>
			<a class="rz-close" href="javascript:;"> <img
				src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<div
			style="padding: 20px 0; margin: 0 auto; width: 400px; text-align: center; font-size: 1.5em;"></div>
		<p style="margin: 40px auto; width: 460px;">
			<a href="/myAccount/bindBankCard" class="rz-button ok"
				style="width: 122px; cursor: pointer">确定</a>
		</p>
	</div>

	<!-- 自定义CONFIRM弹框 -->
	<div class="rz-box-con" id="confirmDiv"
		style="width: 500px; height: 200px">
		<div class="rz-title">
			<h2>提示</h2>
			<a class="rz-close" href="javascript:;" onclick="confirmCancel()">
				<img src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<div
			style="padding: 20px 0; margin: 0 auto; width: 400px; text-align: center; font-size: 1.5em;"></div>
		<p style="margin: 40px auto; width: 460px;">
			<button class="rz-button ok" style="width: 122px; cursor: pointer">确定</button>
			<button class="rz-button reset" style="width: 122px; cursor: pointer">取消</button>
		</p>
	</div>

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
					<div class="mtitle">银行卡</div>
					<!--  -->
					<div class="huaxing_jingdong_tab clearfix">
						<c:if test="${list!=null}">
							<a class="btn_hx active" boxindex="1" href="javascript:void(0);">华兴银行</a>
							<a class="btn_jd" boxindex="2" href="javascript:void(0);">京东支付</a>

						</c:if>
					</div>
					<div class="hx_box" id="boxindex_1" >
						<c:choose>
							<c:when
								test="${CURRENT_USER.certification == 2 || CURRENT_USER.certification == 3 }">
								<!-- 已开户 -->
								<c:choose>
									<c:when
										test="${CURRENT_USER.bank == 2 || CURRENT_USER.bank == 3}">
										<!-- 已绑卡 -->
										<div class="hxBnakCard" style="height: 270px;">
											<div class="hb_name clearfix">
												<span>广东华兴银行</span> <a href="javascript:void(0);">已绑定</a>
											</div>
											<div class="cardInfo">
												<p>${CURRENT_USER.acName}</p>
												<p>
													<span id="hxe">${CURRENT_USER.acNo}</span> <a id="copyCard"
														href="javascript:void(0);">复制</a>
												</p>
											</div>
											<div class="link clearfix" style="padding-left: 32px;">
												<a class="a_search" style="float: left; margin-top: 38px;"
													href="/myFinancial/accountRecharge">账户开通完成，去<span>充值</span>，开始领钱儿之旅
												</a>
											</div>
										</div>
										
										<div class="new-bank-tip">已绑定银行卡</div>
										<div class="hxBnakCard new-hxBnakCard" style="height: 116px;position: relative;">
											<div class="clearfix bankname" style="height: 44px;line-height: 44px;background: #fbddd0;padding-right: 18px;">
												<img src="" class="banklogo" style="margin-top:10px;margin-left: 12px;">
											</div>
											<div class="cardInfo" style="border:0px;padding-top:18px;">
												<!-- <p class="acName"></p> -->
												<p class="cardno"></p>
												<span style="position: absolute;right: 24px;top: 76px;">储蓄卡</span>
											</div>
											
										</div>
										
										
										
									</c:when>
									<c:when
										test="${CURRENT_USER.bank == 0 || CURRENT_USER.bank == 1}">
										<div class="hxBnakCard">
											<div class="hb_name bg_gray clearfix">
												<span>广东华兴银行</span>
											</div>
											<div class="cardInfo">
												<p>${CURRENT_USER.acName}</p>
												<p>
													<span id="hxe">${CURRENT_USER.acNo}</span> <a id="copyCard"
														href="javascript:void(0);">复制</a>
												</p>
											</div>
											<div class="link">
												<a class="a_tanhao" href="javascript:hxTiedCard();">请绑卡<span>激活</span>华兴E账户
												</a>
											</div>
										</div>
										<div class="wsts">
											<h3>温馨提示：</h3>
											<p>1、为了您的正常投资理财，请先<span>激活华兴银行存管账户</span> 。<a href="/hxbankOpertion#pc1">图文指引&gt;&gt;</a></p>
											<p>2、如您已绑卡成功，此页面仍提示“请绑卡激活华兴E账户”，请耐心等待1~2分钟并尝试手动刷新此页面。   </p>
											<p>3、遇到任何问题，请及时拨打热线400-0051-655。</p>
										</div>
									</c:when>
								</c:choose>
							</c:when>
							<c:when
								test="${CURRENT_USER.certification == 0 || CURRENT_USER.certification== 1}">
								<div class="kt">
									<p>
										根据国家监管要求，领钱儿已接入华兴银行资金存管系统。用户实名认证、绑卡、投资、充值、提现前需先开通银行存管账户。为了节省您的时间，请先
										<a style="color: blue;"
											href="https://www.ghbibank.com.cn/eAccountF/download/ocx/USBKey_Assistant.exe">安装华兴银行控件</a>，重启浏览器。
									</p>
									<div class="formtable">
										<div class="name">
											<span>姓&nbsp;&nbsp;&nbsp;名：</span> <input id="hxAccountName"
												name="acName" type="text"
												onblur="testName('hxAccountName','hxAccountNameTip','hxAccountNameTipImg')">
											<em id="hxAccountNameTip"></em> <span
												id="hxAccountNameTipImg" style="display: none;"> <img
												src="/resource/images/bg_true.gif" />
											</span>
										</div>
										<div class="idcard">
											<span>身份证：</span> <input id="hxAccountIdCard" name="idNo"
												type="text"
												onblur="testID('hxAccountIdCard','hxAccountIdCardTip','hxAccountTipImg')">
											<em id="hxAccountIdCardTip"></em> <span id="hxAccountTipImg"
												style="display: none;"> <img
												src="/resource/images/bg_true.gif" />
											</span>
										</div>
										<div class="acMobile">
											<span>手机号：</span> <input id="hxAccountMobile" name="mobile"
												type="text"
												onblur="testTel('hxAccountMobile','hxAccountTelTip','hxAccountTelTipImg')">
											<em id="hxAccountTelTip"></em> <span id="hxAccountTelTipImg"
												style="display: none;"> <img
												src="/resource/images/bg_true.gif" />
											</span>
										</div>
										<span style="color: #ea5513;">*
											请确保手机号码与即将绑定的银行卡预留手机号码一致</span> <a href="javascript:void(0);"
											onclick="accountOpen()">立即开通</a>
										<!-- <a href="/bank/queryAccountOpen">查询开通</a> -->
										<form id="accountOpenForm" method="post" action=""
											target="_blank">
											<input type="hidden" id="accountOpenRequestData"
												name="RequestData" value="" /> <input type="hidden"
												id="accountOpenTransCode" name="transCode" value="" />
										</form>
									</div>
								</div>
								<div class="wsts">
                                            <h3>温馨提示：</h3>
                                            <p>1、为了您的正常投资理财，请先<span>开通华兴银行存管账户</span> 。<a href="/hxbankOpertion#pc1">图文指引&gt;&gt;</a></p>
                                            <p>2、如您已跳转至华兴银行页面开户成功，此页面仍提示开通存管账户，请等待1~2分钟并尝试手动刷新此页面。
</p>
                                            <p>3、遇到任何问题，请及时拨打热线400-0051-655。</p>
                                        </div>
							</c:when>
						</c:choose>
						<div class="OpenStatus"
							style="font-size: 14px; color: #ea5513; text-align: center;"></div>
					</div>
					<form style="display: none" id="tiedCardForm" name="tiedCardForm"
						target="_blank" method="post" action="">
						<input id="requestData" name="RequestData" value="" /> <input
							id="transCode" name="transCode" value="" />
					</form>




					<!-- 存放状态，用于清空表单 -->
					<input id="certification" type="hidden"
						value="${CURRENT_USER.certification}"> <input
						id="messageType" type="hidden">
					<div class="jd_box" id="boxindex_2" style="display: none;">
						<c:choose>
							<c:when test="${list != null && list.size() > 0}">
								<!-- 创建弹框START -->
								<div class="rz-box-con " id="newBnak">
									<div class="rz-title">
										<h2>新认证用户</h2>
										<a href="javascript:void(0)" id="closeDialog" class="rz-close"
											style="padding-top: 15px;"><img
											src="/resource/images/img_rz-close.png" /></a>

									</div>
									<div>
										<form id="addNewBankForm">
											<input type="hidden" id="bankShort" name="bankShort" /> <input
												type="hidden" name="UID" id="UID" value="}">
											<table>
												<tr>
													<td width="100">真实姓名：</td>
													<td width="650"><input type="text" name="name"
														id="name" onblur="testName('name','name-tip','span_name')"
														onkeyup="nameKeyUp()" />
														<p class="message-tip" id="name-tip"></p> <span
														style="display: none" id="span_name"> <img
															src="/resource/images/bg_true.gif" />
													</span></td>
												</tr>
												<tr>
													<td width="100">身份证号：</td>
													<td width="650"><input type="text" name="idCard"
														id="idCard"
														onblur="testID('idCard','idCard-tip','span_idCard')"
														onkeyup="idcardKeyUp()" />
														<p class="message-tip" id="idCard-tip"></p> <span
														style="display: none" id="span_idCard"> <img
															src="/resource/images/bg_true.gif" />
													</span></td>
												</tr>
												<tr>
													<td width="100">银行卡号：</td>
													<td width="650">
														<!--img:132*53 -->
														<div
															style="float: left; border: 1px solid #ddd; padding-left: 10px; padding-top: 2px; height: 35px; line-height: 35px; width: 271px;">
															<div id="bankNumberFormat"
																style="display: none; position: absolute; width: 60%; height: 35px; background-color: white; left: 60px; top: 175px; border: 1px solid #ddd; font-size: 1.6em;"></div>
															<input type="text" class="idcard" id="number"
																name="number" onblur="testBankCard(this)"
																onfocus="bankNumberFocus(this)" maxlength="19"
																onkeyup="return bankNumberKeyUp(event)" value="" /> <img
																id="bankImg" src="" width="87" height="35"
																style="display: none; margin-top: -5px; vertical-align: middle" />
														</div> <a href="javascript:;" class="seleBank">选择银行</a>
														<p class="message-tip" id="number-tip"></p> <span
														style="display: none" id="span_number"><img
															src="/resource/images/bg_true.gif" /></span>
														<div id='BankListDiv'></div>
													</td>
												</tr>
												<tr>
													<td width="100">手机号码：</td>
													<td width="650"><input type="text" id="tel" name="tel"
														onblur="testTel2(this)" onkeyup="telKeyUp()"
														placeholder="请填写该卡在银行预留的手机号码 " />
														<p class="message-tip" id="tel-tip"></p> <span
														style="display: none" id="span_tel"><img
															src="/resource/images/bg_true.gif" /></span></td>
												</tr>
												<tr>
													<td width="100">短信验证码：</td>
													<td width="650"><input type="text" id="msgcode"
														name="msgcode" onkeyup="msgcodeKeyUp()"
														style="width: 68px; height: 32px; line-height: 32px;" />
														<a href="javascript:void(0);" id="yzmCode">获取验证码</a> <a
														href="javascript:void(0);" id="yzmCodeShadow"></a> <a
														href="javascript:void(0);" id="nomessage"
														style="padding-left: 10px; color: #5b9fe2">没有收到短信?</a>
														<div class="none-reason" style="display: none;">
															<i class="arrow"></i> <strong class="font-red">手机号码有误？</strong><br>
															<strong>可按以下步骤依次确认解决问题:</strong><br> 1.
															请确认该预留手机号是当前使用手机号<br> 2. 若银行预留手机号已停用，请联系银行修改<br>
															3. 银行预留手机号码修改完毕后请重新绑定<br>
														</div>
														<p class="message-tip" id="msgcode_tip"></p></td>
												</tr>
												<tr>
													<td colspan="2"><a class="rz-button"
														onclick="submitBindBank('addNewBankForm')"
														style="margin-left: 100px; cursor: pointer">提交</a> <a
														class="rz-button reset"
														onclick="document.getElementById('closeDialog').click()"
														style="margin-left: 10px; cursor: pointer">取消</a></td>
												</tr>
											</table>
										</form>

									</div>
								</div>
								<!-- 创建弹框END -->

								<div class="zonglan clearfix">
									<div class="realname_tip">
										<!-- 										<p>目前京东支付已经停用，2017年7月26日起，京东支付只能兑付，不能充值投资，请投资人开通并激活华兴E账户，进行投资。</p> -->
									</div>

									<div class="realname_title">
										<p>银行卡绑定</p>
									</div>
									<!-- 记录银行卡数量,用于判断最后一张银行卡禁止删除 -->
									<input id="bankCardNumber" type="hidden"
										value="${list.size() }">
									<div>
										<c:forEach items="${list }" var="us">
											<!-- 默认银行卡 -->
											<c:choose>
												<c:when test="${us.userBank.isdefault == 1 }">
													<div class="div-bank">
														<dl>
															<dt>
																<img src="${us.supportBank.bankLogo }" /> <span
																	class="type"><em>储蓄卡</em></span> <span class="type"><em>默认</em></span>
															</dt>
														</dl>
														<p class="person">持卡人姓名：${realname}</p>

														<p class="tel">手机号：${us.userBank.tel}</p>
														<p class="tel">银行卡号：**** ****
															****${us.userBank.number}</p>
													</div>
												</c:when>
												<c:otherwise>
													<div class="div-bank">
														<dl>
															<dt>
																<img src="${us.supportBank.bankLogo }" /> <span
																	class="type"><em>储蓄卡</em></span>
															</dt>
														</dl>
														<p class="person">持卡人姓名：*${realname}</p>

														<p class="tel">手机号：${us.userBank.tel}</p>
														<p class="tel">银行卡号：**** **** ****
															${us.userBank.number}</p>
														<a href="javascript:;" class="remove set"
															onclick="setBankCardDefault('${us.userBank.id }')">设置默认</a>
														<a href="javascript:;" class="remove"
															onclick="delBankCard('${us.userBank.id }')">删除</a>
													</div>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<div class="tip-bank">
											<span>+</span>
											<p>添加银行卡</p>
										</div>
									</div>
								</div>
							</c:when>

							<c:otherwise>
								<c:choose>
									<c:when test="${jdBindCardAvailable == 'Y' }">
										<div class="zonglan clearfix">
											<div class="realname_tip">
												<!-- 												<p>目前京东支付已经停用，2017年7月26日起，京东支付只能兑付，不能充值投资，请投资人开通并激活华兴E账户，进行投资。</p> -->
											</div>

											<div class="realname_title">
												<p>银行卡绑定</p>
											</div>
											<form id="bindBankForm" method="post">
												<input type="hidden" id="bankShort" name="bankShort" />
												<table class="table_realname table_bind">
													<tr>
														<td width="100" height="54">真实姓名：</td>
														<td width="650"><input type="text" id="name"
															name="name"
															onblur="testName('name','name-tip','span_name')"
															onkeyup="nameKeyUp()" value="" />
															<p class="message-tip" id="name-tip"></p> <span
															style="display: none" id="span_name"> <img
																src="/resource/images/bg_true.gif" />
														</span></td>
													</tr>
													<tr>
														<td width="100" height="54">身份证号：</td>
														<td width="650"><input type="text" id="idCard"
															name="idCard"
															onblur="testID('idCard','idCard-tip','span_idCard')"
															onkeyup="idcardKeyUp()" value="" />
															<p class="message-tip" id="idCard-tip"></p> <span
															style="display: none;" id="span_idCard"> <img
																src="/resource/images/bg_true.gif" />
														</span></td>
													</tr>
													<tr>
														<td width="100" height="54">银行卡号：</td>
														<td width="650">
															<div
																style="float: left; border: 1px solid #ddd; padding-top: 2px; height: 35px; line-height: 35px; width: 280px;">
																<div id="bankNumberFormat"
																	style="display: none; position: absolute; width: 60%; height: 35px; background-color: white; left: 60px; top: 82px; border: 1px solid #ddd; font-size: 1.6em;"></div>
																<input type="text" class="idcard" id="number"
																	name="number" onblur="testBankCard(this)"
																	onfocus="bankNumberFocus(this)"
																	onkeyup="return bankNumberKeyUp(event)" value="" /> <img
																	id="bankImg" src="" width="87" height="35"
																	style="display: none; margin-top: -5px; vertical-align: middle" />
															</div> <a href="javascript:;" class="seleBank2">选择银行</a>
															<p class="message-tip" id="number-tip"></p> <span
															style="display: none" id="span_number"> <img
																src="/resource/images/bg_true.gif" />
														</span>
															<div id='BankListDiv2'></div>
														</td>
													</tr>
													<tr>
														<td width="100" height="54">手机号码：</td>
														<td width="650"><input type="text" id="tel"
															name="tel" onblur="testTel2(this)" onkeyup="telKeyUp()"
															placeholder="请填写该卡在银行预留的手机号码 " />
															<p class="message-tip" id="tel-tip"></p> <span
															style="display: none" id="span_tel"> <img
																src="/resource/images/bg_true.gif" />
														</span></td>
													</tr>
													<tr>
														<td width="100" height="54">短信验证码：</td>
														<td width="650"><input type="text" id="msgcode"
															name="msgcode" onkeyup="msgcodeKeyUp()"
															style="width: 68px; height: 23px; line-height: 23px;" />
															<a href="javascript:void(0);" id="yzmCode">获取验证码</a> <a
															href="javascript:void(0);" id="yzmCodeShadow"></a> <a
															href="javascript:;" id="nomessage"
															style="padding-left: 10px; color: #5b9fe2">没有收到短信?</a>
															<div class="none-reason" style="display: none;">
																<i class="arrow"></i> <strong class="font-red">手机号码有误？</strong><br>
																<strong>可按以下步骤依次确认解决问题:</strong><br> 1.
																请确认该预留手机号是当前使用手机号<br> 2. 若银行预留手机号已停用，请联系银行修改<br>
																3. 银行预留手机号码修改完毕后请重新绑定<br>
															</div>
															<p class="message-tip" id="msgcode_tip"></p></td>
													</tr>
													<tr>
														<td colspan="2"><a class="button_infor"
															onclick="submitBindBank('bindBankForm')"
															style="margin-top: 20px; margin-left: 100px; cursor: pointer">提交
														</a></td>
													</tr>
												</table>
											</form>
										</div>
									</c:when>

									<c:otherwise>
										<div class="zonglan clearfix">
											<div class="realname_tip">
												<!-- 												<p>目前京东支付已经停用，2017年7月26日起，京东支付只能兑付，不能充值投资，请投资人开通并激活华兴E账户，进行投资。</p> -->
												<!-- 												<p>京东绑卡功能已停用，如有需要请与领钱儿客服联系。</p> -->
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

			</div>
		</div>
	</div>

	<!-- 开通华兴银行账户弹框 START -->
	<div id="accountOpenDialog" class="cancelPaymentAlert"
		style="z-index: 9999; display: none;">
		<div class="contentBox"
			style="padding: 0 48px; line-height: 24px; font-size: 14px; text-align: left;">
			请您在新打开的页面中开通存管账户，开通完成前，请不要关闭此页面！<br>开通完成后，请根据您的情况点击下方按钮。
		</div>
		<div class="buttonBox clearfix">
			<a class="determine" href="/bank/queryAccountOpen">开通成功</a> <a
				class="cancel" href="/helpCenterGuide"
				style="width: 138px; height: 38px; line-height: 38px; border: 1px solid #ea5513; background: none; color: #ea5513;">遇到问题</a>
		</div>
	</div>

	<!-- 验证提示 START -->
	<div class="rz-box-con" id="accountOpenVery"
		style="display: none; width: 400px; height: 190px; padding-bottom: 0; margin: -195px -200px -195px -200px;">
		<div class="rz-title">
			<h2>提示</h2>
			<a href="javascript:void(0)" onclick="closeVery();" id="closeDialog"
				class="rz-close" style="padding-top: 20px;"><img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<div style="margin: 0 auto; width: 400px;">

			<p
				style="width: 100%; height: 50px; line-height: 50px; margin-bottom: 0; padding-left: 0px;">1234</p>
			<div style="">
				<a class="rz-button" onclick="closeVery();"
					style="float: none; display: block; margin: 0px auto; cursor: pointer">确定</a>
			</div>
		</div>
	</div>
	<!-- 开通华兴银行账户弹框 END -->

	<!-- 激活华兴银行账户弹框 START -->
	<div id="bindCardDialog" class="cancelPaymentAlert"
		style="z-index: 9999; display: none;">
		<div class="contentBox"
			style="padding: 0 48px; line-height: 24px; font-size: 14px; text-align: left;">
			请您在新打开的页面中绑卡激活E账户，激活之前请不要关闭此页面！</div>
		<div class="buttonBox clearfix">
			<a id="a_tiedCardRes" class="determine"
				href="/bank/queryTiedCardResult">激活成功</a> <a class="cancel"
				href="/helpCenterGuide"
				style="width: 138px; height: 38px; line-height: 38px; border: 1px solid #ea5513; background: none; color: #ea5513;">遇到问题</a>
		</div>
	</div>
	<!-- 激活华兴银行账户弹框 END -->

	<!-- 自定义弹框 start -->
	<div class="rz-box-con" id="div_customer">
		<div class="rz-title">
			<h2></h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<p style="padding-top: 30px; font-size: 16px; text-align: center">

		</p>
		<p style="padding-top: 20px;">
			<a href="javascript:void(0)" class="rz-button "></a> <a
				href="javascript:void(0)" class="rz-button reset"></a>
		</p>
	</div>
	<!-- 自定义弹框 end -->

	<!-- 自定义弹框提示信息 start -->
	<div class="rz-box-con" id="div_customer_tip" style="width: 400px;">
		<div class="rz-title">
			<h2></h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" /></a>
		</div>
		<p
			style="padding-top: 30px; font-size: 20px; text-align: center; color: #ea5513">
		</p>
		<p style="padding-top: 20px;">
			<a href="javascript:void(0)" class="rz-button">确定</a>
		</p>
	</div>
	<!-- 自定义弹框提示信息 end -->

	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->

	<script>
	$(function(){ 	
		//绑卡信息
		setTimeout(function(){
			$.ajax({
				url :'/bank/queryBindCardInfo',
				type : 'POST',
				async: false,
				data : {
				
				},
				
				dataType : 'json',
				success : function(data) {
					
					if(data.code==200){
						
						if(data.obj.logo==null){
							$('.bankname').html(data.obj.bankname).css({'color':'#333','text-indent':'12px'})
						}else{
							$('.banklogo').attr('src',data.obj.logo)
						}
						
						
						$('.cardno').html(data.obj.cardno)
					}
					
				},
				error : function(d) {
				}
			});
			
		},200)
	
		/* $.ajax({
			url :'/bank/userHxAccout',
			type : 'post',
			data : {
			
			},
			dataType : 'json',
			success : function(data) {
				if(data.code==200){
					$('.acName').html(data.obj.acName);
					
				}
				
			},
			error : function(d) {
			}
		}); */
		})
		var c = document.getElementById("hxe");
		var s;
		if (c != null) {
			s = c.innerHTML;
		} else {
			s = "";
		}
		var clipboard = new Clipboard('#copyCard', {
			text : function() {
				return s;
			}
		});

		clipboard.on('success', function(e) {
			var timeout;
			$('#copyCard').text('复制成功');
			$('#copyCard').css({
				'border-color' : '#63E665',
				'color' : '#63E665'
			});
			timeout = setTimeout(function() {
				$('#copyCard').text('复制');
				$('#copyCard').css({
					'border-color' : '#ff884e',
					'color' : '#ff884e'
				});
				clearTimeout(timeout);
			}, 1000)

		});

		clipboard.on('error', function(e) {
			console.log(e);
		});

		$(function() {
			geturltype();
		});
		function geturltype() {
			var urlstr = GetQueryString("kType");
			if (urlstr == 1) {
				$('.huaxing_jingdong_tab a').each(function() {
					if ($(this).attr('boxindex') == '1') {
						$(this).trigger('click');
					}
				});
			}
		}

		/*获取url参数*/
		function GetQueryString(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		}

	</script>


</body>
</html>