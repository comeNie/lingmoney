<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>我的邀请</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="/resource/css/myInvitation.css">
<script src="/resource/js/jquery-1.8.3.min.js"></script>
<script src="/resource/js/tabbedContent.js"></script>
   <script src="/resource/js/jquery.mobile-1.0a4.1.min.js"></script>
	<!-- <script src="/resource/js/myInvitation.js"></script> -->
</head>
<body style="background:#f9f9f9">
<input id="token" value="${token}" type="hidden">
	<div class="main">
<div class="myInvitation-money">
	<p>奖励总额(元)</p>
	<span>0</span>
</div>

  	<section class="clear">
        		<div class="tabbed_content">
			<div class="tabs">
			<div class="moving_bg">&nbsp;</div>
				<span class="tab_item">邀请列表</span>
				<span class="tab_item">奖励排名</span>
			</div>
			
			<div class="slide_content">						
				<div class="tabslider" >
					<div style="font-size:0rem;line-height:0rem;">&nbsp;</div>
					   <ul>
							<div class="myInvitation-box">
								<p class="myInvitation-box-tabs">
									<span>好友</span>
									<span>首投奖励(元)</span>
									<span>是否绑卡</span>
									<span>注册时间</span>
								</p>
								<div class="myInvitation-box-list">
									<!-- <p>
									<span>好友</span>
									<span>首投奖励(元)</span>
									<span>是否绑卡</span>
									<span>注册时间</span>
									</p> -->
									
								</div>
							</div>
                       </ul>
                       <ul class="listMap-list">
							<!-- <div class="myInvitation-tops">
								<p class="myInvitation-tops1">01</p>
								<p class="myInvitation-tops2"><span>累计邀请<b>10</b>人</span><span style="font-size:0.24rem;color:#868798;padding-top:0.08rem;">2017年4月</span></p>
								<p class="myInvitation-tops3">获得现金红包1666元</p>
								
							</div> -->
							
                       </ul>					   
                     
					
					</div>
				<br style="clear:both" />
			</div>
			
		</div>
		
		
	
	</section>
  
</div>

<script type="text/javascript">
$(document).ready(function () {  
	$.ajax({
		url:'/invitingFriends/invitingFriendsList?token='+ $('#token').val(),
	    type:'get',
	    success: function(data) {
	       var html="",nohtml=""
	    	if(data.listMap!='') {
	    		  $.each(data.listMap, function(i, item){ 
	    			  
	    			  setTimeout(function(){callbank();},100)
	    			html+='<p data-cardStatus="'+item.cardStatus+'" class="cardStatus" data-rewardMoney="'+item.rewardMoney+'"><span>'+item.telephone+'</span><span>'+item.rewardMoney+'</span><span class="cardStatusspan"></span><span>'+item.regDate+'</span></p>'
	    		 }) 
                 $('.myInvitation-box-list').html(html)
          
	    		
	    	} else {
	    		 nohtml+=' <div class="no-data"><p>暂无数据哦~</p><img src="/resource/images/no-data.png" style="width:2.3rem;"/></div>'
	    		$('.myInvitation-box-list').html(nohtml) 
	    		
	    	}
	    }
	})
var sum=0
var callbank=function(){
		$('.cardStatus').each(function(){
			var cardStatus=$(this).attr('data-cardStatus')
			if(cardStatus==0){
				$(this).find('.cardStatusspan').html('否')
			}else if(cardStatus==1){
				$(this).find('.cardStatusspan').html('是')
			}
			
			sum+=Number($(this).attr('data-rewardMoney'))
			$('.myInvitation-money >span').html(sum)
		})
	}	
	
	
}); 	


$.ajax({
	url:'/invitingFriends/invitingFriendsLevel',
    type:'post',
    data:{
    	 /* "token":token */
    },
    success: function(data) {
       var html="",nohtml=""
    	if(data.listMap!='') {
    		  $.each(data.listMap, function(i, item){ 
    			  setTimeout(function(){calbank();},100)
    			html+='<div class="myInvitation-tops" data-status="'+item.status+'">'
    			html+='<p class="myInvitation-tops1">'+item.level+'</p>'
    			html+='<p class="myInvitation-tops2"><span>累计邀请<b>'+item.count+'</b>人</span><span style="font-size:0.24rem;color:#868798;padding-top:0.08rem;" class="nodate"></span></p>'
    			html+='<p class="myInvitation-tops3">'+item.rewardContent+' <span>'+item.rewardDate+'</span></p>'
    			html+='</div>'
    		 }) 
             $('.listMap-list').html(html)
      
    		
    	} else {
    		 nohtml+=' <div class="no-data"><p>暂无数据哦~</p><img src="/resource/images/no-data.png" style="width:2.3rem;"/></div>'
    		$('.listMap-list').html(nohtml) 
    		
    	}
    }
})
var calbank=function(){
	$('.myInvitation-tops').each(function(){
		if($(this).attr('data-status')==2){
			$(this).find('.nodate').html('已发放')
			$(this).find('.myInvitation-tops3').css('line-height','0.5rem')
			$(this).find('.myInvitation-tops3 >span').css('color','#868798')
		}else if($(this).attr('data-status')==0){
			$(this).find('.nodate').html('未发放')
			$(this).find('.myInvitation-tops3 >span').hide()
		}
	})
}

</script>




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
</body>
</html>
