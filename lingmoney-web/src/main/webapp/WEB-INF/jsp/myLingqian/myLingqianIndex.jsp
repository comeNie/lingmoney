<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/common/top.jsp"%>
<%@ include file="/common/huaxingDialog.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=2, user-scalable=yes" />
<jsp:include page="/common/head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="/resource/css/style.css"
	media="screen" />
<link href="/resource/css/jquery-ui-1.8.16.custom.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="/resource/css/tip-twitter.css"
	type="text/css" />
<script type="text/javascript" src="/resource/js/jquery.poshytip.js"></script>
<script type="text/javascript" src="/resource/js/calculator.js"></script>

<script type="text/javascript">
 $(document).ready(function(){
	 $(".right02 li").hover(function(){
	   	 $(this).css("background-image","url('/resource/images/bg_mul_hover.png')");
	   	 $(this).find("strong").css("color","#ea5513");
	   },function(){
	     $(this).css("background-image","url('/resource/images/bg_mul.jpg')");
	   	 $(this).find("strong").css("color","#717171");
	   });
	 
	 $(".rz-close, .reset, .hx_close").click(function(){
			$("#rz-box-bg").hide();
			$("#div_zidong").hide();
			$("#div_kaitong").hide();
			$("#deep").hide();
			$("#zhc").hide();
			$("#hxAccountDialog").hide();
	 });	
	  
	var autoPay = $("#autoPay").val();
	var wallet = $("#wallet").val();
	if(autoPay==1&&wallet==1){//已开通
		$(".top_wallet").find(".top_wallet_img").hide();
	}else{
		$(".top_wallet").find("div").hide();
		$(".top_wallet").hover(function(){
			 $(this).find(".top_wallet_img").hide();
			 $(this).find("div").show();
		},function(){
			 $(this).find(".top_wallet_img").show();
			 $(this).find("div").hide();
		});
	}
	 
	 $("#isNotDing").click(function(){
		 if(autoPay==1){//只需要你修改wallet=1
			var conn = sendReq("/users/updateWalletStatus.html");
			window.location.reload();
		 }else{
			var conn = sendReq("/users/userAutopayModel.html");
			if(conn==0){//需要开通，跳转到开通页面
				$("#div_zidong").hide();
				$("#div_kaitong").show();
				offsetDiv("#div_kaitong");
				var url = '<%=request.getContextPath()%>/users/walletautopayTransfer.html';
				window.open(url);
				return false;
			}
		 }
	  });

		//是否开通自动支付模式
		$("#open").click(function() {
			//验证用户是是否登录和是否绑定信息
			var autoPay = $("#autoPay").val();
			var wallet = $("#wallet").val();
			var con = sendReq("/product/validateUsers.html");
			if (con == '0001') {//未登录 
				$("#zhc").show();
				$("#rz-box-bg").show();
				offsetDiv("#zhc");
				return false;
			}

			if (con == '0002') {//未认证 
				$("#deep").show();
				$("#rz-box-bg").show();
				offsetDiv("#deep");
				return false;
			}

			if (con == '0000') {
				//已经登录且实名认证,判断是否需要开通自动定投功能
				$("#div_zidong").show();
				offsetDiv("#div_zidong");
				$("#rz-box-bg").show();
			}
		});

		theRestOfTime = $("#theRestOfTime").val();
		if (undefined != theRestOfTime && theRestOfTime > 0) {
			paymentCountDown()
			paymentCountDownInterval = setInterval(
					"paymentCountDown()", 1000);
		}

		var firstTimeIn = $("#firstTimeIn").val();
		if (firstTimeIn == "true") {
			var accountOpen = $("#accountOpen").val();
			var bindBank = $("#bindBank").val();

			if (accountOpen == 0) {
				showHXDialog(0);
			} else if (bindBank == 0) {
				showHXDialog(1);
			}
		}
	});

 
	//账户总额提示
	function showTip(types) {
		if (types == 1)
			$("#blbtip_1,#blbtip_2").show();
		else if (types == 0)
			$("#blbtip_1,#blbtip_2").hide();
	}
 
	//剩余时间
	var theRestOfTime = -1;
	// 倒计时interval
	var paymentCountDownInterval;
	function paymentCountDown() {
		theRestOfTime = theRestOfTime - 1000;
		var m = 0;
		var s = 0;
		if (theRestOfTime >= 0) {
			m = Math.floor(theRestOfTime / 1000 / 60 % 60);
			s = Math.floor(theRestOfTime / 1000 % 60);
			if (m < 10) {
				m = "0" + m;
			}
			if (s < 10) {
				s = "0" + s;
			}
			$("#theRestOfTimeSpan").text("00:" + m + ":" + s);
		} else {
			window.clearInterval("paymentCountDownInterval");
			theRestOfTime = -1;
		}
	}

	
