<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>我推荐的人</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1" />
<link rel="stylesheet" href="/resource/css/recommendation.css">
<script src="/resource/js/jquery-1.8.3.min.js"></script>
<script src="/resource/js/recommendation.js"></script>
</head>
<body style="background:#f9f9f9">
<div class="main">
<input id="token" value="${token}" type="hidden">
   <div class="Invitation-record">
		<p class=""><span>好友</span><span>是否绑卡</span><span>注册时间</span></p>
		<ul class="Invitation-box">
			<!-- <li><span>好友</span><span>首投奖励(元)</span><span>是否绑卡</span><span>注册时间</span></li> -->
			
		</ul>
   </div>
 <!--   <div class="recommendation-button">邀请好友</div> -->
</div>
<div class="error_tip"></div>
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