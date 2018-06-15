<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>梦想</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<link rel="icon" type="image/x-icon" href="/resource/images/ico.ico">
<link rel="stylesheet" href="/resource/css/index.css">
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<style type="text/css">
html{
   font-size: 100%;
}
</style>
</head>
<body>
<input id="token" type="hidden" value="${token}">
	<div style="margin-top:3.2rem;text-align: center;">
		<img src="/resource/images/wait.png" style="width:4.37rem"/>
		<p style="font-size:0.52rem;padding-top:0.5rem">敬请期待</p>
		<span style="font-size:0.32rem;color:#868798">产品建设中</span>
	</div>
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