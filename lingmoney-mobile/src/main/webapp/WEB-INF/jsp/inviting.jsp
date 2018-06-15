<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>邀请好友</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<link rel="icon" type="image/x-icon" href="/resource/images/ico.ico">
<link rel="stylesheet" href="/resource/css/Invitation.css">
<script src="/resource/js/jquery-1.8.3.min.js"></script>
<script src="/resource/js/Invitation.js"></script>
</head>
<body style="background: #f9f9f9;">
	<input id="token" value="${token}" type="hidden">
<div class="screen">
	<div class="main" style="height:100%;">
		<p class="Invitation-tip">人脉变现</p>
		<p class="Invitation-tipsimg">
			<img src="/resource/images/Invitation1.png" />
		</p>
		<p class="Invitation-actime">活动时间：2018年4月16日-2018年6月30日</p>
		<div class="Invitation-bigimg">
			<img src="/resource/images/Invitation2.png" />
		</div>
		<p class="Invitation-btn">
			<span class="Invitation-btn-left">活动规则</span>
			<a href="/invitingFriends/myInviting?token=${token}">
			<span class="Invitation-btn-right">我的邀请</span></a>
		</p>
		<div class="Invitation-class">
			<ul>
				<li>好友绑卡<br>得好礼
				</li>
				<li>好友首投<br>得返现
				</li>
				<li>榜上有名<br>得现金
				</li>
			</ul>
		</div>

		<div class="Invitation-haoli">
			<p class="Invitation-haoli-tip">好友绑卡 得好礼</p>
			<p class="Invitation-haoli-tips">邀请好友注册并绑卡，您可获得以下奖励</p>
			<div class="Invitation-haoli-list">
				<ul>
					<li>
						<div class="jiaxi05">
							<p>
								30<b>元</b>
							</p>
							<span>投资红包</span>
						</div>
					<p class="status-P1">邀请3位(已完成)</p> 
					</li>
					<li>
						<div class="jiaxi1">
							<p>
								10<b>元</b>
							</p>
							<span>现金红包</span>
						</div>
					<p class="status-P2">再邀请2位</p> 
					</li>
					<li>
						<div class="jiaxi2">
							<p>
								50<b>元</b>
							</p>
							<span>现金红包</span>
						</div>
				<p class="status-P3">再邀请8位</p> 
					</li>
				</ul>
			</div>
		</div>

		<div class="Invitation-demoney">
			<p class="Invitation-haoli-tip">好友首投 得现金</p>
			<p class="Invitation-haoli-tips">
				好友首次投资1个月及以上产品<br>
金额满足以下条件您可获得对应现金奖励
			</p>
			<div class="Invitation-demoney-list">
				<ul>
					<li>
						<div>
							2<b>元</b>
						</div>
						<p>满100元及以上</p>
					</li>
					<li>
						<div>
							6<b>元</b>
						</div>
						<p>满1000元及以上</p>
					</li>
					 <li>
						<div>
							12<b>元</b>
						</div>
						<p>满2000元及以上</p>
					</li>
					<!--<li>
						<div>
							76<b>元</b>
						</div>
						<p>满10000元</p>
					</li> -->
				</ul>
			</div>
		</div>

		<div class="Invitation-uptop">
			<p class="Invitation-haoli-tip">人脉风云排行榜</p>
			<p class="Invitation-haoli-tips" style="padding:0.24rem">活动期间，人脉风云排行榜上榜条件，邀请人需邀请首投人数≥10人，排名实时更新，最终以每月最后一天上午12:00为准！次月初三个工作日内发放用户账户中。</p>
			<!-- <div class="Invitation-tabs">
			奖励
				 <ul>
					<li class="Invitation-active"></li>
					 <li>排行榜</li> 
				</ul> 
			</div> -->

			<div class="Invitation-uptop-box">
				<div class="Invitation-huizhang "
					style="display: block">
					<ul>
						<li>
							<div>
								<p>现金红包</p>
								<b>1666</b>元
							</div>
							<p class="Invitation-mc">第一名</p>
						</li>
						<li>
							<div>
								<p>现金红包</p>
								<b>888</b>元
							</div>
							<p class="Invitation-mc">第二名</p>
						</li>
						<li>
							<div>
								<p>现金红包</p>
								<b>666</b>元
							</div>
							<p class="Invitation-mc">第三名</p>
						</li>
						<li style="margin-left: 1.2rem;">
							<div>
								<p>爱奇艺年卡</p>
								<b>会员</b>
							</div>
							<p class="Invitation-mc">第四-六名</p>
						</li>
						<li>
							<div>
								<p>星巴克电子券</p>
								<b>30</b>元
							</div>
							<p class="Invitation-mc">第七-十名</p>
						</li>

					</ul>
				</div>

				<div class="Invitation-topph" style="margin-top:0.24rem;">
					<div class="Invitation-topph-head">
						<ul>
							<li style="width: 20%;">排名</li>
							<li>用户</li>
							<li>累计邀请好友数</li>
						</ul>
					</div>
					<div class="Invitation-topph-list maquee">
						<ul class="Invitationul">
						</ul>
						<ul class="Invitationulno">
						</ul>
					</div>
				</div>


			</div>
		</div>
		<p class="wx-tip">如有恶意注册，将取消奖励资格。</p>
	</div>
	<div class="Invitation-btnt"><a href="/recNow" style="color:#fff;" id="ios-Android">邀请好友</a></div> 
	<div class="index-Popup-bg"></div>
	<!-- 活动说明  -->
	<div class="Invitation-tc">
		<i><img src="/resource/images/Invitation6.png" /></i>
		<p class="Invitation-tc-tip">活动规则</p>
		<p class="Invitation-tc-bt">

