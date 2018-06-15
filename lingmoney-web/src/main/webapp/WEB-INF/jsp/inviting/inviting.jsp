<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>邀好友赚奖励</title>
	<link rel="stylesheet" href="/resource/css/Invitation.css">
    <script src="/resource/js/jquery-1.8.3.min.js"></script>
	<script src="/resource/js/Invitation.js"></script>

</head>
<body>
<div class="Invitation-banner">
    <div style="width:960px;margin:0 auto;position: relative;height:461px;">
        <div class="Invitation-bannetbs" style="left:0px;"><a href="/usersRecommend/show" style="color:#efa48d"><span style=" background: #efa48d;
    border-radius: 100px;
    color: #fff;">邀请好友</span></a><span class="my-yaoqing">我的邀请</span></div>
    </div>
	
</div>
<div class="Invitation-warp">
	<div class="Invitation-box1 Invitation-box">
		<p class="Invitation-box-tip">好友绑卡 得好礼</p>
		<p class="Invitation-box-tips">邀请好友注册并绑卡，您可获得以下奖励</p>
		<ul>
			<li class="jiaxi05"><div class="Invitation-cssbj"><span class=""><b>30</b>元<br>投资红包</span></div><p class="status-P1">邀请3位</p></li>
			<li class="jiaxi1"><div class="Invitation-cssbj"><span class=""><b>10</b>元<br>现金红包</span></div><p class="status-P2">再邀请2位</p></li>
			<li class="jiaxi2"><div class="Invitation-cssbj"><span class=""><b>50</b>元<br>现金红包</span></div><p class="status-P3">再邀请8位</p></li>
		</ul>
	</div>
	
	
	<div class="Invitation-box2 Invitation-box">
		<p class="Invitation-box-tip">好友首投 得现金</p>
		<p class="Invitation-box-tips">好友首次投资1个月及以上产品<br>
金额满足以下条件您可获得对应现金奖励</p>
		<ul>
			<li><div>2<span>元</span></div><p>满100元及以上</p></li>
			<li><div>6<span>元</span></div><p>满1000元及以上</p></li>
			<li><div>12<span>元</span></div><p>满2000元及以上</p></li>
		</ul>
	</div>
	
	
	<div class="Invitation-box3 Invitation-box">
		<p class="Invitation-box-tip">人脉风云排行榜</p>
		<p class="Invitation-box-tips">排名最终以每月最后一天上午12：00为准，次月初三个工作日内发放用户账户中</p>
		<div class="Invitation-top">
			<ul>
				<li><div>现金红包<br><span>1666</span>元</div><p>第一名</p></li>
				<li><div>现金红包<br><span>888</span>元</div><p>第二名</p></li>
				<li><div>现金红包<br><span>666</span>元</div><p>第三名</p></li>
				<li style="margin-left:260px;"><div>爱奇艺会员<br><span>年卡</span></div><p>第四-六名</p></li>
				<li><div>星巴克电子券<br><span>30</span>元</div><p>第七-十名</p></li>
			</ul>
		</div>
		
		<div class="Invitation-toplist">
			<p class="Invitation-toplist-nav"><span>排名</span> <span>用户</span> <span>累计邀请好友数</span></p>
			<div class="Invitation-topph-list maquee">
					<ul class="Invitationul">
						<li><span>1</span>  <span>用户1</span>  <span>累计邀请好友数1</span></li>

					</ul>
					<ul class="Invitationulno"></ul>
				</div>
		</div>
		
	</div>
	
	
	
	
	<div class="Invitation-box4 Invitation-box">
		<p class="Invitation-box-tip">活动规则</p>
		<p class="Invitation-guize" style="font-size: 18px;">
<b>1、好友绑卡 得好礼</b><br>
1）30元投资红包（有效期7天，限三月期以上产品满5000元使用，产品成立后，返现至账户余额，不与其他活动、加息券同时享受，不支持债权转让）。<br>
2）现金红包：需要邀请人绑卡后1-3个工作日内发放可用余额中。<br>
<b>2、好友首投 得现金</b><br>
1）首投只能获得1档奖励，以最高奖励为准<br>
2）奖励于好友投资标的成立后，以现金红包方式在1-3个工作日内发放可用余额中。<br>
<b>3、人脉风云排行榜</b><br>
1）人脉风云排行榜上榜条件，邀请人需邀请首投人数≥10人<br>
2）排名实时更新，最终以每月最后一天上午12:00为准！次月初三个工作日内发放用户账户中。<br>
<b>4、如有恶意注册，将取消用户奖励资格。</b><br>
<b>5、如有疑问请联系在线客服或拨打客服电话400-0051-655。</b><br>
		</p>
		
	</div>


<div class="tc-box">
     <i><img src="/resource/images/Invitation201804-3.png" class="closed"/></i>
	<div class="Invitation-hylist">
		<p class="Invitation-hylist-tip">邀请列表</p>
		<div class="Invitation-hylist-yq">
			<p class="Invitation-hylist-yqnav"><span>好友</span> <span>首投奖励（元）</span> <span>是否绑卡</span> <span>注册时间</span></p>
			<ul class="myInvitation-box-list">
				<li><span>好友</span> <span>首投奖励（元）</span> <span>是否绑卡</span> <span>注册时间</span></li>
				<li><span>好友</span> <span>首投奖励（元）</span> <span>是否绑卡</span> <span>注册时间</span></li>
				<li><span>好友</span> <span>首投奖励（元）</span> <span>是否绑卡</span> <span>注册时间</span></li>
			</ul>
		</div>
	</div>
	
	
	<div class="Invitation-hylist">
		<p class="Invitation-hylist-tip">奖励排名</p>
		<div class="Invitation-hylist-yq">
			<p class="Invitation-hylist-yqnav"><span>排名</span> <span>邀请人数</span> <span>奖励</span> <span>状态</span></p>
			<ul class="listMap-list">
				<li><span>好友</span> <span>首投奖励（元）</span> <span>是否绑卡</span> <span>注册时间</span></li>
				<li><span>好友</span> <span>首投奖励（元）</span> <span>是否绑卡</span> <span>注册时间</span></li>
				<li><span>好友</span> <span>首投奖励（元）</span> <span>是否绑卡</span> <span>注册时间</span></li>
			</ul>
		</div>
	</div>
	
	
	
</div>

	
</div>
<div class="index-Popup-bg"></div>
<script>
//滚动
/* function autoScroll(obj){  
	$(obj).find(".Invitationul").animate({  
		marginTop : "-38px"  
	},500,function(){  
		$(this).css({marginTop : "0px"}).find("li:first").appendTo(this);  
	})  
   }  
$(function(){  
	setInterval('autoScroll(".maquee")',3000);
	  
})  */


</script>
</body>
</html>