<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/top.jsp"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "No-cache");
	response.setDateHeader("Expires", -1);
	response.setHeader("Cache-Control", "No-store");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>我的领地</title>
<link rel="stylesheet" href="/resource/css/index.css">
<link rel="stylesheet" href="/resource/css/lingbaoMarketCss/market.css">
<link rel="stylesheet" href="/resource/css/lingbaoMarketCss/styleCJ.css">
<link rel="stylesheet" type="text/css" href="/resource/css/style.css" media="screen" />
<link rel="icon" type="image/x-icon" href="/resource/ico.ico">
<link rel="shortcut icon" type="image/x-icon" href="/resource/ico.ico">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/jquery.SuperSlide.2.1.2.js"></script>
<script type="text/javascript" src="/resource/js/lingbaoMarketJs/index.js"></script>
<script type="text/javascript" src="/resource/js/lingbaoMarketJs/public.js"></script>
<script type="text/javascript" src="/resource/js/lingbaoMarketJs/choujiang.js"></script>
</head>
<body>
	<jsp:include page="/common/kefu.jsp"></jsp:include>
	<!-- 头部开始 -->
	<jsp:include page="/common/welcome.jsp"></jsp:include>
	<!-- 头部结束 -->
	<input id="typeID" type="hidden" value="${typeID}">
	<div class="ld_box">
		<div class="shanDeng" id="deng">
			<div id="luck"><!-- luck -->
				<table>
					<tr>
						<td class="luck-unit luck-unit-0"></td>
						<td class="luck-unit luck-unit-1"></td>
						<td class="luck-unit luck-unit-2"></td>
						<td class="luck-unit luck-unit-3"></td>
					</tr>
					<tr>
						<td class="luck-unit luck-unit-9"></td>
						<td colspan="2" class="cjBtn" id="btn" style="height:74px;">注：<span id="btnRatio">${ratio }</span>%的中奖率<br/>抽奖所需领宝：<span id="cost">100</span>个/次</td>
						<td class="luck-unit luck-unit-4"></td>
					</tr>
					<tr>
						<td class="luck-unit luck-unit-8"></td>
						<td class="luck-unit luck-unit-7"></td>
						<td class="luck-unit luck-unit-6"></td>
						<td class="luck-unit luck-unit-5"></td>
					</tr>
				</table>
			</div><!-- luckEnd -->
		</div>
		<div class="mingdanbox clearfix">
			<div class="md_left">
				<div class="mdl_title">抽奖规则</div>
			</div>
			<div class="md_right">
				<div class="mingdan">
					<div class="bd">
							<ul class="infoList">
								<c:if test="${lotteryList!=null }">
									<c:forEach items="${lotteryList }" var="list">
										<li class="clearfix">
											<span class="uname">${list.account }</span>
											<span class="giftname">${list.giftname }</span>
											<span class="giftdate">${list.dates }</span>
										</li>
									</c:forEach>
								</c:if>
							</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 抽奖按钮提示框 S -->
	<div id="rz-box-bg"></div>
	<div class="rz-box-con " id="zhc">
		<div class="rz-title">
			<h2>登录</h2>
		</div>
		<p class="color"
			style="padding-top: 30px; font-size: 16px; color: #c54107">您还未登录，请先登录。</p>
		<p style="padding-top: 10px;">
			<a href="/login" class="rz-button">去登录</a> <a
				href="javascript:void(0)" class="rz-button reset">取消</a>
		</p>
	</div>
	<!-- 抽奖按钮提示框 E -->
	
	<!-- 中奖提示框 S -->
	<div class="rz-box-con successQian" id="successQian">
		<div class="rz-title">
			<h2></h2>
			<a href="javascript:void(0)" class="rz-close"> <img
				src="/resource/images/img_rz-close.png" />
			</a>
		</div>
		<div class="successQian_con">
			<div></div>
			<p id="successContent" style="margin-bottom: 0; line-height: 50px; height: 50px; font-size: 24px;"></p>
			<p id="successQianP" style="font-size: 18px; margin-bottom: 20px;">+5领宝</p>
			<a href="javascript:void(0)" class="rz-button">确定</a>
		</div>
	</div>
	<!-- 中奖提示框 E -->
	<!-- 底部开始 -->
	<jsp:include page="/common/bottom.jsp"></jsp:include>
	<!-- 底部结束 -->
</body>
</html>