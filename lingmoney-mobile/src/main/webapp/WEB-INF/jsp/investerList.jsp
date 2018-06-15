<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name = "format-detection" content = "telephone=no">
<!-- <meta name="viewport" content="width=device-width,initial-scale=1"> -->
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<title>投资人列表</title>
<!-- <meta charset="utf-8" /> -->
<!-- <meta name="viewport" content="width=device-width,initial-scale=1" /> -->
<!-- <script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script> -->
<link rel="stylesheet" href="/resource/css/project.css">
<script src="/resource/js/jquery-1.8.3.min.js"></script>
</head>
<body style="background:#f9f9f9">

<input id="pid" value="${pId}" type="hidden" >
<div class="main">

<div class="records">
	<ul class="records-box">
		<li>投资用户</li>
		<li>投资时间</li>
		<li>投资金额</li>
	</ul>
	
	<ul class="records-list">
		<!-- <li><span>投资用户</span><span>投资时间</span><span>投资金额</span></li>
		<li><span>投资用户</span><span>投资时间</span><span>投资金额</span></li>
		<li><span>投资用户</span><span>投资时间</span><span>投资金额</span></li> -->
	   
	</ul>
	
	
</div>
</div>

<script type="text/javascript">
$(document).ready(function () {  
	$.ajax({
		url:'/product/tradingRecordListVersionOne?proId=' + $('#pid').val(),
	    type:'get',
	    success: function(data) {
	       var html="",nohtml=""
	    	if(data.code == 200) {
	    		 $.each(data.rows, function(i, item){ 
	    			 html+='<li><span>'+item.name+'</span><span>'+item.date+'</span><span>'+item.money+'</span></li>'
	    		 })
	    		 $('.records-list').html(html) 
	    		
	    	} else {
	    		nohtml+=' <div class="no-data"><p>暂无数据哦~</p><img src="/resource/images/no-data.png"/></div>'
	    		$('.records-list').html(nohtml) 
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