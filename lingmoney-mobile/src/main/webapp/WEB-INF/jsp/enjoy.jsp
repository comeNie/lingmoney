<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>特享</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<meta charset="utf-8">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<link rel="icon" type="image/x-icon" href="/resource/images/ico.ico">
<link rel="stylesheet" href="/resource/css/enjoy.css">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
</head>
<body style="background: #f0f0f0;">
<input id="token" type="hidden" value="${token}">
<div class="main">
	<a href="http://test.lingmoney.cn/activity/activityShow?activityKey=ICBC">
	<div class="enjoy">
		<div><img src="/resource/images/enjoy1.png"/></div>
		<p><span>工行绿色办卡通道</span><span class="enjoy-right">万德众归集团专享福利</span></p>
	
	</div>
	</a>
	<a href="" id="kefutell">
	<div class="enjoy">
		<div><img src="/resource/images/enjoy2.png"/></div>
		<p><span>专属客服</span><span class="enjoy-right">一对一专属贴心服务</span></p>
	<!--  <b class="enjoy-qidai">敬请期待</b> -->
	</div>
	</a>
	<div class="enjoy">
		<div><img src="/resource/images/enjoy3.png"/></div>
		<p><span>专属理财师</span><span class="enjoy-right">一对一管家式专属理财师</span></p>
	<b class="enjoy-qidai">敬请期待</b>
	</div>


</div>
</body>
</html>
<script>

	$(function(){
	    var u = navigator.userAgent;
	    var ua = navigator.userAgent.toLowerCase();
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {   //微信内置浏览器+应用宝链接
	       
	          $('#kefutell').attr('href','http://a1.7x24cc.com/phone_webChat.html?accountId=N000000011412&chatId=1554ce87-f9fe-47c0-b357-ba7098eed4e9')
	           
	      
	    }else{
	        if(isiOS){
	           
	               //跳转到ios下载地址 
	                $('#kefutell').attr('href','http://a1.7x24cc.com/phone_webChat.html?accountId=N000000011412&chatId=01adaca2-f054-450d-a55f-b9d38144dcc6')
	               

	          
	        }else if(isAndroid){
	          
	            	 $('#kefutell').attr('href','http://a1.7x24cc.com/phone_webChat.html?accountId=N000000011412&chatId=1554ce87-f9fe-47c0-b357-ba7098eed4e9')
	               
	           
	        }

	    }
	    
	});

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