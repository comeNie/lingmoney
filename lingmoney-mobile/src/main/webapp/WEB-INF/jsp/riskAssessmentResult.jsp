<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>测评结果</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1" />
	<link rel="stylesheet" href="/resource/css/assessment.css">
    <script src="/resource/js/jquery-1.8.3.min.js"></script>
	<script src="/resource/js/assessment.js"></script>
</head>
<body style="background:#f9f9f9">
	<input id="score" value="${score}" type="hidden">
	<input id="token" value="${token}" type="hidden">
	<div class="main">

<div class="result-tip">您的风险测评结果为</div>
<div class="result-img"></div>

<p class="result-chengdu">希望本金安全，极低的风险容忍程度</p>

<div class="result-btn resultbut">重新测试</div>

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
</body>
</html>