</script>


</head>
<body>
	<input type="hidden" value="${firstTimeIn }" id="firstTimeIn">

	<!-- 冻结金额详情弹窗 -->
	<div class="rz-box-con" id="dongjieForm" style="margin: -200px -350px -200px -350px;">
		<div class="rz-title">
			<h2>冻结金额详情</h2>
			<a href="javascript:void(0)" class="rz-close" onclick="showDj(1);"> <img
				src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<table>
			<thead>
				<tr>
					<td>待支付</td>
					<td>支付中</td>
					<td>待成立</td>
					<td>提现中</td>
				<tr>
			</thead>
			<tbody>
				<tr>
					<td id="waitBuyAmount"></td>
					<td id="buyingAmount"></td>
					<td id="waitCompleteAmount"></td>
					<td id="withdrawalsAmount"></td>
				</tr>
			</tbody>
		</table>
		<p style="padding-top: 10px;">
			<a href="javascript:void(0);" class="rz-button" onclick="showDj(1);">我知道了</a> 
		</p>
	</div>

	<div class="rz-box-con" id="deep">
		<div class="rz-title">
			<h2>深度注册</h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<p class="color"
			style="padding-top: 30px; font-size: 16px; color: #c54107">您只有在进行了身份认证及银行卡绑定后，才能购买本产品</p>
		<p style="padding-top: 10px;">
			<a href="/myAccount/bindBankCard" class="rz-button">去认证</a> <a
				href="javascript:void(0)" class="rz-button reset">取消</a>
		</p>
	</div>

	<!--确认开通弹框 -->
	<div class="rz-box-con" id="div_kaitong">
		<div class="rz-title">
			<h2>请在新页面完成开通</h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<p style="padding-top: 30px; font-size: 16px; text-align: center">
			完成开通前请<span style="margin: 0 5px; color: #ea5513">不要关闭此窗口</span>，完成开通后根据您的情况点击下面按钮
		</p>
		<p style="padding-top: 20px;">
			<a href="/users/showWalletResult.html" class="rz-button ">已开通</a> <a
				href="/users/showWalletResult.html" class="rz-button reset">开通遇到问题</a>
		</p>
	</div>

	<div class="rz-box-con " id="zhc">
		<div class="rz-title">
			<h2>登录</h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<p class="color"
			style="padding-top: 30px; font-size: 16px; color: #c54107">您还未登录，请先登录。</p>
		<p style="padding-top: 10px;">
			<a href="/login.html" class="rz-button">确认</a> <a
				href="javascript:void(0)" class="rz-button reset">取消</a>
		</p>
	</div>
	
	<!--弹框  -->
	<div id="rz-box-bg"></div>
	<!-- 开通自动支付弹框 -->
	<div class="rz-box-con" id="div_zidong">
		<div class="rz-title">
			<h2>开通功能</h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<p style="padding-top: 30px; font-size: 16px; text-align: center">
			是否开通<span style="margin: 0 5px; color: #ea5513">代扣代缴功能</span>
		</p>
		<p style="padding-top: 20px;">
			<a href="javascript:void(0)" id="isNotDing"
				class="rz-button button_Jump">确定</a> <a href="javascript:void(0)"
				class="rz-button reset">取消</a>
		</p>
	</div>
	<div class="dong">
		<h2>
			尊敬的<span class="color">${CURRENT_USER.nickName==null?CURRENT_USER.loginAccount:CURRENT_USER.nickName }</span>会员</br>
			您在领钱儿的总收益为:
		</h2>
		<strong> <fmt:formatNumber type="currency"
				value="${UserAccount.income}" />
		</strong>
	</div>

	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->

	<!-- 用户导航开始 -->
	<jsp:include page="/common/navmember.jsp"></jsp:include>
	<!-- 用户导航结束 -->

	<div class="mainbody_member" style="padding-top: 0px;">
		<div class="box clearfix">
			<div class="mem_confor" style="margin-top: 0px;">
				<h3 class="clearfix" style="position:relative;">
					<input type="hidden" id="nickNameX"
						value="${CURRENT_USER.nickName == null ? CURRENT_USER.loginAccount : CURRENT_USER.nickName }" />
					<span id="sayHello"></span>
					<a href="/myFinancial/redPacket?type=0&hrpType=1"  onclick="clickReddot()">
					   <img src="/resource/images/coupons.png" >
					   <i class="redDot" style="position:absolute; display:none; width: 8px;height: 8px; background: #f00;border-radius: 50%;top:20px;right:5px;"></i>	
					</a>
				</h3>

				<ul class="clearfix">
					<li class="li_left">
						<div>
							<p class="head">
								<c:choose>
									<c:when
										test="${CURRENT_USER.picture == null || CURRENT_USER.picture == '' }">
										<img src="/resource/images/img_memberTouxiang.gif" width="105"
											height="105" />
									</c:when>
									<c:otherwise>
										<img src="${CURRENT_USER.picture }" width="105" height="105" />
									</c:otherwise>
								</c:choose>
							</p>
							<p>
								<a href="/myAccount/myInfo" class="color">编辑资料</a>
							</p>
						</div>

					</li>
					<li class="li_center">
						<p style="font-size: 16px; padding-bottom: 15px;">
							欢迎您，<span class="color">${CURRENT_USER.nickName==null?CURRENT_USER.loginAccount:CURRENT_USER.nickName }</span>
						</p>
						<div class="div_level clearfix">
							<p style="width: 120px;">
								会员身份：${CURRENT_USER.degreeName} <img
									src="${CURRENT_USER.degreePicture} " class="level" />
							</p>

							<p>
								推荐码：<span class="color">${CURRENT_USER.referralCode }</span>
							</p>
							<p>
								<a href="/usersMessage/list.html">站内信：<span class="color">(${unRead })</span></a>
							</p>
						</div>
						<div style="margin-bottom: 10px;">
							我的领宝：<span class="color" style="font-size: 22px;">${UserAccount.countLingbao}</span>个&nbsp;&nbsp;查看如何
							<a href="/helpCenterLingbao.html" class="color" style="margin: 0">获取领宝</a>
						</div>
						<div class="div-progress" style="padding-left: 0">
							<span class="name-pro">安全级别：</span>
							<dl class="barbox barbox2">
								<dd class="barline barline2">
									<c:choose>
										<c:when
											test="${CURRENT_USER.certification == 0 && CURRENT_USER.bank == 0 }">
											<div w="33.33" style="width: 0px;" class="charts"></div>
										</c:when>
										<c:when
											test="${(CURRENT_USER.certification == 1 || CURRENT_USER.certification == 2 || CURRENT_USER.certification == 3) && (CURRENT_USER.bank == 1 || CURRENT_USER.bank == 2 || CURRENT_USER.bank == 3)}">
											<div w="100" style="width: 0px;" class="charts"></div>
										</c:when>
										<c:when
											test="${CURRENT_USER.certification == 0 && CURRENT_USER.bank == 1 }">
											<div w="66.66" style="width: 0px;" class="charts"></div>
										</c:when>
										<c:when
											test="${CURRENT_USER.certification == 1 && CURRENT_USER.bank == 0 }">
											<div w="66.66" style="width: 0px;" class="charts"></div>
										</c:when>
									</c:choose>
									<div w="50" style="width: 0px;" class="charts"></div>
								</dd>
							</dl>
							<span class="percent" style="color: #717171"> <c:choose>
									<c:when
										test="${CURRENT_USER.certification == 0 && CURRENT_USER.bank == 0 }">低</c:when>
									<c:when
										test="${(CURRENT_USER.certification == 1 || CURRENT_USER.certification == 2 || CURRENT_USER.certification == 3) && (CURRENT_USER.bank == 1 || CURRENT_USER.bank == 2 || CURRENT_USER.bank == 3)}">高</c:when>
									<c:when
										test="${CURRENT_USER.certification == 0 && CURRENT_USER.bank == 1 }">中</c:when>
									<c:when
										test="${CURRENT_USER.certification == 1 && CURRENT_USER.bank == 0 }">中</c:when>
								</c:choose>
							</span>
						</div> <!--进度条--> <script language="javascript">
							function animate() {
								$(".charts").each(function(i, item) {
									var a = parseInt($(item).attr("w"));
									$(item).animate({
										width : a + "%"
									}, 500);
								});
							}
							animate();
						</script>
						<div class="mem_rz">
							<a title="已通过手机验证"><img src="/resource/images/rz_phone.gif" /></a>
							<c:choose>
								<c:when test="${CURRENT_USER.certification == 0 }">
									<a href="/myAccount/bindBankCard" title="未通过身份验证"> <img
										src="/resource/images/rz_realname.gif" />
									</a>
								</c:when>
								<c:when test="${CURRENT_USER.certification == 1 || CURRENT_USER.certification == 2 || CURRENT_USER.certification == 3}">
									<a href="javascript:void(0)" title="已通过身份验证"
										style="cursor: default;"> <img
										src="/resource/images/rz_realname_ok.gif" />
									</a>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${CURRENT_USER.bank == 0 }">
									<a href="/myAccount/bindBankCard" title="未绑定银行卡"> <img
										src="/resource/images/rz_card.gif" />
									</a>
								</c:when>
								<c:when test="${CURRENT_USER.bank == 1 || CURRENT_USER.bank == 2 || CURRENT_USER.bank == 3 }">
									<a href="javascript:void(0)" title="已绑定银行卡"
										style="cursor: default;"> <img
										src="/resource/images/rz_card_ok.gif" />
									</a>
								</c:when>
							</c:choose>
						</div>
						<p style="margin-bottom: 5px;">
							最后登录时间：
							<fmt:formatDate value="${CURRENT_USER.lastLogin}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</p>
					</li>

					<li style="width: 426px; padding-top: 0px;">
						<div class="my_money">
							<!-- 理财资金 -->
							<div class="accountBalance yhcg">
								<div class="accountBalance_title">
									<span>理财资金</span>
								</div>
					             <div class="balanceBox clearfix">
	                                   <div class="b_left">
	                                       <div class="bl_1">
	                                           <div id="blbtip_1" class="tip">账户总额=可用余额+冻结金额</div>
	                                           <p>当前理财金额（元）
	                                           </p>
	                                           <div class="bl_t"><c:if test="${financialInfo==null}">0.00</c:if>
	                                         <c:if test="${financialInfo!=null}">
	                                             <fmt:formatNumber pattern="#,##0.00#"
	                                                 value="${financialInfo.financialMoney }" />
	                                         </c:if></div>
	                                       </div>
	                                   </div>
	                                </div>
	                                <div class="financial_earnings clearfix">
	                                   <div class="fe_financial">
	                                   <p>预期收益（元）</p>
	                                       <p>
	                                           <span> 
	                                              <c:choose>
	                                                   <c:when test="${financialInfo == null }">0.00</c:when>
	                                                   <c:otherwise>
	                                                       <fmt:formatNumber pattern="#,##0.00#" value="${financialInfo.interest }" />
	                                                   </c:otherwise>
	                                               </c:choose>
	                                           </span>
	                                       </p>
	                                   </div>
	                                   <div class="fe_earnings">
	                                   <p>已赚收益（元）</p>
	                                       <p><span> ${income }</span></p>
	                                   </div>
	                                </div>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div class="clearfix" style="padding-top: 25px;">
				<div class="leftMember">
					<div class="calendar-members">
						<form name="CLD" class="content">
							<div class="rili-thead clearfix">
								<p class="p-currenttime">
									北京时间&nbsp;&nbsp;<font size="4"><span id="h_m"> </span></font>
								</p>
								<p>
									<span>公历</span> <select name="SY" id="SY"
										onchange="submit1('second');" style="font-SIZE: 9pt">
										<script>
											for (i = 1900; i < 2050; i++)
												document.write('<option>' + i);
										</script>
									</select><span>年</span> <select name="SM" id="SM"
										onchange="submit1('second');" style="font-SIZE: 9pt">
										<script>
											for (i = 1; i < 13; i++)
												document.write('<option>' + i);
										</script>
									</select><span>月</span>
								</p>
								<p class="p-more">
									<a href="/myFinancial/accountFlow"
										style="color: #fff; font-size: 13px">更多操作记录</a>
								</p>
							</div>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="datetable">

								<tbody>
									<tr style="background: #eee;">
										<td width="54" height="30">星期日</td>
										<td width="54" height="30">星期一</td>
										<td width="54" height="30">星期二</td>
										<td width="54" height="30">星期三</td>
										<td width="54" height="30">星期四</td>
										<td width="54" height="30">星期五</td>
										<td width="54" height="30">星期六</td>
									</tr>
									<script>
										var gNum;
										for (i = 0; i < 6; i++) {
											document
													.write('<tr align="center">');
											for (j = 0; j < 7; j++) {
												gNum = i * 7 + j;
												document
														.write('<td id="GD' + gNum +'" class="riqi"><font id="SD'
																+ gNum
																+ '" size=3 face="Arial Black"');
												if (j == 0)
													document
															.write('color="#f4a27d"'); /*原颜色为red*/
												if (j == 6)
													document
															.write('color="#f4a27d"'); /*原颜色为000080*/
												document
														.write('></font><br/><font id="LD' + gNum + '" size=3 style="font-size:9pt"></font></td>');
											}
											document.write('</tr>');
										}
									</script>
									<tr>
									</tr>
								</tbody>
							</table>

							<div class="tuli">
								<span style="background: none">图例：</span><span
									style="background-position: left top; color: #22ac38">充值</span><span
									style="background-position: left -65px; color: #e23a39">理财</span><span
									style="width: 75px; background-position: left -130px; color: #6a005f">到期/赎回</span><span
									style="background-position: left -195px; color: #00b7ee">提现</span>
								<span style="background-position: left -326px; color: #fcb700">补费</span>
								<span
									style="width: 85px; background-position: left -260px; color: #6a3906">当天多项操作</span>
							</div>
						</form>

						<!--  日历改背景的方法-->
						<script type="text/javascript">
							function cleartip() {
								for (ii = 0; ii < 42; ii++) {
									$("#GD" + ii).removeAttr("style");
									$("#GD" + ii).removeAttr("title");
								}
							}
						</script>
						<script type="text/javascript" src="/resource/js/zzsc.js"></script>
					</div>
				</div>

				<div class="RightMember">
					<div class="right01">
						<div class="right01-title">
							<h2>理财排名</h2>
							<strong>${ranking}</strong>
						</div>

						<ul class="clearfix">
							<c:forEach items="${listUsersAccount }" var="item"
								varStatus="status">
								<c:if test="${ranking<=3 }">
									<c:if test="${status.index<=2}">
										<li
											<c:if test="${status.index==ranking-1}">class="mysort"</c:if>>
											<div class="clearfix">
												<p class="right01_li01">
													<c:choose>
														<c:when test="${item.pic==null||item.pic=='' }">
															<img src="/resource/images/bg_membersTouxiang.gif"
																width="28" height="28" />
														</c:when>
														<c:otherwise>
															<img src="${item.pic }" width="28" height="28" />
														</c:otherwise>
													</c:choose>
												</p>
												<p class="right01_li02">
													<span title="${item.account}">${item.account}</span>
												</p>
												<p class="right01_li03">
													${item.dName} <img src="${item.pic}" width="28" height="28" />
												</p>
												<p class="right01_li04">
													<img src="/resource/images/sort0${item.ranking}.jpg" />
												</p>
											</div>
										</li>
									</c:if>
								</c:if>
								<c:if test="${ranking>=4 }">
									<li>
										<div class="clearfix">
											<p class="right01_li01">
												<c:choose>
													<c:when test="${item.pic==null||item.pic=='' }">
														<img src="/resource/images/bg_membersTouxiang.gif"
															width="28" height="28" />
													</c:when>
													<c:otherwise>
														<img src="${item.pic }" width="28" height="28" />
													</c:otherwise>
												</c:choose>
											</p>
											<p class="right01_li02">
												<span title="${item.account}">${item.account}</span>
											</p>
											<p class="right01_li03">
												${item.dName} <img src="${item.dPic}" width="28" height="28" />
											</p>
											<p class="right01_li04">
												<strong>${item.ranking}</strong>
											</p>
										</div>
									</li>
								</c:if>
							</c:forEach>
						</ul>
						<a href="/product/list/0_0_0_0_0_1.html">我要去理财</a>
					</div>
					<jsp:include page="/common/calculator.jsp"></jsp:include>
					<div class="right02">
						<ul class="clearfix">
							<li style="background-position: 5px -10px;">
								<p>
									<strong>理财计算器</strong>
								</p>
								<p style="color: #717171">为理财提供精确化的计算参考</p>
							</li>
						</ul>
						<ul class="clearfix">
							<li onclick="location.href='/usersAsk/list.html'"
								style="background-position: 5px -102px;">
								<p>
									<strong>您问我答</strong>
								</p>
								<p style="color: #717171">理财疑问，专业解答</p>
							</li>
						</ul>
						<ul class="clearfix">
							<li onclick="location.href='/infoNotice/list.html'"
								style="background-position: 5px -196px; border: none">
								<p>
									<strong>站内公告</strong>
								</p>
								<p style="color: #717171">平台信息、银行维护早知道</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="qiandai"></div>
	</div>

	<!-- 提示开通华兴E账户弹框 -->
	<input type="hidden" id="accountOpen"
		value="${CURRENT_USER.certification }">
	<input type="hidden" id="bindBank" value="${CURRENT_USER.bank }">

	<script>
		$(document).ready(function() {
			xianshi();
			$("#nav_member01").addClass("nav-hover");
			$(".main-head li:last").css("border", "none");
			$(".main3 li:last").css("margin-right", "0");
			$(".licai li:last").css("margin-right", "0");
			$(function() {
				var mydate = new Date();
				var mytime = mydate.toLocaleTimeString();
				$("#time").text(mytime);
			});

			/*收益状况*/
			$(".qiandai").click(function() {
				$(".dong").show();
			})
			$(".dong").click(function() {
				$(this).hide();
			})
			/*收益状况*/
			submit1("frist");
			/*计算器*/
			$("a.close").click(function() {
				$(".calculator").slideUp(500);
				$("#rz-box-bg").hide();
				resets();
			});
			$(".right02 ul:eq(0)").click(function() {
				offsetDivC(".calculator");
				$(".calculator").slideDown(1000);
				$("#rz-box-bg").show();
				resets();
			});
			/*计算器*/
			
			/*卡劵红点是否显示*/
				  
				  $.ajax({
					url:  '/activityReward/notCheckedRedPacket',
					type: 'post',
					data:{		
					},
					dataType: 'json',
					success: function(d) {	
							if(d.count>0){
						$(".redDot").show()
						}
					},
					error: function(d) {}
				});	 
				  /*卡劵红点是否显示*/
		});
		document.onkeyup = function(e) {
			e = e || window.event;
			var code = e.which || e.keyCode;
			if (code == 27) {
				$(".dong").hide();
			}
		}

		// 1充值 2提现 
		function widthDrawOrRecharge(type) {
			var accountOpen = $("#accountOpen").val();
			var bindBank = $("#bindBank").val();

			if (accountOpen == 0) {
				showHXDialog(0);
			} else if (bindBank == 0) {
				showHXDialog(1);
			} else {
				if (type == 1) {
					window.location.href = "/myFinancial/accountRecharge";
				} else {
					window.location.href = "/myFinancial/accountWithdraw";
				}
			}
		}
		
		function clickReddot() {
			$.ajax({
				url:'/activityReward/checkedRedPacket',
				type: 'post',					
				data:{		
				},
				dataType: 'json',
				success: function(d) {
					$(".redDot").hide()
				},
				error: function(d) {}
			});	
		}

		function xg() {
			$(".riqi").css("border", "2px solid #fff");
			$(".riqi").hover(function() {
				$(this).css("border", "2px solid #d5db34");
			}, function() {
				$(this).css("border", "2px solid #fff");
			});

			$('.demo-tip-twitter').poshytip({
				content : '[title]',
				className : 'tip-twitter',
				showTimeout : 1,
				alignTo : 'target',
				alignX : 'left',
				alignY : 'center',
				offsetX : 0,
				allowTipHover : true,
				fade : true,
				slide : true
			});
		}

		function submit1(falg) {
			var sy = $("#SY").val();
			var sm = $("#SM").val();
			var date_time = "";
			var y, m;
			if (falg == "second") {
				date_time = $("#SY").val() + "-" + $("#SM").val();
				y = CLD.SY.selectedIndex + 1900;
				m = CLD.SM.selectedIndex;
				cleartip();
				drawCld(y, m);
			} else {
				var Today = new Date();
				var tY = Today.getFullYear();
				var tM = Today.getMonth();
				CLD.SY.selectedIndex = tY - 1900;
				CLD.SM.selectedIndex = tM;
				drawCld(tY, tM);
			}

			var day_TheArray = new Array(); //先声明一维
			var str_TheArray = new Array(); //先声明一维
			var tdsStyleBackground_TheArray = new Array(); //先声明一维
			var index = 0;
			$.ajaxSettings.async = false;
			var noCache = Date();
			$
					.getJSON(
							"/myAccount/statisticsMonth?date_time=" + date_time,
							{
								"noCache" : noCache
							},
							function(data) {
								if (data != undefined && data != null) {
									$
											.each(
													data.Data,
													function(idx, item) {
														var ins = 0;
														var ts = 0;
														var tdsStyleBackground = "";
														var str = "";
														day_TheArray[index] = idx;
														$
																.each(
																		item,
																		function(
																				next,
																				item1) {
																			//0：充值，1：提现，2：理财，3：到期/赎回，4：补费
																			if (item1.type == 0) {
																				ins = 1;
																				ts += 1;
																				str += "<p class='tip-p'><span class='tip-span1'>"
																						+ item1.operateTimeStr
																						+ "</span> <span class='tip-span2-chong' style='color:#fff'>充值</span> <span class='tip-span3-chong'>"
																						+ item1.moneyFormat
																						+ "</span></p>";
																			} else if (item1.type == 1) {
																				ins = 2;
																				ts += 1;
																				str += "<p class='tip-p'><span class='tip-span1'>"
																						+ item1.operateTimeStr
																						+ "</span> <span class='tip-span2-ti' style='color:#fff'>提现</span> <span class='tip-span3-ti'>"
																						+ item1.moneyFormat
																						+ "</span></p>";
																			} else if (item1.type == 2) {
																				ins = 3;
																				ts += 1;
																				str += "<p class='tip-p'><span class='tip-span1'>"
																						+ item1.operateTimeStr
																						+ "</span> <span class='tip-span2-tou' style='color:#fff'>理财</span> <span class='tip-span3-tou'>"
																						+ item1.moneyFormat
																						+ "</span></p>";
																			} else if (item1.type == 3) {
																				ins = 4;
																				ts += 1;
																				str += "<p class='tip-p'><span class='tip-span1'>"
																						+ item1.operateTimeStr
																						+ "</span> <span class='tip-span2-huan' style='color:#fff'>到/赎</span> <span class='tip-span3-huan'>"
																						+ item1.moneyFormat
																						+ "</span></p>";
																			} else if (item1.type == 4) {
																				ins = 5;
																				ts += 1;
																				str += "<p class='tip-p'><span class='tip-span1'>"
																						+ item1.operateTimeStr
																						+ "</span> <span class='tip-span2-bu' style='color:#fff'>补费</span> <span class='tip-span3-bu'>"
																						+ item1.moneyFormat
																						+ "</span></p>";
																			}
																		});
														if (ts == 1) {
															if (ins == 1) {
																tdsStyleBackground = "url('/resource/images/bg_chong.png') no-repeat left top"; //充值的样式
															} else if (ins == 2) {
																tdsStyleBackground = "url('/resource/images/bg_ti.png') no-repeat left top"; //提现的样式
															} else if (ins == 3) {
																tdsStyleBackground = "url('/resource/images/bg_tou.png') no-repeat left top"; //理财的样式
															} else if (ins == 4) {
																tdsStyleBackground = "url('/resource/images/bg_huan.png') no-repeat left top"; //赎回的样式
															} else if (ins == 5) {
																tdsStyleBackground = "url('/resource/images/bg_bu.png') no-repeat left top"; //补费的样式
															}
														} else {
															tdsStyleBackground = "url('/resource/images/bg_duo.png') no-repeat left top"; //多项操作的样式
														}
														tdsStyleBackground_TheArray[index] = tdsStyleBackground
														str_TheArray[index] = str;
														index += 1;
													});
								}

							});

			for (ii = 0; ii < 42; ii++) {
				var today = $("#SD" + ii).text();
				var ins = 0;
				var ts = 0;
				var str = "";
				var tds = document.getElementById("GD" + ii);
				for (var j = 0; j < day_TheArray.length; j++) {
					var days = day_TheArray[j];
					if (today == days) {
						tds.style.background = tdsStyleBackground_TheArray[j];
						tds.style.backgroundSize = "cover";
						tds.className = "riqi demo-tip-twitter";
						tds.title = str_TheArray[j];
					}
				}
			}

			xg();// title样式
		}

		function changeTime() {
			var myDate = new Date();
			var hours = myDate.getHours(); //获取当前小时数(0-23)
			var minute = myDate.getMinutes(); //获取当前分钟数(0-59)
			var seconds = myDate.getSeconds();
			function p(s) {
				return s < 10 ? '0' + s : s;
			}
			var h_m = hours + ":" + p(minute) + ":" + p(seconds);
			document.getElementById("h_m").innerHTML = h_m;
		}
		changeTime();
		setInterval("changeTime()", 1000);

		function xianshi() {
			var d = new Date()
			var h = d.getHours();
			var m = d.getMinutes();
			var name = $("#nickNameX").val();
			var text = "";
			if (h >= 3 && h < 6) {
				text += "清晨了，";
				text += "<span class='color'>" + name + "</span>";
				text += "！难得的宁静时光，感受最真实的自己！";
			} else if ((h >= 6 && h < 11) || (h == 11 && m >= 0 && m < 30)) {
				text += "上午好，";
				text += "<span class='color'>" + name + "</span>";
				text += "！从微笑开始，迎接新一天的挑战！";
			} else if ((h == 11 && m >= 30 && m < 60) || (h >= 12 && h < 13)) {
				text += "中午好，";
				text += "<span class='color'>" + name + "</span>";
				text += "！ 吃顿丰盛的午餐，犒劳一下忙碌了一个上午的自己！";
			} else if (h >= 13 && h < 18) {
				text += "下午好，";
				text += "<span class='color'>" + name + "</span>";
				text += "！忙里偷点闲，为自己充充电！";
			} else if (h >= 18 && h <= 23) {
				text += "晚上好，";
				text += "<span class='color'>" + name + "</span>";
				text += "！尽情享受与家人相处的时光吧！";
			} else if ((h > 23 && h <= 24) || (h >= 0 && h < 3)) {
				text += "夜深了，";
				text += "<span class='color'>" + name + "</span>";
				text += "！不要太拼了！要注意身体呦！";
			}
			$("#sayHello").html(text);
		}
		
		
		
		//冻结明细
		function showDj(object){
			if(object==0){
				dongjiemx();
				$('#dongjieForm').show();
				$('#rz-box-bg').show();
				
			}else if(object==1){
				$('#dongjieForm').hide();
				$('#rz-box-bg').hide();
			}
		}
		
		//冻结金额明细
		function dongjiemx(){
			$.ajax({
				url: '/myFinancial/getUserFreeingAmount',
				type: 'post',
				dataType: 'json',
				success: function(d) {
					var _html="";
					_html='<tr>'+
						'<td>'+d.obj.waitBuyAmount+'元<a href="/myFinancial/finanCialManage">详情</a></td>'+
						'<td>'+d.obj.buyingAmount+'元<a href="/myFinancial/finanCialManage">详情</a></td>'+
						'<td>'+d.obj.waitCompleteAmount+'元<a href="/myFinancial/finanCialManage">详情</a></td>'+
						'<td>'+d.obj.withdrawalsAmount+'元<a href="/myFinancial/accountFlow">详情</a></td>'+
						'</tr>';
					
				$('#dongjieForm').find('tbody').html(_html);
					
				},
				error: function(d) {}
			});
		}
		/*
			银行存管与京东支付切换
		*/
		function licaitab(par){
			var tabboxname=$(par).attr('id');
			if(tabboxname=='yhcg'){
				$('.yhcg').hide();
				$('.lczj').show();
			}else if(tabboxname=='lczj'){
				$('.lczj').hide();
				$('.yhcg').show();
			}
		}
		$(function(){
			
		});
		
		
	</script>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>