<b>1、好友绑卡 得好礼</b><br>
1）30元投资红包（有效期7天，限三月期以上产品满5000元使用，产品成立后，返现至账户余额，不与其他活动、加息券同时享受，不支持债权转让）。<br>
2）现金红包：需要邀请人绑卡后1-3个工作日内发放可用余额中。<br>
 
<b>2、好友首投 得现金</b><br>
1）首投只能获得1档奖励，以最高奖励为准<br>
2）奖励于好友投资标的成立后，已现金红包方式在1-3个工作日内发放可用余额中。<br>
 
<b>3、人脉风云排行榜</b><br>
1）人脉风云排行榜上榜条件，邀请人需邀请首投人数≥10人<br>
2）排名实时更新，最终以每月最后一天上午12:00为准！次月初三个工作日内发放用户账户中。<br>
 
<b>4、如有恶意注册，将取消用户奖励资格。</b><br>
<b>5、如有疑问请联系在线客服或拨打客服电话400-0051-655。</b>
		</p>
	</div>
	
<!-- ios推荐码 -->	
<div class="Invitation-ios">
 <i><img src="/resource/images/Invitation6.png" /></i>
 	<div class="Invitation-code">
 		<img src="">
 		
 	</div>
 	 <p class="Invitation-code-tip">邀请好友，扫描二维码</p>
</div>	
</div>	
	<input type="hidden" value="我正在领钱儿领现金红包，你也来看看吧！" id="shareTitle">
	<input type="hidden" value="赶快与好友一起瓜分福利，最高可获得1666元现金红包！" id="shareDescr">
	<input type="hidden" value="http://static.lingmoney.cn/lingqian/2018/9a38e139-5d7d-4c8b-862b-f7a96fb21e4d.png" id="shareImgUrl">
	<input type="hidden" value="" id="shareUrl">
	<script type="text/javascript">
$(document).ready(function () { 
	$.ajax({
		url:'/invitingFriends/invitingFriendsLevel',
	    type:'post',
	    data:{
	    	/* "token":'ae7a19b30aa24f0dabe6985f815a7e3fc311c074c4734bc5b8c345aee65274fd' */
	    },
	    success: function(data) {
	       var html="",nohtml=""
	    	if(data.listMap!='') {
	    		  $.each(data.listMap, function(i, item){ 
		    		  if(item.telephone != null){
		    			    	    			html+='<li><span  style="width:20%;">'+item.level+'</span>  <span>'+item.telephone+'</span>  <span>共邀请'+item.count+'位</span></li>'
		    		  }
	    		 }) 
                 $('.Invitationul').html(html)
                 $('.Invitationulno').hide()
	    		
	    	} else {
	    		 nohtml+=' <div class="no-data"><p>暂无数据哦~</p><img src="/resource/images/no-data.png" style="width:2.3rem;"/></div>'
	    		$('.Invitationulno').html(nohtml) 
	    		$('.Invitationul').hide();
	    	}
	    }
	})
	
	$.ajax({
		url:'/invitingFriends/invitingRewards?token='+$('#token').val(),
	    type:'get',
	    success: function(data) {
		    console.log(data);
	    	if(data.code == 200){
	    		$.each(data.listMap, function(i, item){ 
		    		console.log(item);
                    if(item.status1 == 0){
                        $('.jiaxi05').addClass('Invitation-haoli-listbjl0');
                        $('.status-P1').html('邀请3位')
                    }else if(item.status1 == 2){
                        $('.jiaxi05').addClass('Invitation-haoli-listbjl').css('color','#FFFC03');
                        $('.status-P1').html('邀请3位(已完成)')
                    }
                    
                    if(item.status2 == 0){
                        $('.jiaxi1').addClass('Invitation-haoli-listbjl0');
                    }else if(item.status2 == 2){
                        $('.jiaxi1').addClass('Invitation-haoli-listbjl').css('color','#FFFC03');
                        $('.status-P1').html('再邀请2位(已完成)')
                    } 
                    
                    if(item.status3 == 0){
                        $('.jiaxi2').addClass('Invitation-haoli-listbjl0');
                    }else if(item.status3 == 2){
                        $('.jiaxi2').addClass('Invitation-haoli-listbjl').css('color','#FFFC03');
                        $('.status-P1').html('再邀请8位(已完成)')
                    }
                });
            }else{
            	$('.jiaxi05').addClass('Invitation-haoli-listbjl0');
                $('.jiaxi1').addClass('Invitation-haoli-listbjl0');
                $('.jiaxi2').addClass('Invitation-haoli-listbjl0');
                $('.status-P1').html('邀请3位')
            }
	    }
	});
	
	//获取推荐码
	   $.ajax({
			url:'/users/getMyRefferCode?token='+$('#token').val(),
		    type:'get',
		    data:{
		    	/*token:token*/
		    },
		    success: function(data) {
		    	if(data.code == 200) {
	              
	               $('#shareUrl').val('http://app2.lingmoney.cn/infoInterface/recommend?referralTel='+ data.obj.referralCode) 
	             
		    	} else {
		    		
		    	}
		    }
		})	   
	
	
	
	
	
	
	
	
	
}); 	

</script>






</body>
</html>
<script>
(function (doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            if(clientWidth>=750){
                docEl.style.fontSize = '100px';
            }else{
                docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
            }
        };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);	
</script>