<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>我的推荐码</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1" />
<link rel="stylesheet" href="/resource/css/recommendation.css">
<script src="/resource/js/jquery-1.8.3.min.js"></script>
<script src="/resource/js/recommendation.js"></script>
</head>
<body style="background:#f9f9f9">
 <input id="token" value="${token }" type="hidden"> 
<div class="main">
   <div class="recommendation-box">
		<p class="recommendation-box-tip">邀请好友，扫描二维码</p>
		<div class="recommendation-box-img"><img src=""/></div>
		<div class="recommendation-box-ma">推荐码：<span  id="copycode" >QWEWQE</span> <a href="/sendReferralCode">复制</a></div>
   </div>
  <p class="recommendation-tip">邀请说明：<br>
1、每位会员拥有专属、唯一的推荐码<br>
2、当你推荐亲朋好友到领钱儿进行理财时，记得让好友在注册领钱儿会员时填写您的推荐码<br>
3、领钱儿将对您的推荐信息进行记录，并不定期退出推荐奖励活动。</p>

<p class="recommendation-list"><span class="recommendation-rightbor">邀请活动</span><span class="recommendation-jilv">邀请记录</span></p>
<a href="/recNow"><div class="recommendation-button">邀请好友</div></a>
  
</div>
	<input type="hidden" value="我正在领钱儿领现金红包，你也来看看吧！" id="shareTitle">
	<input type="hidden" value="赶快与好友一起瓜分福利，最高可获得1666元现金红包！" id="shareDescr">
	<input type="hidden" value="http://static.lingmoney.cn/lingqian/2018/9a38e139-5d7d-4c8b-862b-f7a96fb21e4d.png" id="shareImgUrl">
	<input type="hidden" value="" id="shareUrl">
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