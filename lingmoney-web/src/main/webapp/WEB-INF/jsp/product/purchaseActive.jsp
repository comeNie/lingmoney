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
	<link rel="stylesheet" type="text/css" href="/resource/css/purchaseActive.css"/>
	<script type="text/javascript" src="/resource/js/purchaseActive.js"></script>
</head>
<body class="jiaoyi">
	<!-- 自定义ALERT弹框 -->
	<div id="rz-box-bg"></div>
	<div class="rz-box-con" id="alert" style="width:500px;height:230px">
	    <div class="rz-title">
	       <h2>提示</h2>
	       <a class="rz-close" href="javascript:;"><img src="/resource/images/img_rz-close.png"/></a>
	    </div>
	    <div style="padding:20px 0;margin:0 auto;width:400px;text-align:center;font-size:1.5em;"></div>
	    <p style="margin: 20px auto; width: 460px;">
	       <a href="javascript:void(0);" class="rz-button ok" style="width:122px;cursor:pointer">确定</a>
	    </p>
	</div>
	
	<div>
		<input type="text" id="dizNumber" value="${dizNumber}" style="display: none;">
		<input type="text" id="tId" value="${tId}" style="display: none;">
		<input type="text" id="infoId" value="${infoId}" style="display: none;">
		<input type="text" id="pType" value="${pType}" style="display: none;">
	</div>
		
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	
	<div class="content-jiaoyi">
		<div class="top clearfix">
			<div class="left">
				<p>订单提交成功，请您尽快付款！订单编号：${dizNumber}</p>
				<p >
					请您在提交订单后<span class="color">24小时</span>内完成支付，否则订单会自动取消。
				</p>
			</div>
			<div class="right">
				<p>
					应付金额<b class="color">${financialMoney }</b>元
				</p>
			</div>
		</div>
		<div class="zhifu">
			<i class="icon2 jd"></i>
			<div class="notice">
				<p>以下支付方式由京东支付提供</p>
			</div>

			<div class="main-zhifu">
				<div class="p-amount">
					<em>支付</em> <strong class="color">${financialMoney }</strong> <em>元</em>
				</div>
				<div class="bottom">
					<div class="tab">
						<ul class="clearfix">
							<li><a href="javascript:void(0);" class="active">快捷支付</a></li>
							<li><a href="javascript:void(0);">银行支付</a></li>
						</ul>
						<div class="tabContent">
							<div class="active">
							  	<div class="p-value clearfix">
									<div class="p-value-inner ">
										<dl class="div-input clearfix">
											<c:forEach var="userBank" items="${usersBank}" varStatus="status">
												<c:choose>
											   		<c:when test="${userBank.isdefault == 1}">
														<dt id="selectBank">
								      						<img class="bank_img" src="${userBank.bankLogo}"/> 
								      						<span>储蓄卡(${userBank.number.substring(userBank.number.length() - 4, userBank.number.length())})</span>
								      						<span>每日限额:<em>${userBank.upperLimit.split('/')[0]}</em></span>
								      						<input name="userBankId" type="text" value="${userBank.id}" style="display: none;">
								      						<input name="userBankTel" type="text" value="${userBank.tel}" style="display: none;">
														</dt>
													</c:when>
												</c:choose> 
											</c:forEach>
											<dd class="icon2"></dd>
										</dl>
									</div>
									<a href="javascript:void(0);" id="addCard"><b>+</b>绑定新卡</a>
									<div class="pop">
										<c:forEach var="userBank" items="${usersBank}" varStatus="status">
											<c:choose>
											   <c:when test="${userBank.isdefault == 1}">  
											        <div class="pop-div select"> 
							      						<img  class="bank_img" src="${userBank.bankLogo}"/> 
							      						<span>储蓄卡(${userBank.number.substring(userBank.number.length() - 4, userBank.number.length())})</span>
							      						<input name="userBankId" type="text" value="${userBank.id}" style="display: none;">
							      						<input name="userBankTel" type="text" value="${userBank.tel}" style="display: none;">
							      						<span>每日限额:<em>${userBank.upperLimit.split('/')[0]}</em></span>
							      						<i class="icon2"></i>					
													</div>     
											   </c:when>
											   <c:otherwise> 
											   	 	<div class="pop-div"> 
							      						<img  class="bank_img" src="${userBank.bankLogo}"/> 
							      						<span>储蓄卡(${userBank.number.substring(userBank.number.length() - 4, userBank.number.length())})</span>
							      						<input name="userBankId" type="text" value="${userBank.id}" style="display: none;">
							      						<input name="userBankTel" type="text" value="${userBank.tel}" style="display: none;">
							      						<span>每日限额:<em>${userBank.upperLimit.split('/')[0]}</em></span>
							      						<i class="icon2"></i>					
													</div>
											   </c:otherwise>
											</c:choose>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="onlineBankSel">
								<c:forEach var="onlineBank" items="${onlineBank}">
		      						<a href="javascript:void(0);"><img src="${onlineBank.bankLogo}" />
		      						<input name="onlineBankNo" type="text" value="${onlineBank.bankNo}" style="display: none;">
		      						<span style="display: none;">${onlineBank.ebankUpperlimit}</span>
		      						</a>
								</c:forEach>
								<div class="xianDiv">
								  <p>银行支付限额</p>
								  <div id="onlineBankQuota"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<button type="button" class="btn-zhifu" value='0'>立即支付</button>
		</div>
	</div>
	
	<!-- 输入手机验证码接口 -->
	<div class="rz-box-con box-message">
		<div class="rz-title"> <h2>确认支付</h2>
			<a href="javascript:void(0);" class="rz-close">
			  <img src="/resource/images/img_rz-close.png"/>
			</a>
		</div>
		<div class="middle">
			<p class="p-top">请输入短信验证码</p>	
			<p style="font-size:12px;"><span class="bank_tel"></span>  <a href="#" class="color">手机号码有误？</a></p>	
			<div class="box-input">
			   <input type="text" class="message-code" value="" maxlength="6"/> 
			   <a id="rz-box-ok" href="javascript:void(0);" class="color pay">确认</a>
			   <p>
		   		 <span class="color">短信验证码已发送，请注意查收</span> 
		   		 <a id="rz-box-clear" href="javascript:void(0);" style="color:blue">清空</a>
			  </p>
			</div>
		</div>
		
		<div class='lower clearfix'>
			<div class="left">
			   <a href="javascript:void(0);" id="yzmCode">获取验证码</a>
			   <a href="javascript:void(0);" id="yzmCodeShadow"></a>
			</div>
			<div class="right">
			   <a href="javascript:void(0);" class="box-message-close">关闭</a>
			</div>
		</div>
	</div>

	<div class="rz-box-con box-limit" style="width: 500px; display: none;">
		<div class="rz-title">
			<a href="javascript:void(0);" class="rz-close"><img src="/resource/images/img_rz-close.png"/></a>
		</div>
		<div>
			<p style="height: 70px; line-height: 70px; color: red; text-align: center; font-size: 18px">购买金额大于限额，请选择其它银行卡或重新购买！</p>
		</div>
		<div style="margin: 10px auto; width: 142px;">
			<button type="button" class="rz-button ok"
				style="width: 122px; height: 37px; line-height: 37px; rgb (255, 255, 255); cursor: pointer;">确定</button>
		</div>
	</div>

	<div id="rz-box-bg"></div>
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
</body>
</html>