<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
    <title>梦想</title>
    <link rel="stylesheet" href="/resource/css/dream.css">
    <script type="text/javascript" src="/resource/js/deram.js"></script>
</head>
<body style="background:#f0f0f0">

<div class="main">
    <input id="token" type="hidden" value="${token}">

    <div class="Dream-banner"><img src="/resource/images/drembaner.png"></div>
    
    <!-- 数据列表div -->
    <div class="Dream"></div>

    <div class="Dream-look"><a href="/dream/dreamAdd?token=${token}&dreamid=12">随便看看</a></div>
    <p class="Dream-tips">来领钱儿<br>
从今天开始，梦想，化繁为简！</p>
 
  
</div>

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
<script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script>
<!-- <script type="text/javascript" src="/resource/js/flexible.js"></script> -->
<script type="text/javascript" src="/resource/js/iscroll.js"></script>
<script type="text/javascript" src="/resource/js/navbarscroll.js"></script>
<script type="text/javascript">

$(function(){
	setTimeout(function(){ $('.wrapper').navbarscroll();},200)
   
    
    queryDreamInfo();
    
  });

</script>
</body>
</html>