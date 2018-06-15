<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "No-cache");
	response.setDateHeader("Expires", -1);
	response.setHeader("Cache-Control", "No-store");
%>
<!DOCTYPE html>
<!-- saved from url=(0045)http://www.uwin.cc/taobao/20150408/index.html -->
<html lang="zh-CN"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>领钱儿抽奖</title>
<link rel="stylesheet" type="text/css" href="/resource/css/styleCJ.css"/>
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/resource/js/jquery.SuperSlide.js"></script>
<script type="text/javascript" src="/resource/js/choujiang.js"></script>

<!--移动端版本兼容 -->
<script type="text/javascript">
	var phoneWidth =  parseInt(window.screen.width);
	var phoneScale = phoneWidth/640;
	var ua = navigator.userAgent;
	if (/Android (\d+\.\d+)/.test(ua)){
		var version = parseFloat(RegExp.$1);
		if(version>2.3){
			document.write('<meta name="viewport" content="width=640, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
		}else{
			document.write('<meta name="viewport" content="width=640, target-densitydpi=device-dpi">');
		}
	} else {
		document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
	};
</script>
<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<!--移动端版本兼容 end-->
</head>
<body>
	<input id="typeID" type="hidden" value="${typeID}">
	<input id="uid" type="hidden" value="${uid}">
	<div class="shanDeng" id="deng">
		<div id="luck"><!-- luck -->
			<table>
				<tr>
					<td class="luck-unit luck-unit-0">
						
					</td>
					<td class="luck-unit luck-unit-1">
					
					</td>
					<td class="luck-unit luck-unit-2">
					
					</td>
					<td class="luck-unit luck-unit-3">
						
					</td>
				</tr>
				<tr>
					<td class="luck-unit luck-unit-9">
						
					</td>
					<td rowspan="0" colspan="2" class="cjBtn" id="btn"><div style="line-height:30px; background: url('/resource/images/choujiangImg/moile_btn.jpg') no-repeat;"><p style="margin:0; padding:0;  margin-top: 38px;">抽奖</p>
					<p id="btnPtext" style="font-size:20px; background:none; color:#5a1515; margin-top:20px;">注：<abbr id="btnRatio">${ratio }</abbr >%的中奖率<br/><span id="cost" style="width:252px; background: none;">100LB/次</span></p></div></td>
					<td class="luck-unit luck-unit-4">
						
					</td>
				</tr>
				<tr>
					<td class="luck-unit luck-unit-8">
						
					</td>
					<td class="luck-unit luck-unit-7">
						
					</td>
					<td class="luck-unit luck-unit-6">
						
					</td>
					<td class="luck-unit luck-unit-5">
						
					</td>
				</tr>
			</table>
		</div><!-- luckEnd -->
	</div>
	<div class="hdgz">
		<!-- <p><span>活动规则</span><br>(1)每参与一次抽奖，扣除领宝积分20分。<br>(2)中奖与否，领宝积分一律不予退还。<br>(3)手机话费充值为后台直接充值到账,其他礼品均为邮寄。<br>(4)本活动礼品不支持兑换现金。<br>(5)本活动最终解释权归“领钱儿”网所有,如有疑问请致电客服人员。
		</p> -->
	</div>
	<div class="zhongjiangmd">
		<h3>中奖名单</h3>
		<div class="mingdan">
			<div class="bd">
				<ul class="infoList">
					<c:if test="${lotteryList!=null }">
						<c:forEach items="${lotteryList }" var="list">
							<li class="clearfix">
								恭喜-${list.account }--获得${list.giftname }
							</li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	<!-- 提示S -->
	<div id="shadowDiv" class="mask"></div>
	<div id="luckyDiv" class="alertShow" style="display:none;">
		<div class="showTitle">恭喜您（Rp）爆发中奖啦！</div>
		<div class="giftName">&nbsp;</div>
		<div class="price">&nbsp;</div>
		<div class="showBtnBox clearfix">
			<a class="goCar" id="goCar" href="gotoGiftCar"><img src="/resource/images/choujiangImg/btn_car.png"></a>
			<a class="goCar" id="goMyLingbao" href="gotoMyLingbao"><img src="/resource/images/choujiangImg/btn_lingbao.png"></a>
			<a class="close" href="javascript:void(0);"><img src="/resource/images/choujiangImg/btn_close.png"></a>
		</div>
		<p class="tip">*奖品已加入购物车，点击查看购物车领取中奖物品。</p>
	</div>
	
	<div id="sorryDiv" class="alertShow" style="display:none;">
		<div class="showTitle">很遗憾！您没有中奖~</div>
		<div class="giftName cor">—加油！再接再厉—</div>
		<div class="lian">~~o(&gt_&lt)o~~</div>
		<div class="showBtnBox clearfix">
			<a class="close only" href="javascript:void(0);"><img src="/resource/images/choujiangImg/btn_close.png"></a>
		</div>
	</div>
	
	<div id="apologyDiv" class="jifenAlert" style="display:none;">
		<div class="jifenTop clearfix">
			<span>温馨提示</span>
			<a href="javascript:void(0);">×</a>
		</div>
		<div class="jifenBox">您的积分不足！</div>
	</div>
	<!-- 提示E -->
	
	<script>
		jQuery(".mingdan").slide({
			mainCell : ".bd ul",
			autoPlay : true,
			effect : "topMarquee",
			vis : 8,
			interTime : 50,
			trigger : "click"
		});
	</script>
</body>
</html>